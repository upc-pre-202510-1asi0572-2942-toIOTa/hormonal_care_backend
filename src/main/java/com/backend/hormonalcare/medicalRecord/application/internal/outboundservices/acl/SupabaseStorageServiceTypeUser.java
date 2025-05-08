package com.backend.hormonalcare.medicalRecord.application.internal.outboundservices.acl;

import okhttp3.*;
import org.springframework.stereotype.Component;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.tika.Tika;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.Arrays;

@Component("typeUserSupabaseStorageService")
public class SupabaseStorageServiceTypeUser {
    private final OkHttpClient client = new OkHttpClient();
    private final SupabasePropertiesTypeUser properties;

    public SupabaseStorageServiceTypeUser(SupabasePropertiesTypeUser properties) {
        this.properties = properties;
    }

    public String uploadFile(byte[] fileData, String originalFileName) throws IOException {
        if (fileData == null || originalFileName == null || originalFileName.isEmpty()) {
            return null; // Retorna null si no se proporciona un archivo
        }

        // Validar el tamaño del archivo (2MB máximo)
        final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
        if (fileData.length > MAX_FILE_SIZE) {
            throw new IOException("El archivo es demasiado grande. El tamaño máximo permitido es 2MB.");
        }

        // Validar el formato de la imagen usando Apache Tika
        Tika tika = new Tika();
        String detectedType = tika.detect(fileData);
        if (!detectedType.startsWith("image/")) {
            throw new IOException("El archivo no es una imagen válida.");
        }

        // Validar la extensión del archivo
        String fileExtension = FilenameUtils.getExtension(originalFileName).toLowerCase();
        if (!fileExtension.equals("jpg") && !fileExtension.equals("jpeg") && !fileExtension.equals("png")) {
            throw new IOException("Formato de archivo no soportado: " + fileExtension);
        }

        String uniqueFileName = "profile-images/" + UUID.randomUUID().toString().substring(0, 8) + ".jpg";

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
                throw new IOException("Error al subir el archivo: " + response);
            }
        }

        return properties.getUrl() + "/storage/v1/object/public/" + properties.getBucket() + "/" + uniqueFileName;
    }

    public void deleteFile(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

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

    public SupabasePropertiesTypeUser getProperties() {
        return this.properties;
    }

}




