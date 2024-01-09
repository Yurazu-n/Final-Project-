package org.owen.control;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.owen.model.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherPredictor implements WeatherProvider {

    private final String apiKey;

    public WeatherPredictor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<Weather> getWeather(Location location) throws WeatherExecutionException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLat() + "&lon=" +
                location.getLon() + "&units=metric" + "&appid=" + apiKey;
        Request request = new Request.Builder().url(url).build();
        try {
            ResponseBody responseBody = client.newCall(request).execute().body();
            String responseString = responseBody.string();

            JsonArray filteredEntries = predictionFilter(responseString);
            return weatherListContructor(filteredEntries, location);

        } catch (IOException e) {
            throw new WeatherExecutionException("Execution Error");
        }
    }

    private static JsonArray predictionFilter(String response) {
        JsonArray filteredEntries = new JsonArray();
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        JsonArray responseList = jsonObject.getAsJsonArray("list");
        for (JsonElement element : responseList) {
            JsonObject entry = element.getAsJsonObject();
            String dt_txt = entry.get("dt_txt").getAsString();

            if (dt_txt.endsWith("12:00:00")) {
                filteredEntries.add(entry);
            }
        }
        return filteredEntries;
    }

    private static List<Weather> weatherListContructor(JsonArray filteredEntries, Location location) {
        List<Weather> weathers = new ArrayList<>();
        for (JsonElement element : filteredEntries) {
            JsonObject listElement = element.getAsJsonObject();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Instant instant = LocalDateTime.parse(listElement.get("dt_txt").getAsString(), formatter).toInstant(ZoneOffset.UTC);
            String predictionInstant = DateTimeFormatter.ISO_INSTANT.format(instant);

            weathers.add(new Weather(
                    listElement.getAsJsonObject("main").get("temp").getAsDouble(),
                    listElement.getAsJsonObject("main").get("humidity").getAsInt(),
                    listElement.getAsJsonObject("wind").get("speed").getAsDouble(),
                    listElement.getAsJsonObject("clouds").get("all").getAsInt(),
                    listElement.get("pop").getAsInt(),
                    DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
                    "prediction-provider",
                    predictionInstant,
                    location)
            );
        }
        return weathers;
    }
}