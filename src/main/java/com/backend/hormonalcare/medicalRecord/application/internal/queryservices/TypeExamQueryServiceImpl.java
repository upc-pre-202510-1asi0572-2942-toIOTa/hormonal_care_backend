package com.backend.hormonalcare.medicalRecord.application.internal.queryservices;

import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.TypeExam;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetTypeExamByIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.services.TypeExamQueryService;
import com.backend.hormonalcare.medicalRecord.infrastructure.persistence.jpa.repositories.TypeExamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TypeExamQueryServiceImpl implements TypeExamQueryService {
    private final TypeExamRepository typeExamRepository;

    public TypeExamQueryServiceImpl(TypeExamRepository typeExamRepository) {
        this.typeExamRepository = typeExamRepository;
    }

    @Override
    public Optional<TypeExam> handle(GetTypeExamByIdQuery query) {
        return typeExamRepository.findById(query.id());

    }
}
