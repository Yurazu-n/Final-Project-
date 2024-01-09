package org.owen.control;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDataProvider {

    private String path;

    public SQLiteDataProvider(String path) {
        this.path = path;
    }

    public List<List<String>> getDataAsString(String sentence, int number) throws BusinessUnitExecutionException {
        List<List<String>> dataList = new ArrayList<>();

        try {
            if (number == 1) {

                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:" + path + "/Datamart.db";

                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sentence);


                while (resultSet.next()) {
                    List<String> hotelResult = new ArrayList<>();
                    if (resultSet.getString("name") != null) {
                        String name = resultSet.getString("name");
                        String stars = resultSet.getString("stars");
                        String latitude = resultSet.getString("latitude");
                        String longitude = resultSet.getString("longitude");

                        hotelResult.add(name);
                        hotelResult.add(stars);
                        hotelResult.add(latitude);
                        hotelResult.add(longitude);
                    }
                    dataList.add(hotelResult);
                }
                return dataList;

            } else {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:" + path + "/Datamart.db";

                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String currentDateString = currentDate.format(formatter);

                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sentence + " WHERE predictionTs >= '" + currentDateString + "';");


                while (resultSet.next()) {
                    List<String> weatherResult = new ArrayList<>();
                    if (resultSet.getString("temperature") != null) {

                        String temperature = resultSet.getString("temperature");
                        String humidity = resultSet.getString("humidity");
                        String windSpeed = resultSet.getString("windSpeed");
                        String clouds = resultSet.getString("clouds");
                        String precipitationProb = resultSet.getString("precipitationProb");
                        String predictionTs = resultSet.getString("predictionTs");

                        weatherResult.add(temperature);
                        weatherResult.add(humidity);
                        weatherResult.add(windSpeed);
                        weatherResult.add(clouds);
                        weatherResult.add(precipitationProb);
                        weatherResult.add(predictionTs);

                    }
                    dataList.add(weatherResult);
                }
                return dataList;
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new BusinessUnitExecutionException("Error obtaining viewable data");
        }
    }
}

