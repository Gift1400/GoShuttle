package za.ac.cput.goshuttle.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_name", nullable = false)
    private String routeName;

    @Column(name = "pickup_area", nullable = false)
    private String pickupArea;

    @Column(name = "campus", nullable = false)
    private String campus;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    public Route() {}

    private Route(Builder builder) {
        this.routeName     = builder.routeName;
        this.pickupArea    = builder.pickupArea;
        this.campus        = builder.campus;
        this.departureTime = builder.departureTime;
    }

    public static class Builder {
        private String routeName;
        private String pickupArea;
        private String campus;
        private LocalTime departureTime;

        public Builder setRouteName(String routeName) {
            this.routeName = routeName;
            return this;
        }
        public Builder setPickupArea(String pickupArea) {
            this.pickupArea = pickupArea;
            return this;
        }
        public Builder setCampus(String campus) {
            this.campus = campus;
            return this;
        }
        public Builder setDepartureTime(LocalTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }
        public Route build() {
            return new Route(this);
        }
    }

    // ── Getters ──────────────────────────────────────
    public Long getId()                { return id; }
    public String getRouteName()       { return routeName; }
    public String getPickupArea()      { return pickupArea; }
    public String getCampus()          { return campus; }
    public LocalTime getDepartureTime(){ return departureTime; }

    // ── Setters ──────────────────────────────────────
    public void setId(Long id)                      { this.id = id; }
    public void setRouteName(String routeName)       { this.routeName = routeName; }
    public void setPickupArea(String pickupArea)     { this.pickupArea = pickupArea; }
    public void setCampus(String campus)             { this.campus = campus; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime = departureTime; }
}