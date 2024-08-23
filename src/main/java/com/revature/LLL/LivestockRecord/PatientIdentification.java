package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.LLL.User.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class PatientIdentification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("animal_id")
    private int animal_id;
    @JsonProperty("breed")
    private String breed;
    @JsonProperty("age")
    private int age;
    @JsonProperty("sex")
    private Sex sex;
    @JsonProperty("owner_info")
    @JoinColumn(name = "user_id")
    private User owner;

    public enum Sex {
        MALE, FEMALE
    }
}
