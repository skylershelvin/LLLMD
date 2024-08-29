package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MedicalHistory {
    @JsonProperty("previous_illnesses")
    private String previous_illnesses;
    @JsonProperty("previous_treatments")
    private TreatmentPlan previous_treatment;

    @JsonProperty("vaccination_history")
    private String vaccination_history;
}
