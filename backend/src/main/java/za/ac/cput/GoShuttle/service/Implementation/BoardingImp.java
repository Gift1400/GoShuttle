package za.ac.cput.GoShuttle.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.GoShuttle.domain.Boarding;
import za.ac.cput.GoShuttle.repository.BoardingRepository;
import za.ac.cput.GoShuttle.service.BoardingService;

import java.util.List;

@Service
public class BoardingImp implements BoardingService {
    private final BoardingRepository boardingService;

    @Autowired
    public BoardingImp(BoardingRepository boardingRep){
        this.boardingService = boardingRep;
    }

    @Override
    public Boarding create(Boarding boarding) {
        return boardingService.save(boarding);
    }

    @Override
    public Boarding read(Long id) {
        return boardingService.findById(id).orElse(null);
    }

    @Override
    public Boarding update(Boarding boarding) {
        return boardingService.save(boarding);
    }

    @Override
    public boolean delete(Long id) {
        if(boardingService.existsById(id)){
            boardingService.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public List<Boarding> findAll() {
        return boardingService.findAll();
    }

    @Override
    public Boarding findByBoardingID(Long boarding) {
        return boardingService.findBoardingByBoardingID(boarding);
    }
}
