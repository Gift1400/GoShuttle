package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.Bus;
import za.ac.cput.GoShuttle.domain.Driver;
import za.ac.cput.GoShuttle.domain.Routes;
import za.ac.cput.GoShuttle.util.Helper;

public class busFactory {
    public static Bus createBus(Long busID, Long busNumber, Routes routeID, Driver driverID, int capacity){
        if (Helper.isEmpty(busID) || Helper.isEmpty(busNumber) ){
            return null;
        }
        if(capacity == 0){
            return null;
        }
        return new Bus.Builder()
                .setBusID(busID)
                .setBusNumber(busNumber)
                .setRoutes(routeID)
                .setDriver(driverID)
                .setCapacity(capacity)
                .Build();
    }
}
