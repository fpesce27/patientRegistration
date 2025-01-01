package com.patientRegistration.PatientRegistration.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class S3ServiceTest {
    private final AmazonS3 amazonS3 = mock(AmazonS3.class);
    private final S3Service s3Service = new S3Service(amazonS3);
    private final String BUCKET_NAME = null; // as the spring ctx is not loaded, the value is null

    @Test
    void testUploadFile_success() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test-document.jpg");
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getSize()).thenReturn(1024L);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));

        s3Service.uploadFile("test-document.jpg", file);

        verify(amazonS3).putObject(eq(BUCKET_NAME), eq("test-document.jpg"), any(InputStream.class), any(ObjectMetadata.class));
    }

    @Test
    void testGetFileUrl() throws MalformedURLException {
        String expectedUrl = "http://s3.amazonaws.com/my-local-bucket/test-document.jpg";
        when(amazonS3.getUrl(BUCKET_NAME, "test-document.jpg")).thenReturn(java.net.URI.create(expectedUrl).toURL());

        String result = s3Service.getFileUrl("test-document.jpg");

        assertEquals(expectedUrl, result);
        verify(amazonS3).getUrl(BUCKET_NAME, "test-document.jpg");
    }
}
