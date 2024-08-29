package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.LLL.User.dtos.OwnerInfoDTO;
import com.revature.LLL.util.exceptions.DataNotFoundException;
import com.revature.LLL.util.interfaces.Serviceable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;


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
    public LivestockRecord create(LivestockRecord newLivestockRecord) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try{
            livestockRecordRepository.insertLivestockRecord(
                objectMapper.writeValueAsString(newLivestockRecord.getPatientIdentification()),
                objectMapper.writeValueAsString(newLivestockRecord.getVetRecord()),
                newLivestockRecord.getMedicalHistory() != null ? objectMapper.writeValueAsString(newLivestockRecord.getMedicalHistory()) : null,
                newLivestockRecord.getCondition() != null ? objectMapper.writeValueAsString(newLivestockRecord.getCondition()) : null,
                newLivestockRecord.getPlan() != null ? objectMapper.writeValueAsString(newLivestockRecord.getPlan()) : null,
                newLivestockRecord.getHealth() != null ? objectMapper.writeValueAsString(newLivestockRecord.getHealth()) : null,
                newLivestockRecord.getNotes() != null ? objectMapper.writeValueAsString(newLivestockRecord.getNotes()) : null
            );
        } catch (JsonProcessingException e) {
            throw new JsonProcessingException("Error converting LivestockRecord to JSON string") {
            };
        }

        // Fetch the newly created record (assuming there's a way to identify it, e.g., by a unique field)
        return livestockRecordRepository.findAllByPatientIdentificationAnimalId(
                newLivestockRecord.getPatientIdentification().getAnimalId()
        ).orElseThrow(() -> new RuntimeException("Failed to fetch the newly created LivestockRecord"));
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
    public LivestockRecord update(LivestockRecord updatedObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // set updatedObject's owner_info to the existing owner_info of the given animal_id
        OwnerInfoDTO ownerInfo = livestockRecordRepository.findAllByPatientIdentificationAnimalId(updatedObject.getPatientIdentification().getAnimalId()).orElseThrow(() -> new DataNotFoundException("No livestock found with that animalId")).getPatientIdentification().getOwnerInfo();
        updatedObject.getPatientIdentification().setOwnerInfo(ownerInfo);

        try{
            livestockRecordRepository.updateLivestockRecord(
                    objectMapper.writeValueAsString(updatedObject.getPatientIdentification()),
                    objectMapper.writeValueAsString(updatedObject.getVetRecord()),
                    updatedObject.getMedicalHistory() != null ? objectMapper.writeValueAsString(updatedObject.getMedicalHistory()) : null,
                    updatedObject.getCondition() != null ? objectMapper.writeValueAsString(updatedObject.getCondition()) : null,
                    updatedObject.getPlan() != null ? objectMapper.writeValueAsString(updatedObject.getPlan()) : null,
                    updatedObject.getHealth() != null ? objectMapper.writeValueAsString(updatedObject.getHealth()) : null,
                    updatedObject.getNotes() != null ? objectMapper.writeValueAsString(updatedObject.getNotes()) : null,
                    updatedObject.getPatientIdentification().getAnimalId()
            );
        } catch (JsonProcessingException e) {
            throw new JsonProcessingException("Error converting LivestockRecord to JSON string") {
            };
        }

        // Fetch the newly created record (assuming there's a way to identify it, e.g., by a unique field)
        return livestockRecordRepository.findAllByPatientIdentificationAnimalId(
                updatedObject.getPatientIdentification().getAnimalId()
        ).orElseThrow(() -> new RuntimeException("Failed to fetch the newly created LivestockRecord"));
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

    public LivestockRecord updateSymptoms(LivestockRecord livestockRecord) {

        return livestockRecordRepository.save(livestockRecord);
    }

    public LivestockRecord updateMedicalHistory(LivestockRecord livestockRecord) {
        return livestockRecordRepository.save(livestockRecord);
    }
}
