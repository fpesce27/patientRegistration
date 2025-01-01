package com.patientRegistration.PatientRegistration.model.validation;

import com.patientRegistration.PatientRegistration.exception.PhoneNumberAlreadyExistsException;
import com.patientRegistration.PatientRegistration.model.Patient;
import com.patientRegistration.PatientRegistration.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberValidation implements PatientRegisterValidation {
    private final PatientRepository patientRepository;

    @Override
    public void validate(Patient patient) {
        if (patientRepository.existsByPhoneNumber(patient.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException();
        }
    }
}
