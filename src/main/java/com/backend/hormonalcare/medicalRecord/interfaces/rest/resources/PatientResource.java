package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

public record PatientResource(
        String typeOfBlood,
        String personalHistory,
        String familyHistory,
        String patientRecordId,
        Long profileId,
        Long doctorId) {
}
