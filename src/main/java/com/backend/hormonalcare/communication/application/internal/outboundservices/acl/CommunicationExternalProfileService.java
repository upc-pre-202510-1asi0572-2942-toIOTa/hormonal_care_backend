package com.backend.hormonalcare.communication.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;

import com.backend.hormonalcare.profile.interfaces.acl.ProfileContextFacade;

@Service
public class CommunicationExternalProfileService {
    private final ProfileContextFacade profilesContextFacade;

    public CommunicationExternalProfileService(ProfileContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }
    
    public boolean profileExists(Long profileId) {
        return profilesContextFacade.profileExists(profileId);
    }
    
    public String getProfileName(Long profileId) {
        return profilesContextFacade.getProfileFullName(profileId);
    }
    
    public String getProfileImage(Long profileId) {
        return profilesContextFacade.getProfileImage(profileId);
    }
}