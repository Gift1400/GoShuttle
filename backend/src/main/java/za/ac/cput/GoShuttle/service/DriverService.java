package za.ac.cput.GoShuttle.service;

import za.ac.cput.GoShuttle.domain.Driver;

import java.util.List;

public interface DriverService extends IService<Driver,Long>{
    List<Driver> findAll();
    Driver findDriverByDriverID(Long driver);
}
