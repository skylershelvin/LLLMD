// src/test/java/com/revature/LLL/LivestockRecord/LivestockRecordControllerTestSuite.java
package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.LLL.User.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
     * User type is VET
     * @throws Exception
     */
    @Test
    public void testGetLivestockRecordsByUserIdAsVet() throws Exception {
        // Arrange
        int userId = 1;
        String userType = "VET";

        User vet = new User();
        vet.setUserId(userId);

        VetRecord vetRecord = new VetRecord();
        vetRecord.setVetDetails(vet);

        LivestockRecord record1 = new LivestockRecord();
        record1.setVetRecord(vetRecord);

        LivestockRecord record2 = new LivestockRecord();
        record2.setVetRecord(vetRecord);

        List<LivestockRecord> records = Arrays.asList(record1, record2);

        when(livestockRecordService.findAllByVetRecordVetDetailsUserId(userId)).thenReturn(records);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/user")
                        .param("userId", String.valueOf(userId))
                        .header("userType", userType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }

    /**
     * Test the getLivestockRecordsByUserId method in LivestockRecordController
     * User type is OWNER
     * @throws Exception
     */
    @Test
    public void testGetLivestockRecordsByUserIdAsOwner() throws Exception {
        // Arrange
        int userId = 1;
        String userType = "OWNER";

        User owner = new User();
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
                        .header("userType", userType)
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




}