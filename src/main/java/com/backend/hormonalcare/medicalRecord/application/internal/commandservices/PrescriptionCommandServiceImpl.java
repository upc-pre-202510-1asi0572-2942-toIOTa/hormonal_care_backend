package com.backend.hormonalcare.medicalRecord.application.internal.commandservices;

import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreatePrescriptionCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.UpdatePrescriptionCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.entities.Prescription;
import com.backend.hormonalcare.medicalRecord.domain.services.PrescriptionCommandService;
import com.backend.hormonalcare.medicalRecord.infrastructure.persistence.jpa.repositories.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrescriptionCommandServiceImpl implements PrescriptionCommandService {
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionCommandServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public Optional<Prescription> handle(CreatePrescriptionCommand command) {
        var prescription = new Prescription(command);
        prescriptionRepository.save(prescription);
        return Optional.of(prescription);
    }

    @Override
    public Optional<Prescription> handle(UpdatePrescriptionCommand command) {
        var id = command.id();
        if (!prescriptionRepository.existsById(id)) {
            throw new IllegalArgumentException("Prescription with id " + command.id() + "does not exists");
        }
        var result = prescriptionRepository.findById(id);
        var prescriptionToUpdate = result.get();
        try {
            var updatedPrescription = prescriptionRepository.save(prescriptionToUpdate.updateInformation(command));
            return Optional.of(updatedPrescription);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating prescription: " + e.getMessage());
        }

    }
}