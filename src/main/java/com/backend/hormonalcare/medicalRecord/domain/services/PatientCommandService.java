package com.backend.hormonalcare.medicalRecord.domain.services;
import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.Patient;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreatePatientCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.UpdatePatientCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.UpdatePatientDoctorIdCommand;

import java.util.Optional;

public interface PatientCommandService {
    Optional<Patient> handle(CreatePatientCommand command);
    Optional<Patient> handle(UpdatePatientCommand command);
    Optional<Patient> handle(UpdatePatientDoctorIdCommand command);
}
