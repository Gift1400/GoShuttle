package za.ac.cput.GoShuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.GoShuttle.domain.Driver;
import za.ac.cput.GoShuttle.domain.LiveTrip;
import za.ac.cput.GoShuttle.service.DriverService;
import za.ac.cput.GoShuttle.service.LiveTripService;

import java.util.List;
@RestController
@RequestMapping("/LiveTrip")
public class LiveTripController {
    private final LiveTripService liveTripCon;

    @Autowired
    public LiveTripController(LiveTripService liveTripServ){
        this.liveTripCon = liveTripServ;
    }
    @PostMapping("/create")
    public LiveTrip createLiveTrip(@RequestBody LiveTrip trip){
        return liveTripCon.create(trip);
    }
    @GetMapping("/read/{tripID}")
    public LiveTrip readLiveTrip(@PathVariable Long tripID){
        return liveTripCon.read(tripID);
    }
    @PutMapping("/update")
    public LiveTrip updateLiveTrip(@RequestBody LiveTrip trip){
        return liveTripCon.update(trip);
    }
    @DeleteMapping("/delete?{driverID}")
    public boolean deleteLiveTrip(@PathVariable Long driverID){
        return liveTripCon.delete(driverID);
    }
    @GetMapping("/findAll")
    public List<LiveTrip> getAll(){
        return liveTripCon.findAll();
    }
    @GetMapping("/findLiveTripByTripID/{tripID}")
    public LiveTrip getLiveTripByTripID(@PathVariable Long tripID){
        return liveTripCon.findLiveTripByTripID(tripID);
    }
}
