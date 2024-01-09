package org.owen.model;

public class Location {

    private Double lat, lon;
    private String islandName;
    private String iataCode;

    public Location(Double lat, Double lon, String islandName, String iataCode) {
        this.lat = lat;
        this.lon = lon;
        this.islandName = islandName;
        this.iataCode = iataCode;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getIslandName() {
        return islandName;
    }

    public String getIataCode() {
        return iataCode;
    }
}

