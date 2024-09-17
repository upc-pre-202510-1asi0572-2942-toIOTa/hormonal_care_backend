package com.backend.hormonalcare.medicalRecord.domain.model.aggregates;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateTypeExamCommand;
import com.backend.hormonalcare.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class TypeExam extends AuditableAbstractAggregateRoot<TypeExam> {
    private String name;

    public TypeExam() {
    }
    public TypeExam(String name) {
        this.name = name;
    }
    public TypeExam(CreateTypeExamCommand command) {
        this.name = command.name();
    }
}
