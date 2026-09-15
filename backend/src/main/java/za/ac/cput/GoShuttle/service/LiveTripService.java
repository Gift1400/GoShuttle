package za.ac.cput.GoShuttle.service;

import za.ac.cput.GoShuttle.domain.LiveTrip;

import java.util.List;

public interface LiveTripService extends IService<LiveTrip,Long> {
    List<LiveTrip> findAll();
    LiveTrip findLiveTripByTripID(Long trip);
}
