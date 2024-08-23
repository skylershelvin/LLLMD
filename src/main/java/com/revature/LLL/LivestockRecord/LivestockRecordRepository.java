package com.revature.LLL.LivestockRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LivestockRecordRepository extends JpaRepository<LivestockRecord, Integer> {
    List<LivestockRecord> findAllByOwner_UserId(int userId);

    List<LivestockRecord> findAllByPatientIdentification_Owner_UserId(int userId);

}