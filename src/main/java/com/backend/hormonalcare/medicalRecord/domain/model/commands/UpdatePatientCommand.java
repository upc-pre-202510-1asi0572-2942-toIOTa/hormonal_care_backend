package com.backend.hormonalcare.medicalRecord.domain.model.commands;

public record UpdatePatientCommand(Long id, String typeOfBlood) {
}
