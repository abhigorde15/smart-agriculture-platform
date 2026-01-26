package com.smartagri.controller;

import com.smartagri.dto.UserDto;
import com.smartagri.model.CropRecommendation;
import com.smartagri.model.DiseaseDetection;
import com.smartagri.repository.CropRecommendationRepository;
import com.smartagri.repository.DiseaseDetectionRepository;
import com.smartagri.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private CropRecommendationRepository cropRecommendationRepository;
	@Autowired
    private DiseaseDetectionRepository diseaseDetectionRepository;

    @GetMapping("/farmers")
    public List<UserDto> getAllFarmers() {
        return userRepository.findAll().stream()
                .map(u -> new UserDto(
                        u.getId(),
                        u.getFullName(),
                        u.getEmail(),
                        u.getRole()))
                .collect(Collectors.toList());
    }

    @GetMapping("/predictions/crops")
    public List<CropRecommendation> getAllCropRecommendations() {
        return cropRecommendationRepository.findAll();
    }

    @GetMapping("/predictions/diseases")
    public List<DiseaseDetection> getAllDiseaseDetections() {
        return diseaseDetectionRepository.findAll();
    }
}
