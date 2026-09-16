package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.Routes;
import za.ac.cput.GoShuttle.util.Helper;

import java.sql.Time;

public class RoutesFactory {
    public static Routes createRoutes(int routesId, String code,
                                      String name, int frequencyMinute,
                                      int durationMinutes, Time serviceStart,
                                      Time serviceEnd){

        if(Helper.isNull(routesId)){
            return null;
        }

        return new Routes.Builder()
                .setRouteId(routesId)
                .setCode(code)
                .setName(name)
                .setFrequencyMinutes(frequencyMinute)
                .setDurationMinutes(durationMinutes)
                .setServiceStart(serviceStart)
                .setServiceEnd(serviceEnd)
                .build();
    }
}
