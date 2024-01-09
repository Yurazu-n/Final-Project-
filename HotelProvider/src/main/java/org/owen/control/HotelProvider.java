package org.owen.control;
import org.owen.model.Hotel;
import org.owen.model.Location;

import java.util.List;

public interface HotelProvider {
    List<Hotel> getHotels(Location location) throws HotelExecutionException;
}

