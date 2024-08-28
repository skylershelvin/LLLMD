package com.revature.LLL.LivestockRecord;

import com.revature.LLL.LivestockRecord.converters.*;
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

    @Column(columnDefinition = "json")
    @Convert(converter = PatientIdentificationConverter.class)
    private PatientIdentification patientIdentification;

    @Column(columnDefinition = "json")
    @Convert(converter= MedicalHistoryConverter.class)
    private MedicalHistory medicalHistory;

    @Column(columnDefinition = "json")
    @Convert(converter= CurrentConditionConverter.class)
    private CurrentCondition condition;

    @Column(columnDefinition = "json")
    @Convert(converter= TreatmentPlanConverter.class)
    private TreatmentPlan plan;

    @Column(columnDefinition = "json")
    @Convert(converter = LivestockHealthConverter.class)
    private LivestockHealth health;

    @Column(columnDefinition = "json")
    @Convert(converter = VetRecordConverter.class)
    private VetRecord vetRecord;

    @Column(columnDefinition = "json")
    @Convert(converter = AdditionalNotesConverter.class)
    private AdditionalNotes notes;
}