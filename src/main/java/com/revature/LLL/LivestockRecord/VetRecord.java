package com.revature.LLL.LivestockRecord;

import com.revature.LLL.User.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.LLL.User.dtos.VetDetailsDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VetRecord {
    @JsonProperty("vet_details")
    private VetDetailsDTO vetDetails;
    @JsonProperty("record_date")
    private LocalDate recordDate;
    @JsonProperty("signature")
    private String signature;
}
