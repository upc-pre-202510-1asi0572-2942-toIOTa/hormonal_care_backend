package com.backend.hormonalcare.communication.infrastructure.persistence.mongodb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.backend.hormonalcare.communication.domain.model.aggregates.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationIdOrderByCreatedAtAsc(String conversationId);
    List<Message> findByConversationIdAndDeletedFalseOrderByCreatedAtAsc(String conversationId);
    
    // Sobrescribir findById para tener en cuenta mensajes eliminados
    default Optional<Message> findByIdAndNotDeleted(String id) {
        return findById(id).filter(message -> !message.isDeleted());
    }
}