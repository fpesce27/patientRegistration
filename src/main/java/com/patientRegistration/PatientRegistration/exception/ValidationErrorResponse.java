package com.patientRegistration.PatientRegistration.exception;

import java.util.List;

public record ValidationErrorResponse(String message, List<String> errors) {
}
