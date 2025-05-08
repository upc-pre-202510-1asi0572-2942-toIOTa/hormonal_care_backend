package com.backend.hormonalcare.medicalRecord.interfaces.rest.transform;

import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.Patient;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.PatientWithProfileResource;
import com.backend.hormonalcare.profile.interfaces.acl.ProfileDetails;

public class PatientWithProfileResourceFromEntityAssembler {
    public static PatientWithProfileResource toResourceFromEntity(Patient entity, ProfileDetails profileDetails) {
        return new PatientWithProfileResource(
            entity.getId(),
            profileDetails != null ? profileDetails.getFullName() : null,
            profileDetails != null ? profileDetails.getImage() : null,
            profileDetails != null ? profileDetails.getGender() : null,
            profileDetails != null ? profileDetails.getPhoneNumber() : null,
            profileDetails != null ? profileDetails.getBirthday() : null,
            entity.getTypeOfBlood(),
            entity.getPersonalHistory() != null ? entity.getPersonalHistory().personalHistory() : null,
            entity.getFamilyHistory() != null ? entity.getFamilyHistory().familyHistory() : null,
            entity.getDoctor(),
            entity.getProfileId()
        );
    }
}
