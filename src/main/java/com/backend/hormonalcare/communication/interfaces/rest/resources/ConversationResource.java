package com.backend.hormonalcare.communication.interfaces.rest.resources;

import java.util.Date;
import java.util.Set;

public record ConversationResource(
    String id,
    Set<Long> participants,
    String lastMessageContent,
    Date lastMessageDate,
    Date createdAt
) {
}