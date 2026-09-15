package za.ac.cput.GoShuttle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.GoShuttle.domain.Driver;
import za.ac.cput.GoShuttle.repository.DriverRepository;

import java.util.List;

@Service
public class DriverImp implements DriverService{
    private final DriverRepository driverService;

    @Autowired
    public DriverImp(DriverRepository driverRep){
        this.driverService = driverRep;
    }

    @Override
    public Driver create(Driver driver) {
        return driverService.save(driver);
    }

    @Override
    public Driver read(Long aLong) {
        return driverService.findById(aLong).orElse(null);
    }

    @Override
    public Driver update(Driver driver) {
        return driverService.save(driver);
    }

    @Override
    public boolean delete(Long aLong) {
        if(driverService.existsById(aLong)){
            driverService.deleteById(aLong);
            return true;
        }
        return false;
    }
    @Override
    public List<Driver> findAll() {
        return driverService.findAll();
    }

    @Override
    public Driver findDriverByDriverID(Long driver) {
        return driverService.findDriverByDriverID(driver);
    }
}
