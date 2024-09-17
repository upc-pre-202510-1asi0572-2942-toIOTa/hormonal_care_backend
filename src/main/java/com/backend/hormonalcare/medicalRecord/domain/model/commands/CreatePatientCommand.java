package com.backend.hormonalcare.medicalRecord.domain.model.commands;

import java.util.Date;

public record CreatePatientCommand(
        String firstName,
        String lastName,
        String gender,
        Integer age,
        String phoneNumber,
        String email,
        String Image,
        Date birthday,
        Long userId,
        String typeofblood,
        Long doctorId) {
}
