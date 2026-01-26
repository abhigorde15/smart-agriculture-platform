package com.smartagri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CropRecommendationResponse {

    @JsonProperty("recommended_crop")
    private String recommendedCrop;
    private Double confidence;

    public CropRecommendationResponse() {
    }

    public CropRecommendationResponse(String recommendedCrop, Double confidence) {
        this.recommendedCrop = recommendedCrop;
        this.confidence = confidence;
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
