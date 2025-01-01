package com.patientRegistration.PatientRegistration.service;

import com.patientRegistration.PatientRegistration.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void testSendConfirmationEmail() {
        Patient patient = new Patient();
        patient.setEmail("test@example.com");
        patient.setName("John Doe");

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() -> emailService.notify(patient));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(patient.getEmail());
        message.setSubject("Registration Confirmation");
        message.setText("Dear " + patient.getName() + ",\n\nYour registration was successful.");
        verify(mailSender, times(1)).send(message);
    }
}
