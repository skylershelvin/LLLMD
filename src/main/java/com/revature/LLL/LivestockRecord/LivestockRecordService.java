package com.revature.LLL.LivestockRecord;

import com.revature.LLL.util.interfaces.Serviceable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivestockRecordService implements Serviceable<LivestockRecord> {
    private final LivestockRecordRepository livestockRecordRepository;

    public LivestockRecordService(LivestockRecordRepository livestockRecordRepository){
        this.livestockRecordRepository = livestockRecordRepository;
    }

    public List<LivestockRecord> getLivestockRecords(int userId) {
        return livestockRecordRepository.findAllByOwner_UserId(userId);
    }

    @Override
    public List<LivestockRecord> findAll() {
        return List.of();
    }

    @Override
    public LivestockRecord create(LivestockRecord newObject) {
        return livestockRecordRepository.save(newObject);
    }

    @Override
    public LivestockRecord findById(int id) {
        return null;
    }

    @Override
    public Boolean update(LivestockRecord updatedObject) {
        return null;
    }

    @Override
    public Boolean delete(LivestockRecord deletedObject) {
        return null;
    }
}
