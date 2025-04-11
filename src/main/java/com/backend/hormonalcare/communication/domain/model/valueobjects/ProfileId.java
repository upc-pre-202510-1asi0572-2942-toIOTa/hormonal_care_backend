package com.backend.hormonalcare.communication.domain.model.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ProfileId {
    private final Long profileId;

    public ProfileId(Long profileId) {
        this.profileId = profileId;
    }
}