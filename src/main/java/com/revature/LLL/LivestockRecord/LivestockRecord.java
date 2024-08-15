package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.LLL.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="livestock") //livestock table
public class LivestockRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int entryId;
    private User owner;
    private MedicalHistory medicalHistory;
    private CurrentCondition condition;
    private TreatmentPlan plan;
    private LivestockHealth health;
    private VetRecord vetRecord;
    private AdditionalNotes notes;
}
