// CampusController.java
package za.ac.cput.GoShuttle.Controller;
import za.ac.cput.GoShuttle.Entity.Campus;
import za.ac.cput.GoShuttle.Repository.CampusRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/campuses")
@CrossOrigin(origins = "http://localhost:3000")
public class CampusController {
    private final CampusRepository campusRepository;
    public CampusController(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }
    @GetMapping
    public ResponseEntity<List<Campus>> getAll() {
        return ResponseEntity.ok(campusRepository.findAll());
    }
    @PostMapping
    public ResponseEntity<Campus> create(@RequestBody Campus campus) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campusRepository.save(campus));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        campusRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}