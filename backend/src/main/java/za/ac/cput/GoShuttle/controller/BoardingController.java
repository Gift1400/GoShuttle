package za.ac.cput.GoShuttle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.GoShuttle.domain.Boarding;
import za.ac.cput.GoShuttle.service.BoardingService;

import java.util.List;

@RestController
@RequestMapping("Boarding")
public class BoardingController {
    private final BoardingService boardingCon;

    @Autowired
    public BoardingController(BoardingService boardingServ){
        this.boardingCon = boardingServ;
    }
    @PostMapping("/create")
    public Boarding createBoarding(@RequestBody Boarding boarding){
       return boardingCon.create(boarding);
    }
    @GetMapping("/read/{boardingID}")
    public Boarding readBoarding(@PathVariable Long boardingID){
        return boardingCon.read(boardingID);
    }
    @PutMapping("/update")
    public Boarding updateBoarding(@RequestBody Boarding boarding) {
        return boardingCon.update(boarding);
    }
    @DeleteMapping("/delete/{boardingID}")
    public boolean deleteMapping(@PathVariable Long boardingID){
        return boardingCon.delete(boardingID);
    }
    @GetMapping("/findAll")
    public List<Boarding> getAll(){
        return boardingCon.findAll();
    }
    @GetMapping("/findBoardingByBoardingID/{boardingID}")
    public Boarding getBoardingByID(@PathVariable Long boardingID){
        return boardingCon.findByBoardingID(boardingID);
    }
}
