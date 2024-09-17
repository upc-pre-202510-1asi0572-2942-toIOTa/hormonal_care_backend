package com.backend.hormonalcare.medicalRecord.domain.model.commands;

public record UpdateMedicalExamCommand(Long id, String name, Long typeExamId, Long medicalRecordId) {
}
