package com.patientRegistration.PatientRegistration.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {
    private final S3Service s3Service = mock(S3Service.class);
    private final DocumentService documentService = new DocumentService(s3Service);

    @Test
    void testUploadDocument_success() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test-document.jpg");
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getSize()).thenReturn(1024L);
        when(file.getInputStream()).thenReturn(Mockito.mock(java.io.InputStream.class));

        String expectedFileName = "1234_test-document.jpg";
        when(s3Service.getFileUrl(anyString())).thenReturn("http://s3.amazonaws.com/my-local-bucket/" + expectedFileName);

        String result = documentService.uploadDocument(file);

        verify(s3Service).uploadFile(contains("test-document.jpg"), eq(file));
        assertTrue(result.contains("http://s3.amazonaws.com"));
    }
}
