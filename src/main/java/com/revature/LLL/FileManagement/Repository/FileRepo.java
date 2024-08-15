package com.revature.LLL.FileManagement.Repository;

import com.revature.LLL.FileManagement.File.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<File, Long> {
}
