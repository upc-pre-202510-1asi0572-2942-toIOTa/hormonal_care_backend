package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

public record DoctorResource(
        Long professionalIdentificationNumber,
        String subSpecialty,
        String certification,
        Long appointmentFee,
        Long subscriptionId,
        Long profileId,
        String doctorRecordId
) {
}
