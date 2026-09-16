package za.ac.cput.GoShuttle.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.GoShuttle.domain.RouteStops;
import za.ac.cput.GoShuttle.repository.RouteStopsRepository;
import za.ac.cput.GoShuttle.service.IRouteStopsService;

@Service
public class RouteStopsSeviceImpl implements IRouteStopsService {
    private final RouteStopsRepository repository;

    @Autowired
    public RouteStopsSeviceImpl(RouteStopsRepository repository){
        this.repository = repository;
    }
    @Override
    public RouteStops create(RouteStops routeStops) {
        return repository.save(routeStops);
    }

    @Override
    public RouteStops read(Integer routeStopsId) {
        return repository.findById(routeStopsId).orElse(null);
    }

    @Override
    public RouteStops update(RouteStops routeStops) {
        return repository.save(routeStops);
    }

    @Override
    public boolean delete(Integer routeStopsId) {
        if(repository.existsById(routeStopsId)){
            repository.deleteById(routeStopsId);
            return true;
        }
        return false;
    }
}
