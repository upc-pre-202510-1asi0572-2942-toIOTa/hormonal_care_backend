package com.backend.hormonalcare.medicalRecord.interfaces.rest.transform;


import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateMedicalExamCommand;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.CreateMedicalExamResource;

public class CreateMedicalExamCommandFromResourceAssembler {
    public static CreateMedicalExamCommand toCommandFromResource(CreateMedicalExamResource resource) {
        return new CreateMedicalExamCommand(
                resource.name(),
                resource.typeExamId(),
                resource.medicalRecordId()
        );
    }
}
