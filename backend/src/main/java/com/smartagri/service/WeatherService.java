package com.smartagri.service;

import com.smartagri.dto.WeatherAlert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<WeatherAlert> getAlerts(String location) {
        List<WeatherAlert> alerts = new ArrayList<>();
        if (location == null || location.isEmpty()) {
            return alerts;
        }

        try {
            String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, location, apiKey);
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map body = response.getBody();

            if (body != null) {
                // Parse Weather Data
                List<Map> weatherList = (List<Map>) body.get("weather");
                Map main = (Map) body.get("main");

                if (weatherList != null && !weatherList.isEmpty()) {
                    String mainWeather = (String) weatherList.get(0).get("main");
                    if ("Rain".equalsIgnoreCase(mainWeather) || "Thunderstorm".equalsIgnoreCase(mainWeather)
                            || "Drizzle".equalsIgnoreCase(mainWeather)) {
                        alerts.add(new WeatherAlert(
                                "HEAVY_RAIN",
                                "Rain detected in " + location + ". Ensure drainage.",
                                "HIGH"));
                    } else if ("Clear".equalsIgnoreCase(mainWeather)) {
                        alerts.add(new WeatherAlert(
                                "CLEAR_SKY",
                                "Clear skies in " + location + ". Good for spraying.",
                                "LOW"));
                    }
                }

                if (main != null) {
                    Number humidity = (Number) main.get("humidity");
                    Number temp = (Number) main.get("temp");
                    double temperature = temp != null ? temp.doubleValue() : 0;

                    if (humidity != null && humidity.doubleValue() > 80) {
                        alerts.add(new WeatherAlert(
                                "HIGH_HUMIDITY",
                                "High humidity detected. Watch out for fungal diseases.",
                                "MEDIUM"));
                    }

                    if (temperature > 35) {
                        alerts.add(new WeatherAlert(
                                "HEAT_WAVE",
                                "High temperatures expected. Ensure irrigation.",
                                "HIGH"));
                    }
                }
            }

        } catch (Exception e) {
            alerts.add(new WeatherAlert(
                    "SERVICE_UNAVAILABLE",
                    "Could not fetch weather for " + location + ". " + e.getMessage(),
                    "LOW"));
        }
        return alerts;
    }
}
