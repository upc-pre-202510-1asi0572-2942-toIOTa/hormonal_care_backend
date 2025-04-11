package com.backend.hormonalcare.communication.domain.model.commands;

public record SendMessageCommand(
    Long senderId,
    Long recipientId,
    String content,
    String conversationId
) {
}