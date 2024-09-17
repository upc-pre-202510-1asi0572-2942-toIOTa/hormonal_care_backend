package com.backend.hormonalcare.medicalRecord.application.internal.commandservices;


import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.TypeExam;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateTypeExamCommand;
import com.backend.hormonalcare.medicalRecord.domain.services.TypeExamCommandService;
import com.backend.hormonalcare.medicalRecord.infrastructure.persistence.jpa.repositories.TypeExamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeExamCommandServiceImpl implements TypeExamCommandService {
    private final TypeExamRepository typeExamRepository;

    public TypeExamCommandServiceImpl(TypeExamRepository typeExamRepository) {
        this.typeExamRepository = typeExamRepository;
    }

    @Override
    public Optional<TypeExam> handle(CreateTypeExamCommand command) {
        var typeExam = new TypeExam(command);
        typeExamRepository.save(typeExam);
        return Optional.of(typeExam);
    }
}


