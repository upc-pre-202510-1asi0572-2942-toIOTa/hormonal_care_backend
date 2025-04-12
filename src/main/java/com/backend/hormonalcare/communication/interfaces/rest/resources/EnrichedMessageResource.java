package com.backend.hormonalcare.communication.interfaces.rest.resources;

import java.util.Date;

import com.backend.hormonalcare.communication.domain.model.valueobjects.MessageStatus;

public record EnrichedMessageResource(
    String id,
    String conversationId,
    ParticipantResource sender,     // Información enriquecida del remitente
    ParticipantResource recipient,  // Información enriquecida del destinatario
    String content,
    MessageStatus status,
    Date createdAt
) {
}