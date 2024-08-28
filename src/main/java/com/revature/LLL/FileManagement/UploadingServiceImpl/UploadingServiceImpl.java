package com.revature.LLL.FileManagement.UploadingServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.revature.LLL.FileManagement.File.UploadedFile;
import com.revature.LLL.FileManagement.UploadingService.UploadingService;

@Service
public class UploadingServiceImpl implements UploadingService {

    private final String uploadDir = "D:/UploadDirectory";

    public List<UploadedFile> fileReaderService() {
        return null;
    }

    @Override
    public void uploadFile(MultipartFile file) {
        try {
            Path dirPath = Paths.get(uploadDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

// Define the path where the file will be saved
            Path copyLocation = dirPath.resolve(StringUtils.cleanPath(file.getOriginalFilename()));

// Copy the file to the defined location
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file" + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }
}
