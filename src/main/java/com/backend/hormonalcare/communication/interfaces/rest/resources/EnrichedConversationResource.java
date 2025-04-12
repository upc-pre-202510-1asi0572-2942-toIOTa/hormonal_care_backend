package com.backend.hormonalcare.communication.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

public record EnrichedConversationResource(
    String id,
    List<ParticipantResource> participants,
    String lastMessageContent,
    Date lastMessageDate,
    Date createdAt
) {
}