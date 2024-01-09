package org.owen.control;

import org.owen.model.Weather;

public interface EventPublisher {
    void publishWeather(Weather weather) throws WeatherExecutionException;
}
