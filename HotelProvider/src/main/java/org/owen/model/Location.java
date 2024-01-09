package org.owen.model;

public class Location {
    private double lat, lon;
    private String iataCode;

    public Location(double lat, double lon, String iataCode) {
        this.lat = lat;
        this.lon = lon;
        this.iataCode = iataCode;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getIataCode() {
        return iataCode;
    }
}
