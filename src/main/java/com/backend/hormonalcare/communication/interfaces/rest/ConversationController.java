package com.backend.hormonalcare.communication.interfaces.rest;

import com.backend.hormonalcare.communication.domain.model.queries.GetConversationByIdQuery;
import com.backend.hormonalcare.communication.domain.model.queries.GetConversationsByProfileIdQuery;
import com.backend.hormonalcare.communication.domain.services.ConversationCommandService;
import com.backend.hormonalcare.communication.domain.services.ConversationQueryService;
import com.backend.hormonalcare.communication.interfaces.rest.resources.ConversationResource;
import com.backend.hormonalcare.communication.interfaces.rest.resources.CreateConversationResource;
import com.backend.hormonalcare.communication.interfaces.rest.transform.ConversationResourceFromEntityAssembler;
import com.backend.hormonalcare.communication.interfaces.rest.transform.CreateConversationCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/communication/conversations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConversationController {
    
    private final ConversationCommandService conversationCommandService;
    private final ConversationQueryService conversationQueryService;
    
    public ConversationController(ConversationCommandService conversationCommandService, ConversationQueryService conversationQueryService) {
        this.conversationCommandService = conversationCommandService;
        this.conversationQueryService = conversationQueryService;
    }
    
    @PostMapping
    public ResponseEntity<ConversationResource> createConversation(@RequestBody CreateConversationResource resource) {
        var createConversationCommand = CreateConversationCommandFromResourceAssembler.toCommandFromResource(resource);
        
        try {
            var conversationOptional = conversationCommandService.handle(createConversationCommand);
            
            if (conversationOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            var conversationResource = ConversationResourceFromEntityAssembler
                .toResourceFromEntity(conversationOptional.get());
                
            return new ResponseEntity<>(conversationResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationResource> getConversationById(@PathVariable String conversationId) {
        var query = new GetConversationByIdQuery(conversationId);
        var conversationOptional = conversationQueryService.handle(query);
        
        if (conversationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var conversationResource = ConversationResourceFromEntityAssembler
            .toResourceFromEntity(conversationOptional.get());
            
        return ResponseEntity.ok(conversationResource);
    }
    
    @GetMapping("/user/{profileId}")
    public ResponseEntity<List<ConversationResource>> getConversationsByProfileId(@PathVariable Long profileId) {
        var query = new GetConversationsByProfileIdQuery(profileId);
        var conversations = conversationQueryService.handle(query);
        
        var conversationResources = conversations.stream()
            .map(ConversationResourceFromEntityAssembler::toResourceFromEntity)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(conversationResources);
    }
}