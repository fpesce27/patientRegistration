package com.patientRegistration.PatientRegistration.dto;

import com.patientRegistration.PatientRegistration.model.NotificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record PatientRegistrationDTO(
        @NotNull
        @Size(min = 3, max = 100)
        String name,

        @NotNull
        @Size(min = 3, max = 100)
        @Email
        String email,

        @NotNull
        String street,

        @NotNull
        String city,

        @NotNull
        String state,

        @NotNull
        String zipCode,

        @NotNull
        @Size(min = 3, max = 15)
        String phoneNumber,

        @NotNull
        MultipartFile documentPhoto,
        NotificationType notificationType
) {
        public PatientRegistrationDTO() {
                this(null, null, null, null, null, null, null, null, NotificationType.EMAIL);
        }
}
