package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

public record DoctorResource(
        Long professionalIdentificationNumber,
        String subSpecialty,
        Long profileId,
        String doctorRecordId
) {
}
