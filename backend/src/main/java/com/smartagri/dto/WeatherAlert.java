package com.smartagri.dto;

public class WeatherAlert {
    private String type; // HEAVY_RAIN, HEATWAVE, HIGH_HUMIDITY
    private String message;
    private String severity; // HIGH, MEDIUM, LOW

    public WeatherAlert() {
    }

    public WeatherAlert(String type, String message, String severity) {
        this.type = type;
        this.message = message;
        this.severity = severity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
