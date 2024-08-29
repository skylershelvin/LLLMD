package com.revature.LLL.LivestockRecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivestockRecordRepository extends JpaRepository<LivestockRecord, Integer> {

    Optional<LivestockRecord> findAllByEntryId(int entryId);

    // jsonb_extract_path_text is a PostgreSQL function that extracts a text value from a JSON object
    @Query(value = "SELECT * FROM livestock WHERE json_extract_path_text(patient_identification::json, 'owner_info', 'userId')::INTEGER = :userId;",
            nativeQuery = true)
    List<LivestockRecord> findAllByPatientIdentificationOwnerInfoUserId(@Param("userId") int userId);

    @Query(value = "SELECT * FROM livestock WHERE json_extract_path_text(patient_identification::json, 'animal_id')::INTEGER = :animalId;",
            nativeQuery = true)
    Optional<LivestockRecord> findAllByPatientIdentificationAnimalId(@Param("animalId") int animalId);

    @Query(value = "SELECT * FROM livestock WHERE jsonb_extract_path_text(vet_record::jsonb, 'vet_details', 'userId')::INTEGER = :userId", nativeQuery = true)
    List<LivestockRecord> findAllByVetRecordVetDetailsUserId(@Param("userId") int userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO livestock (patient_identification, vet_record, medical_history, condition, plan, health, notes) VALUES (CAST(:patientIdentification AS json), CAST(:vetRecord AS json), CAST(:medicalHistory AS json), CAST(:condition AS json), CAST(:plan AS json), CAST(:health AS json), CAST(:notes AS json))", nativeQuery = true)
    void insertLivestockRecord(@Param("patientIdentification") String patientIdentification,
                               @Param("vetRecord") String vetRecord,
                               @Param("medicalHistory") String medicalHistory,
                               @Param("condition") String condition,
                               @Param("plan") String plan,
                               @Param("health") String health,
                               @Param("notes") String notes);

    @Modifying
    @Transactional
    @Query(value = "UPDATE livestock SET patient_identification = CAST(:patientIdentification AS json), vet_record = CAST(:vetRecord AS json), medical_history = CAST(:medicalHistory AS json), condition = CAST(:condition AS json), plan = CAST(:plan AS json), health = CAST(:health AS json), notes = CAST(:notes AS json) WHERE json_extract_path_text(patient_identification::json, 'animal_id')::INTEGER = :animalId;", nativeQuery = true)
    void updateLivestockRecord(@Param("patientIdentification") String patientIdentification,
                               @Param("vetRecord") String vetRecord,
                               @Param("medicalHistory") String medicalHistory,
                               @Param("condition") String condition,
                               @Param("plan") String plan,
                               @Param("health") String health,
                               @Param("notes") String notes,
                               @Param("animalId") int animalId);
}