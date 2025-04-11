package com.backend.hormonalcare.communication.infrastructure.persistence.mongodb.repositories;

import com.backend.hormonalcare.communication.domain.model.aggregates.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationIdOrderByCreatedAtAsc(String conversationId);
    List<Message> findByConversationIdAndDeletedFalseOrderByCreatedAtAsc(String conversationId);
}