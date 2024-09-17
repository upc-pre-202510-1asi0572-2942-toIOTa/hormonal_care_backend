package com.backend.hormonalcare.medicalRecord.domain.model.aggregates;

import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateDoctorCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.valueobjects.*;
import com.backend.hormonalcare.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;



@Getter
@Entity
public class Doctor extends AuditableAbstractAggregateRoot<Doctor> {

    @Embedded
    private ProfessionalIdentificationNumber professionalIdentificationNumber;

    @Embedded
    private SubSpecialty subSpecialty;

    @Embedded
    private Certification certification;

    private Long appointmentFee;

    @Embedded
    private CodeDoctor codeDoctor;

    private Long subscriptionId;

    @Embedded
    private ProfileId profileId;

    @Embedded
    @Column(name = "doctorRecord_id")
    private  DoctorRecordId doctorRecordId;

    // Constructor vacío
    public Doctor() {
        
    }

    // Constructor con parámetros primitivos
    public Doctor(Long professionalIdentificationNumber, String subSpecialty, String certification, Long appointmentFee, Long subscriptionId, Long profileId) {
        this.professionalIdentificationNumber = new ProfessionalIdentificationNumber(professionalIdentificationNumber);
        this.subSpecialty = new SubSpecialty(subSpecialty);
        this.certification = new Certification(certification);
        this.appointmentFee = appointmentFee;
        this.subscriptionId = subscriptionId;
        this.codeDoctor = new CodeDoctor(generateCodeDoctor());
        this.profileId = new ProfileId(profileId);
        this.doctorRecordId = new DoctorRecordId();
    }

    // Constructor con parámetros de tipo objeto
    public Doctor(ProfessionalIdentificationNumber professionalIdentificationNumber, SubSpecialty subSpecialty, Certification certification, Long appointmentFee, Long subscriptionId, ProfileId profileId) {
        this.professionalIdentificationNumber = professionalIdentificationNumber;
        this.subSpecialty = subSpecialty;
        this.certification = certification;
        this.appointmentFee = appointmentFee;
        this.subscriptionId = subscriptionId;
        this.codeDoctor = new CodeDoctor(generateCodeDoctor());
        this.profileId = profileId;
        this.doctorRecordId = new DoctorRecordId();
    }

    // Constructor con comando y perfil
    public Doctor(CreateDoctorCommand command, ProfileId profileId){
        this.professionalIdentificationNumber = new ProfessionalIdentificationNumber(command.professionalIdentificationNumber());
        this.subSpecialty = new SubSpecialty(command.subSpecialty());
        this.certification = new Certification(command.certification());
        this.appointmentFee = command.appointmentFee();
        this.subscriptionId = command.subscriptionId();
        this.codeDoctor = new CodeDoctor(generateCodeDoctor());
        this.profileId = profileId;
        this.doctorRecordId = new DoctorRecordId();
    }


    public Doctor updateInformation(Long appointmentFee, Long subscriptionId) {
        this.appointmentFee = appointmentFee;
        this.subscriptionId = subscriptionId;
        return this;
    }

    private String generateCodeDoctor(){
        return "D" + System.currentTimeMillis();
    }

    public Long getProfileId() {
        return this.profileId.profileId();
    }

    public  String getDoctorRecordId() {
        return this.doctorRecordId.doctorRecordId();
    }


}
