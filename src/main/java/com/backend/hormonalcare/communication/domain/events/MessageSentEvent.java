package com.backend.hormonalcare.communication.domain.events;

public class MessageSentEvent {
    private final String messageId;
    private final Long senderId;
    private final Long recipientId;
    private final String conversationId;

    public MessageSentEvent(String messageId, Long senderId, Long recipientId, String conversationId) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.conversationId = conversationId;
    }

    public String getMessageId() {
        return messageId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public String getConversationId() {
        return conversationId;
    }
}