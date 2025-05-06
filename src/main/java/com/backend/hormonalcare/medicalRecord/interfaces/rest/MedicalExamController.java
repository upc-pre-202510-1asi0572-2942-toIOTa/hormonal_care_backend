package com.backend.hormonalcare.medicalRecord.interfaces.rest;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.CreateMedicalExamCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.commands.DeleteMedicalExamCommand;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetMedicalExamByIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.queries.GetMedicalExamByMedicalRecordIdQuery;
import com.backend.hormonalcare.medicalRecord.domain.model.valueobjects.TypeMedicalExam;
import com.backend.hormonalcare.medicalRecord.domain.services.MedicalExamCommandService;
import com.backend.hormonalcare.medicalRecord.domain.services.MedicalExamQueryService;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.CreateMedicalExamResource;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.MedicalExamResource;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.resources.UpdateMedicalExamResource;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.transform.CreateMedicalExamCommandFromResourceAssembler;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.transform.MedicalExamResourceFromEntityAssembler;
import com.backend.hormonalcare.medicalRecord.interfaces.rest.transform.UpdateMedicalExamCommandFromResourceAssembler;
import java.io.IOException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.backend.hormonalcare.medicalRecord.application.internal.outboundservices.acl.SupabaseStorageServiceMedicalExam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/api/v1/medical-record/medical-exam", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalExamController {


    private final MedicalExamCommandService medicalExamCommandService;
    private final MedicalExamQueryService medicalExamQueryService;
    private final SupabaseStorageServiceMedicalExam supabaseStorageService;

    public MedicalExamController(SupabaseStorageServiceMedicalExam supabaseStorageService,MedicalExamCommandService medicalExamCommandService, MedicalExamQueryService medicalExamQueryService) {
        this.supabaseStorageService = supabaseStorageService;
        this.medicalExamCommandService = medicalExamCommandService;
        this.medicalExamQueryService = medicalExamQueryService;
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MedicalExamResource> createMedicalExam(
            @RequestParam("typeMedicalExam") TypeMedicalExam typeMedicalExam,
            @RequestParam("uploadDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate uploadDate,
            @RequestParam("medicalRecordId") Long medicalRecordId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            // Verificar si el archivo está vacío
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            // Check file size limit (2MB)
            final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
            if (file.getSize() > MAX_FILE_SIZE) {
                return ResponseEntity.badRequest().body(null);
            }

            // Subir el archivo y obtener la URL
            String url = supabaseStorageService.uploadFile(file.getBytes(), file.getOriginalFilename());

            // Si la URL es nula o vacía, devolver un error
            if (url == null || url.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            // Crear el comando para crear un examen médico
            var command = new CreateMedicalExamCommand(
                    url,
                    typeMedicalExam,
                    uploadDate,
                    medicalRecordId
            );

            // Ejecutar el comando para crear el examen
            var medicalExam = medicalExamCommandService.handle(command);
            if (medicalExam.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Convertir el examen médico a un recurso y devolver la respuesta
            var resource = MedicalExamResourceFromEntityAssembler.toResourceFromEntity(medicalExam.get());
            return new ResponseEntity<>(resource, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); // O usa un logger
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }



    @GetMapping("/medicalRecordId/{medicalRecordId}")
    public ResponseEntity<List<MedicalExamResource>> getMedicalExamsByMedicalRecordId(@PathVariable Long medicalRecordId) {
        var getMedicalExamsByMedicalRecordIdQuery = new GetMedicalExamByMedicalRecordIdQuery(medicalRecordId);
        var medicalExams = medicalExamQueryService.handle(getMedicalExamsByMedicalRecordIdQuery);
        if (medicalExams.isEmpty()) return ResponseEntity.notFound().build();
        var medicalExamResources = medicalExams.stream()
                .map(MedicalExamResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(medicalExamResources);
    }

    @DeleteMapping("/{medicalExamId}")
    public ResponseEntity<Void> deleteMedicalExam(@PathVariable Long medicalExamId) {
        var deleteMedicalExamCommand = new DeleteMedicalExamCommand(medicalExamId);
        medicalExamCommandService.handle(deleteMedicalExamCommand);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{medicalExamId}")
    public ResponseEntity<MedicalExamResource> getMedicalExamById(@PathVariable Long medicalExamId) {
        var getMedicalExamByIdQuery = new GetMedicalExamByIdQuery(medicalExamId);
        var medicalExam = medicalExamQueryService.handle(getMedicalExamByIdQuery);
        if (medicalExam.isEmpty()) return ResponseEntity.notFound().build();
        var medicalExamResource = MedicalExamResourceFromEntityAssembler.toResourceFromEntity(medicalExam.get());
        return ResponseEntity.ok(medicalExamResource);
    }

    @PutMapping("/{medicalExamId}")
    public ResponseEntity<MedicalExamResource> updateMedicalExam(@PathVariable Long medicalExamId, @RequestBody UpdateMedicalExamResource updateMedicalExamResource) {
        var updateMedicalExamCommand = UpdateMedicalExamCommandFromResourceAssembler.toCommandFromResource(medicalExamId, updateMedicalExamResource);
        var updatedMedicalExam = medicalExamCommandService.handle(updateMedicalExamCommand);
        if (updatedMedicalExam.isEmpty()) return ResponseEntity.badRequest().build();
        var medicalExamResource = MedicalExamResourceFromEntityAssembler.toResourceFromEntity(updatedMedicalExam.get());
        return ResponseEntity.ok(medicalExamResource);
    }
}
