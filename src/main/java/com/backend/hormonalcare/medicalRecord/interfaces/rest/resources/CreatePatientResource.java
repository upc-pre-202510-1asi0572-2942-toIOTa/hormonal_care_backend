package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

import java.util.Date;

public record CreatePatientResource(
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
