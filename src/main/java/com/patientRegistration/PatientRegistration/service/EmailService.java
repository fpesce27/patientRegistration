package com.patientRegistration.PatientRegistration.service;

import com.patientRegistration.PatientRegistration.model.NotificationType;
import com.patientRegistration.PatientRegistration.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService implements NotificationService {
    private final JavaMailSender mailSender;

    @Async
    public void notify(Patient patient) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(patient.getEmail());
        message.setSubject("Registration Confirmation");
        message.setText("Dear " + patient.getName() + ",\n\nYour registration was successful.");
        mailSender.send(message);
        log.info("Email sent to: {}", patient.getEmail());
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.EMAIL;
    }
}
