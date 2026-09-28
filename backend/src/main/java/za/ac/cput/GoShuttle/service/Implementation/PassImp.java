package za.ac.cput.GoShuttle.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.GoShuttle.domain.Pass;
import za.ac.cput.GoShuttle.repository.PassRepository;
import za.ac.cput.GoShuttle.service.PassService;
import java.util.List;
@Service
public class PassImp implements PassService {
    private final PassRepository passService;

    @Autowired
    public PassImp(PassRepository passRepository) {
        this.passService = passRepository;
    }


    @Override
    public List<Pass> findAll() {
        return passService.findAll();
    }

    @Override
    public Pass findByPassId(String passId) {
        return passService.getPassByPassId(passId);
    }

    @Override
    public Pass create(Pass pass) {
        return passService.save(pass);
    }

    @Override
    public Pass read(String passId) {
        return passService.findById(passId).orElse(null);
    }

    @Override
    public Pass update(Pass pass) {
        return passService.save(pass);
    }

    @Override
    public boolean delete(String passId) {
        if (passService.existsById(passId)) {
            passService.deleteById(passId);
            return true;
        }

        return false;
    }

}
