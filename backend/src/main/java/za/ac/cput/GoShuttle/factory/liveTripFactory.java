package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.LiveTrip;
import za.ac.cput.GoShuttle.util.Helper;

import java.time.LocalDateTime;

public class liveTripFactory {
    public static LiveTrip createLiveTrip(Long tripID, Long busID, Long routeID, Long currentStopID, Helper.liveTripStatus status,
                                          int arrivesInMinute, int toCampusMinutes, LocalDateTime timeStamp){
        if (Helper.isEmpty(tripID) || Helper.isEmpty(busID) || Helper.isEmpty(routeID) || Helper.isEmpty(currentStopID)){
            return null;
        }
        if(Helper.isNull(arrivesInMinute) || Helper.isNull(toCampusMinutes)){
            return null;
        }
        return new LiveTrip.Builder()
                .setTripID(tripID)
                .setBusID(busID)
                .setRouteID(routeID)
                .setCurrentStopID(currentStopID)
                .setStatus(status)
                .setSArrivesInMinute(arrivesInMinute)
                .setToCampusMinutes(toCampusMinutes)
                .setTimeStamp(timeStamp)
                .Build();
    }
}
