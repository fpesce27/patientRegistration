package com.patientRegistration.PatientRegistration.service;

import com.patientRegistration.PatientRegistration.exception.DocumentPhotoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final S3Service s3Service;

    public String uploadDocument(MultipartFile document) {
        try {
            String fileName = UUID.randomUUID() + "_" + document.getOriginalFilename();
            s3Service.uploadFile(fileName, document);
            return s3Service.getFileUrl(fileName);
        } catch (IOException e) {
            throw new DocumentPhotoException("Error uploading document photo");
        }
    }

    public void deleteDocument(String documentUrl) {
        String fileName = documentUrl.substring(documentUrl.lastIndexOf("/") + 1);
        s3Service.deleteFile(fileName);
    }
}
