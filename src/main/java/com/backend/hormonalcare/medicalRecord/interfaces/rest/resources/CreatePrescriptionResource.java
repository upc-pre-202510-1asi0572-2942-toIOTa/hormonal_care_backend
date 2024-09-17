package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

import java.util.Date;

public record CreatePrescriptionResource(
        Long doctorId,
        Long patientId,
        Date prescriptionDate,
        String notes) { }

