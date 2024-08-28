package com.revature.LLL.FileManagement.File;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UploadedFile {

    @Id
    @GeneratedValue
    private Long id;
    private String previousIllnesses;
    private String getPreviousTreatments;
    private String vaccinationHistory;

    public UploadedFile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreviousIllnesses() {
        return previousIllnesses;
    }

    public void setPreviousIllnesses(String previousIllnesses) {
        this.previousIllnesses = previousIllnesses;
    }

    public String getGetPreviousTreatments() {
        return getPreviousTreatments;
    }

    public void setGetPreviousTreatments(String getPreviousTreatments) {
        this.getPreviousTreatments = getPreviousTreatments;
    }

    public String getVaccinationHistory() {
        return vaccinationHistory;
    }

    public void setVaccinationHistory(String vaccinationHistory) {
        this.vaccinationHistory = vaccinationHistory;
    }

    @Override
    public String toString() {
        return "UploadedFile{" +
                "id=" + id +
                ", previousIllnesses='" + previousIllnesses + '\'' +
                ", getPreviousTreatments='" + getPreviousTreatments + '\'' +
                ", vaccinationHistory='" + vaccinationHistory + '\'' +
                '}';
    }

    public UploadedFile(Long id, String previousIllnesses, String getPreviousTreatments, String vaccinationHistory) {
        super();
        this.id = id;
        this.previousIllnesses = previousIllnesses;
        this.getPreviousTreatments = getPreviousTreatments;
        this.vaccinationHistory = vaccinationHistory;
    }
}
