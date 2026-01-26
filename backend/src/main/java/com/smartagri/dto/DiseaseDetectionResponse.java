package com.smartagri.dto;

public class DiseaseDetectionResponse {

    private String disease;
    private Double confidence;
    private String suggestion;

    public DiseaseDetectionResponse() {
    }

    public DiseaseDetectionResponse(String disease, Double confidence, String suggestion) {
        this.disease = disease;
        this.confidence = confidence;
        this.suggestion = suggestion;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
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
