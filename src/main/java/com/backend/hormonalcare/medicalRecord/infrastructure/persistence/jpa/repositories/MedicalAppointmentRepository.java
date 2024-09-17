package com.backend.hormonalcare.medicalRecord.infrastructure.persistence.jpa.repositories;

import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.MedicalAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Long> {

    List<MedicalAppointment> findByEventDate(LocalDate eventDate);
    List<MedicalAppointment> findByPatientId(Long patientId);
    List<MedicalAppointment> findByEventDateAndStartTime(LocalDate eventDate, String startTime);

}
