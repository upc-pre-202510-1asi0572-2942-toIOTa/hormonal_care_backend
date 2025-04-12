package com.backend.hormonalcare.communication.application.internal.enrichers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.hormonalcare.communication.application.internal.outboundservices.acl.CommunicationExternalProfileService;
import com.backend.hormonalcare.communication.domain.model.aggregates.Message;
import com.backend.hormonalcare.communication.interfaces.rest.resources.EnrichedMessageResource;
import com.backend.hormonalcare.communication.interfaces.rest.resources.ParticipantResource;

@Service
public class MessageEnricherService {

    private final CommunicationExternalProfileService profileService;
    
    public MessageEnricherService(CommunicationExternalProfileService profileService) {
        this.profileService = profileService;
    }
    
    public EnrichedMessageResource enrichMessage(Message message) {
        // Obtener información del remitente
        var senderInfo = new ParticipantResource(
            message.getSenderId(),
            profileService.getProfileName(message.getSenderId()),
            profileService.getProfileImage(message.getSenderId())
        );
        
        // Obtener información del destinatario
        var recipientInfo = new ParticipantResource(
            message.getRecipientId(),
            profileService.getProfileName(message.getRecipientId()),
            profileService.getProfileImage(message.getRecipientId())
        );
            
        return new EnrichedMessageResource(
            message.getId(),
            message.getConversationId(),
            senderInfo,
            recipientInfo,
            message.getContent(),
            message.getStatus(),
            message.getCreatedAt()
        );
    }
    
    public List<EnrichedMessageResource> enrichMessages(List<Message> messages) {
        return messages.stream()
            .map(this::enrichMessage)
            .collect(Collectors.toList());
    }
}