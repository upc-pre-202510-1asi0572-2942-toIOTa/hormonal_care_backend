package com.backend.hormonalcare.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Age(Integer age) {
    public Age() { this(null); }
    public Age {
        if (age == null) {
            throw new IllegalArgumentException("Age cannot be null");
        }
        if (age < 18) {
            throw new IllegalArgumentException("Age must be greater than 18");
        }
    }


}