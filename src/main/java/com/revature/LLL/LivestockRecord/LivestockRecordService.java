package com.revature.LLL.LivestockRecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivestockRecordService {
    private final LivestockRecordRepository livestockRecordRepository;

    public LivestockRecordService(LivestockRecordRepository livestockRecordRepository){
        this.livestockRecordRepository = livestockRecordRepository;
    }

    public List<LivestockRecord> getLivestockRecords(int userId) {
        return livestockRecordRepository.findAllByOwner_UserId(userId);
    }
}
