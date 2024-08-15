package com.revature.LLL.LivestockMedicalRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivestockMedicalRecordRepository extends JpaRepository<LivestockMedicalRecord, Integer> {
}
