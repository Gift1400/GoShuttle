package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.Bus;
import za.ac.cput.GoShuttle.domain.LiveTrip;
import za.ac.cput.GoShuttle.domain.RouteStops;
import za.ac.cput.GoShuttle.domain.Routes;
import za.ac.cput.GoShuttle.util.Helper;

import java.time.LocalDateTime;

public class liveTripFactory {
    public static LiveTrip createLiveTrip(Long tripID, Bus busID, Routes routeID, RouteStops currentStopID, Helper.liveTripStatus status,
                                          int arrivesInMinute, int toCampusMinutes, LocalDateTime timeStamp){
        if (Helper.isEmpty(tripID) ){
            return null;
        }
        if(Helper.isNull(arrivesInMinute) || Helper.isNull(toCampusMinutes)){
            return null;
        }
        return new LiveTrip.Builder()
                .setTripID(tripID)
                .setBus(busID)
                .setRoute(routeID)
                .setCurrentStop(currentStopID)
                .setStatus(status)
                .setArrivesInMinutes(arrivesInMinute)
                .setToCampusMinutes(toCampusMinutes)
                .setTimestamp(timeStamp)
                .build();
    }
}
