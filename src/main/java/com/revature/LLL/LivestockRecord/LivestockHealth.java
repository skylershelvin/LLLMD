package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LivestockHealth {
    @JsonProperty("monitoring_schedule")
    private String monitoring_schedule;
    @JsonProperty("progress_notes")
    private String progress_notes;
}
