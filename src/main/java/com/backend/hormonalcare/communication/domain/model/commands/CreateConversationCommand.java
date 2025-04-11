package com.backend.hormonalcare.communication.domain.model.commands;

public record CreateConversationCommand(
    Long participant1Id,
    Long participant2Id
) {
}