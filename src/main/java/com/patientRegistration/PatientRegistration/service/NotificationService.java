package com.patientRegistration.PatientRegistration.service;

import com.patientRegistration.PatientRegistration.model.NotificationType;
import com.patientRegistration.PatientRegistration.model.Patient;

public interface NotificationService {
    void notify(Patient patient);
    NotificationType getNotificationType();
}
