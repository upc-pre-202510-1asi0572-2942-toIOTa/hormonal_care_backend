package com.backend.hormonalcare.communication.interfaces.rest.resources;

public record SendMessageResource(
    Long senderId,
    Long recipientId,
    String content,
    String conversationId
) {
}