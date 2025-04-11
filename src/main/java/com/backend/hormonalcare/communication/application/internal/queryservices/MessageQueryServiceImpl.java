package com.backend.hormonalcare.communication.application.internal.queryservices;

import com.backend.hormonalcare.communication.domain.model.aggregates.Message;
import com.backend.hormonalcare.communication.domain.model.queries.GetMessageByIdQuery;
import com.backend.hormonalcare.communication.domain.model.queries.GetMessagesByConversationIdQuery;
import com.backend.hormonalcare.communication.domain.services.MessageQueryService;
import com.backend.hormonalcare.communication.infrastructure.persistence.mongodb.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageQueryServiceImpl implements MessageQueryService {
    
    private final MessageRepository messageRepository;
    
    public MessageQueryServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    
    @Override
    public Optional<Message> handle(GetMessageByIdQuery query) {
        return messageRepository.findById(query.messageId());
    }
    
    @Override
    public List<Message> handle(GetMessagesByConversationIdQuery query) {
        return messageRepository.findByConversationIdAndDeletedFalseOrderByCreatedAtAsc(query.conversationId());
    }
}