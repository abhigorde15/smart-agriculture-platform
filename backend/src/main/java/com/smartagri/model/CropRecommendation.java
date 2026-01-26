package com.smartagri.model;

import jakarta.persistence.*;

@Entity
@Table(name = "crop_recommendations")
public class CropRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    private String recommendedCrop;
    private Double confidence;

    public CropRecommendation() {
    }

    public CropRecommendation(Long id, User farmer, String recommendedCrop, Double confidence) {
        this.id = id;
        this.farmer = farmer;
        this.recommendedCrop = recommendedCrop;
        this.confidence = confidence;
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

    public String getRecommendedCrop() {
        return recommendedCrop;
    }

    public void setRecommendedCrop(String recommendedCrop) {
        this.recommendedCrop = recommendedCrop;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}
