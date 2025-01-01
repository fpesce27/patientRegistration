package com.patientRegistration.PatientRegistration.repository;

import com.patientRegistration.PatientRegistration.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Patient> findByExternalId(String externalId);

    @Override
    default void delete(Patient patient) {
        patient.setDeletedAt(LocalDateTime.now());
        save(patient);
    };
}
