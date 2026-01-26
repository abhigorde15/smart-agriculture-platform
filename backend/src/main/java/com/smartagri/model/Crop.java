package com.smartagri.model;

import jakarta.persistence.*;

@Entity
@Table(name = "crops")
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    @Column(nullable = false)
    private String name;

    private String sowingDate;
    private String expectedHarvestDate;

    @Enumerated(EnumType.STRING)
    private CropStatus status;

    public Crop() {
    }

    public Crop(Long id, User farmer, String name, String sowingDate, String expectedHarvestDate, CropStatus status) {
        this.id = id;
        this.farmer = farmer;
        this.name = name;
        this.sowingDate = sowingDate;
        this.expectedHarvestDate = expectedHarvestDate;
        this.status = status;
    }

    public static CropBuilder builder() {
        return new CropBuilder();
    }

    public static class CropBuilder {
        private Long id;
        private User farmer;
        private String name;
        private String sowingDate;
        private String expectedHarvestDate;
        private CropStatus status;

        public CropBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CropBuilder farmer(User farmer) {
            this.farmer = farmer;
            return this;
        }

        public CropBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CropBuilder sowingDate(String sowingDate) {
            this.sowingDate = sowingDate;
            return this;
        }

        public CropBuilder expectedHarvestDate(String expectedHarvestDate) {
            this.expectedHarvestDate = expectedHarvestDate;
            return this;
        }

        public CropBuilder status(CropStatus status) {
            this.status = status;
            return this;
        }

        public Crop build() {
            return new Crop(id, farmer, name, sowingDate, expectedHarvestDate, status);
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSowingDate() {
        return sowingDate;
    }

    public void setSowingDate(String sowingDate) {
        this.sowingDate = sowingDate;
    }

    public String getExpectedHarvestDate() {
        return expectedHarvestDate;
    }

    public void setExpectedHarvestDate(String expectedHarvestDate) {
        this.expectedHarvestDate = expectedHarvestDate;
    }

    public CropStatus getStatus() {
        return status;
    }

    public void setStatus(CropStatus status) {
        this.status = status;
    }
}
