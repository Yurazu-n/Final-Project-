package org.owen.control;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WeatherDataBase {

    private final String path;

    public WeatherDataBase(String path) {
        this.path = path;
    }

    public void save(String message) throws BusinessUnitExecutionException {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        String islandName = jsonObject.getAsJsonObject("location").get("islandName").getAsString();
        Statement statement = dataBaseCreator(path, islandName);

        String query = "SELECT COUNT(*) FROM " + islandName + "Weather"
                + " WHERE predictionTs = '" + jsonObject.get("predictionTs").getAsString() + "';";

        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                if (resultSet.getInt(1) > 0) {
                    update(statement, message);
                } else {
                    insert(statement, message);
                }
            }
        } catch (SQLException e) {
            throw new BusinessUnitExecutionException("Error");
        }
    }

    private static Statement dataBaseCreator(String path, String islandName) throws BusinessUnitExecutionException {
        Statement statement = null;
        try {
            String url = "jdbc:sqlite:" + path + "/Datamart.db";
            statement = DriverManager.getConnection(url).createStatement();
            createTable(statement, islandName);

        } catch (SQLException e) {
            throw new BusinessUnitExecutionException("Execution Error");
        }
        return statement;
    }

    private static void createTable(Statement statement, String islandName) throws BusinessUnitExecutionException {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS " + islandName + "Weather" + " (" +
                    "predictionTs TEXT PRIMARY KEY, \n" +
                    "temperature REAL,\n" +
                    "humidity INTEGER,\n" +
                    "windSpeed REAL,\n" +
                    "clouds INTEGER,\n" +
                    "precipitationProb REAL,\n" +
                    "islandName TEXT,\n" +
                    "iataCode TEXT, \n" +
                    "latitude REAL, \n" +
                    "longitude REAL)"
            );
        } catch (SQLException e) {
            throw new BusinessUnitExecutionException("Execution Error");
        }
    }

    private static void insert(Statement statement, String message) throws BusinessUnitExecutionException {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        try {
            statement.execute("INSERT INTO " + jsonObject.getAsJsonObject("location").get("islandName").getAsString() + "Weather" +
                    " (temperature, humidity, windSpeed, clouds, precipitationProb, islandName, iataCode, predictionTs, latitude, longitude)\n" +
                    "VALUES(" + jsonObject.get("temp").getAsDouble() + "," +
                    jsonObject.get("humidity").getAsInt() + "," +
                    jsonObject.get("windSpeed").getAsDouble() + "," +
                    jsonObject.get("clouds").getAsInt() + "," +
                    jsonObject.get("precipitationProb").getAsDouble() + "," +
                    "'" + jsonObject.getAsJsonObject("location").get("islandName").getAsString() + "'," +
                    "'" + jsonObject.getAsJsonObject("location").get("iataCode").getAsString() + "', " +
                    "'" + jsonObject.get("predictionTs").getAsString() + "'," +
                    jsonObject.getAsJsonObject("location").get("lat").getAsDouble() + "," +
                    jsonObject.getAsJsonObject("location").get("lon").getAsDouble() + ");"
            );
        } catch (SQLException e) {
            throw new BusinessUnitExecutionException("Execution Error");
        }
    }

    private static void update(Statement statement, String message) throws BusinessUnitExecutionException {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        try {
            statement.execute("UPDATE " + jsonObject.getAsJsonObject("location").get("islandName").getAsString() + "Weather" +
                    " SET temperature = " + jsonObject.get("temp").getAsDouble() +
                    ", humidity = " + jsonObject.get("humidity").getAsInt() +
                    ", windSpeed = " + jsonObject.get("windSpeed").getAsDouble() +
                    ", clouds = " + jsonObject.get("clouds").getAsInt() +
                    ", precipitationProb = " + jsonObject.get("precipitationProb").getAsDouble() +
                    ", islandName = '" + jsonObject.getAsJsonObject("location").get("islandName").getAsString() + "'" +
                    ", iataCode = '" + jsonObject.getAsJsonObject("location").get("iataCode").getAsString() + "'" +
                    ", latitude = " + jsonObject.getAsJsonObject("location").get("lat").getAsDouble() +
                    ", longitude = " + jsonObject.getAsJsonObject("location").get("lon").getAsDouble() +
                    " WHERE predictionTs = '" + jsonObject.get("predictionTs").getAsString() + "';");
        } catch (SQLException e) {
            throw new BusinessUnitExecutionException("Execution Error");
        }
    }
}
