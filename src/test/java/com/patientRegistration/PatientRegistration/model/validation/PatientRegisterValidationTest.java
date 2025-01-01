package com.patientRegistration.PatientRegistration.model.validation;

import com.patientRegistration.PatientRegistration.exception.EmailAlreadyExistsException;
import com.patientRegistration.PatientRegistration.exception.InvalidAddressException;
import com.patientRegistration.PatientRegistration.exception.PhoneNumberAlreadyExistsException;
import com.patientRegistration.PatientRegistration.model.Address;
import com.patientRegistration.PatientRegistration.model.Patient;
import com.patientRegistration.PatientRegistration.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class PatientRegisterValidationTest {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PhoneNumberValidation phoneNumberValidation;
    @Autowired
    private PatientEmailValidation patientEmailValidation;
    @Autowired
    private AddressValidation addressValidation;
    @Autowired
    private List<PatientRegisterValidation> patientRegisterValidations;

    @Test
    void testNotValidPatient_exitsByPhoneNumber() {
        createPatient(); // Create a patient with phone number "1234567890"
        Patient patient = createPatient(); // Create a patient with phone number "1234567890"
        patient.setAddress(null);
        patient.setEmail("test2@gmail.com");
        Assertions.assertThrows(PhoneNumberAlreadyExistsException.class, () -> {
            phoneNumberValidation.validate(patient);
        });
    }

    @Test
    void testNotValidPatient_exitsByEmail() {
        createPatient(); // Create a patient with email
        Patient patient = createPatient(); // Create a patient with email
        patient.setAddress(null);
        patient.setPhoneNumber("1234567891");
        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> {
            patientEmailValidation.validate(patient);
        });
    }

    @Test
    void testNotValidPatient_addressIsInvalid() {
        Patient patient = createPatient();
        patient.setAddress(new Address("Test2", "Test2", "Test2", "1234"));
        Assertions.assertThrows(InvalidAddressException.class, () -> {
            addressValidation.validate(patient);
        });
    }

    @Test
    void testValidPatient() {
        createPatient(); // Create a patient
        Patient patient = new Patient();
        patient.setEmail("test2@test.com");
        patient.setPhoneNumber("1234567891");
        patient.setAddress(new Address("Test", "Test", "Test", "12345"));
        Assertions.assertDoesNotThrow(() -> {
            patientRegisterValidations.forEach(validation -> validation.validate(patient));
        });
    }

    private Patient createPatient() {
        Patient patient = new Patient();
        patient.setPhoneNumber("1234567890");
        patient.setEmail("test@test.com");
        patient.setName("Test");
        patient.setAddress(new Address("Test", "Test", "Test", "12345"));
        patient.setDocumentPhotoUrl("http://test.com");
        patientRepository.save(patient);
        return patient;
    }
}
