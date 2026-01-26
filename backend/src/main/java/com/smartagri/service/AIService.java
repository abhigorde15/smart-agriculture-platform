package com.smartagri.service;

import com.smartagri.dto.CropRecommendationRequest;
import com.smartagri.dto.CropRecommendationResponse;
import com.smartagri.dto.DiseaseDetectionResponse;
import com.smartagri.model.CropRecommendation;
import com.smartagri.model.DiseaseDetection;
import com.smartagri.model.User;
import com.smartagri.repository.CropRecommendationRepository;
import com.smartagri.repository.DiseaseDetectionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class AIService {
	@Autowired
    private  RestTemplate restTemplate;
	@Autowired
    private CropRecommendationRepository cropRecommendationRepository;
	@Autowired
    private DiseaseDetectionRepository diseaseDetectionRepository;
	@Autowired
    private CloudinaryService cloudinaryService;

    @Value("${ml.service.crop-recommendation.url}")
    private String cropRecommendationUrl;

    @Value("${ml.service.disease-detection.url}")
    private String diseaseDetectionUrl;

    public CropRecommendationResponse getCropRecommendation(CropRecommendationRequest request, User user) {
        CropRecommendationResponse responseBody = null;
        try {
        	System.out.println(request.toString());
        	ResponseEntity<CropRecommendationResponse> response = restTemplate
                    .postForEntity(java.util.Objects.requireNonNull(cropRecommendationUrl), request,
                            CropRecommendationResponse.class);
            responseBody = response.getBody();
           

            if (responseBody != null && responseBody.getConfidence() == null) {
                responseBody.setConfidence(0.85); // default confidence
            }

        } catch (Exception e) {
            // ML Service unreachable
        }

        if (responseBody == null) {
            responseBody = new CropRecommendationResponse(
                    "Rice (Fallback - ML Service Unavailable)",
                    0.85);
        }

        // Save history
        CropRecommendation history = new CropRecommendation(
                null,
                user,
                responseBody.getRecommendedCrop(),
                responseBody.getConfidence());
        cropRecommendationRepository.save(history);

        return responseBody;
    }

    public DiseaseDetectionResponse detectDisease(MultipartFile file, User user) throws IOException {

        
        String imageUrl = cloudinaryService.uploadFile(file);
       

        DiseaseDetectionResponse responseBody = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Create temp file
            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());

            // IMPORTANT: HF expects "image" key, NOT "file"
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new FileSystemResource(tempFile.toFile()));

            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

          
            ResponseEntity<com.smartagri.dto.HFDiseaseResponse> response =
                    restTemplate.postForEntity(
                            java.util.Objects.requireNonNull(diseaseDetectionUrl),
                            requestEntity,
                            com.smartagri.dto.HFDiseaseResponse.class
                    );

            com.smartagri.dto.HFDiseaseResponse hfResponse = response.getBody();

        

            if (hfResponse != null && hfResponse.getPredicted_class() != null) {

                // Clean & format disease name
                String diseaseName = hfResponse.getPredicted_class()
                        .replace("___", " - ")
                        .replace("_", " ");

                responseBody = new DiseaseDetectionResponse(
                        diseaseName,
                        hfResponse.getConfidence() / 100.0, // Convert 100 → 1.0
                        "Consult an agriculture expert for proper treatment."
                );
            }

            
            Files.deleteIfExists(tempFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

       
        if (responseBody == null) {
            responseBody = new DiseaseDetectionResponse(
                    "Leaf Blight (Fallback - ML Service Unavailable)",
                    0.91,
                    "Use appropriate fungicide."
            );
        }

        // Save history
        DiseaseDetection history = new DiseaseDetection(
                null,
                user,
                imageUrl,
                responseBody.getDisease(),
                responseBody.getConfidence(),
                responseBody.getSuggestion()
        );

        diseaseDetectionRepository.save(history);

        return responseBody;
    }

}
