package com.smartagri.service;

import com.smartagri.dto.CropDto;
import com.smartagri.model.Crop;
import com.smartagri.model.CropStatus;
import com.smartagri.model.User;
import com.smartagri.repository.CropRepository;
import com.smartagri.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropService {
	@Autowired
    private CropRepository cropRepository;
	@Autowired
    private UserRepository userRepository;

    public List<CropDto> getMyCrops(Long farmerId) {
        return cropRepository.findAllByFarmerId(java.util.Objects.requireNonNull(farmerId)).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CropDto addCrop(Long farmerId, CropDto dto) {
        User farmer = userRepository.findById(java.util.Objects.requireNonNull(farmerId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Crop crop = new Crop(
                null,
                farmer,
                dto.getName(),
                dto.getSowingDate() != null ? dto.getSowingDate().toString() : null,
                dto.getExpectedHarvestDate() != null ? dto.getExpectedHarvestDate().toString() : null,
                CropStatus.ACTIVE);

        return mapToDto(java.util.Objects.requireNonNull(cropRepository.save(crop)));
    }

    public CropDto updateStatus(Long id, CropStatus status) {
        Crop crop = cropRepository.findById(java.util.Objects.requireNonNull(id))
                .orElseThrow(() -> new RuntimeException("Crop not found"));
        crop.setStatus(status);
        return mapToDto(cropRepository.save(crop));
    }

    private CropDto mapToDto(Crop crop) {
        return new CropDto(
                crop.getId(),
                crop.getName(),
                crop.getSowingDate() != null ? java.time.LocalDate.parse(crop.getSowingDate()) : null,
                crop.getExpectedHarvestDate() != null ? java.time.LocalDate.parse(crop.getExpectedHarvestDate()) : null,
                crop.getStatus());
    }
}
