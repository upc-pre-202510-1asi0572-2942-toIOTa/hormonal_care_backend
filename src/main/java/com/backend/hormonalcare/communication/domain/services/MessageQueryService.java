package com.backend.hormonalcare.communication.domain.services;

import com.backend.hormonalcare.communication.domain.model.aggregates.Message;
import com.backend.hormonalcare.communication.domain.model.queries.GetMessageByIdQuery;
import com.backend.hormonalcare.communication.domain.model.queries.GetMessagesByConversationIdQuery;

import java.util.List;
import java.util.Optional;

public interface MessageQueryService {
    Optional<Message> handle(GetMessageByIdQuery query);
    List<Message> handle(GetMessagesByConversationIdQuery query);
}