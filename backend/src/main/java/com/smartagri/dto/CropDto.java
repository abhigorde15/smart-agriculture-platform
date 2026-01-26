package com.smartagri.dto;

import com.smartagri.model.CropStatus;
import java.time.LocalDate;

public class CropDto {
    private Long id;
    private String name;
    private LocalDate sowingDate;
    private LocalDate expectedHarvestDate;
    private CropStatus status;

    public CropDto() {
    }

    public CropDto(Long id, String name, LocalDate sowingDate, LocalDate expectedHarvestDate, CropStatus status) {
        this.id = id;
        this.name = name;
        this.sowingDate = sowingDate;
        this.expectedHarvestDate = expectedHarvestDate;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getSowingDate() {
        return sowingDate;
    }

    public void setSowingDate(LocalDate sowingDate) {
        this.sowingDate = sowingDate;
    }

    public LocalDate getExpectedHarvestDate() {
        return expectedHarvestDate;
    }

    public void setExpectedHarvestDate(LocalDate expectedHarvestDate) {
        this.expectedHarvestDate = expectedHarvestDate;
    }

    public CropStatus getStatus() {
        return status;
    }

    public void setStatus(CropStatus status) {
        this.status = status;
    }
}
