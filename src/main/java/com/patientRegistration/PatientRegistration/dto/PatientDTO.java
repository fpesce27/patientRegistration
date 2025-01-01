package com.patientRegistration.PatientRegistration.dto;

import com.patientRegistration.PatientRegistration.model.Address;
import com.patientRegistration.PatientRegistration.model.Patient;

public record PatientDTO(
        String name,
        String email,
        Address address,
        String phoneNumber,
        String documentPhotoUrl,
        String externalId
) {
    public static PatientDTO fromEntity(Patient patient) {
        return new PatientDTO(
                patient.getName(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getPhoneNumber(),
                patient.getDocumentPhotoUrl(),
                patient.getExternalId()
        );
    }
}
