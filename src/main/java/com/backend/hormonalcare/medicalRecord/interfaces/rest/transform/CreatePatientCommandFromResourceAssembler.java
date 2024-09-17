package com.backend.hormonalcare.medicalRecord.interfaces.rest.transform;

import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreatePatientCommand;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.CreatePatientResource;

public class CreatePatientCommandFromResourceAssembler {
    public static CreatePatientCommand toCommandFromResource(CreatePatientResource resource){
        return new CreatePatientCommand(
                resource.firstName(),
                resource.lastName(),
                resource.gender(),
                resource.age(),
                resource.phoneNumber(),
                resource.email(),
                resource.Image(),
                resource.birthday(),
                resource.userId(),
                resource.typeofblood(),
                resource.doctorId()
        );
    }
}
