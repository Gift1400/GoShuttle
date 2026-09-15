package za.ac.cput.GoShuttle.service;

import za.ac.cput.GoShuttle.domain.Bus;

import java.util.List;

public interface BusService extends IService<Bus,Long>{
    List<Bus> findAll();
    Bus findBusByBusID(Long bus);
}
