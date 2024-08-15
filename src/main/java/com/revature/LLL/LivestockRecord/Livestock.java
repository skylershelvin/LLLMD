package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.LLL.User.User;
import lombok.Data;

@Data
public class Livestock {
    @JsonProperty("animal_id")
    private int animal_id;
    @JsonProperty("breed")
    private String breed;
    @JsonProperty("age")
    private int age;
    @JsonProperty("sex")
    private Sex sex;
    @JsonProperty("owner_info")
    private User owner;

    public enum Sex {
        MALE, FEMALE
    }
}
