package com.revature.LLL.FileManagement.DataService;

import com.revature.LLL.FileManagement.File.File;

import java.util.List;

public interface DataService {
    List<File> getFileDataList();

    int saveData(List<File> fileDataList);
}
