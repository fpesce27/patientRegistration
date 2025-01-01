package com.patientRegistration.PatientRegistration.model;

public record Address(
        String street,
        String city,
        String state,
        String zipCode
) {
}
