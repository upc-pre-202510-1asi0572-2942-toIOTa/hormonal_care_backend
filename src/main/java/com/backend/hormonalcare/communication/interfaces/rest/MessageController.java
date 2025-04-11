package com.backend.hormonalcare.communication.interfaces.rest;

import com.backend.hormonalcare.communication.domain.model.commands.DeleteMessageCommand;
import com.backend.hormonalcare.communication.domain.model.queries.GetMessageByIdQuery;
import com.backend.hormonalcare.communication.domain.model.queries.GetMessagesByConversationIdQuery;
import com.backend.hormonalcare.communication.domain.services.MessageCommandService;
import com.backend.hormonalcare.communication.domain.services.MessageQueryService;
import com.backend.hormonalcare.communication.interfaces.rest.resources.MessageResource;
import com.backend.hormonalcare.communication.interfaces.rest.resources.SendMessageResource;
import com.backend.hormonalcare.communication.interfaces.rest.resources.UpdateMessageStatusResource;
import com.backend.hormonalcare.communication.interfaces.rest.transform.MessageResourceFromEntityAssembler;
import com.backend.hormonalcare.communication.interfaces.rest.transform.SendMessageCommandFromResourceAssembler;
import com.backend.hormonalcare.communication.interfaces.rest.transform.UpdateMessageStatusCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/communication/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
    
    private final MessageCommandService messageCommandService;
    private final MessageQueryService messageQueryService;
    
    public MessageController(MessageCommandService messageCommandService, MessageQueryService messageQueryService) {
        this.messageCommandService = messageCommandService;
        this.messageQueryService = messageQueryService;
    }
    
    @PostMapping
    public ResponseEntity<MessageResource> sendMessage(@RequestBody SendMessageResource resource) {
        var sendMessageCommand = SendMessageCommandFromResourceAssembler.toCommandFromResource(resource);
        
        try {
            var messageOptional = messageCommandService.handle(sendMessageCommand);
            
            if (messageOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            var messageResource = MessageResourceFromEntityAssembler
                .toResourceFromEntity(messageOptional.get());
                
            return new ResponseEntity<>(messageResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResource> getMessageById(@PathVariable String messageId) {
        var query = new GetMessageByIdQuery(messageId);
        var messageOptional = messageQueryService.handle(query);
        
        if (messageOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var messageResource = MessageResourceFromEntityAssembler
            .toResourceFromEntity(messageOptional.get());
            
        return ResponseEntity.ok(messageResource);
    }
    
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<MessageResource>> getMessagesByConversationId(@PathVariable String conversationId) {
        var query = new GetMessagesByConversationIdQuery(conversationId);
        var messages = messageQueryService.handle(query);
        
        var messageResources = messages.stream()
            .map(MessageResourceFromEntityAssembler::toResourceFromEntity)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(messageResources);
    }
    
    @PutMapping("/{messageId}/status")
    public ResponseEntity<MessageResource> updateMessageStatus(
        @PathVariable String messageId, 
        @RequestBody UpdateMessageStatusResource resource
    ) {
        var updateMessageStatusCommand = UpdateMessageStatusCommandFromResourceAssembler
            .toCommandFromResource(messageId, resource);
            
        var messageOptional = messageCommandService.handle(updateMessageStatusCommand);
        
        if (messageOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var messageResource = MessageResourceFromEntityAssembler
            .toResourceFromEntity(messageOptional.get());
            
        return ResponseEntity.ok(messageResource);
    }
    
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String messageId) {
        messageCommandService.handle(new DeleteMessageCommand(messageId));
        return ResponseEntity.noContent().build();
    }
}