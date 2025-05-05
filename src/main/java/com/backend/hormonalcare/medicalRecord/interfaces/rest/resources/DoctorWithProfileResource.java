package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

public record DoctorWithProfileResource(
        Long id,
        String fullName,
        String image,
        String gender, // Added gender attribute
        String phoneNumber, // Added phoneNumber attribute
        String birthday,
        Long professionalIdentificationNumber,
        String subSpecialty,
        Long profileId,
        String doctorRecordId // Added birthday attribute
) {
}