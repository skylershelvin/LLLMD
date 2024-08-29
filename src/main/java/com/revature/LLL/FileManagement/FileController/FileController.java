package com.revature.LLL.FileManagement.FileController;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.revature.LLL.FileManagement.DataService.DataService;
import com.revature.LLL.FileManagement.File.UploadedFile;
import com.revature.LLL.FileManagement.UploadingService.UploadingService;

@RestController
@RequestMapping("/file")
public class FileController {

    private final UploadingService uploadingService;

    private final DataService dataService;

    public FileController(UploadingService uploadingService, DataService dataService) {
        this.uploadingService = uploadingService;
        this.dataService = dataService;
    }

    //optional since front end will have the uploading page
    //this is for testing purposes
    //TODO remove after testing
    @GetMapping("/")
    public String index() {
        return "uploadPage";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        uploadingService.uploadFile(file);

        redirectAttributes.addFlashAttribute("message", "your file has been successfully uploaded '" + file.getOriginalFilename()
                + "' !");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/saveData")
    public String saveData(Model model) {
        List<UploadedFile> uploadedFileDataList = dataService.getFileDataList();
        int noOfRecords = dataService.saveData(uploadedFileDataList);
        model.addAttribute("noOfRecords", noOfRecords);
        return "success";
    }

}
