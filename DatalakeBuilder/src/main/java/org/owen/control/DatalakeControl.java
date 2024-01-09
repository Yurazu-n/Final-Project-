package org.owen.control;

public class DatalakeControl {

    private WeatherStoreBuilder weatherStoreBuilder;
    private HotelStoreBuilder hotelStoreBuilder;

    public DatalakeControl(String path) {
        this.weatherStoreBuilder = new WeatherStoreBuilder(path);
        this.hotelStoreBuilder = new HotelStoreBuilder(path);
    }

    public void createDataLake() throws MyEventException {
        weatherStoreBuilder.weatherStoreBuild();
        hotelStoreBuilder.hotelStoreBuild();
    }
}
