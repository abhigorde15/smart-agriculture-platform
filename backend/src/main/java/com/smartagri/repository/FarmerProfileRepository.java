package com.smartagri.repository;

import com.smartagri.model.FarmerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FarmerProfileRepository extends JpaRepository<FarmerProfile, Long> {
    Optional<FarmerProfile> findByUserId(Long userId);
}
