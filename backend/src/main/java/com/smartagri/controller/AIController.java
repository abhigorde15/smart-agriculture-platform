package com.smartagri.controller;

import com.smartagri.dto.CropRecommendationRequest;
import com.smartagri.dto.CropRecommendationResponse;
import com.smartagri.dto.DiseaseDetectionResponse;
import com.smartagri.model.User;
import com.smartagri.service.AIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai")
public class AIController {
	@Autowired
    private AIService aiService;

    @PostMapping("/recommend")
    public ResponseEntity<CropRecommendationResponse> recommendCrop(
            @RequestBody CropRecommendationRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(aiService.getCropRecommendation(request, user));
    }

    @PostMapping("/detect")
    public ResponseEntity<DiseaseDetectionResponse> detectDisease(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user) throws IOException {
        return ResponseEntity.ok(aiService.detectDisease(file, user));
    }
}
