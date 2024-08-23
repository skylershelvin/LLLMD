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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int entryId;

    @JoinColumn(name = "animal_id")
    @Convert(converter = PatientIdentificationConverter.class)
    private PatientIdentification patientIdentification;

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