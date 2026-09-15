package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.Bus;
import za.ac.cput.GoShuttle.util.Helper;

public class busFactory {
    public static Bus createBus(Long busID,Long busNumber,Long routeID,Long driverID,int capacity){
        if (Helper.isEmpty(busID) || Helper.isEmpty(busNumber) || Helper.isEmpty(routeID) || Helper.isEmpty(driverID)){
            return null;
        }
        if(capacity == 0){
            return null;
        }
        return new Bus.Builder()
                .setBusID(busID)
                .setBusNumber(busNumber)
                .setRouteID(routeID)
                .setDriverID(driverID)
                .setCapacity(capacity)
                .Build();
    }
}
