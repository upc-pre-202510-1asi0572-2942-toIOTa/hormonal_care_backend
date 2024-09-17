package com.backend.hormonalcare.medicalRecord.domain.services;

import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.TypeExam;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetTypeExamByIdQuery;

import java.util.Optional;

public interface TypeExamQueryService {
    Optional<TypeExam> handle(GetTypeExamByIdQuery query);
}
