package org.owen.control;
import org.owen.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HotelControl {
    private final List<Location> locations = new ArrayList<>(List.of(
            new Location(28.16667, -17.33333, "TCI"),
            new Location(28.09973, -15.41343, "LPA"),
            new Location(28.96302, -13.54769, "ACE"),
            new Location(28.50038, -13.86272, "FUE"),
            new Location(28.68351, -17.76421, "SPC"),
            new Location(27.80628, -17.915779, "VDE"),
            new Location(28.091631, -17.11331, "GMZ")));

    private HotelSearcher hotelSearcher;
    private HotelPublisher hotelPublisher;

    public HotelControl(String apikey, String apiSecret) {
        this.hotelSearcher = new HotelSearcher(apikey, apiSecret);
        this.hotelPublisher = new HotelPublisher();
    }

    public void execute(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable updateTask = () -> {
            for (Location location : getLocations()) {
                try {
                    List<Hotel> hotels = getHotelSearcher().getHotels(location);
                    for (Hotel hotel : hotels){
                        getHotelPublisher().publishHotel(hotel);
                    }
                } catch (HotelExecutionException e) {
                    System.out.println("Execution Error");
                }
            }
        };

        scheduler.scheduleAtFixedRate(updateTask, 0, 3, TimeUnit.HOURS);
    }

    private List<Location> getLocations() {
        return locations;
    }

    private HotelSearcher getHotelSearcher() {
        return hotelSearcher;
    }

    private HotelPublisher getHotelPublisher() {
        return hotelPublisher;
    }
}
