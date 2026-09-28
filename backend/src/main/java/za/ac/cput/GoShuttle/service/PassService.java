package za.ac.cput.GoShuttle.service;

import org.springframework.stereotype.Service;
import za.ac.cput.GoShuttle.domain.Pass;

import java.util.List;

public interface PassService extends IService<Pass, String>{
    List<Pass> findAll();
    Pass findByPassId(String passId);

}
