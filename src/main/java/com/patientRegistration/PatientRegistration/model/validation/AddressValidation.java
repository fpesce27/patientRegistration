package com.patientRegistration.PatientRegistration.model.validation;

import com.patientRegistration.PatientRegistration.exception.InvalidAddressException;
import com.patientRegistration.PatientRegistration.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class AddressValidation implements PatientRegisterValidation {
    @Override
    public void validate(Patient patient) {
        if (patient.getAddress().street() == null || patient.getAddress().street().isBlank()) {
            throw new InvalidAddressException("Street is required");
        }

        if (patient.getAddress().city() == null || patient.getAddress().city().isBlank()) {
            throw new InvalidAddressException("City is required");
        }

        if (patient.getAddress().state() == null || patient.getAddress().state().isBlank()) {
            throw new InvalidAddressException("State is required");
        }

        if (!isValidZipCode(patient.getAddress().zipCode())) {
            throw new InvalidAddressException("Invalid zip code for the provided state");
        }
    }

    private boolean isValidZipCode(String zipCode) {
        return zipCode != null && zipCode.matches("\\d{5}");
    }
}
