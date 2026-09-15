package za.ac.cput.goshuttle.controller;

import za.ac.cput.goshuttle.entity.Route;
import za.ac.cput.goshuttle.repository.RouteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "http://localhost:3000")
public class RouteController {

    private final RouteRepository routeRepository;

    public RouteController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Route>> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Long id) {
        Optional<Route> route = routeRepository.findById(id);
        return route
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-campus/{campus}")
    public ResponseEntity<List<Route>> getByCampus(@PathVariable String campus) {
        List<Route> routes = routeRepository.findByCampus(campus);
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/by-pickup/{pickupArea}")
    public ResponseEntity<List<Route>> getByPickupArea(@PathVariable String pickupArea) {
        List<Route> routes = routeRepository.findByPickupArea(pickupArea);
        return ResponseEntity.ok(routes);
    }

    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        Route saved = routeRepository.save(route);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id,
                                             @RequestBody Route updated) {
        Optional<Route> existing = routeRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Route route = new Route.Builder()
                .setRouteName(updated.getRouteName())
                .setPickupArea(updated.getPickupArea())
                .setCampus(updated.getCampus())
                .setDepartureTime(updated.getDepartureTime())
                .build();
        route.setId(existing.get().getId());
        Route saved = routeRepository.save(route);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        if (!routeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        routeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}