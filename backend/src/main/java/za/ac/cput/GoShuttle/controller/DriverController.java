package za.ac.cput.GoShuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.GoShuttle.domain.Bus;
import za.ac.cput.GoShuttle.domain.Driver;
import za.ac.cput.GoShuttle.service.BusService;
import za.ac.cput.GoShuttle.service.DriverService;

import java.util.List;
@RestController
@RequestMapping("/Driver")
public class DriverController {
     private final DriverService driverCon;

    @Autowired
    public DriverController(DriverService driverServ){
        this.driverCon = driverServ;
    }
    @PostMapping("/create")
    public Driver createDriver(@RequestBody Driver driver){
        return driverCon.create(driver);
    }
    @GetMapping("/read/{driverID}")
    public Driver readDriver(@PathVariable Long driverID){
        return driverCon.read(driverID);
    }
    @PutMapping("/update")
    public Driver updateDriver(@RequestBody Driver driver){
        return driverCon.update(driver);
    }
    @DeleteMapping("/delete?{driverID}")
    public boolean deleteDriver(@PathVariable Long driverID){
        return driverCon.delete(driverID);
    }
    @GetMapping("/findAll")
    public List<Driver> getAll(){
        return driverCon.findAll();
    }
    @GetMapping("/findDriverByDriverID/{driverID}")
    public Driver getDriverByDriverID(@PathVariable Long driverID){
        return driverCon.findDriverByDriverID(driverID);
    }
}
