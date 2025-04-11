package com.backend.hormonalcare.communication.application.internal.eventhandlers;

import com.backend.hormonalcare.communication.domain.events.MessageSentEvent;
import com.backend.hormonalcare.notification.domain.model.commands.CreateNotificationCommand;
import com.backend.hormonalcare.notification.domain.model.valueobjects.State;
import com.backend.hormonalcare.notification.domain.services.NotificationCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationCreatorOnMessageSent {
    
    private final NotificationCommandService notificationCommandService;
    
    public NotificationCreatorOnMessageSent(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }
    
    @EventListener
    public void handle(MessageSentEvent event) {
        var createNotificationCommand = new CreateNotificationCommand(
            "New Message",
            "You have received a new message",
            State.UNREAD,
            event.getRecipientId()
        );
        
        notificationCommandService.handle(createNotificationCommand);
    }
}