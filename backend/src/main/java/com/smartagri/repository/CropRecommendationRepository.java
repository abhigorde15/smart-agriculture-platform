package com.smartagri.repository;

import com.smartagri.model.CropRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRecommendationRepository extends JpaRepository<CropRecommendation, Long> {
}
