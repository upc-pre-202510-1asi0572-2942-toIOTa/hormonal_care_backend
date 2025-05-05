package com.backend.hormonalcare.medicalRecord.interfaces.rest;

import com.backend.hormonalcare.medicalRecord.domain.model.commands.UpdateDoctorCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetAllDoctorsQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetAllMedicalAppointmentQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetDoctorByDoctorRecordIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetDoctorByIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetDoctorByProfileIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetProfileIdByDoctorIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.valueobjects.DoctorRecordId;
import com.backend.hormonalcare.medicalRecord.domain.model.valueobjects.ProfileId;
import com.backend.hormonalcare.medicalRecord.domain.services.DoctorCommandService;
import com.backend.hormonalcare.medicalRecord.domain.services.DoctorQueryService;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.CreateDoctorResource;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.DoctorResource;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.DoctorWithProfileResource;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.MedicalAppointmentResource;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.UpdateDoctorResource;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.transform.CreateDoctorCommandFromResourceAssembler;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.transform.DoctorResourceFromEntityAssembler;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.transform.DoctorWithProfileResourceFromEntityAssembler;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.transform.MedicalAppointmentResourceFromEntityAssembler;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.transform.UpdateDoctorCommandFromResourceAssembler;
import com.backend.hormonalcare.profile.interfaces.acl.ProfileDetails;
import com.backend.hormonalcare.medicalRecord.application.internal.outboundservices.acl.ExternalProfileService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/doctor/doctor", produces = MediaType.APPLICATION_JSON_VALUE)
public class DoctorController {
    private final DoctorCommandService doctorCommandService;
    private final DoctorQueryService doctorQueryService;
    private final ExternalProfileService externalProfileService;

    public DoctorController(DoctorCommandService doctorCommandService, DoctorQueryService doctorQueryService, ExternalProfileService externalProfileService) {
        this.doctorCommandService = doctorCommandService;
        this.doctorQueryService = doctorQueryService;
        this.externalProfileService = externalProfileService;
    }

    @PostMapping
    public ResponseEntity<DoctorResource> createDoctor(@RequestBody CreateDoctorResource resource){
        var createDoctorCommand = CreateDoctorCommandFromResourceAssembler.toCommandFromResource(resource);
        var optionalDoctor = doctorCommandService.handle(createDoctorCommand);
        if (optionalDoctor.isPresent()) {
            var doctor = optionalDoctor.get();
            if (doctor.getDoctorRecordId().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var getDoctorByDoctorRecordIdQuery = new GetDoctorByDoctorRecordIdQuery(new DoctorRecordId(doctor.getDoctorRecordId()));
            var doctorQueryResult = doctorQueryService.handle(getDoctorByDoctorRecordIdQuery);
            if (doctorQueryResult.isEmpty()) return ResponseEntity.badRequest().build();
            var doctorResource = DoctorResourceFromEntityAssembler.toResourceFromEntity(doctorQueryResult.get());
            return new ResponseEntity<>(doctorResource, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{doctorId}/profile-id")
    public ResponseEntity<Long> getProfileIdByDoctorId(@PathVariable Long doctorId) {
        var getProfileIdByDoctorIdQuery = new GetProfileIdByDoctorIdQuery(doctorId);
        var profileId = doctorQueryService.handle(getProfileIdByDoctorIdQuery);
        if (profileId.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(profileId.get());
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<DoctorResource> getDoctorByProfileId(@PathVariable Long profileId) {
        var getDoctorByProfileIdQuery = new GetDoctorByProfileIdQuery(new ProfileId(profileId));
        var doctor = doctorQueryService.handle(getDoctorByProfileIdQuery);
        if (doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var doctorResource = DoctorResourceFromEntityAssembler.toResourceFromEntity(doctor.get());
        return ResponseEntity.ok(doctorResource);
    }

    @GetMapping("/record/{doctorRecordId}")
    public ResponseEntity<DoctorResource> getDoctorByDoctorRecordId(@PathVariable String doctorRecordId) {
        var DoctorRecordId = new DoctorRecordId(doctorRecordId);
        var getDoctorByDoctorRecordIdQuery = new GetDoctorByDoctorRecordIdQuery(DoctorRecordId);
        var doctor = doctorQueryService.handle(getDoctorByDoctorRecordIdQuery);
        if (doctor.isEmpty()) {return ResponseEntity.notFound().build();}
        var doctorResource = DoctorResourceFromEntityAssembler.toResourceFromEntity(doctor.get());
        return ResponseEntity.ok(doctorResource);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorWithProfileResource> getDoctorById(@PathVariable Long doctorId) {
        var getDoctorByIdQuery = new GetDoctorByIdQuery(doctorId);
        var doctorOptional = doctorQueryService.handle(getDoctorByIdQuery);

        if (doctorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var doctor = doctorOptional.get();
        var profileDetailsOptional = externalProfileService.fetchProfileDetails(doctor.getProfileId());
        var profileDetails = profileDetailsOptional.orElse(null);

        var doctorWithProfileResource = DoctorWithProfileResourceFromEntityAssembler.toResourceFromEntity(doctor, profileDetails);
        return ResponseEntity.ok(doctorWithProfileResource);
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResource> updateDoctor(@PathVariable Long doctorId, @RequestBody UpdateDoctorResource updateDoctorResource){
        var updateDoctorCommand = UpdateDoctorCommandFromResourceAssembler.toCommandFromResource(doctorId, updateDoctorResource);
        var updatedDoctor = doctorCommandService.handle(updateDoctorCommand);
        if(updatedDoctor.isEmpty()) return ResponseEntity.notFound().build();
        var doctorResource = DoctorResourceFromEntityAssembler.toResourceFromEntity(updatedDoctor.get());
        return ResponseEntity.ok(doctorResource);
    }
    @GetMapping
    public ResponseEntity<List<DoctorResource>> getAllDoctors(){
        var doctor = doctorQueryService.handle(new GetAllDoctorsQuery());
        var doctorResources = doctor.stream()
                .map(DoctorResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(doctorResources);
    }
}