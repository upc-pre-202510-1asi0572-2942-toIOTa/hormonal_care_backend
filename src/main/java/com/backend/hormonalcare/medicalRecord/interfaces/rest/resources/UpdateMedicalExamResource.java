package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

public record UpdateMedicalExamResource(
        String name,
        Long typeExamId,
        Long medicalRecordId) {}
