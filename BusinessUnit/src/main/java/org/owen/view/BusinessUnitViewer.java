package org.owen.view;

import org.owen.control.BusinessUnitExecutionException;
import org.owen.control.SQLiteDataProvider;

import java.util.List;
import java.util.Scanner;

public class BusinessUnitViewer {

    private Scanner scanner;

    public BusinessUnitViewer() {
        this.scanner = new Scanner(System.in);
    }

    public void run(SQLiteDataProvider sqLiteDataProvider) throws BusinessUnitExecutionException {
        while (true) {
            System.out.println("1. What is going to be the weather of the islands the next 5 days?");
            System.out.println("2. Which hotels can I find for each island?");
            System.out.println("3. Exit");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    System.out.println("---------------------");
                    System.out.println("1. Tenerife");
                    System.out.println("2. Gran Canaria");
                    System.out.println("3. Lanzarote");
                    System.out.println("4. Fuerteventura");
                    System.out.println("5. La Palma");
                    System.out.println("6. El Hierro");
                    System.out.println("7. La Gomera");
                    System.out.println("8. Go Back");
                    System.out.println();
                    System.out.println("Insert an number according to the options: ");
                    System.out.println("---------------------");
                    System.out.println();

                    int islandNumber = getUserChoice();
                    switch (islandNumber) {
                        case 1:
                            System.out.println("Showing the table:");
                            List<List<String>> tenerife = sqLiteDataProvider.getDataAsString("SELECT * FROM TenerifeWeather", 0);
                            for (List<String> weatherCase : tenerife) {
                                System.out.println("Temperature: " + weatherCase.get(0));
                                System.out.println("Humidity: " + weatherCase.get(1));
                                System.out.println("Wind Speed: " + weatherCase.get(2));
                                System.out.println("Clouds: " + weatherCase.get(3));
                                System.out.println("Precipitation Probability: " + weatherCase.get(4));
                                System.out.println("Prediction Date: " + weatherCase.get(5));
                                System.out.println("---------------------");
                            }
                            break;
                        case 2:
                            System.out.println("Showing the table:");
                            List<List<String>> granCanaria = sqLiteDataProvider.getDataAsString("SELECT * FROM TenerifeWeather", 0);
                            for (List<String> weatherCase : granCanaria) {
                                System.out.println("Temperature: " + weatherCase.get(0));
                                System.out.println("Humidity: " + weatherCase.get(1));
                                System.out.println("Wind Speed: " + weatherCase.get(2));
                                System.out.println("Clouds: " + weatherCase.get(3));
                                System.out.println("Precipitation Probability: " + weatherCase.get(4));
                                System.out.println("Prediction Date: " + weatherCase.get(5));
                                System.out.println("---------------------");
                            }
                            break;
                        case 3:
                            System.out.println("Showing the table:");
                            List<List<String>> lanzarote = sqLiteDataProvider.getDataAsString("SELECT * FROM TenerifeWeather", 0);
                            for (List<String> weatherCase : lanzarote) {
                                System.out.println("Temperature: " + weatherCase.get(0));
                                System.out.println("Humidity: " + weatherCase.get(1));
                                System.out.println("Wind Speed: " + weatherCase.get(2));
                                System.out.println("Clouds: " + weatherCase.get(3));
                                System.out.println("Precipitation Probability: " + weatherCase.get(4));
                                System.out.println("Prediction Date: " + weatherCase.get(5));
                                System.out.println("---------------------");
                            }
                            break;
                        case 4:
                            System.out.println("Showing the table:");
                            List<List<String>> fuerteventura = sqLiteDataProvider.getDataAsString("SELECT * FROM TenerifeWeather", 0);
                            for (List<String> weatherCase : fuerteventura) {
                                System.out.println("Temperature: " + weatherCase.get(0));
                                System.out.println("Humidity: " + weatherCase.get(1));
                                System.out.println("Wind Speed: " + weatherCase.get(2));
                                System.out.println("Clouds: " + weatherCase.get(3));
                                System.out.println("Precipitation Probability: " + weatherCase.get(4));
                                System.out.println("Prediction Date: " + weatherCase.get(5));
                                System.out.println("---------------------");

                            }
                            break;
                        case 5:
                            System.out.println("Showing the table:");
                            List<List<String>> laPalma = sqLiteDataProvider.getDataAsString("SELECT * FROM TenerifeWeather", 0);
                            for (List<String> weatherCase : laPalma) {
                                System.out.println("Temperature: " + weatherCase.get(0));
                                System.out.println("Humidity: " + weatherCase.get(1));
                                System.out.println("Wind Speed: " + weatherCase.get(2));
                                System.out.println("Clouds: " + weatherCase.get(3));
                                System.out.println("Precipitation Probability: " + weatherCase.get(4));
                                System.out.println("Prediction Date: " + weatherCase.get(5));
                                System.out.println("---------------------");
                            }
                            break;
                        case 6:
                            System.out.println("Showing the table:");
                            List<List<String>> elHierro = sqLiteDataProvider.getDataAsString("SELECT * FROM TenerifeWeather", 0);
                            for (List<String> weatherCase : elHierro) {
                                System.out.println("Temperature: " + weatherCase.get(0));
                                System.out.println("Humidity: " + weatherCase.get(1));
                                System.out.println("Wind Speed: " + weatherCase.get(2));
                                System.out.println("Clouds: " + weatherCase.get(3));
                                System.out.println("Precipitation Probability: " + weatherCase.get(4));
                                System.out.println("Prediction Date: " + weatherCase.get(5));
                                System.out.println("---------------------");
                            }
                            break;
                        case 7:
                            System.out.println("Showing the table:");
                            List<List<String>> laGomera = sqLiteDataProvider.getDataAsString("SELECT * FROM LaGomeraWeather", 0);
                            for (List<String> weatherCase : laGomera) {
                                System.out.println("Temperature: " + weatherCase.get(0));
                                System.out.println("Humidity: " + weatherCase.get(1));
                                System.out.println("Wind Speed: " + weatherCase.get(2));
                                System.out.println("Clouds: " + weatherCase.get(3));
                                System.out.println("Precipitation Probability: " + weatherCase.get(4));
                                System.out.println("Prediction Date: " + weatherCase.get(5));
                                System.out.println("---------------------");
                            }
                            break;
                        case 8:
                            System.out.println("Going back-");
                            break;
                        default:
                            System.out.println("Not valid option, going back-");
                            break;
                    }
                case 2:
                    System.out.println("---------------------");
                    System.out.println("1. Tenerife");
                    System.out.println("2. Gran Canaria");
                    System.out.println("3. Lanzarote");
                    System.out.println("4. Fuerteventura");
                    System.out.println("5. La Palma");
                    System.out.println("6. El Hierro");
                    System.out.println("7. La Gomera");
                    System.out.println("8. Go Back");
                    System.out.println();
                    System.out.println("Insert an number according to the options: ");
                    System.out.println("---------------------");
                    System.out.println();

                    int hotelNumber = getUserChoice();
                    switch (hotelNumber) {
                        case 1:
                            System.out.println("Showing the table:");
                            List<List<String>> tenerife = sqLiteDataProvider.getDataAsString("SELECT * FROM Hotels WHERE iataCode = 'TCI'", 1);
                            if (!tenerife.isEmpty()) {
                                for (List<String> hotel : tenerife) {
                                    System.out.println("Name of the Hotel: " + hotel.get(0));
                                    System.out.println("Stars of the Hotel: " + hotel.get(1));
                                    System.out.println("Location by Latitude & Longitude");
                                    System.out.println("     -Latitude: " + hotel.get(2));
                                    System.out.println("     -Longitude: " + hotel.get(3));
                                    System.out.println("---------------------");
                                }
                            } else {
                                System.out.println("---------------------");
                                System.out.println();
                                System.out.println("Sorry, there are no hotels we can provide you for this island");
                                System.out.println();
                                System.out.println("---------------------");
                            }
                            System.out.println();
                            break;
                        case 2:
                            System.out.println("Showing the table:");
                            List<List<String>> granCanaria = sqLiteDataProvider.getDataAsString("SELECT * FROM Hotels WHERE iataCode = 'LPA'", 1);
                            if (!granCanaria.isEmpty()) {
                                for (List<String> hotel : granCanaria) {
                                    System.out.println("Name of the Hotel: " + hotel.get(0));
                                    System.out.println("Stars of the Hotel: " + hotel.get(1));
                                    System.out.println("Location by Latitude & Longitude");
                                    System.out.println("     -Latitude: " + hotel.get(2));
                                    System.out.println("     -Longitude: " + hotel.get(3));
                                    System.out.println("---------------------");
                                }
                            } else {
                                System.out.println("---------------------");
                                System.out.println();
                                System.out.println("Sorry, there are no hotels we can provide you for this island");
                                System.out.println();
                                System.out.println("---------------------");
                            }
                            System.out.println();
                            break;
                        case 3:
                            System.out.println("Showing the table:");
                            List<List<String>> lanzarote = sqLiteDataProvider.getDataAsString("SELECT * FROM Hotels WHERE iataCode = 'ACE'", 1);
                            if (!lanzarote.isEmpty()) {
                                for (List<String> hotel : lanzarote) {
                                    System.out.println("Name of the Hotel: " + hotel.get(0));
                                    System.out.println("Stars of the Hotel: " + hotel.get(1));
                                    System.out.println("Location by Latitude & Longitude");
                                    System.out.println("     -Latitude: " + hotel.get(2));
                                    System.out.println("     -Longitude: " + hotel.get(3));
                                    System.out.println("---------------------");
                                }
                            } else {
                                System.out.println("---------------------");
                                System.out.println();
                                System.out.println("Sorry, there are no hotels we can provide you for this island");
                                System.out.println();
                                System.out.println("---------------------");
                            }
                            break;
                        case 4:
                            System.out.println("Showing the table:");
                            List<List<String>> fuerteventura = sqLiteDataProvider.getDataAsString("SELECT * FROM Hotels WHERE iataCode = 'FUE'", 1);
                            if (!fuerteventura.isEmpty()) {
                                for (List<String> hotel : fuerteventura) {
                                    System.out.println("Name of the Hotel: " + hotel.get(0));
                                    System.out.println("Stars of the Hotel: " + hotel.get(1));
                                    System.out.println("Location by Latitude & Longitude");
                                    System.out.println("     -Latitude: " + hotel.get(2));
                                    System.out.println("     -Longitude: " + hotel.get(3));
                                    System.out.println("---------------------");
                                }
                            } else {
                                System.out.println("---------------------");
                                System.out.println();
                                System.out.println("Sorry, there are no hotels we can provide you for this island");
                                System.out.println();
                                System.out.println("---------------------");
                            }
                            System.out.println();
                            break;
                        case 5:
                            System.out.println("Showing the table:");
                            List<List<String>> laPalma = sqLiteDataProvider.getDataAsString("SELECT * FROM Hotels WHERE iataCode = 'SPC'", 1);
                            if (!laPalma.isEmpty()) {
                                for (List<String> hotel : laPalma) {
                                    System.out.println("Name of the Hotel: " + hotel.get(0));
                                    System.out.println("Stars of the Hotel: " + hotel.get(1));
                                    System.out.println("Location by Latitude & Longitude");
                                    System.out.println("     -Latitude: " + hotel.get(2));
                                    System.out.println("     -Longitude: " + hotel.get(3));
                                    System.out.println("---------------------");
                                }
                            } else {
                                System.out.println("---------------------");
                                System.out.println();
                                System.out.println("Sorry, there are no hotels we can provide you for this island");
                                System.out.println();
                                System.out.println("---------------------");
                            }
                            break;
                        case 6:
                            System.out.println("Showing the table:");
                            List<List<String>> elHierro = sqLiteDataProvider.getDataAsString("SELECT * FROM Hotels WHERE iataCode = 'VDE'", 1);
                            if (!elHierro.isEmpty()) {
                                for (List<String> hotel : elHierro) {
                                    System.out.println("Name of the Hotel: " + hotel.get(0));
                                    System.out.println("Stars of the Hotel: " + hotel.get(1));
                                    System.out.println("Location by Latitude & Longitude");
                                    System.out.println("     -Latitude: " + hotel.get(2));
                                    System.out.println("     -Longitude: " + hotel.get(3));
                                }
                            } else {
                                System.out.println("---------------------");
                                System.out.println();
                                System.out.println("Sorry, there are no hotels we can provide you for this island");
                                System.out.println();
                                System.out.println("---------------------");
                            }
                            System.out.println();
                            break;
                        case 7:
                            System.out.println("Showing the table:");
                            List<List<String>> laGomera = sqLiteDataProvider.getDataAsString("SELECT * FROM Hotels WHERE iataCode = 'GMZ'", 1);
                            if (!laGomera.isEmpty()) {
                                for (List<String> hotel : laGomera) {
                                    System.out.println("Name of the Hotel: " + hotel.get(0));
                                    System.out.println("Stars of the Hotel: " + hotel.get(1));
                                    System.out.println("Location by Latitude & Longitude");
                                    System.out.println("     -Latitude: " + hotel.get(2));
                                    System.out.println("     -Longitude: " + hotel.get(3));
                                    System.out.println();
                                }
                            } else {
                                System.out.println("---------------------");
                                System.out.println();
                                System.out.println("Sorry, there are no hotels we can provide you for this island");
                                System.out.println();
                                System.out.println("---------------------");
                            }
                            break;
                        case 8:
                            System.out.println("Going back-");
                            break;
                        default:
                            System.out.println("Not valid option, going back-");
                            break;
                    }
            }
        }
    }

    private int getUserChoice() {
        System.out.print("Write a number according to the options above: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

