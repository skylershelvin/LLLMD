package com.revature.LLL.MedicalRecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livestock_medical_records")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entryId;
    @OneToOne
    @JoinColumn(name="patient_identification")
    // Patient from patient_identification table
    private int patientId;
    @ManyToOne
    @JoinColumn(name="owner_id")
    // Owner from farmers table
    private int ownerId;
    private String medicalHistory;
    private String currentCondition;
    private String treatmentPlan;
    private String healthMonitoring;
    private String recordKeeping;
    private String additionalNotes;
}
