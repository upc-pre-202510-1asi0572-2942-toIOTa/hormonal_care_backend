package com.backend.hormonalcare.medicalRecord.application.internal.commandservices;

import com.backend.hormonalcare.medicalRecord.application.internal.outboundservices.acl.ExternalProfileService;
import com.backend.hormonalcare.medicalRecord.domain.events.DoctorCreatedEvent;
import com.backend.hormonalcare.medicalRecord.domain.model.aggregates.Doctor;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateDoctorCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.UpdateDoctorCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.valueobjects.ProfessionalIdentificationNumber;
import com.backend.hormonalcare.medicalRecord.domain.model.valueobjects.SubSpecialty;
import com.backend.hormonalcare.medicalRecord.domain.services.DoctorCommandService;
import com.backend.hormonalcare.medicalRecord.infrastructure.persistence.jpa.repositories.DoctorRepository;
import com.backend.hormonalcare.medicalRecord.infrastructure.persistence.jpa.repositories.PatientRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorCommandServiceImpl implements DoctorCommandService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ExternalProfileService externalProfileService;
    private final ApplicationEventPublisher eventPublisher;

    public DoctorCommandServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository, ExternalProfileService externalProfileService, ApplicationEventPublisher eventPublisher) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.externalProfileService = externalProfileService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Doctor> handle(CreateDoctorCommand command) {

        var profileId = externalProfileService.fetchProfileIdByPhoneNumber(command.phoneNumber());
        if (profileId.isEmpty()){
            profileId = externalProfileService.createProfile(
                    command.firstName(),
                    command.lastName(),
                    command.gender(),
                    command.phoneNumber(),
                    command.image(),
                    command.birthday(),
                    command.userId());
        } else{
            doctorRepository.findByProfileId(profileId.get()).ifPresent(doctor -> {
                throw new IllegalArgumentException("Doctor already exists");
            });
            patientRepository.findByProfileId(profileId.get()).ifPresent(patient -> {
                throw new IllegalArgumentException("Patient already exists");
            });
        }
        if (profileId.isEmpty()) throw new IllegalArgumentException("Unable to create profile");

        var doctor = new Doctor(
                new ProfessionalIdentificationNumber(command.professionalIdentificationNumber()),
                new SubSpecialty(command.subSpecialty()),
                profileId.get()
        );
        doctorRepository.save(doctor);
        eventPublisher.publishEvent(new DoctorCreatedEvent(doctor.getId()));
        return Optional.of(doctor);
    }

    @Override
    public Optional<Doctor> handle(UpdateDoctorCommand command) {
        var id = command.id();
        if (!doctorRepository.existsById(id))
            throw new IllegalArgumentException("Doctor with id "+ id +" does not exist");
        var result = doctorRepository.findById(id);
        var doctorToUpdate = result.get();
        try{
            var updatedDoctor = doctorRepository.save(doctorToUpdate.updateInformation(command.appointmentFee(), command.subscriptionId()));
            return Optional.of(updatedDoctor);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating doctor with id "+ id);
        }
    }


}


