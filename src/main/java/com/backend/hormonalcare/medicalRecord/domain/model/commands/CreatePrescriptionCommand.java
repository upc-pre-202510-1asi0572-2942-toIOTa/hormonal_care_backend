package com.backend.hormonalcare.medicalRecord.domain.model.commands;

import java.util.Date;

public record CreatePrescriptionCommand(Long DoctorId, Long PatientId, Date prescriptionDate, String notes) {
}
