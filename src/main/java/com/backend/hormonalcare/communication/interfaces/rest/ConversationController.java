package com.backend.hormonalcare.communication.interfaces.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.hormonalcare.communication.application.internal.enrichers.ConversationEnricherService;
import com.backend.hormonalcare.communication.domain.model.queries.GetConversationByIdQuery;
import com.backend.hormonalcare.communication.domain.model.queries.GetConversationsByProfileIdQuery;
import com.backend.hormonalcare.communication.domain.services.ConversationCommandService;
import com.backend.hormonalcare.communication.domain.services.ConversationQueryService;
import com.backend.hormonalcare.communication.interfaces.rest.resources.CreateConversationResource;
import com.backend.hormonalcare.communication.interfaces.rest.resources.EnrichedConversationResource;
import com.backend.hormonalcare.communication.interfaces.rest.transform.CreateConversationCommandFromResourceAssembler;

@RestController
@RequestMapping(value = "/api/v1/communication/conversations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConversationController {
    
    private final ConversationCommandService conversationCommandService;
    private final ConversationQueryService conversationQueryService;
    private final ConversationEnricherService conversationEnricherService;
    
    public ConversationController(
        ConversationCommandService conversationCommandService, 
        ConversationQueryService conversationQueryService,
        ConversationEnricherService conversationEnricherService) {
        this.conversationCommandService = conversationCommandService;
        this.conversationQueryService = conversationQueryService;
        this.conversationEnricherService = conversationEnricherService;
    }
    
    @PostMapping
    public ResponseEntity<EnrichedConversationResource> createConversation(@RequestBody CreateConversationResource resource) {
        var createConversationCommand = CreateConversationCommandFromResourceAssembler.toCommandFromResource(resource);
        
        try {
            var conversationOptional = conversationCommandService.handle(createConversationCommand);
            
            if (conversationOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            var enrichedConversation = conversationEnricherService.enrichConversation(conversationOptional.get());
                
            return new ResponseEntity<>(enrichedConversation, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{conversationId}")
    public ResponseEntity<EnrichedConversationResource> getConversationById(@PathVariable String conversationId) {
        var query = new GetConversationByIdQuery(conversationId);
        var conversationOptional = conversationQueryService.handle(query);
        
        if (conversationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var enrichedConversation = conversationEnricherService.enrichConversation(conversationOptional.get());
            
        return ResponseEntity.ok(enrichedConversation);
    }
    
    @GetMapping("/user/{profileId}")
    public ResponseEntity<List<EnrichedConversationResource>> getConversationsByProfileId(@PathVariable Long profileId) {
        var query = new GetConversationsByProfileIdQuery(profileId);
        var conversations = conversationQueryService.handle(query);
        
        var enrichedConversations = conversationEnricherService.enrichConversations(conversations);
            
        return ResponseEntity.ok(enrichedConversations);
    }
}