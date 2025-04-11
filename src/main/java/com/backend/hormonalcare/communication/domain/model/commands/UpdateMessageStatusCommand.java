package com.backend.hormonalcare.communication.domain.model.commands;

import com.backend.hormonalcare.communication.domain.model.valueobjects.MessageStatus;

public record UpdateMessageStatusCommand(
    String messageId,
    MessageStatus status
) {
}