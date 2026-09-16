package za.ac.cput.GoShuttle.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.GoShuttle.domain.Bus;
import za.ac.cput.GoShuttle.repository.BusRepository;
import za.ac.cput.GoShuttle.service.BusService;

import java.util.List;

@Service
public class BusImp implements BusService {
    private final  BusRepository busService;

    @Autowired
    public BusImp(BusRepository busRep){
        this.busService = busRep;
    }

    @Override
    public Bus create(Bus bus) {
        return busService.save(bus);
    }

    @Override
    public Bus read(Long aLong) {
        return busService.findById(aLong).orElse(null);
    }

    @Override
    public Bus update(Bus bus) {
        return busService.save(bus);
    }

    @Override
    public boolean delete(Long aLong) {
        if(busService.existsById(aLong)){
            busService.deleteById(aLong);
            return true;
        }
        return false;
    }
    @Override
    public List<Bus> findAll() {
        return busService.findAll();
    }

    @Override
    public Bus findBusByBusID(Long bus) {
        return busService.findBusByBusID(bus);
    }
}
