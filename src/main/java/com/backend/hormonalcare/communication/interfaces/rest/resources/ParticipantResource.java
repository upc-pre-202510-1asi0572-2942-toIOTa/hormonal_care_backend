package com.backend.hormonalcare.communication.interfaces.rest.resources;

public record ParticipantResource(
    Long id,
    String fullName,
    String imageUrl
) {
}