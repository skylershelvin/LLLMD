package com.revature.LLL.FileManagement.DataServiceImpl;

import com.revature.LLL.FileManagement.DataService.DataService;
import com.revature.LLL.FileManagement.File.UploadedFile;
import com.revature.LLL.FileManagement.Repository.FileRepo;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.access.SecurityConfig.createList;

@Service
public class DataServiceImpl implements DataService {

    @Value("${app.upload.file:${user.home}}")
    public String EXCEL_FILE_PATH;

    @Autowired
    FileRepo fileRepo;

    Workbook workbook;

    @Override
    public List<UploadedFile> getFileDataList() {
        List<String> list = new ArrayList<String>();

        DataFormatter dataFormatter = new DataFormatter();

        try {
            workbook = WorkbookFactory.create(new File(EXCEL_FILE_PATH));
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }

        System.out.println("-------Workbook has '" + workbook.getNumberOfSheets() + "' Sheets-----");

        Sheet sheet = workbook.getSheetAt(0);

        int noOfColumns = sheet.getRow(0).getLastCellNum();
        System.out.println("-------Sheet has '" + noOfColumns + "' columns------");

        for (Row row : sheet) {
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                list.add(cellValue);
            }
        }

        List<UploadedFile> uploadedFileList = createList(list, noOfColumns);

        try {
            workbook.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return uploadedFileList;
    }

    private List<UploadedFile> createList(List<String> list, int noOfColumns) {
        ArrayList<UploadedFile> uploadedFileList = new ArrayList<UploadedFile>();

        int i = noOfColumns;
        do {
            UploadedFile uFile = new UploadedFile();

            uFile.setPreviousIllnesses(list.get(i));
            uFile.setGetPreviousTreatments(list.get(i + 1));
            uFile.setVaccinationHistory(list.get(i + 2));

            uploadedFileList.add(uFile);
            i = i + (noOfColumns);
        } while (i < list.size());
        return uploadedFileList;

    }

    @Override
    public int saveData(List<UploadedFile> uploadedFileDataList) {
        uploadedFileDataList = fileRepo.saveAll(uploadedFileDataList);
        return uploadedFileDataList.size();

    }
}
