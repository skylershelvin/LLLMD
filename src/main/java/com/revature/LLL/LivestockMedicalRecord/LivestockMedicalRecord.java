package com.revature.LLL.LivestockMedicalRecord;

import com.revature.LLL.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livestock_medical_records")
public class LivestockMedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entryId;
    @OneToOne
    @JoinColumn(name="patient_identification")
    // Patient from patient_identification table
    private int patientId;
    @ManyToOne
    @JoinColumn(name="owner_id")
    private User ownerId;
    private String medicalHistory;
    private String currentCondition;
    private String treatmentPlan;
    private String healthMonitoring;
    private String recordKeeping;
    private String additionalNotes;
}
