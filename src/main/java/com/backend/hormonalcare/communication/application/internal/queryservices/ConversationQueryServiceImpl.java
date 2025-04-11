package com.backend.hormonalcare.communication.application.internal.queryservices;

import com.backend.hormonalcare.communication.domain.model.aggregates.Conversation;
import com.backend.hormonalcare.communication.domain.model.queries.GetConversationByIdQuery;
import com.backend.hormonalcare.communication.domain.model.queries.GetConversationsByProfileIdQuery;
import com.backend.hormonalcare.communication.domain.services.ConversationQueryService;
import com.backend.hormonalcare.communication.infrastructure.persistence.mongodb.repositories.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationQueryServiceImpl implements ConversationQueryService {
    
    private final ConversationRepository conversationRepository;
    
    public ConversationQueryServiceImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }
    
    @Override
    public Optional<Conversation> handle(GetConversationByIdQuery query) {
        return conversationRepository.findById(query.conversationId());
    }
    
    @Override
    public List<Conversation> handle(GetConversationsByProfileIdQuery query) {
        return conversationRepository.findByParticipantId(query.profileId());
    }
}