package com.backend.hormonalcare.communication.interfaces.rest.transform;

import com.backend.hormonalcare.communication.domain.model.aggregates.Conversation;
import com.backend.hormonalcare.communication.interfaces.rest.resources.ConversationResource;

public class ConversationResourceFromEntityAssembler {
    public static ConversationResource toResourceFromEntity(Conversation entity) {
        return new ConversationResource(
            entity.getId(),
            entity.getParticipants(),
            entity.getLastMessageContent(),
            entity.getLastMessageDate(),
            entity.getCreatedAt()
        );
    }
}