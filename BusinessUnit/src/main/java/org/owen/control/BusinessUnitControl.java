package org.owen.control;

import org.owen.view.BusinessUnitViewer;

import java.nio.file.*;

public class BusinessUnitControl {

    private static WeatherMessageSaver weatherMessageConsumer;
    private static HotelMessageSaver hotelMessageConsumer;
    private static BusinessUnitViewer businessUnitViewer;
    private static SQLiteDataProvider sqLiteDataProvider;

    public BusinessUnitControl(String path) {
        this.weatherMessageConsumer = new WeatherMessageSaver(path);
        this.hotelMessageConsumer = new HotelMessageSaver(path);
        this.businessUnitViewer = new BusinessUnitViewer();
        this.sqLiteDataProvider = new SQLiteDataProvider(path);
    }

    public void execute() throws BusinessUnitExecutionException {
        getWeatherMessageConsumer().saveMessages();
        getHotelMessageConsumer().saveMessages();
        getBusinessUnitViewer().run(sqLiteDataProvider);
    }

    private static WeatherMessageSaver getWeatherMessageConsumer() {
        return weatherMessageConsumer;
    }

    private static HotelMessageSaver getHotelMessageConsumer() {
        return hotelMessageConsumer;
    }

    private static BusinessUnitViewer getBusinessUnitViewer() {
        return businessUnitViewer;
    }
}
