package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.Driver;
import za.ac.cput.GoShuttle.util.Helper;

public class driverFactory {
    public static Driver createDriver(Long driverID,String driverName){
        if(Helper.isEmpty(driverID) ){
            return null;
        }
        return new Driver.Builder()
                .setDriverID(driverID)
                .setDriverName(driverName)
                .build();
    }
}
