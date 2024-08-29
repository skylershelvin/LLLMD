package com.revature.LLL.FileManagement.UploadingService;

import org.springframework.web.multipart.MultipartFile;

public interface UploadingService {

    public void uploadFile(MultipartFile file);
}
