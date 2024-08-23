package com.revature.LLL.FileManagement.FileController;

import com.revature.LLL.FileManagement.DataService.DataService;
import com.revature.LLL.FileManagement.DataServiceImpl.DataServiceImpl;
import com.revature.LLL.FileManagement.Repository.FileRepo;
import com.revature.LLL.FileManagement.UploadingService.UploadingService;
import com.revature.LLL.FileManagement.UploadingServiceImpl.UploadingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.revature.LLL.FileManagement.File.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


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
    public String index(){
        return "uploadPage";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){

        uploadingService.uploadFile(file);

        redirectAttributes.addFlashAttribute("message", "your file has been successfully uploaded '" + file.getOriginalFilename()
        + "' !");
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/saveData")
    public String saveData(Model model){
        List<UploadedFile> uploadedFileDataList = dataService.getFileDataList();
        int noOfRecords = dataService.saveData(uploadedFileDataList);
        model.addAttribute("noOfRecords", noOfRecords);
        return "success";
    }

}
