package com.backend.hormonalcare.communication.domain.services;

import com.backend.hormonalcare.communication.domain.model.aggregates.Conversation;
import com.backend.hormonalcare.communication.domain.model.queries.GetConversationByIdQuery;
import com.backend.hormonalcare.communication.domain.model.queries.GetConversationsByProfileIdQuery;

import java.util.List;
import java.util.Optional;

public interface ConversationQueryService {
    Optional<Conversation> handle(GetConversationByIdQuery query);
    List<Conversation> handle(GetConversationsByProfileIdQuery query);
}