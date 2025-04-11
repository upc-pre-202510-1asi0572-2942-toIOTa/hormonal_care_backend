package com.backend.hormonalcare.communication.application.internal.commandservices;

import com.backend.hormonalcare.communication.domain.model.aggregates.Conversation;
import com.backend.hormonalcare.communication.domain.model.commands.CreateConversationCommand;
import com.backend.hormonalcare.communication.domain.services.ConversationCommandService;
import com.backend.hormonalcare.communication.infrastructure.persistence.mongodb.repositories.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationCommandServiceImpl implements ConversationCommandService {
    
    private final ConversationRepository conversationRepository;
    
    public ConversationCommandServiceImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }
    
    @Override
    public Optional<Conversation> handle(CreateConversationCommand command) {
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