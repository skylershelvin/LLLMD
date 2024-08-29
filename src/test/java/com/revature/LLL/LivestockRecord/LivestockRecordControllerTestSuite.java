// src/test/java/com/revature/LLL/LivestockRecord/LivestockRecordControllerTestSuite.java
package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.LLL.User.User;
import com.revature.LLL.User.dtos.OwnerInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LivestockRecordControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivestockRecordService livestockRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test the getLivestockRecordByEntryId method in LivestockRecordController
     * @throws Exception
     */
    @Test
    public void testGetLivestockRecordByEntryId() throws Exception {
        // Arrange
        int entryId1 = 1;
        int entryId2 = 2;

        LivestockRecord record1 = new LivestockRecord();
        record1.setEntryId(entryId1);

        LivestockRecord record2 = new LivestockRecord();
        record2.setEntryId(entryId2);

        when(livestockRecordService.findById(entryId1)).thenReturn(record1);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/entry")
                        .param("entryId", String.valueOf(entryId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record1)));
    }

    /**
     * Test the getLivestockRecordsByUserId method in LivestockRecordController
     * User type is OWNER
     * @throws Exception
     */
    @Test
    public void testGetLivestockRecordsByUserId() throws Exception {
        // Arrange
        int userId = 1;

        OwnerInfoDTO owner = new OwnerInfoDTO();
        owner.setUserId(userId);

        PatientIdentification patientIdentity = new PatientIdentification();
        patientIdentity.setOwnerInfo(owner);

        LivestockRecord record1 = new LivestockRecord();
        record1.setPatientIdentification(patientIdentity);

        LivestockRecord record2 = new LivestockRecord();
        record2.setPatientIdentification(patientIdentity);

        List<LivestockRecord> records = Arrays.asList(record1, record2);

        when(livestockRecordService.findAllByPatientIdentificationOwnerInfoUserId(userId)).thenReturn(records);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/user")
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }

    /**
     * Test the getLivestockRecordByAnimalId method in LivestockRecordController
     * @throws Exception
     */
    @Test
    public void testGetLivestockRecordByAnimalId() throws Exception {
        // Arrange
        int animalId = 1;

        PatientIdentification patientIdentity = new PatientIdentification();
        patientIdentity.setAnimalId(animalId);

        LivestockRecord record = new LivestockRecord();
        record.setPatientIdentification(patientIdentity);

        when(livestockRecordService.findByPatientIdentificationAnimalId(animalId)).thenReturn(record);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/animal")
                        .param("animalId", String.valueOf(animalId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record)));
    }

    @Test
    public void testFindByAnimalId() throws Exception{
        int animalId = 1;
        PatientIdentification patientIdentification = new PatientIdentification();
        patientIdentification.setAnimalId(animalId);
        LivestockRecord record = new LivestockRecord();
        record.setPatientIdentification(patientIdentification);

        when(livestockRecordService.findByPatientIdentificationAnimalId(animalId)).thenReturn(record);
        mockMvc.perform(get("/medicalRecord/animal")
                        .param("animalId", String.valueOf(animalId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record)));
        verify(livestockRecordService, times(1)).findByPatientIdentificationAnimalId(animalId);
    }

    @Test
    public void testCreateLivestockRecord() throws Exception {
        // Arrange

        OwnerInfoDTO owner = new OwnerInfoDTO(1, "Charles", "Tester", "charles@mail.com");
        PatientIdentification patientIdentification = new PatientIdentification();
        patientIdentification.setBreed("pitbull");
        patientIdentification.setAge(12);
        patientIdentification.setSex(PatientIdentification.Sex.FEMALE);
        patientIdentification.setOwnerInfo(owner);

        // Mock the sequence value
        int nextAnimalId = 100;
        patientIdentification.setAnimalId(nextAnimalId);

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setVaccination_history("modernapfizer");

        CurrentCondition condition = new CurrentCondition();
        condition.setDiagnosis("ACL tear");

        TreatmentPlan plan = new TreatmentPlan();
        plan.setMedications_prescribed("tylenol, ibuprofen");

        LivestockHealth health = new LivestockHealth();
        health.setMonitoring_schedule("weekly blood pressure and weight monitoring");

        AdditionalNotes notes = new AdditionalNotes();
        notes.setEnvironmental_factors("not much personal space due to crowded barn");

        LivestockRecord livestockRecord = new LivestockRecord();
        livestockRecord.setPatientIdentification(patientIdentification);
        livestockRecord.setMedicalHistory(medicalHistory);
        livestockRecord.setCondition(condition);
        livestockRecord.setPlan(plan);
        livestockRecord.setHealth(health);
        livestockRecord.setNotes(notes);



        when(livestockRecordService.create(any(LivestockRecord.class))).thenReturn(livestockRecord);

        // Act & Assert
        mockMvc.perform(post("/medicalRecord/animal")
                        .content(objectMapper.writeValueAsString(livestockRecord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(livestockRecord)));
        verify(livestockRecordService, times(1)).create(any(LivestockRecord.class));
    }

    @Test
    public void testUpdateSymptoms() throws Exception {
        // Arrange
        String newSymptoms = "symptom3, symptom4";

        CurrentCondition condition = new CurrentCondition();
        condition.setSymptoms(newSymptoms);

        LivestockRecord record1 = new LivestockRecord();
        record1.setCondition(condition);
        record1.setPatientIdentification(new PatientIdentification());
        record1.getPatientIdentification().setAnimalId(1);

        when(livestockRecordService.findByPatientIdentificationAnimalId(1)).thenReturn(record1);
        when(livestockRecordService.updateSymptoms(eq(record1))).thenReturn(record1);

        // Act & Assert
        mockMvc.perform(patch("/medicalRecord/symptoms")
                        .param("animalId", String.valueOf(1))
                        .content(objectMapper.writeValueAsString(newSymptoms))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record1)));
        verify(livestockRecordService, times(1)).findByPatientIdentificationAnimalId(1);
        verify(livestockRecordService, times(1)).updateSymptoms(record1);
    }
    @Test
    public void testUpdateMedicalHistory() throws Exception{
        // set up MedicalHistory and TreatmentPlan objects to use during the patch
        MedicalHistory medicalHistory = new MedicalHistory();
        TreatmentPlan treatmentPlan = new TreatmentPlan();
        treatmentPlan.setAntibiotics("antibiotic1, antibiotic2");
        treatmentPlan.setMedications_prescribed("medication1, medication2");
        treatmentPlan.setTreatment_procedures("procedure1");
        treatmentPlan.setFollowup_instructions("instructions1");

        medicalHistory.setPrevious_treatment(treatmentPlan);
        medicalHistory.setPrevious_illnesses("illness1, illness2");
        medicalHistory.setVaccination_history("vaccination1, vaccination2");

        // set up PatientIdentification object to be used to find which record to update via animal_id
        PatientIdentification mockPatientIdentification = new PatientIdentification();
        mockPatientIdentification.setAnimalId(1);
        mockPatientIdentification.setBreed("dog");
        mockPatientIdentification.setAge(5);
        mockPatientIdentification.setSex(PatientIdentification.Sex.MALE);
        mockPatientIdentification.setOwnerInfo(new OwnerInfoDTO(1, "John", "Doe", "john@mail.com"));



        LivestockRecord record1 = new LivestockRecord();
        record1.setMedicalHistory(medicalHistory);
        record1.setPatientIdentification(mockPatientIdentification);

        // return the record1 which has animal_id = 1, needed because the patch request needs to find the record to update
        when(livestockRecordService.findByPatientIdentificationAnimalId(1)).thenReturn(record1);

        // return the record1 after updating the medical history
        when(livestockRecordService.updateMedicalHistory(record1)).thenReturn(record1);

        mockMvc.perform(patch("/medicalRecord/medicalHistory")
                        .param("animalId", String.valueOf(1))
                        .content(objectMapper.writeValueAsString(record1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record1)));   // expects record1 to be returned after updating the medical history

        verify(livestockRecordService, times(1)).updateMedicalHistory(record1);
    }
}