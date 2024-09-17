package com.backend.hormonalcare.medicalRecord.domain.model.commands;

public record CreateMedicalExamCommand(String name, Long typeExamId, Long medicalRecordId) {
}
