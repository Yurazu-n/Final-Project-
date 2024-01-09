package org.owen;

import org.owen.control.DatalakeControl;
import org.owen.control.MyEventException;

public class Main {

    public static void main(String[] args) throws MyEventException {
        DatalakeControl datalakeControl = new DatalakeControl(args[0]);
        datalakeControl.createDataLake();
    }
}
