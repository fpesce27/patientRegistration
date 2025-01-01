package com.patientRegistration.PatientRegistration.service;

import com.patientRegistration.PatientRegistration.dto.PatientDTO;
import com.patientRegistration.PatientRegistration.dto.PatientRegistrationDTO;
import com.patientRegistration.PatientRegistration.exception.PatientNotFoundException;
import com.patientRegistration.PatientRegistration.exception.PatientRegistrationException;
import com.patientRegistration.PatientRegistration.factory.NotificationFactory;
import com.patientRegistration.PatientRegistration.model.NotificationType;
import com.patientRegistration.PatientRegistration.model.Patient;
import com.patientRegistration.PatientRegistration.model.validation.PatientRegisterValidation;
import com.patientRegistration.PatientRegistration.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final List<PatientRegisterValidation> patientRegisterValidations;
    private final NotificationFactory notificationFactory;
    private final DocumentService documentService;

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(PatientDTO::fromEntity).toList();
    }

    public PatientDTO getPatient(String externalId) {
        return PatientDTO.fromEntity(patientRepository.findByExternalId(externalId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with externalId " + externalId + " not found")));
    }

    public void deleteByExternalId(String externalId) {
        Patient patient = patientRepository.findByExternalId(externalId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with externalId " + externalId + " not found"));
        patientRepository.delete(patient);
    }

    public PatientDTO registerPatient(PatientRegistrationDTO registrationDTO) {
        log.debug("Starting patient registration process for {}", registrationDTO.email());
        Patient patient = Patient.fromRegistrationDTO(registrationDTO);

        patientRegisterValidations.forEach(validation -> validation.validate(patient));
        log.info("Patient Registration Validated: {}", patient);

        try {
            String documentUrl = documentService.uploadDocument(registrationDTO.documentPhoto());
            patient.setDocumentPhotoUrl(documentUrl);
            log.info("Patient Document Uploaded to: {}", documentUrl);

            PatientDTO savedPatient = PatientDTO.fromEntity(patientRepository.save(patient));
            notifyCreation(patient);

            return savedPatient;
        } catch (Exception e) {
            log.error("Error during patient registration", e);
            documentService.deleteDocument(patient.getDocumentPhotoUrl());
            throw new PatientRegistrationException("Failed to register patient", e);
        }
    }

    @Async
    public void notifyCreation(Patient patient) {
        NotificationService notificationService = notificationFactory.getNotificationService(NotificationType.EMAIL);
        notificationService.notify(patient);
    }
}