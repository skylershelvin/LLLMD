package com.revature.LLL.LivestockRecord;

import com.revature.LLL.util.exceptions.UnauthorizedException;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicalRecord")
public class LivestockRecordController {
    private final LivestockRecordService livestockRecordService;

    @Autowired
    public LivestockRecordController(LivestockRecordService livestockRecordService) {
        this.livestockRecordService = livestockRecordService;
    }

    @GetMapping
    public ResponseEntity<List<LivestockRecord>> getLivestockRecords(@Valid @RequestHeader int userId) {
        if (userId == 0) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(livestockRecordService.getLivestockRecords(userId));
        }
    }

    @PostMapping("/symptoms")
    public ResponseEntity<LivestockRecord> postSymptoms(@Valid @RequestBody LivestockRecord livestockRecord, @RequestHeader String userType) {
        if(!userType.equals("EMPLOYEE")) throw new UnauthorizedException("You must be a vet to add symptoms");

        LivestockRecord newLivestockRecord = livestockRecordService.create(livestockRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLivestockRecord);
    }
}
