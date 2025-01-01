package com.patientRegistration.PatientRegistration.model;

import com.patientRegistration.PatientRegistration.dto.PatientRegistrationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;
import java.util.UUID;

@Setter
@Getter
@Entity
@ToString
@SQLRestriction("deleted_at IS NULL")
public class Patient extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    @Email
    private String email;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 10000)
    private String documentPhotoUrl;

    @Column(nullable = false, unique = true)
    private String externalId;


    public Patient() {
        externalId = UUID.randomUUID().toString();
    }

    public static Patient fromRegistrationDTO(PatientRegistrationDTO patientDTO) {
        Patient patient = new Patient();
        patient.name = patientDTO.name();
        patient.email = patientDTO.email();
        patient.address = new Address(patientDTO.street(), patientDTO.city(), patientDTO.state(), patientDTO.zipCode());
        patient.phoneNumber = patientDTO.phoneNumber();
        return patient;
    }
}
