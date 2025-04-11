package com.backend.hormonalcare.communication.domain.services;

import com.backend.hormonalcare.communication.domain.model.aggregates.Message;
import com.backend.hormonalcare.communication.domain.model.commands.DeleteMessageCommand;
import com.backend.hormonalcare.communication.domain.model.commands.SendMessageCommand;
import com.backend.hormonalcare.communication.domain.model.commands.UpdateMessageStatusCommand;

import java.util.Optional;

public interface MessageCommandService {
    Optional<Message> handle(SendMessageCommand command);
    Optional<Message> handle(UpdateMessageStatusCommand command);
    void handle(DeleteMessageCommand command);
}