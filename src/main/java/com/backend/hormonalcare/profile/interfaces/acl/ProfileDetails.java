package com.backend.hormonalcare.profile.interfaces.acl;

public class ProfileDetails {
    private final Long profileId;
    private final String fullName;
    private final String image;

    public ProfileDetails(Long profileId, String fullName, String image) {
        this.profileId = profileId;
        this.fullName = fullName;
        this.image = image;
    }

    public Long getProfileId() {
        return profileId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getImage() {
        return image;
    }
}
