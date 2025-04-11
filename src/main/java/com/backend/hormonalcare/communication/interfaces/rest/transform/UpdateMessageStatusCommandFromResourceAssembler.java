package com.backend.hormonalcare.communication.interfaces.rest.transform;

import com.backend.hormonalcare.communication.domain.model.commands.UpdateMessageStatusCommand;
import com.backend.hormonalcare.communication.interfaces.rest.resources.UpdateMessageStatusResource;

public class UpdateMessageStatusCommandFromResourceAssembler {
    public static UpdateMessageStatusCommand toCommandFromResource(String messageId, UpdateMessageStatusResource resource) {
        return new UpdateMessageStatusCommand(
            messageId,
            resource.status()
        );
    }
}