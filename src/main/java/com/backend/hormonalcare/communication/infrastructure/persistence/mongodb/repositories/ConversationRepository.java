package com.backend.hormonalcare.communication.infrastructure.persistence.mongodb.repositories;

import com.backend.hormonalcare.communication.domain.model.aggregates.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    @Query("{'participants': ?0}")
    List<Conversation> findByParticipantId(Long participantId);
    
    @Query("{'participants': {$all: [?0, ?1]}}")
    Optional<Conversation> findByParticipants(Long participant1Id, Long participant2Id);
}