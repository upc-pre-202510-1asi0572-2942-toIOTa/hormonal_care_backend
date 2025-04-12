package com.backend.hormonalcare.communication.application.internal.enrichers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.hormonalcare.communication.application.internal.outboundservices.acl.CommunicationExternalProfileService;
import com.backend.hormonalcare.communication.domain.model.aggregates.Conversation;
import com.backend.hormonalcare.communication.interfaces.rest.resources.EnrichedConversationResource;
import com.backend.hormonalcare.communication.interfaces.rest.resources.ParticipantResource;

@Service
public class ConversationEnricherService {

    private final CommunicationExternalProfileService profileService;
    
    public ConversationEnricherService(CommunicationExternalProfileService profileService) {
        this.profileService = profileService;
    }
    
    public EnrichedConversationResource enrichConversation(Conversation conversation) {
        List<ParticipantResource> enrichedParticipants = conversation.getParticipants().stream()
            .map(profileId -> new ParticipantResource(
                profileId,
                profileService.getProfileName(profileId),
                profileService.getProfileImage(profileId)
            ))
            .collect(Collectors.toList());
            
        return new EnrichedConversationResource(
            conversation.getId(),
            enrichedParticipants,
            conversation.getLastMessageContent(),
            conversation.getLastMessageDate(),
            conversation.getCreatedAt()
        );
    }
    
    public List<EnrichedConversationResource> enrichConversations(List<Conversation> conversations) {
        return conversations.stream()
            .map(this::enrichConversation)
            .collect(Collectors.toList());
    }
}