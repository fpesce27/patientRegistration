package com.patientRegistration.PatientRegistration.exception;

public class PhoneNumberAlreadyExistsException extends RuntimeException {
    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }

    public PhoneNumberAlreadyExistsException() {
        super("Phone number already exists");
    }
}
