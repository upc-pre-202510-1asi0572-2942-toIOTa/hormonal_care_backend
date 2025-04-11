package com.backend.hormonalcare.communication.interfaces.rest.resources;

import com.backend.hormonalcare.communication.domain.model.valueobjects.MessageStatus;

import java.util.Date;

public record MessageResource(
    String id,
    String conversationId,
    Long senderId,
    Long recipientId,
    String content,
    MessageStatus status,
    Date createdAt
) {
}