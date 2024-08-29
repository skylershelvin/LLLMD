package com.revature.LLL.LivestockRecord;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.LLL.util.exceptions.UnauthorizedException;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/medicalRecord")
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
     *
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
     *
     * @param userId
     * @return
     */
    // localhost:8080/medicalRecord/user?userId=3
    @GetMapping("/user")
    public ResponseEntity<List<LivestockRecord>> getLivestockRecordsByUserId(@Valid @RequestParam int userId) {
        if (userId == 0) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(livestockRecordService.findAllByPatientIdentificationOwnerInfoUserId(userId));
        }
    }

    /**
     * Get all livestock record by animal ID
     *
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
     *     "notes": {
     *         "environmental_factors": "not much personal space due to crowded barn",
     *         "behavioral_observations": "reserved, easygoing"
     *     }
     * }

     * @param livestockRecord
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/animal")
    public ResponseEntity<LivestockRecord> createLivestockRecord(@Valid @RequestBody LivestockRecord livestockRecord) throws JsonProcessingException {
        // livestock record must have a patient identification
        if(livestockRecord.getPatientIdentification() == null) {

            return ResponseEntity.badRequest().build();
        }

        // fetch the next animal_id from the sequence created in the database on dbeaver
        int nextAnimalId = jdbcTemplate.queryForObject("SELECT nextval('animal_id_seq')", Integer.class);

        // set the animal_id in the PatientIdentification JSON object
        livestockRecord.getPatientIdentification().setAnimalId(nextAnimalId);

        return ResponseEntity.status(HttpStatus.CREATED).body(livestockRecordService.create(livestockRecord));
    }
    /**
     * Update a livestock record. Does not update the owner_info field of the PatientIdentification JSON object, instead
     * it uses the existing owner_info of the given animal_id when updating the record.
     * example request body:
     * {
     *     "patientIdentification": {
     *         "animal_id": 20,
     *         "breed": "labrador",
     *         "age": 12,
     *         "sex": "MALE"
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
     *         "medications_prescribed": ["ibuprofen"],
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
     *             "firstName": "Moe",
     *             "lastName": "Jama",
     *             "email": "joe@mail.com"
     *         },
     *         "record_date": "2024-08-28",
     *         "signature": "JMamas"
     *     },
     *     "notes": {
     *         "environmental_factors": "not much personal space due to crowded barn",
     *         "behavioral_observations": "reserved, easygoing"
     *     }
     * }
     * @param livestockRecord
     * @return
     * @throws JsonProcessingException
     */
    @PatchMapping("/animal")
    public ResponseEntity<LivestockRecord> updateLivestockRecord(@Valid @RequestBody LivestockRecord livestockRecord) throws JsonProcessingException {

        // livestock record must have a patient identification and existing animal_id
        if(livestockRecord.getPatientIdentification() == null || livestockRecord.getPatientIdentification().getAnimalId() == 0) {
            System.out.println(livestockRecord);
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(livestockRecordService.update(livestockRecord));
    }
    @PatchMapping("/symptoms")
    public ResponseEntity<LivestockRecord> updateSymptoms(@Valid @RequestBody String symptoms, @RequestParam int animalId) {
        // check if entry in livestock table exists via animal_id
        Optional<LivestockRecord> optionalLivestockRecord = Optional.ofNullable(livestockRecordService.findByPatientIdentificationAnimalId(animalId));
        if (optionalLivestockRecord.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // get current condition json
        LivestockRecord livestockRecord = optionalLivestockRecord.get();
        CurrentCondition condition = livestockRecord.getCondition();

        // make new condition if none exists for the livestock record
        if (condition == null) {
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
    public ResponseEntity<LivestockRecord> updateMedicalHistory(@Valid @RequestBody MedicalHistory medicalHistory, @RequestParam int animalId){


        // check if entry in livestock table exists
        Optional<LivestockRecord> optionalLivestockRecord = Optional.ofNullable(livestockRecordService.findByPatientIdentificationAnimalId(animalId));
        if (optionalLivestockRecord.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LivestockRecord livestockRecord = optionalLivestockRecord.get();
        MedicalHistory currentMedicalHistory = livestockRecord.getMedicalHistory();

        // update medical history json in livestock object and update the database record
        currentMedicalHistory.setPrevious_illnesses(medicalHistory.getPrevious_illnesses());
        currentMedicalHistory.setVaccination_history(medicalHistory.getVaccination_history());
        currentMedicalHistory.setPrevious_treatment(medicalHistory.getPrevious_treatment());

        livestockRecord.setMedicalHistory(currentMedicalHistory);
        livestockRecordService.updateMedicalHistory(livestockRecord);

        return ResponseEntity.ok(livestockRecord);
    }
}