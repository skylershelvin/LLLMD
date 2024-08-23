package com.revature.LLL.LivestockRecord;

import com.revature.LLL.LivestockRecord.converters.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entryId;

    @ManyToOne
    private User owner;

    @Convert(converter= MedicalHistoryConverter.class)
    private MedicalHistory medicalHistory;

    @Convert(converter= CurrentConditionConverter.class)
    private CurrentCondition condition;

    @Convert(converter= TreatmentPlanConverter.class)
    private TreatmentPlan plan;

    @Convert(converter = LivestockHealthConverter.class)
    private LivestockHealth health;

    @Convert(converter = VetRecordConverter.class)
    private VetRecord vetRecord;

    @Convert(converter = AdditionalNotesConverter.class)
    private AdditionalNotes notes;
}

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "livestock_medical_records")
//public class LivestockMedicalRecord {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int entryId;
//    @OneToOne
//    @JoinColumn(name="patient_identification")
//    // Patient from patient_identification table
//    private int patientId;
//    @ManyToOne
//    @JoinColumn(name="owner_id")
//    private User ownerId;
//    private String medicalHistory;
//    private String currentCondition;
//    private String treatmentPlan;
//    private String healthMonitoring;
//    private String recordKeeping;
//    private String additionalNotes;
//}