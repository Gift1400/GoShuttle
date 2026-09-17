package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.*;
import za.ac.cput.GoShuttle.util.Helper;

import java.time.LocalDateTime;

@Entity
@Table(name = "live_trip")
public class LiveTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Routes route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_stop_id")
    private RouteStops currentStop;

    @Enumerated(EnumType.STRING)
    private Helper.liveTripStatus status;

    private int arrivesInMinutes;
    private int toCampusMinutes;
    private LocalDateTime timestamp;

    protected LiveTrip() {}

    public LiveTrip(Builder builder) {
        this.tripID = builder.tripID;
        this.bus = builder.bus;
        this.route = builder.route;
        this.currentStop = builder.currentStop;
        this.status = builder.status;
        this.arrivesInMinutes = builder.arrivesInMinutes;
        this.toCampusMinutes = builder.toCampusMinutes;
        this.timestamp = builder.timestamp;
    }

    public Long getTripID() {
        return tripID;
    }

    public Bus getBus() {
        return bus;
    }

    public Routes getRoute() {
        return route;
    }

    public RouteStops getCurrentStop() {
        return currentStop;
    }

    public Helper.liveTripStatus getStatus() {
        return status;
    }

    public int getArrivesInMinutes() {
        return arrivesInMinutes;
    }

    public int getToCampusMinutes() {
        return toCampusMinutes;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "LiveTrip{" +
                "tripID=" + tripID +
                ", bus=" + bus +
                ", route=" + route +
                ", currentStop=" + currentStop +
                ", status=" + status +
                ", arrivesInMinutes=" + arrivesInMinutes +
                ", toCampusMinutes=" + toCampusMinutes +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class Builder {
        private Long tripID;
        private Bus bus;
        private Routes route;
        private RouteStops currentStop;
        private Helper.liveTripStatus status;
        private int arrivesInMinutes;
        private int toCampusMinutes;
        private LocalDateTime timestamp;

        public Builder setTripID(Long tripID) {
            this.tripID = tripID;
            return this;
        }

        public Builder setBus(Bus bus) {
            this.bus = bus;
            return this;
        }

        public Builder setRoute(Routes route) {
            this.route = route;
            return this;
        }

        public Builder setCurrentStop(RouteStops currentStop) {
            this.currentStop = currentStop;
            return this;
        }

        public Builder setStatus(Helper.liveTripStatus status) {
            this.status = status;
            return this;
        }

        public Builder setArrivesInMinutes(int arrivesInMinutes) {
            this.arrivesInMinutes = arrivesInMinutes;
            return this;
        }

        public Builder setToCampusMinutes(int toCampusMinutes) {
            this.toCampusMinutes = toCampusMinutes;
            return this;
        }

        public Builder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder copy(LiveTrip liveTrip) {
            this.tripID = liveTrip.tripID;
            this.bus = liveTrip.bus;
            this.route = liveTrip.route;
            this.currentStop = liveTrip.currentStop;
            this.status = liveTrip.status;
            this.arrivesInMinutes = liveTrip.arrivesInMinutes;
            this.toCampusMinutes = liveTrip.toCampusMinutes;
            this.timestamp = liveTrip.timestamp;
            return this;
        }

        public LiveTrip build() {
            return new LiveTrip(this);
        }
    }
}