package com.backend.hormonalcare.communication.interfaces.rest.transform;

import com.backend.hormonalcare.communication.domain.model.aggregates.Message;
import com.backend.hormonalcare.communication.interfaces.rest.resources.MessageResource;

public class MessageResourceFromEntityAssembler {
    public static MessageResource toResourceFromEntity(Message entity) {
        return new MessageResource(
            entity.getId(),
            entity.getConversationId(),
            entity.getSenderId(),
            entity.getRecipientId(),
            entity.getContent(),
            entity.getStatus(),
            entity.getCreatedAt()
        );
    }
}