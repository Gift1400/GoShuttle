package za.ac.cput.GoShuttle.service;

import za.ac.cput.GoShuttle.domain.Boarding;

import java.util.List;

public interface BoardingService extends IService<Boarding,Long>{
    List<Boarding> findAll();
    Boarding findByBoardingID(Long boarding);
}
