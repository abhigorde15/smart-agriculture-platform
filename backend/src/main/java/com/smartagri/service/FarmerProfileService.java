package com.smartagri.service;

import com.smartagri.dto.FarmerProfileDto;
import com.smartagri.model.FarmerProfile;
import com.smartagri.model.User;
import com.smartagri.repository.FarmerProfileRepository;
import com.smartagri.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FarmerProfileService {
	@Autowired
    private FarmerProfileRepository profileRepository;
	@Autowired
    private UserRepository userRepository;

    public FarmerProfileDto getProfile(Long userId) {
        FarmerProfile profile = profileRepository.findByUserId(userId)
                .orElse(null);

        if (profile == null) {
            return null;
        }

        return mapToDto(profile);
    }

    @Transactional
    public FarmerProfileDto createOrUpdateProfile(Long userId, FarmerProfileDto dto) {
        User user = userRepository.findById(java.util.Objects.requireNonNull(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        FarmerProfile profile = profileRepository.findByUserId(userId)
                .orElse(new FarmerProfile(
                        null, user, null, null, null, null, null, null, null));

        profile.setLandArea(dto.getLandArea());
        profile.setLocation(dto.getLocation()); // Added map
        if (dto.getSoilType() != null) {
            try {
                profile.setSoilType(com.smartagri.model.SoilType.valueOf(dto.getSoilType()));
            } catch (IllegalArgumentException e) {
                // handle invalid enum
            }
        }
        if (dto.getIrrigationMethod() != null) {
            try {
                profile.setIrrigationMethod(com.smartagri.model.IrrigationMethod.valueOf(dto.getIrrigationMethod()));
            } catch (IllegalArgumentException e) {
                // handle invalid enum
            }
        }
        profile.setState(dto.getState());
        profile.setDistrict(dto.getDistrict());
        profile.setPreferredLanguage(dto.getPreferredLanguage());

        FarmerProfile saved = profileRepository.save(profile);
        return mapToDto(saved);
    }

    private FarmerProfileDto mapToDto(FarmerProfile profile) {
        return new FarmerProfileDto(
                profile.getId(),
                profile.getUser().getId(),
                profile.getLandArea(),
                profile.getLocation(),
                profile.getSoilType() != null ? profile.getSoilType().name() : null,
                profile.getIrrigationMethod() != null ? profile.getIrrigationMethod().name() : null,
                profile.getPreferredLanguage(),
                profile.getDistrict(),
                profile.getState());
    }
}
