package com.revature.LLL.LivestockMedicalRecord;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medicalRecord")
public class LivestockMedicalRecordController {
    private final LivestockMedicalRecordService livestockMedicalRecordService;

    public LivestockMedicalRecordController(LivestockMedicalRecordService livestockMedicalRecordService) {
        this.livestockMedicalRecordService = livestockMedicalRecordService;
    }
}
