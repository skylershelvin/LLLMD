package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("medicalRecord")
public class LivestockRecordController {
    private final LivestockRecordService livestockRecordService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    /**
     * Insert a new livestock record
     * example request body:
     * {
     *     "patientIdentification": {
     *         "breed": "labrador",
     *         "age": 12,
     *         "sex": "FEMALE",
     *         "owner_info": {
     *             "userId": 3,
     *             "firstName": "Charles",
     *             "lastName": "Tester",
     *             "email": "charles@mail.com"
     *         }
     *     },
     *     "medicalHistory": {
     *         "previous_illnesses": [],
     *         "previous_treatments": [{
     *             "medications_prescribed": [],
     *             "antibiotics": [],
     *             "treatment_procedures": "Rest, Ice, Compression, Elevation",
     *             "followup_instructions": "Come back in 6 weeks"
     *         }],
     *         "vaccination_history": ["moderna", "pfizer"]
     *     },
     *     "condition": {
     *         "examination_date": "2024-08-24",
     *         "diagnosis": "ACL tear",
     *         "diagnosis_tests": ["Lachman Test", "Anterior Drawer Test"],
     *         "symptoms": ["headache", "fever", "depression"]
     *     },
     *     "plan": {
     *         "medications_prescribed": ["tylenol", "ibuprofen"],
     *         "antibiotics": ["penecillin", "levofloxacin"],
     *         "treatment_procedures": "Rest, Ice, Compression, Elevation",
     *         "followup_instructions": "Come back in 6 weeks"
     *     },
     *     "health": {
     *         "monitoring_schedule": "weekly blood pressure and weight monitoring",
     *         "progress_notes": "patient seems to be in a better mood"
     *     },
     *     "vetRecord": {
     *         "vet_details": {
     *             "userId": 3,
     *             "firstName": "Joe",
     *             "lastName": "Mama",
     *             "email": "joe@mail.com",
     *             "userType": "VET"
     *         },
     *         "record_date": "2024-08-26",
     *         "signature": "JMamas"
     *     },
     *     "notes": {
     *         "environmental_factors": "not much personal space due to crowded barn",
     *         "behavioral_observations": "reserved, easygoing"
     *     }
     * }
     * @param livestockRecord
     * @param userType
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/animal")
    public ResponseEntity<LivestockRecord> createLivestockRecord(@Valid @RequestBody LivestockRecord livestockRecord, @RequestParam String userType) throws JsonProcessingException {
        // check if userType is vet
        if(!userType.equals("VET")) throw new UnauthorizedException("You must be a vet to insert a livestock entry");

        System.out.println(livestockRecord);
        // livestock record must have a vet record and patient identification
        if(livestockRecord.getVetRecord() == null || livestockRecord.getPatientIdentification() == null) {
            return ResponseEntity.badRequest().build();
        }

        // fetch the next animal_id from the sequence created in the database on dbeaver
        int nextAnimalId = jdbcTemplate.queryForObject("SELECT nextval('animal_id_seq')", Integer.class);

        // set the animal_id in the PatientIdentification JSON object
        livestockRecord.getPatientIdentification().setAnimalId(nextAnimalId);

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
