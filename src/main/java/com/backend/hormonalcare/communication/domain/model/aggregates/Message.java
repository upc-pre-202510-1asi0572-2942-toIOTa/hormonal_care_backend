package com.backend.hormonalcare.communication.domain.model.aggregates;

import com.backend.hormonalcare.communication.domain.model.commands.SendMessageCommand;
import com.backend.hormonalcare.communication.domain.model.valueobjects.MessageStatus;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    
    private String conversationId;
    
    private Long senderId;
    
    private Long recipientId;
    
    private String content;
    
    private MessageStatus status;
    
    @CreatedDate
    private Date createdAt;
    
    @LastModifiedDate
    private Date updatedAt;
    
    private boolean deleted;
    
    public Message() {
        this.status = MessageStatus.SENT;
        this.deleted = false;
    }
    
    public Message(SendMessageCommand command, String conversationId) {
        this.conversationId = conversationId;
        this.senderId = command.senderId();
        this.recipientId = command.recipientId();
        this.content = command.content();
        this.status = MessageStatus.SENT;
        this.deleted = false;
    }
    
    public void markAsDelivered() {
        if (this.status == MessageStatus.SENT) {
            this.status = MessageStatus.DELIVERED;
        }
    }
    
    public void markAsRead() {
        if (this.status == MessageStatus.SENT || this.status == MessageStatus.DELIVERED) {
            this.status = MessageStatus.READ;
        }
    }
    
    public void markAsDeleted() {
        this.deleted = true;
    }
}