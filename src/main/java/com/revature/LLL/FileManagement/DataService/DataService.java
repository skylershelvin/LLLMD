package com.revature.LLL.FileManagement.DataService;

import com.revature.LLL.FileManagement.File.UploadedFile;

import java.util.List;

public interface DataService {
    List<UploadedFile> getFileDataList();

    int saveData(List<UploadedFile> uploadedFileDataList);
}
