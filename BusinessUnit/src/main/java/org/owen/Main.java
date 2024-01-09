package org.owen;

import org.owen.control.BusinessUnitControl;
import org.owen.control.BusinessUnitExecutionException;

public class Main {
    public static void main(String[] args) throws BusinessUnitExecutionException {
        if (!args[0].isEmpty()) {
            BusinessUnitControl businessUnitControl = new BusinessUnitControl(args[0]);
            businessUnitControl.execute();
        }
    }
}
