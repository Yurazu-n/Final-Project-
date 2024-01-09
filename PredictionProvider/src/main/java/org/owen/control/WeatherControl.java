package org.owen.control;
import org.owen.model.*;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WeatherControl {
    private final List<Location> locations = new ArrayList<>(List.of(
            new Location(28.16667, -17.33333, "Tenerife", "TCI"),
            new Location(28.09973, -15.41343, "GranCanaria", "LPA"),
            new Location(28.96302, -13.54769, "Lanzarote", "ACE"),
            new Location(28.50038, -13.86272, "Fuerteventura", "FUE"),
            new Location(28.68351, -17.76421, "LaPalma", "SPC"),
            new Location(27.80628, -17.915779, "ElHierro", "VDE"),
            new Location(28.091631, -17.11331, "LaGomera", "GMZ")));

    private final WeatherProvider weatherProvider;
    private final PredictionPublisher eventPublisher;

    public WeatherControl(String apiKey) {
        this.weatherProvider = new WeatherPredictor(apiKey);
        this.eventPublisher = new PredictionPublisher();
    }

    public void execute() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        LocalTime horaActual = LocalTime.now();
        scheduler.scheduleAtFixedRate(() -> {
            for (Location location : getLocations()) {
                try {
                    List<Weather> weathers = getWeatherProvider().getWeather(location);
                    for (Weather weather : weathers) {
                        getEventPublisher().publishWeather(weather);
                    }
                } catch (WeatherExecutionException e) {
                    System.out.println("Execution Error");
                }
            }
        }, timeUntilMidnight(horaActual), 6, TimeUnit.HOURS);
    }

    private long timeUntilMidnight(LocalTime actualTime) {
        long timeUntilMidnight = actualTime.until(LocalTime.NOON, ChronoUnit.SECONDS);

        if (timeUntilMidnight <= 0) {
            timeUntilMidnight += 24 * 60 * 60;
        }

        return timeUntilMidnight;
    }

    private List<Location> getLocations() {
        return locations;
    }

    private WeatherProvider getWeatherProvider() {
        return weatherProvider;
    }

    private PredictionPublisher getEventPublisher() {
        return eventPublisher;
    }
}


