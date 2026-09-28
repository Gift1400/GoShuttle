package za.ac.cput.GoShuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.GoShuttle.domain.Pass;
import za.ac.cput.GoShuttle.service.PassService;

import java.util.List;

@RestController
@RequestMapping("/Pass")
public class PassController {
    public final PassService passCtrl;

    @Autowired
    public PassController(PassService passService) {
        this.passCtrl = passService;
    }

    @PostMapping("/create")
    public Pass createPass(@RequestBody Pass pass) {
        return passCtrl.create(pass);
    }
    @GetMapping("/read/{passId}")
    public Pass readPass(@PathVariable("passId") String passId) {
        return passCtrl.read(passId);
    }

    @PutMapping("/update")
    public Pass updatePass(@RequestBody Pass pass) {
        return passCtrl.update(pass);
    }

    @DeleteMapping("/delete/{passId}")
    public void deletePass(@PathVariable("passId") String passId) {
        passCtrl.delete(passId);
    }

    @GetMapping("/findAll")
    public List<Pass> findAllPass() {
        return passCtrl.findAll();
    }

    @GetMapping("/findPassByPassId/{passId}")
    public Pass findPassByPassId(@PathVariable("passId") String passId) {
        return passCtrl.findByPassId(passId);
    }


}
