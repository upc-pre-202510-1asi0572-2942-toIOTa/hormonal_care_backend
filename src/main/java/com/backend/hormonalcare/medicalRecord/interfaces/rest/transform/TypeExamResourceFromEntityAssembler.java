package com.backend.hormonalcare.medicalRecord.interfaces.rest.transform;
import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.TypeExam;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.TypeExamResource;

public class TypeExamResourceFromEntityAssembler {
    public static TypeExamResource toResourceFromEntity(TypeExam entity) {
        return new TypeExamResource(
                entity.getName()
        );
    }
}
