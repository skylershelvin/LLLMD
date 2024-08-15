package com.revature.LLL.LivestockRecord;

import com.revature.LLL.User.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VetRecord {
    @JsonProperty("vet_details")
    private User vet_details;
    @JsonProperty("record_date")
    private LocalDate record_date;
    @JsonProperty("signature")
    private String signature;
}
