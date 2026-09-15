package za.ac.cput.goshuttle.controller;

import za.ac.cput.goshuttle.entity.Booking;
import za.ac.cput.goshuttle.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<List<Booking>> getByStudent(@PathVariable Long studentId) {
        List<Booking> bookings = bookingRepository.findByStudentId(studentId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/by-student/{studentId}/confirmed")
    public ResponseEntity<List<Booking>> getConfirmedByStudent(@PathVariable Long studentId) {
        List<Booking> bookings = bookingRepository
                .findByStudentIdAndStatus(studentId, "CONFIRMED");
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/by-route/{routeId}")
    public ResponseEntity<List<Booking>> getByRoute(@PathVariable Long routeId) {
        List<Booking> bookings = bookingRepository.findByRouteId(routeId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {
        Optional<Booking> existing = bookingRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Booking booking = existing.get();
        booking.setStatus("CANCELLED");
        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        if (!bookingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}