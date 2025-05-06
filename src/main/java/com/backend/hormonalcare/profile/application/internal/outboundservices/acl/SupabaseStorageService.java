package com.backend.hormonalcare.profile.application.internal.outboundservices.acl;

import okhttp3.*;
import org.springframework.stereotype.Component;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component("profileSupabaseStorageService")
public class SupabaseStorageService {
    private final OkHttpClient client = new OkHttpClient();
    private final SupabaseProperties properties;

    public SupabaseStorageService(SupabaseProperties properties) {
        this.properties = properties;
    }

        public String uploadFile(byte[] fileData, String originalFileName) throws IOException {
            String uniqueFileName = "p/" + UUID.randomUUID().toString().substring(0, 8) + ".jpg";
        
        byte[] imageDataToUpload;
        
        try {
            // Intenta redimensionar la imagen
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            
            Thumbnails.of(inputStream)
                     .size(64, 64)
                     .outputFormat("jpg") // Convertir a JPG para mayor compresión
                     .toOutputStream(outputStream);
                     
            imageDataToUpload = outputStream.toByteArray();
        } catch (Exception e) {
            // Si falla el redimensionamiento, usa la imagen original
            System.out.println("Error al procesar la imagen. Usando imagen original sin redimensionar: " + e.getMessage());
            imageDataToUpload = fileData;
        }
    
        // Determinar el Content-Type
        String contentType = "image/jpeg"; // Tipo predeterminado
        
        RequestBody requestBody = RequestBody.create(imageDataToUpload, MediaType.parse(contentType));
    
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

    public void deleteFile(String filePath) throws IOException {
        Request request = new Request.Builder()
                .url(properties.getUrl() + "/storage/v1/object/" + properties.getBucket() + "/" + filePath)
                .addHeader("apikey", properties.getKey())
                .addHeader("Authorization", "Bearer " + properties.getKey())
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to delete file: " + response);
            }
        }
    }

    public SupabaseProperties getProperties() {
        return this.properties;
    }

}


