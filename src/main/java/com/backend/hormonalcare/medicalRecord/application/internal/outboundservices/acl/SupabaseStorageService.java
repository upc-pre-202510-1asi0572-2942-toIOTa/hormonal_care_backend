package com.backend.hormonalcare.medicalRecord.application.internal.outboundservices.acl;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        // Redimensionar la imagen a 64x64 píxeles
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(inputStream)
                  .size(64, 64)
                  .outputFormat("jpg") // Convertir a JPG para mayor compresión
                  .toOutputStream(outputStream);

        byte[] resizedImageData = outputStream.toByteArray();

        // Determinar el Content-Type basado en la extensión del archivo
        String contentType = "image/jpeg"; // Forzar el tipo de contenido como imagen JPEG

        RequestBody requestBody = RequestBody.create(resizedImageData, MediaType.parse(contentType));

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


