package com.backend.hormonalcare.profile.domain.model.queries;

import com.backend.hormonalcare.profile.domain.model.valueobjects.Email;

public record GetProfileByEmailQuery(Email email) {
}
