package za.ac.cput.GoShuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import za.ac.cput.GoShuttle.domain.Routes;
import za.ac.cput.GoShuttle.service.Implementation.RoutesServiceImpl;

@RestController
@RequestMapping("/routes")
public class RoutesController {
    private final RoutesServiceImpl service;

    @Autowired
    public RoutesController(RoutesServiceImpl service){
        this.service = service;
    }

    @PostMapping("/create")
    public Routes create(@RequestBody Routes routes){
        return service.create(routes);
    }

    @GetMapping("/read/{routesId}")
    public Routes read(@PathVariable Integer routesId){
        return service.read(routesId);
    }

    @PutMapping("/update")
    public Routes update(@RequestBody Routes routes){
        return service.update(routes);
    }

    @DeleteMapping("/delete/{routesId}")
    public boolean delete(@PathVariable Integer routesId){
        return service.delete(routesId);
    }

    @GetMapping("/getAll")
    public List<Routes> getAll(){
        return service.findAll();
    }
}
