package com.backend.hormonalcare.medicalRecord.domain.model.commands;

import java.util.Date;

public record UpdatePrescriptionCommand(Long id, Long DoctorId, Long PatientId, Date prescriptionDate, String notes) {
}
