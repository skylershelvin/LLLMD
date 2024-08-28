package com.revature.LLL.FileManagement.Repository;

import com.revature.LLL.FileManagement.File.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<UploadedFile, Long> {
}
