package com.backend.hormonalcare.medicalRecord.interfaces.rest.transform;

import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateTypeExamCommand;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.CreateTypeExamResource;

public class CreateTypeExamCommandFromResourceAssembler {
    public static CreateTypeExamCommand toCommandFromResource(CreateTypeExamResource resource) {
        return new CreateTypeExamCommand(
                resource.name()
        );
    }
}

