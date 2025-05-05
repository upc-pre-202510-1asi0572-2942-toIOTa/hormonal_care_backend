package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

public record DoctorWithProfileResource(
        Long id,
        Long professionalIdentificationNumber,
        String subSpecialty,
        Long profileId,
        String doctorRecordId,
        String fullName,
        String image
) {
}