package com.revature.LLL.LivestockMedicalRecord;

import org.springframework.stereotype.Service;

@Service
public class LivestockMedicalRecordService {
    private final LivestockMedicalRecordRepository livestockMedicalRecordRepository;

    public LivestockMedicalRecordService(LivestockMedicalRecordRepository livestockMedicalRecordRepository){
        this.livestockMedicalRecordRepository = livestockMedicalRecordRepository;
    }
}
