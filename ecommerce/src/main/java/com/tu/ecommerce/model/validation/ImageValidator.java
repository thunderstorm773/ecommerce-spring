package com.tu.ecommerce.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {

    private static final long IMAGE_MAX_SIZE_IN_MB = 10;
    private static final int BYTES_IN_MB = 1_048_576;
    private static final String[] ALLOWED_CONTENT_TYPES = {"image/png", "image/jpeg"};

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null) {
            return false;
        }

        long fileSizeInMB = multipartFile.getSize() / BYTES_IN_MB;
        if (fileSizeInMB > IMAGE_MAX_SIZE_IN_MB) {
            return false;
        }

        String fileContentType = multipartFile.getContentType();
        return Arrays.asList(ALLOWED_CONTENT_TYPES).contains(fileContentType);
    }
}
