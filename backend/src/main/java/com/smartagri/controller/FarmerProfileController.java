package com.smartagri.controller;

import com.smartagri.dto.FarmerProfileDto;
import com.smartagri.model.User;
import com.smartagri.service.FarmerProfileService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class FarmerProfileController {
	@Autowired
    private  FarmerProfileService service;

    @GetMapping("/me")
    public ResponseEntity<FarmerProfileDto> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getProfile(user.getId()));
    }

    @PostMapping
    public ResponseEntity<FarmerProfileDto> updateProfile(@AuthenticationPrincipal User user,
            @RequestBody FarmerProfileDto dto) {
        return ResponseEntity.ok(service.createOrUpdateProfile(user.getId(), dto));
    }
}
