package org.owen.control;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HotelDataBase {

    private final String path;

    public HotelDataBase(String path) {
        this.path = path;
    }

    public void save(String message) throws BusinessUnitExecutionException {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        Statement statement = dataBaseCreator(path);

        String query = "SELECT COUNT(*) FROM Hotels"
                + " WHERE hotelId = '" + jsonObject.get("id").getAsString() + "';";

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

    private static Statement dataBaseCreator(String path) throws BusinessUnitExecutionException {
        Statement statement = null;
        try {
            String url = "jdbc:sqlite:" + path + "/Datamart.db";
            statement = DriverManager.getConnection(url).createStatement();
            createTable(statement);

        } catch (SQLException e) {
            throw new BusinessUnitExecutionException("Execution Error");
        }
        return statement;
    }

    private static void createTable(Statement statement) throws BusinessUnitExecutionException {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS Hotels (" +
                    "hotelId TEXT PRIMARY KEY, " +
                    "name TEXT, " +
                    "iataCode TEXT," +
                    "stars INTEGER," +
                    "latitude REAL, " +
                    "longitude REAL)"
            );
        } catch (SQLException e) {
            throw new BusinessUnitExecutionException("Execution Error");
        }
    }

    private static void insert(Statement statement, String message) throws BusinessUnitExecutionException {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        try {
            statement.execute("INSERT INTO Hotels (hotelId, name, iataCode, stars, latitude, longitude) " +
                    "VALUES('" + jsonObject.get("id").getAsString() + "', " +
                    "'" + jsonObject.get("name").getAsString() + "', " +
                    "'" + jsonObject.getAsJsonObject("location").get("iataCode").getAsString() + "', " +
                    jsonObject.get("stars").getAsInt() + ", " +
                    jsonObject.getAsJsonObject("location").get("lat").getAsDouble() + ", " +
                    jsonObject.getAsJsonObject("location").get("lon").getAsDouble()
                    + ");"
            );
        } catch (SQLException e) {
            throw new BusinessUnitExecutionException("Execution Error");
        }
    }

    private static void update(Statement statement, String message) throws BusinessUnitExecutionException {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        try {
            statement.execute("UPDATE Hotels" +
                    " SET hotelId = '" + jsonObject.get("id").getAsString() + "'" +
                    ", name = '" + jsonObject.get("name").getAsString() + "'" +
                    ", iataCode = '" + jsonObject.getAsJsonObject("location").get("iataCode").getAsString() + "'" +
                    ", stars = " + jsonObject.get("stars").getAsInt() +
                    ", latitude = " + jsonObject.getAsJsonObject("location").get("lat").getAsDouble() +
                    ", longitude = " + jsonObject.getAsJsonObject("location").get("lon").getAsDouble() +
                    " WHERE hotelId = '" + jsonObject.get("id").getAsString() + "';");
        } catch (SQLException e) {
            throw new BusinessUnitExecutionException("Execution Error");
        }
    }
}

