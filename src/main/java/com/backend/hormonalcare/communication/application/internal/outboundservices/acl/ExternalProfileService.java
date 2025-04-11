package com.backend.hormonalcare.communication.application.internal.outboundservices.acl;

import com.backend.hormonalcare.profile.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalProfileService {
    private final ProfileContextFacade profilesContextFacade;

    public ExternalProfileService(ProfileContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }
    
    public boolean profileExists(Long profileId) {
        return profilesContextFacade.profileExists(profileId);
    }
    
    public String getProfileName(Long profileId) {
        return profilesContextFacade.getProfileFullName(profileId);
    }
}