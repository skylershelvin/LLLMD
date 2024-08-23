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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

    @Test
    public void testFindAllByPatientIdentificationOwnerInfoUserId() throws Exception {
        // Arrange
        User owner = new User();
        owner.setUserId(1);

        PatientIdentification patientIdentity = new PatientIdentification();
        patientIdentity.setOwnerInfo(owner);

        LivestockRecord record1 = new LivestockRecord();
        record1.setPatientIdentification(patientIdentity);

        LivestockRecord record2 = new LivestockRecord();
        record2.setPatientIdentification(patientIdentity);

        List<LivestockRecord> records = Arrays.asList(record1, record2);

        when(livestockRecordService.findAllByPatientIdentificationOwnerInfoUserId(1)).thenReturn(records);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/user")
                        .param("userId", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }

    @Test
    public void testUpdateMedicalHistory() throws Exception{
        // TODO: need a livestock record with animalId = 1 to test this


        MedicalHistory medicalHistory = new MedicalHistory();
        TreatmentPlan treatmentPlan = new TreatmentPlan();
        treatmentPlan.setAntibiotics(new String[]{"antibiotic1", "antibiotic2"});
        treatmentPlan.setMedications_prescribed(new String[]{"medication1", "medication2"});
        treatmentPlan.setTreatment_procedures("procedure1");
        treatmentPlan.setFollowup_instructions("instructions1");

        medicalHistory.setPrevious_treatments(new TreatmentPlan[]{treatmentPlan});
        medicalHistory.setPrevious_illnesses(new String[]{"illness1", "illness2"});
        medicalHistory.setVaccination_history(new String[]{"vaccination1", "vaccination2"});

        LivestockRecord record1 = new LivestockRecord();
        record1.setMedicalHistory(medicalHistory);
        record1.setEntryId(1);

        when(livestockRecordService.updateMedicalHistory(record1)).thenReturn(record1);

        mockMvc.perform(patch("/medicalRecord/medicalHistory")
                        .param("animalId", String.valueOf(1))
                        .header("userType", "VET")
                        .content(objectMapper.writeValueAsString(record1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record1)));
    }
}