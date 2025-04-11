package com.backend.hormonalcare.communication.domain.services;

import com.backend.hormonalcare.communication.domain.model.aggregates.Conversation;
import com.backend.hormonalcare.communication.domain.model.commands.CreateConversationCommand;

import java.util.Optional;

public interface ConversationCommandService {
    Optional<Conversation> handle(CreateConversationCommand command);
}