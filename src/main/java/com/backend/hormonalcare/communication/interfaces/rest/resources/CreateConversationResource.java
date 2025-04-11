package com.backend.hormonalcare.communication.interfaces.rest.resources;

public record CreateConversationResource(
    Long participant1Id,
    Long participant2Id
) {
}