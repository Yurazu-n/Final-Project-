package org.owen.control;

import org.owen.model.Hotel;

public interface HotelPublish {
    void publishHotel(Hotel hotel) throws HotelExecutionException;
}
