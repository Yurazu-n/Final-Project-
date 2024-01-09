package org.owen.control;

import org.owen.model.*;

import java.util.List;

public interface WeatherProvider {
    List<Weather> getWeather(Location location) throws WeatherExecutionException;
}

