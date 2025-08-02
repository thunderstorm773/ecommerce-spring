package com.tu.ecommerce.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class CdnUtil {

    private static final String RESOURCE_TYPE_KEY = "resource_type";
    private static final String IMAGE_RESOURCE_TYPE = "image";

    @Value("${CDN_CLOUD_NAME}")
    private String cloudName;

    @Value("${CDN_API_KEY}")
    private String apiKey;

    @Value("${CDN_API_SECRET}")
    private String apiSecret;

    private Cloudinary cloudinary;

    @PostConstruct
    private void configCloudinary() {
        Map configMap = ObjectUtils.asMap(
                "cloud_name", this.cloudName,
                "api_key", this.apiKey,
                "api_secret", this.apiSecret,
                "secure", true
        );

        this.cloudinary = new Cloudinary(configMap);
    }

    @Async
    public CompletableFuture<Map> upload(File file) throws IOException {
        Map fileConfig = ObjectUtils.asMap(
                RESOURCE_TYPE_KEY, IMAGE_RESOURCE_TYPE,
                "folder", "images");
        Map uploadResult = this.cloudinary.uploader().upload(file, fileConfig);
        return CompletableFuture.completedFuture(uploadResult);
    }

    public void deleteResource(String partialUrl) throws Exception {
        this.cloudinary.api().deleteResources(Collections.singletonList(partialUrl),
                ObjectUtils.asMap(RESOURCE_TYPE_KEY, IMAGE_RESOURCE_TYPE));
    }

    public String getResourceBase64(String resourceUrl) throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(resourceUrl))
                    .GET().build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if(response.statusCode() == 200) {
                return Base64.getEncoder().encodeToString(response.body());
            } else {
                throw new IOException("Failed to fetch resource, status: " + response.statusCode());
            }
        }
    }
}
