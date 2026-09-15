package za.ac.cput.GoShuttle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.GoShuttle.domain.LiveTrip;
import za.ac.cput.GoShuttle.repository.LiveTripRepository;

import java.util.List;
@Service
public class LiveTripImp implements LiveTripService{
    private final LiveTripRepository liveTripService;

    @Autowired
    public LiveTripImp(LiveTripRepository tripRep){
        this.liveTripService = tripRep;
    }

    @Override
    public LiveTrip create(LiveTrip liveTrip) {
        return liveTripService.save(liveTrip);
    }

    @Override
    public LiveTrip read(Long aLong) {
        return liveTripService.findById(aLong).orElse(null);
    }

    @Override
    public LiveTrip update(LiveTrip liveTrip) {
        return liveTripService.save(liveTrip);
    }

    @Override
    public boolean delete(Long aLong) {
        if(liveTripService.existsById(aLong)){
            liveTripService.deleteById(aLong);
            return true;
        }
        return false;
    }
    @Override
    public List<LiveTrip> findAll() {
        return liveTripService.findAll();
    }

    @Override
    public LiveTrip findLiveTripByTripID(Long trip) {
        return liveTripService.findLiveTripByTripID(trip);
    }
}
