package com.backend.hormonalcare.medicalRecord.domain.model.aggregates;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateMedicalExamCommand;
import com.backend.hormonalcare.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class MedicalExam extends AuditableAbstractAggregateRoot<MedicalExam> {
    private String name;

    @Getter
    @ManyToOne
    @JoinColumn(name = "typeExam_id",referencedColumnName = "id" )
    private TypeExam typeExam;


    @Getter
    @ManyToOne
    @JoinColumn(name = "medicalRecord_id",referencedColumnName = "id" )
    private MedicalRecord medicalRecord;


    public MedicalExam() {
    }
    
    public MedicalExam(String name, Long typeExam, Long medicalRecord) {
        this.name = name;
    }

    public MedicalExam(CreateMedicalExamCommand command, TypeExam typeExam, MedicalRecord medicalRecord) {
        this.name = command.name();
        this.typeExam = typeExam;
        this.medicalRecord = medicalRecord;
    }
    public MedicalExam updateInformation(String name, TypeExam typeExam, MedicalRecord medicalRecord) {
        this.name = name;
        this.typeExam = typeExam;
        this.medicalRecord = medicalRecord;
        return this;
    }
}
