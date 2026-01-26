package com.smartagri.model;

import jakarta.persistence.*;

@Entity
@Table(name = "farmer_profiles")
public class FarmerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Double landArea; // in acres

    private String location;

    @Enumerated(EnumType.STRING)
    private SoilType soilType;

    @Enumerated(EnumType.STRING)
    private IrrigationMethod irrigationMethod;

    private String state;
    private String district;

    private String preferredLanguage; // English, Hindi, Marathi

    public FarmerProfile() {
    }

    public FarmerProfile(Long id, User user, Double landArea, String location, SoilType soilType,
            IrrigationMethod irrigationMethod, String state, String district, String preferredLanguage) {
        this.id = id;
        this.user = user;
        this.landArea = landArea;
        this.location = location;
        this.soilType = soilType;
        this.irrigationMethod = irrigationMethod;
        this.state = state;
        this.district = district;
        this.preferredLanguage = preferredLanguage;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public SoilType getSoilType() {
        return soilType;
    }

    public void setSoilType(SoilType soilType) {
        this.soilType = soilType;
    }

    public IrrigationMethod getIrrigationMethod() {
        return irrigationMethod;
    }

    public void setIrrigationMethod(IrrigationMethod irrigationMethod) {
        this.irrigationMethod = irrigationMethod;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }
}
