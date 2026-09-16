package za.ac.cput.GoShuttle.service;

import za.ac.cput.GoShuttle.domain.Routes;
import java.util.*;

public interface IRoutesService extends IService<Routes, Integer>{
    List<Routes> getAll();

}
