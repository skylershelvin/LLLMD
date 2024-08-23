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

    @Override
    public List<LivestockRecord> findAll() {
        return List.of();
    }

    @Override
    public LivestockRecord create(LivestockRecord newObject) {
        return null;
    }

    @Override
    public LivestockRecord findById(int id) {
        return livestockRecordRepository.findAllByEntryId(id);
    }

    @Override
    public Boolean update(LivestockRecord updatedObject) {
        return null;
    }

    @Override
    public Boolean delete(LivestockRecord deletedObject) {
        return null;
    }

    public List<LivestockRecord> findAllByVetRecordVetDetailsUserId(int userId){
        return livestockRecordRepository.findAllByVetRecordVetDetailsUserId(userId);
    }

    public List<LivestockRecord> findAllByPatientIdentificationOwnerInfoUserId(int userId){
        return livestockRecordRepository.findAllByPatientIdentificationOwnerInfoUserId(userId);
    }

    public LivestockRecord findByPatientIdentificationAnimalId(int animalId) {
        return livestockRecordRepository.findAllByPatientIdentificationAnimalId(animalId);
    }


}