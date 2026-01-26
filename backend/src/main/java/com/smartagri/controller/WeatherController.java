package com.smartagri.controller;

import com.smartagri.dto.FarmerProfileDto;
import com.smartagri.dto.WeatherAlert;
import com.smartagri.model.User;
import com.smartagri.service.FarmerProfileService;
import com.smartagri.service.WeatherService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {
	@Autowired
    private WeatherService weatherService;
	@Autowired
    private FarmerProfileService profileService;

    @GetMapping("/alerts")
    public ResponseEntity<List<WeatherAlert>> getAlerts(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String location) {
        String queryLocation = location;
        if (queryLocation == null) {
            FarmerProfileDto profile = profileService.getProfile(user.getId());
            if (profile != null && profile.getDistrict() != null) {
                queryLocation = profile.getDistrict();
            } else {
                queryLocation = "Mumbai";
            }
        }
        return ResponseEntity.ok(weatherService.getAlerts(queryLocation));
    }
}
