package com.backend.hormonalcare.communication.application.internal.commandservices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.hormonalcare.communication.application.internal.outboundservices.acl.CommunicationExternalProfileService;
import com.backend.hormonalcare.communication.domain.model.aggregates.Conversation;
import com.backend.hormonalcare.communication.domain.model.commands.CreateConversationCommand;
import com.backend.hormonalcare.communication.domain.services.ConversationCommandService;
import com.backend.hormonalcare.communication.infrastructure.persistence.mongodb.repositories.ConversationRepository;

@Service
public class ConversationCommandServiceImpl implements ConversationCommandService {
    
    private final ConversationRepository conversationRepository;
    private final CommunicationExternalProfileService communicationExternalProfileService;
    
    public ConversationCommandServiceImpl(
        ConversationRepository conversationRepository,
        CommunicationExternalProfileService communicationExternalProfileService) {
        this.conversationRepository = conversationRepository;
        this.communicationExternalProfileService = communicationExternalProfileService;
    }
    
    @Override
    public Optional<Conversation> handle(CreateConversationCommand command) {
        // Validate that both participants exist
        boolean participant1Exists = communicationExternalProfileService.profileExists(command.participant1Id());
        boolean participant2Exists = communicationExternalProfileService.profileExists(command.participant2Id());
        
        if (!participant1Exists || !participant2Exists) {
            throw new IllegalArgumentException("One or both participants do not exist");
        }
        
        // Check if conversation already exists
        Optional<Conversation> existingConversation = conversationRepository.findByParticipants(
            command.participant1Id(), 
            command.participant2Id()
        );
        
        if (existingConversation.isPresent()) {
            return existingConversation;
        }
        
        // Create new conversation
        Conversation conversation = new Conversation(command);
        conversationRepository.save(conversation);
        
        return Optional.of(conversation);
    }
}