package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

public record PatientWithProfileResource(
        Long id,
        String fullName,
        String image,
        String gender,
        String phoneNumber,
        String birthday,
        String typeOfBlood,
        String personalHistory,
        String familyHistory,
        Long doctorId,
        Long profileId
) {
}
