package com.revature.LLL.LivestockRecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivestockRecordRepository extends JpaRepository<LivestockRecord, Integer> {
    List<LivestockRecord> findAllByOwner_UserId(int userId);
}
