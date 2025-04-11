package com.backend.hormonalcare.communication.interfaces.rest.resources;

import com.backend.hormonalcare.communication.domain.model.valueobjects.MessageStatus;

public record UpdateMessageStatusResource(
    MessageStatus status
) {
}