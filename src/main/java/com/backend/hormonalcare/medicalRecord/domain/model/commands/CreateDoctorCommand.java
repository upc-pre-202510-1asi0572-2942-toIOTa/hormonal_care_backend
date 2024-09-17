package com.backend.hormonalcare.medicalRecord.domain.model.commands;

import java.util.Date;

public record CreateDoctorCommand(
        String firstName,
        String lastName,
        String gender,
        Integer age,
        String phoneNumber,
        String email,
        String Image,
        Date birthday,
        Long userId,
        Long professionalIdentificationNumber,
        String subSpecialty,
        String certification,
        Long appointmentFee,
        Long subscriptionId
) {
}

