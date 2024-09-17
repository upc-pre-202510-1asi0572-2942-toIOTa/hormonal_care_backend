package com.backend.hormonalcare.medicalRecord.domain.model.aggregates;

import com.backend.hormonalcare.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
public class MedicalRecord extends AuditableAbstractAggregateRoot<MedicalRecord> {

    @Getter
    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    public MedicalRecord() {
        this.patient = new Patient();
    }

    public MedicalRecord(Patient patient) {
        this.patient = patient;
    }

    public Long getPatientId() {
        return patient.getId();
    }
}
