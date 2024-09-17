package com.backend.hormonalcare.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Gender(String gender) {
    public Gender() { this(null); }
    public Gender{
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }
        if (!gender.equals("Male") && !gender.equals("Female")) {
            throw new IllegalArgumentException("Gender must be 'Male' or 'Female'");
        }
    }
    public String getGender(){
        return gender;
    }
}
