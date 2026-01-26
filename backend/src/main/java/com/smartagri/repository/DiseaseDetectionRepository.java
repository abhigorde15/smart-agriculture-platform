package com.smartagri.repository;

import com.smartagri.model.DiseaseDetection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseDetectionRepository extends JpaRepository<DiseaseDetection, Long> {
}
