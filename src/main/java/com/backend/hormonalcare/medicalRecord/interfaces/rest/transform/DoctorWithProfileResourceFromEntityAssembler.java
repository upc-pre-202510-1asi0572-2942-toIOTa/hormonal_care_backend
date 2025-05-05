package com.backend.hormonalcare.medicalRecord.interfaces.rest.transform;

import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.Doctor;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.DoctorWithProfileResource;
import com.backend.hormonalcare.profile.interfaces.acl.ProfileDetails;

public class DoctorWithProfileResourceFromEntityAssembler {
    public static DoctorWithProfileResource toResourceFromEntity(Doctor entity, ProfileDetails profileDetails) {
        return new DoctorWithProfileResource(
            entity.getId(),
            profileDetails != null ? profileDetails.getFullName() : null,
            profileDetails != null ? profileDetails.getImage() : null,
            profileDetails != null ? profileDetails.getGender() : null,
            profileDetails != null ? profileDetails.getPhoneNumber() : null,
            profileDetails != null ? profileDetails.getBirthday() : null,
            entity.getProfessionalIdentificationNumber().professionalIdentificationNumber(),
            entity.getSubSpecialty().subSpecialty(),
            entity.getProfileId(),
            entity.getDoctorRecordId()
        );
    }
}
