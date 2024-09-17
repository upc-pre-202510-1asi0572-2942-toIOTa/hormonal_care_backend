package com.backend.hormonalcare.medicalRecord.domain.services;

import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.TypeExam;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateTypeExamCommand;

import java.util.Optional;

public interface TypeExamCommandService {
    Optional<TypeExam> handle(CreateTypeExamCommand command);
}
