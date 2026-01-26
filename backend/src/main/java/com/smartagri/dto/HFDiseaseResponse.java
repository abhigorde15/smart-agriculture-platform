package com.smartagri.dto;

public class HFDiseaseResponse {

    private String predicted_class;
    private Double confidence;

    public String getPredicted_class() {
        return predicted_class;
    }

    public void setPredicted_class(String predicted_class) {
        this.predicted_class = predicted_class;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}
