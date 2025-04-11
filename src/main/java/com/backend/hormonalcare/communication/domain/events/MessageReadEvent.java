package com.backend.hormonalcare.communication.domain.events;

public class MessageReadEvent {
    private final String messageId;
    private final Long readerId;

    public MessageReadEvent(String messageId, Long readerId) {
        this.messageId = messageId;
        this.readerId = readerId;
    }

    public String getMessageId() {
        return messageId;
    }

    public Long getReaderId() {
        return readerId;
    }
}