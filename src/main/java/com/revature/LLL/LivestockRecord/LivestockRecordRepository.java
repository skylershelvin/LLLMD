package com.revature.LLL.LivestockRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LivestockRecordRepository extends JpaRepository<LivestockRecord, Integer> {

    LivestockRecord findAllByEntryId(int entryId);

    // jsonb_extract_path_text is a PostgreSQL function that extracts a text value from a JSON object
    @Query(value = "SELECT * FROM livestock WHERE jsonb_extract_path_text(patient_identification::jsonb, 'owner_info', 'userId')::INTEGER = :userId;",
            nativeQuery = true)
    List<LivestockRecord> findAllByPatientIdentificationOwnerInfoUserId(@Param("userId") int userId);

    @Query(value = "SELECT * FROM livestock WHERE jsonb_extract_path_text(patient_identification::jsonb, 'animal_id')::INTEGER = :animalId;",
            nativeQuery = true)
    LivestockRecord findAllByPatientIdentificationAnimalId(@Param("animalId") int animalId);

    @Query(value = "SELECT * FROM livestock WHERE jsonb_extract_path_text(record_keeping::jsonb, 'vet_details', 'userId')::INTEGER = :userId;",
            nativeQuery = true)
    List<LivestockRecord> findAllByVetRecordVetDetailsUserId(int userId);
}