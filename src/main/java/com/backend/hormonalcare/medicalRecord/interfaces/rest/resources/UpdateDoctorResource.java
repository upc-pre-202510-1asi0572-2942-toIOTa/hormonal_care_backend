package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

public record UpdateDoctorResource(
        Long id,
        Long appointmentFee,
        Long subscriptionId
) {
}
