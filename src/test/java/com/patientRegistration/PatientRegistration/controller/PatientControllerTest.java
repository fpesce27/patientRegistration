package com.patientRegistration.PatientRegistration.controller;

import com.patientRegistration.PatientRegistration.dto.PatientDTO;
import com.patientRegistration.PatientRegistration.model.Address;
import com.patientRegistration.PatientRegistration.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Test
    void registerPatient_ValidInput_ReturnsOk() throws Exception {
        Address address = new Address("Street", "City", "12345", "State");

        PatientDTO responseDTO = new PatientDTO(
                "John Doe",
                "john@example.com",
                address,
                "1234567890",
                "http://s3.amazonaws.com/my-local-bucket/1234_test-document.jpg",
                "uuid"
        );

        MockMultipartFile documentPhoto = new MockMultipartFile(
                "documentPhoto",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        when(patientService.registerPatient(any())).thenReturn(responseDTO);

        mockMvc.perform(multipart("/api/patient/register")
                        .file(documentPhoto)
                        .param("name", "John Doe")
                        .param("email", "john@example.com")
                        .param("street", "Street")
                        .param("city", "City")
                        .param("zipCode", "12345")
                        .param("state", "State")
                        .param("phoneNumber", "1234567890")
                        .param("notificationType", "EMAIL")
                        .contentType("multipart/form-data"))
                .andExpect(status().isOk());
    }

    @Test
    void registerPatient_MissingPhoto_ReturnsBadRequest() throws Exception {
        mockMvc.perform(multipart("/api/patient/register")
                        .param("name", "John Doe")
                        .param("email", "john@example.com")
                        .param("street", "Street")
                        .param("city", "City")
                        .param("zipCode", "12345")
                        .param("state", "State")
                        .param("phoneNumber", "1234567890")
                        .param("notificationType", "EMAIL")
                        .contentType("multipart/form-data"))
                .andExpect(status().isBadRequest());
    }
}
