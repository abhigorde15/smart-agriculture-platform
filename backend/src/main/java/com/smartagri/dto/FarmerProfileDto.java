package com.smartagri.dto;

public class FarmerProfileDto {
    private Long id;
    private Long userId;
    private Double landArea;
    private String location; // District/State
    private String soilType;
    private String irrigationMethod;
    private String preferredLanguage;
    private String district;
    private String state;

    public FarmerProfileDto() {
    }

    public FarmerProfileDto(Long id, Long userId, Double landArea, String location, String soilType,
            String irrigationMethod, String preferredLanguage, String district, String state) {
        this.id = id;
        this.userId = userId;
        this.landArea = landArea;
        this.location = location;
        this.soilType = soilType;
        this.irrigationMethod = irrigationMethod;
        this.preferredLanguage = preferredLanguage;
        this.district = district;
        this.state = state;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getLandArea() {
        return landArea;
    }

    public void setLandArea(Double landArea) {
        this.landArea = landArea;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getIrrigationMethod() {
        return irrigationMethod;
    }

    public void setIrrigationMethod(String irrigationMethod) {
        this.irrigationMethod = irrigationMethod;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
