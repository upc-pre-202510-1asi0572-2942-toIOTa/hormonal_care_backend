package com.backend.hormonalcare.communication.interfaces.rest.transform;

import com.backend.hormonalcare.communication.domain.model.commands.SendMessageCommand;
import com.backend.hormonalcare.communication.interfaces.rest.resources.SendMessageResource;

public class SendMessageCommandFromResourceAssembler {
    public static SendMessageCommand toCommandFromResource(SendMessageResource resource) {
        return new SendMessageCommand(
            resource.senderId(),
            resource.recipientId(),
            resource.content(),
            resource.conversationId()
        );
    }
}