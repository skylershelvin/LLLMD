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

    @Test
    public void testGetLivestockRecords() throws Exception {
        // Arrange
        LivestockRecord record1 = new LivestockRecord(1, new Livestock(), new User(), new MedicalHistory(), new CurrentCondition(), new TreatmentPlan(), new LivestockHealth(), new VetRecord(), new AdditionalNotes());
        LivestockRecord record2 = new LivestockRecord(2, new Livestock(), new User(), new MedicalHistory(), new CurrentCondition(), new TreatmentPlan(), new LivestockHealth(), new VetRecord(), new AdditionalNotes());
        List<LivestockRecord> records = Arrays.asList(record1, record2);

        when(livestockRecordService.getLivestockRecords(1)).thenReturn(records);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/user")
                        .header("userId", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }

    @Test
    public void testGetLivestockRecordsByLivestockId() throws Exception {
        // Arrange
        LivestockRecord record1 = new LivestockRecord(1, new Livestock(), new User(), new MedicalHistory(), new CurrentCondition(), new TreatmentPlan(), new LivestockHealth(), new VetRecord(), new AdditionalNotes());
        LivestockRecord record2 = new LivestockRecord(2, new Livestock(), new User(), new MedicalHistory(), new CurrentCondition(), new TreatmentPlan(), new LivestockHealth(), new VetRecord(), new AdditionalNotes());
        List<LivestockRecord> records = Arrays.asList(record1, record2);

        when(livestockRecordService.getLivestockRecordsByLivestockId(1)).thenReturn(records);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/livestock")
                        .param("livestockId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }
}