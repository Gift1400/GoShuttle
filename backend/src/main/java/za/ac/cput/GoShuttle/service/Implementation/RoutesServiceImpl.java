package za.ac.cput.GoShuttle.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.GoShuttle.domain.Routes;
import za.ac.cput.GoShuttle.repository.RoutesRepository;
import za.ac.cput.GoShuttle.service.IRoutesService;
import java.util.*;

@Service
public class RoutesServiceImpl implements IRoutesService {
    private final RoutesRepository repository;

    @Autowired
    public RoutesServiceImpl(RoutesRepository repository){
        this.repository = repository;
    }

    @Override
    public Routes create(Routes routes) {
        return repository.save(routes);
    }

    @Override
    public Routes read(Integer routesId) {
        return repository.findById(routesId).orElse(null);
    }

    @Override
    public Routes update(Routes routes) {
        return repository.save(routes);
    }

    @Override
    public boolean delete(Integer routesId) {
        if(repository.existsById(routesId)){
            repository.deleteById(routesId);
            return true;
        }
        return false;
    }

    @Override
    public List<Routes> getAll() {
        return repository.getAll();
    }

}
