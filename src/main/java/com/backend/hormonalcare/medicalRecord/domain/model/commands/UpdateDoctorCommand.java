package com.backend.hormonalcare.medicalRecord.domain.model.commands;

public record UpdateDoctorCommand(
        Long id,
        Long appointmentFee,
        Long subscriptionId)
{
}
