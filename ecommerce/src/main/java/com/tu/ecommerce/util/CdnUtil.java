package com.tu.ecommerce.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class CdnUtil {

    private static final String CLOUD_NAME_KEY = "cloud_name";
    private static final String API_KEY = "api_key";
    private static final String API_SECRET_KEY = "api_secret";
    private static final String RESOURCE_TYPE_KEY = "resource_type";
    private static final String FOLDER_KEY = "folder";
    private static final String VIDEO_RESOURCE_TYPE = "video";
    private static final String IMAGES_FOLDER = "images";

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
                CLOUD_NAME_KEY, this.cloudName,
                API_KEY, this.apiKey,
                API_SECRET_KEY, this.apiSecret
        );

        this.cloudinary = new Cloudinary(configMap);
    }

    @Async
    public CompletableFuture<Map> upload(File file) throws IOException {
        Map fileConfig = ObjectUtils.asMap(
                RESOURCE_TYPE_KEY, VIDEO_RESOURCE_TYPE,
                FOLDER_KEY, IMAGES_FOLDER);
        Map uploadResult = this.cloudinary.uploader().upload(file, fileConfig);
        return CompletableFuture.completedFuture(uploadResult);
    }
}
