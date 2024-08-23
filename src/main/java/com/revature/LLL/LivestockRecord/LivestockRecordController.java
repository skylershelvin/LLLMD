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
import java.util.Optional;

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

    // get livestock record by entryId
    @GetMapping("/entry")
    public ResponseEntity<LivestockRecord> getLivestockRecord(@Valid @RequestParam int entryId) {
        if (entryId == 0) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(livestockRecordService.findById(entryId));
        }
    }

    @PatchMapping("/symptoms")
    public ResponseEntity<LivestockRecord> updateSymptoms(@Valid @RequestBody String[] symptoms, @RequestHeader String userType, @RequestParam int entryId) {
        if(!userType.equals("VET")) throw new UnauthorizedException("You must be a vet to add symptoms");

        // check if entry in livestock table exists
        Optional<LivestockRecord> optionalLivestockRecord = Optional.ofNullable(livestockRecordService.findById(entryId));
        if(optionalLivestockRecord.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // get current condition json
        LivestockRecord livestockRecord = optionalLivestockRecord.get();
        CurrentCondition condition = livestockRecord.getCondition();

        // make new condition if none exists for the livestock record
        if(condition == null) {
            condition = new CurrentCondition();
        }

        // update condition json in livestock object and update the database record
        condition.setSymptoms(symptoms);
        livestockRecord.setCondition(condition);
        livestockRecordService.updateSymptoms(livestockRecord);

        return ResponseEntity.ok(livestockRecord);
    }
}
