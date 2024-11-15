package com.backend.hormonalcare.notification.domain.services;

import com.backend.hormonalcare.notification.domain.model.aggregates.Notification;
import com.backend.hormonalcare.notification.domain.model.queries.GetAllNotificationsByRecipientIdQuery;

import java.util.List;

public interface NotificationQueryService {
    List<Notification> handle(GetAllNotificationsByRecipientIdQuery query);
}
