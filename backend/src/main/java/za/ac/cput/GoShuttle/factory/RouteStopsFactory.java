package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.RouteStops;
import za.ac.cput.GoShuttle.domain.Routes;
import za.ac.cput.GoShuttle.util.Helper;

public class RouteStopsFactory {
    public static RouteStops createRouteStops(int routeStopId, int sequenceOrder, int offsetMinutes,
                                              String kind, Routes routes) {

        if(Helper.isNull(routeStopId)
        && Helper.isNull(sequenceOrder)
        && Helper.isNull(offsetMinutes)
        && Helper.isNullorEmpty(kind)){
            return null;
        }


        return new RouteStops.Builder()
                .setRouteStopsId(routeStopId)
                .setSequenceOrder(sequenceOrder)
                .setOffsetMinutes(offsetMinutes)
                .setKind(kind)
                .setRoutes(routes)
                .build();
    }

}
