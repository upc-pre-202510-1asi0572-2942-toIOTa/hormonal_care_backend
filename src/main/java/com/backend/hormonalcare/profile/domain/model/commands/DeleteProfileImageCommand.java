package com.backend.hormonalcare.profile.domain.model.commands;

public class DeleteProfileImageCommand {
    private final Long profileId;

    public DeleteProfileImageCommand(Long profileId) {
        this.profileId = profileId;
    }

    public Long getProfileId() {
        return profileId;
    }
}
