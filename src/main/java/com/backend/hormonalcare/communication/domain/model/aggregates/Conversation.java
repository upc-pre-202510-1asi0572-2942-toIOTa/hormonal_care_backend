package com.backend.hormonalcare.communication.domain.model.aggregates;

import com.backend.hormonalcare.communication.domain.model.commands.CreateConversationCommand;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Document(collection = "conversations")
@CompoundIndex(name = "idx_participants", def = "{'participants': 1}")
public class Conversation {
    @Id
    private String id;
    
    private Set<Long> participants;
    
    private String lastMessageContent;
    
    private Date lastMessageDate;
    
    @CreatedDate
    private Date createdAt;
    
    @LastModifiedDate
    private Date updatedAt;
    
    public Conversation() {
        this.participants = new HashSet<>();
    }
    
    public Conversation(CreateConversationCommand command) {
        this.participants = new HashSet<>();
        this.participants.add(command.participant1Id());
        this.participants.add(command.participant2Id());
    }
    
    public void updateLastMessage(String content, Date date) {
        this.lastMessageContent = content;
        this.lastMessageDate = date;
    }
    
    public boolean hasParticipant(Long profileId) {
        return participants.contains(profileId);
    }
}