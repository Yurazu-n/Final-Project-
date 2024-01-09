package org.owen;

import org.owen.control.HotelControl;

public class Main {
    public static void main(String[] args) {
        HotelControl hotelControl = new HotelControl(args[0], args[1]);
        hotelControl.execute();
    }
}
