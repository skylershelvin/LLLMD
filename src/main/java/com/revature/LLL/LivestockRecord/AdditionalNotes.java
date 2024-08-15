package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdditionalNotes {
    @JsonProperty("environmental_factors")
    private String environmental_factors;
    @JsonProperty("behavioral_observations")
    private String behavioral_observations;
}
