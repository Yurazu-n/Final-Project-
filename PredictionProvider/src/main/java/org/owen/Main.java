package org.owen;
import org.owen.control.WeatherControl;

public class Main {
    public static void main(String[] args) {
        WeatherControl weatherControl = new WeatherControl(args[0]);
        weatherControl.execute();
    }
}
