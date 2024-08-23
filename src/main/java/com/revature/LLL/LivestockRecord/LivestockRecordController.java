package com.revature.LLL.LivestockRecord;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Get all livestock record by entry ID
     * @param entryId
     * @return
     */
    @GetMapping("/entry")
    public ResponseEntity<LivestockRecord> getLivestockRecordByEntryId(@Valid @RequestParam int entryId) {
        if (entryId == 0) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(livestockRecordService.findById(entryId));
        }
    }

    /**
     * Get all livestock records for user
     * @param userId
     * @param userType
     * @return
     */
    // localhost:8080/medicalRecord/user?userId=123
    @GetMapping("/user")
    public ResponseEntity<List<LivestockRecord>> getLivestockRecordsByUserId(@Valid @RequestParam int userId, @RequestHeader String userType) {
        if (userType.equals("VET")) {
            return ResponseEntity.ok(livestockRecordService.findAllByVetRecordVetDetailsUserId(userId));
        } else if (userType.equals("OWNER")) {
            return ResponseEntity.ok(livestockRecordService.findAllByPatientIdentificationOwnerInfoUserId(userId));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all livestock record by animal ID
     * @param animalId
     * @return
     */
    @GetMapping("/animal")
    public ResponseEntity<LivestockRecord> getLivestockRecordByAnimalId(@Valid @RequestParam int animalId) {
        if (animalId == 0) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(livestockRecordService.findByPatientIdentificationAnimalId(animalId));
        }
    }



}
