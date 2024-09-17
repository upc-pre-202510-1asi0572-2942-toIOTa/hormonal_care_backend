package com.backend.hormonalcare.medicalRecord.interfaces.rest.resources;

public record PatientResource(
        String typeofblood, String patientRecordId, Long profileId, Long doctorId) {
}
