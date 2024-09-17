package com.backend.hormonalcare.medicalRecord.interfaces.rest.transform;

import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateDoctorCommand;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.CreateDoctorResource;

public class CreateDoctorCommandFromResourceAssembler {
    public static CreateDoctorCommand toCommandFromResource(CreateDoctorResource resource) {
        return new CreateDoctorCommand(
                resource.firstName(),
                resource.lastName(),
                resource.gender(),
                resource.age(),
                resource.phoneNumber(),
                resource.email(),
                resource.Image(),
                resource.birthday(),
                resource.userId(),
                resource.professionalIdentificationNumber(),
                resource.subSpecialty(),
                resource.certification(),
                resource.appointmentFee(),
                resource.subscriptionId()
        );
    }
}
