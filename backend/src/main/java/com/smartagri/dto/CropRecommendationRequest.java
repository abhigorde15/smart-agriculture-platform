package com.smartagri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CropRecommendationRequest {
	 @JsonProperty("N")
	 private Double N;

	 @JsonProperty("P")
	 private Double P;

	 @JsonProperty("K")
	 private Double K;

    private Double temperature;
    private Double humidity;
    private Double ph;
    private Double rainfall;
 

    public CropRecommendationRequest() {
    }

    public CropRecommendationRequest(Double nitrogen, Double phosphorus, Double potassium, Double temperature,
            Double humidity, Double ph, Double rainfall) {
        this.N = nitrogen;
        this.P = phosphorus;
        this.K = potassium;
        this.temperature = temperature;
        this.humidity = humidity;
        this.ph = ph;
        this.rainfall = rainfall;
    }

    public Double getN() {
        return N;
    }
    public void setN(Double n) {
        this.N = n;
    }

    public Double getP() {
        return P;
    }
    public void setP(Double p) {
        this.P = p;
    }

    public Double getK() {
        return K;
    }
    public void setK(Double k) {
        this.K = k;
    }


    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Double getRainfall() {
        return rainfall;
    }

    public void setRainfall(Double rainfall) {
        this.rainfall = rainfall;
    }

	@Override
	public String toString() {
		return "CropRecommendationRequest [N=" + N + ", P=" + P + ", K=" + K + ", temperature=" + temperature
				+ ", humidity=" + humidity + ", ph=" + ph + ", rainfall=" + rainfall + "]";
	}

}
