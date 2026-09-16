package za.ac.cput.GoShuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.GoShuttle.domain.RouteStops;
import za.ac.cput.GoShuttle.service.Implementation.RouteStopsSeviceImpl;

@RestController
@RequestMapping("/r_stops")
public class RouteStopsController {
    private final RouteStopsSeviceImpl service;

    @Autowired
    public RouteStopsController(RouteStopsSeviceImpl service){
        this.service = service;
    }

    @PostMapping("/create")
    public RouteStops create(@RequestBody RouteStops routeStops){
        return service.create(routeStops);
    }

    @GetMapping("/read/{routeStopsId}")
    public RouteStops read(@PathVariable int routeStopsId){
        return service.read(routeStopsId);
    }

    @PutMapping("/update")
    public RouteStops update(@RequestBody RouteStops routeStops){
        return service.update(routeStops);
    }

    @DeleteMapping("/delete/{routeStopsId}")
    public boolean delete(@PathVariable int routeStopsId){
        return service.delete(routeStopsId);
    }
}
