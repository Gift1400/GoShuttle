package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.Driver;
import za.ac.cput.GoShuttle.util.Helper;

public class driverFactory {
    public static Driver createDriver(Long driverID,Long driverName){
        if(Helper.isEmpty(driverID) || Helper.isEmpty(driverName)){
            return null;
        }
        return new Driver.Builder()
                .setDriverID(driverID)
                .setDriverName(driverName)
                .Build();
    }
}
