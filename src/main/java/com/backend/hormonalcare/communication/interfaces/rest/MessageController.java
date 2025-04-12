package com.backend.hormonalcare.communication.interfaces.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.hormonalcare.communication.application.internal.enrichers.MessageEnricherService;
import com.backend.hormonalcare.communication.domain.model.commands.DeleteMessageCommand;
import com.backend.hormonalcare.communication.domain.model.queries.GetMessageByIdQuery;
import com.backend.hormonalcare.communication.domain.model.queries.GetMessagesByConversationIdQuery;
import com.backend.hormonalcare.communication.domain.services.MessageCommandService;
import com.backend.hormonalcare.communication.domain.services.MessageQueryService;
import com.backend.hormonalcare.communication.interfaces.rest.resources.EnrichedMessageResource;
import com.backend.hormonalcare.communication.interfaces.rest.resources.SendMessageResource;
import com.backend.hormonalcare.communication.interfaces.rest.resources.UpdateMessageStatusResource;
import com.backend.hormonalcare.communication.interfaces.rest.transform.SendMessageCommandFromResourceAssembler;
import com.backend.hormonalcare.communication.interfaces.rest.transform.UpdateMessageStatusCommandFromResourceAssembler;

@RestController
@RequestMapping(value = "/api/v1/communication/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
    
    private final MessageCommandService messageCommandService;
    private final MessageQueryService messageQueryService;
    private final MessageEnricherService messageEnricherService;
    
    public MessageController(
        MessageCommandService messageCommandService, 
        MessageQueryService messageQueryService,
        MessageEnricherService messageEnricherService) {
        this.messageCommandService = messageCommandService;
        this.messageQueryService = messageQueryService;
        this.messageEnricherService = messageEnricherService;
    }
    
    @PostMapping
    public ResponseEntity<EnrichedMessageResource> sendMessage(@RequestBody SendMessageResource resource) {
        var sendMessageCommand = SendMessageCommandFromResourceAssembler.toCommandFromResource(resource);
        
        try {
            var messageOptional = messageCommandService.handle(sendMessageCommand);
            
            if (messageOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            var enrichedMessage = messageEnricherService.enrichMessage(messageOptional.get());
                
            return new ResponseEntity<>(enrichedMessage, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{messageId}")
    public ResponseEntity<EnrichedMessageResource> getMessageById(@PathVariable String messageId) {
        var query = new GetMessageByIdQuery(messageId);
        var messageOptional = messageQueryService.handle(query);
        
        if (messageOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var enrichedMessage = messageEnricherService.enrichMessage(messageOptional.get());
            
        return ResponseEntity.ok(enrichedMessage);
    }
    
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<EnrichedMessageResource>> getMessagesByConversationId(@PathVariable String conversationId) {
        var query = new GetMessagesByConversationIdQuery(conversationId);
        var messages = messageQueryService.handle(query);
        
        var enrichedMessages = messageEnricherService.enrichMessages(messages);
            
        return ResponseEntity.ok(enrichedMessages);
    }
    
    @PutMapping("/{messageId}/status")
    public ResponseEntity<EnrichedMessageResource> updateMessageStatus(
        @PathVariable String messageId, 
        @RequestBody UpdateMessageStatusResource resource
    ) {
        var updateMessageStatusCommand = UpdateMessageStatusCommandFromResourceAssembler
            .toCommandFromResource(messageId, resource);
            
        var messageOptional = messageCommandService.handle(updateMessageStatusCommand);
        
        if (messageOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var enrichedMessage = messageEnricherService.enrichMessage(messageOptional.get());
            
        return ResponseEntity.ok(enrichedMessage);
    }
    
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String messageId) {
        messageCommandService.handle(new DeleteMessageCommand(messageId));
        return ResponseEntity.noContent().build();
    }
}