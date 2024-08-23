package com.revature.LLL.LivestockRecord;

import com.revature.LLL.util.exceptions.DataNotFoundException;
import com.revature.LLL.util.interfaces.Serviceable;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class LivestockRecordService implements Serviceable<LivestockRecord> {
    private final LivestockRecordRepository livestockRecordRepository;

    public LivestockRecordService(LivestockRecordRepository livestockRecordRepository){
        this.livestockRecordRepository = livestockRecordRepository;
    }

    public List<LivestockRecord> findAllByPatientIdentificationOwnerInfoUserId(int userId){
        return livestockRecordRepository.findAllByPatientIdentificationOwnerInfoUserId(userId);
    }

    public LivestockRecord findByAnimalId(int animalId) throws DataNotFoundException {
        return livestockRecordRepository.findByPatientidentificationOwnerInfoAnimalId(animalId).orElseThrow(() -> new DataNotFoundException("No entry found with that animalId"));
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
    public LivestockRecord findById(int entryId) throws DataNotFoundException {
        return livestockRecordRepository.findByEntryId(entryId).orElseThrow(() -> new DataNotFoundException("No entry found with that entryId"));
    }



    @Override
    public Boolean update(LivestockRecord updatedObject) {
        return null;
    }

    @Override
    public Boolean delete(LivestockRecord deletedObject) {
        return null;
    }

    public LivestockRecord updateSymptoms(LivestockRecord livestockRecord) {
        return livestockRecordRepository.save(livestockRecord);
    }

    public LivestockRecord updateMedicalHistory(LivestockRecord livestockRecord) {
        return livestockRecordRepository.save(livestockRecord);
    }
}
