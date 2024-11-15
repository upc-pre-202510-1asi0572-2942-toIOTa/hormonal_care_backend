package com.backend.hormonalcare.notification.domain.services;

import com.backend.hormonalcare.notification.domain.model.aggregates.Notification;
import com.backend.hormonalcare.notification.domain.model.commands.CreateNotificationCommand;
import com.backend.hormonalcare.notification.domain.model.commands.DeleteNotificationCommand;

import java.util.Optional;

public interface NotificationCommandService {
    Optional<Notification> handle(CreateNotificationCommand command);
    void handle(DeleteNotificationCommand command);
}
