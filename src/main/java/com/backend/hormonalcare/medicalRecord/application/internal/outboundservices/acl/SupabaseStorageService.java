package com.backend.hormonalcare.medicalRecord.application.internal.outboundservices.acl;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class SupabaseStorageService {
    private final OkHttpClient client = new OkHttpClient();
    private final SupabaseProperties properties;

    public SupabaseStorageService(SupabaseProperties properties) {
        this.properties = properties;
    }

    public String uploadFile(byte[] fileData, String originalFileName) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "-" + originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        RequestBody requestBody = RequestBody.create(fileData, MediaType.parse("application/octet-stream"));

        Request request = new Request.Builder()
                .url(properties.getUrl() + "/storage/v1/object/" + properties.getBucket() + "/" + uniqueFileName)
                .addHeader("apikey", properties.getKey())
                .addHeader("Authorization", "Bearer " + properties.getKey())
                .addHeader("Content-Type", "application/octet-stream")
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


