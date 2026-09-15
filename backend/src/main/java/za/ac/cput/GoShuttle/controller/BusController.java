package za.ac.cput.GoShuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.GoShuttle.domain.Bus;
import za.ac.cput.GoShuttle.service.BusService;

import java.util.List;

@RestController
@RequestMapping("/Bus")
public class BusController {
    private final BusService busCon;

    @Autowired
    public BusController(BusService busServ){
        this.busCon = busServ;
    }
    @PostMapping("/create")
    public Bus createBus(@RequestBody Bus bus){
        return busCon.create(bus);
    }
    @GetMapping("/read/{busID}")
    public Bus readBus(@PathVariable Long busID){
        return busCon.read(busID);
    }
    @PutMapping("/update")
    public Bus updateBus(@RequestBody Bus bus){
        return busCon.update(bus);
    }
    @DeleteMapping("/delete?{busID}")
    public boolean deleteBus(@PathVariable Long busID){
        return busCon.delete(busID);
    }
    @GetMapping("/findAll")
    public List<Bus> getAll(){
        return busCon.findAll();
    }
    @GetMapping("/findBusByBusID/{busID}")
    public Bus getBusByBusID(@PathVariable Long busID){
        return busCon.findBusByBusID(busID);
    }
}
