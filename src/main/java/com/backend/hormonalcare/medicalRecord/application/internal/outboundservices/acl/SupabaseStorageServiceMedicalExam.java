package com.backend.hormonalcare.medicalRecord.application.internal.outboundservices.acl;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Component("medicalRecordSupabaseStorageService")
public class SupabaseStorageServiceMedicalExam {
    private final OkHttpClient client = new OkHttpClient();
    private final SupabasePropertiesMedicalExam properties;

    public SupabaseStorageServiceMedicalExam(SupabasePropertiesMedicalExam properties) {
        this.properties = properties;
    }

    public String uploadFile(byte[] fileData, String originalFileName) throws IOException {
        // Check file size limit (2MB)
        final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
        if (fileData.length > MAX_FILE_SIZE) {
            throw new IOException("File is too large. Maximum allowed size is 2MB.");
        }

        // Ensure the file is stored in the "medical-exam" folder
        String uniqueFileName = "medical-exam/" + UUID.randomUUID() + "-" + originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        // Determine the Content-Type based on the file extension
        String contentType;
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();
        switch (fileExtension) {
            case "jpg":
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "pdf":
                contentType = "application/pdf";
                break;
            case "doc":
                contentType = "application/msword";
                break;
            case "docx":
                contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                break;
            case "ppt":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "pptx":
                contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
                break;
            case "xls":
                contentType = "application/vnd.ms-excel";
                break;
            case "xlsx":
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                break;
            default:
                throw new IOException("Unsupported file type: " + fileExtension);
        }

        RequestBody requestBody = RequestBody.create(fileData, MediaType.parse(contentType));

        Request request = new Request.Builder()
                .url(properties.getUrl() + "/storage/v1/object/" + properties.getBucket() + "/" + uniqueFileName)
                .addHeader("apikey", properties.getKey())
                .addHeader("Authorization", "Bearer " + properties.getKey())
                .put(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to upload file: " + response);
            }
        }

        return properties.getUrl() + "/storage/v1/object/public/" + properties.getBucket() + "/" + uniqueFileName;
    }

}


