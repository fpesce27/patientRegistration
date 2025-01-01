package com.patientRegistration.PatientRegistration.controller;

import com.patientRegistration.PatientRegistration.dto.PatientDTO;
import com.patientRegistration.PatientRegistration.dto.PatientRegistrationDTO;
import com.patientRegistration.PatientRegistration.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable String externalId) {
        return ResponseEntity.ok(patientService.getPatient(externalId));
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> deletePatient(@PathVariable String externalId) {
        patientService.deleteByExternalId(externalId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PatientDTO> registerPatient(@Valid @ModelAttribute PatientRegistrationDTO patientRegistrationDTO) {
        return ResponseEntity.ok(patientService.registerPatient(patientRegistrationDTO));
    }
}