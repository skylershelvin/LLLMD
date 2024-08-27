package com.revature.LLL.LivestockRecord;

import com.revature.LLL.util.exceptions.UnauthorizedException;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
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

    @PostMapping("/animal")
    public ResponseEntity<LivestockRecord> createLivestockRecord(@Valid @RequestBody LivestockRecord livestockRecord, @RequestParam String userType) {
        // check if userType is vet
        if(!userType.equals("VET")) throw new UnauthorizedException("You must be a vet to insert a livestock entry");

        System.out.println(livestockRecord);
        // livestock record must have a vet record and patient identification
        if(livestockRecord.getVetRecord() == null || livestockRecord.getPatientIdentification() == null) {
            return ResponseEntity.badRequest().build();
        }

        // fill out new livestock record with values present in the request body
        LivestockRecord newLivestockRecord = new LivestockRecord();
        newLivestockRecord.setPatientIdentification(livestockRecord.getPatientIdentification());
        newLivestockRecord.setVetRecord(livestockRecord.getVetRecord());

        if(livestockRecord.getMedicalHistory() != null) {
            newLivestockRecord.setMedicalHistory(livestockRecord.getMedicalHistory());
        }
        if(livestockRecord.getCondition() != null) {
            newLivestockRecord.setCondition(livestockRecord.getCondition());
        }
        if(livestockRecord.getPlan() != null) {
            newLivestockRecord.setPlan(livestockRecord.getPlan());
        }
        if(livestockRecord.getHealth() != null) {
            newLivestockRecord.setHealth(livestockRecord.getHealth());
        }
        if(livestockRecord.getNotes() != null) {
            newLivestockRecord.setNotes(livestockRecord.getNotes());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(livestockRecordService.create(newLivestockRecord));
    }

    @PatchMapping("/symptoms")
    public ResponseEntity<LivestockRecord> updateSymptoms(@Valid @RequestBody String[] symptoms, @RequestParam int animalId) {
        // check if entry in livestock table exists via animal_id
        Optional<LivestockRecord> optionalLivestockRecord = Optional.ofNullable(livestockRecordService.findByPatientIdentificationAnimalId(animalId));
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

    /*
    request body example:
    {
        "previous_illnesses": ["covid", "ebola"],
        "previous_treatments": [{
            "medications_prescribed": ["tylenol", "ibuprofen"]
        }],
        "vaccination_history": ["moderna", "pfizer"]
    }
     */
    @PatchMapping("/medicalHistory")
    public ResponseEntity<LivestockRecord> updateMedicalHistory(@Valid @RequestBody MedicalHistory medicalHistory, @RequestParam int animalId, @RequestHeader String userType){
        if(!userType.equals("VET")) throw new UnauthorizedException("You must be a vet to add symptoms");

        // check if entry in livestock table exists
        Optional<LivestockRecord> optionalLivestockRecord = Optional.ofNullable(livestockRecordService.findByPatientIdentificationAnimalId(animalId));
        if(optionalLivestockRecord.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LivestockRecord livestockRecord = optionalLivestockRecord.get();
        MedicalHistory currentMedicalHistory = livestockRecord.getMedicalHistory();

        // update medical history json in livestock object and update the database record
        currentMedicalHistory.setPrevious_illnesses(medicalHistory.getPrevious_illnesses());
        currentMedicalHistory.setVaccination_history(medicalHistory.getVaccination_history());
        currentMedicalHistory.setPrevious_treatments(medicalHistory.getPrevious_treatments());

        livestockRecord.setMedicalHistory(currentMedicalHistory);
        livestockRecordService.updateMedicalHistory(livestockRecord);

        return ResponseEntity.ok(livestockRecord);
    }
}
