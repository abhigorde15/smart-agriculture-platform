package com.smartagri.model;

import jakarta.persistence.*;

@Entity
@Table(name = "disease_detections")
public class DiseaseDetection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    private String imagePath; // Local path or cloud URL
    private String detectedDisease;
    private Double confidence;

    @Column(length = 1000)
    private String suggestion;

    public DiseaseDetection() {
    }

    public DiseaseDetection(Long id, User farmer, String imagePath, String detectedDisease, Double confidence,
            String suggestion) {
        this.id = id;
        this.farmer = farmer;
        this.imagePath = imagePath;
        this.detectedDisease = detectedDisease;
        this.confidence = confidence;
        this.suggestion = suggestion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFarmer() {
        return farmer;
    }

    public void setFarmer(User farmer) {
        this.farmer = farmer;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDetectedDisease() {
        return detectedDisease;
    }

    public void setDetectedDisease(String detectedDisease) {
        this.detectedDisease = detectedDisease;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
