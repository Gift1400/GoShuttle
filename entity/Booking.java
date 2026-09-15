package za.ac.cput.goshuttle.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "status")
    private String status = "CONFIRMED";

    public Booking() {}

    private Booking(Builder builder) {
        this.studentId   = builder.studentId;
        this.routeId     = builder.routeId;
        this.bookingDate = builder.bookingDate;
        this.status      = builder.status;
    }

    public static class Builder {
        private Long studentId;
        private Long routeId;
        private LocalDate bookingDate;
        private String status = "CONFIRMED";

        public Builder setStudentId(Long studentId) {
            this.studentId = studentId;
            return this;
        }
        public Builder setRouteId(Long routeId) {
            this.routeId = routeId;
            return this;
        }
        public Builder setBookingDate(LocalDate bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }
        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }
        public Booking build() {
            return new Booking(this);
        }
    }

    // ── Getters ──────────────────────────────────────
    public Long getId()              { return id; }
    public Long getStudentId()       { return studentId; }
    public Long getRouteId()         { return routeId; }
    public LocalDate getBookingDate(){ return bookingDate; }
    public String getStatus()        { return status; }

    // ── Setters ──────────────────────────────────────
    public void setId(Long id)                    { this.id = id; }
    public void setStudentId(Long studentId)       { this.studentId = studentId; }
    public void setRouteId(Long routeId)           { this.routeId = routeId; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
    public void setStatus(String status)           { this.status = status; }
}
