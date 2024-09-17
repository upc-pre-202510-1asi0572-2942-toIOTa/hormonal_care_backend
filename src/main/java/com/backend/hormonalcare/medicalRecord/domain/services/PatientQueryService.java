package com.backend.hormonalcare.medicalRecord.domain.services;
import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.Patient;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetPatientByIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetPatientByPatientRecordIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetPatientByProfileIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetProfileIdByPatientIdQuery;

import java.util.Optional;

public interface PatientQueryService {
    Optional<Patient> handle(GetPatientByIdQuery query);
    Optional<Patient> handle(GetPatientByProfileIdQuery query);
    Optional<Patient> handle(GetPatientByPatientRecordIdQuery query);
    Optional<Long> handle(GetProfileIdByPatientIdQuery query);
}
