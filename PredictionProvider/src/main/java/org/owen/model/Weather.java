package org.owen.model;

public class Weather {
    private double temp;
    private int humidity;
    private double windSpeed;
    private int clouds;
    private double precipitationProb;
    private String ts, ss, predictionTs;
    private Location location;

    public Weather(double temp, int humidity, double windSpeed, int clouds,
                   double precipitationProb, String ts, String ss, String predictionTs, Location location) {
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.clouds = clouds;
        this.precipitationProb = precipitationProb;
        this.ts = ts;
        this.ss = ss;
        this.predictionTs = predictionTs;
        this.location = location;
    }

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getClouds() {
        return clouds;
    }

    public double getPrecipitationProb() {
        return precipitationProb;
    }

    public String getTs() {return ts;}

    public String getSs() {return ss;}

    public String getPredictionTs() {return predictionTs;}

    public Location getLocation() {return location;}
}
