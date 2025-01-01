package com.patientRegistration.PatientRegistration.exception;

import com.patientRegistration.PatientRegistration.model.NotificationType;

public class UnsupportedNotificationTypeException extends RuntimeException {
    public UnsupportedNotificationTypeException(NotificationType type) {
        super("Unsupported notification type: " + type);
    }
}
