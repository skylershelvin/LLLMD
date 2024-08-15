package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TreatmentPlan {
    @JsonProperty("medications_prescribed")
    private String[] medications_prescribed;
    @JsonProperty("antibiotics")
    private String[] antibiotics; //FIXME do we need this AND medications_prescribed?
    @JsonProperty("treatment_procedures")
    private String treatment_procedures;
    @JsonProperty("followup_instructions")
    private String followup_instructions;
}
