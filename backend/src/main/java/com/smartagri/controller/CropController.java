package com.smartagri.controller;

import com.smartagri.dto.CropDto;
import com.smartagri.model.CropStatus;
import com.smartagri.model.User;
import com.smartagri.service.CropService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {
	@Autowired
    private  CropService service;

    @GetMapping
    public ResponseEntity<List<CropDto>> getMyCrops(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getMyCrops(user.getId()));
    }

    @PostMapping
    public ResponseEntity<CropDto> addCrop(@AuthenticationPrincipal User user, @RequestBody CropDto dto) {
        return ResponseEntity.ok(service.addCrop(user.getId(), dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CropDto> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        // Expects {"status": "HARVESTED"}
        CropStatus status = CropStatus.valueOf(statusUpdate.get("status"));
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}
