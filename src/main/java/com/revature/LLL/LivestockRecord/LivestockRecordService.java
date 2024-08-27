package com.revature.LLL.LivestockRecord;

import com.revature.LLL.util.exceptions.DataNotFoundException;
import com.revature.LLL.util.interfaces.Serviceable;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

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

    /**
     * Find a livestock record by its entry ID
     * @param id
     * @return
     * @throws DataNotFoundException
     */
    @Override
    public LivestockRecord findById(int id) throws DataNotFoundException {
        Optional<LivestockRecord> record = livestockRecordRepository.findAllByEntryId(id);
        return record.orElseThrow(() -> new DataNotFoundException("No entry found with that entryId"));
    }

    @Override
    public Boolean update(LivestockRecord updatedObject) {
        return null;
    }

    @Override
    public Boolean delete(LivestockRecord deletedObject) {
        return null;
    }

    /**
     * Find all livestock records by the user ID of the vet who is assigned to the animal
     * @param userId
     * @return
     * @throws DataNotFoundException
     */
    public List<LivestockRecord> findAllByVetRecordVetDetailsUserId(int userId) throws DataNotFoundException{
        Optional <List<LivestockRecord>> records = Optional.of(livestockRecordRepository.findAllByVetRecordVetDetailsUserId(userId));
        return records.orElseThrow(() -> new DataNotFoundException("No livestock found for that vet with that userId"));
    }

    /**
     * Find all livestock records by the user ID of the owner of the animal
     * @param userId
     * @return
     * @throws DataNotFoundException
     */
    public List<LivestockRecord> findAllByPatientIdentificationOwnerInfoUserId(int userId) throws DataNotFoundException{
        Optional <List<LivestockRecord>> records = Optional.of(livestockRecordRepository.findAllByPatientIdentificationOwnerInfoUserId(userId));
        return records.orElseThrow(() -> new DataNotFoundException("No livestock found for that owner with that userId"));
    }

    /**
     * Find a livestock record by the animal ID
     * @param animalId
     * @return
     * @throws DataNotFoundException
     */
    public LivestockRecord findByPatientIdentificationAnimalId(int animalId) throws DataNotFoundException {
        Optional<LivestockRecord> record = livestockRecordRepository.findAllByPatientIdentificationAnimalId(animalId);
        return record.orElseThrow(() -> new DataNotFoundException("No livestock found with that animalId"));
    }

    public void updateSymptoms(LivestockRecord livestockRecord) {
        livestockRecordRepository.save(livestockRecord);
    }

    public LivestockRecord updateMedicalHistory(LivestockRecord livestockRecord) {
        return livestockRecordRepository.save(livestockRecord);
    }
}
