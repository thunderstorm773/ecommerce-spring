package com.tu.ecommerce.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class FileUtil {

    public File saveImage(MultipartFile tempFile) throws IOException {
        byte[] fileBytes = tempFile.getBytes();
        String fileName = tempFile.getOriginalFilename();
        return this.createFile(fileBytes, fileName);
    }

    private File createFile(byte[] fileContent, String fileName) throws IOException {
        this.createTempFilesFolder();

        // Use when upload file from Microsoft Edge or Internet Explorer,
        // because that browsers send path to file as a file name, not just a name
        if (fileName.contains(File.separator)) {
            fileName = new File(fileName).getName();
        }

        String fileFullPath = Constants.TEMP_FILES_PATH + File.separator + fileName;
        File file = new File(fileFullPath);
        file.createNewFile();
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(fileContent);
        }

        return file;
    }

    private void createTempFilesFolder() {
        File tempFolder = new File(Constants.TEMP_FILES_PATH);
        tempFolder.mkdir();
    }
}
