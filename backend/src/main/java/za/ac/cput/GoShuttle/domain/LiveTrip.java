package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import za.ac.cput.GoShuttle.util.Helper;

import java.time.LocalDateTime;
@Entity
public class  LiveTrip {
    @Id
    private Long tripID;
    private Long busID;
    private Long routeID;
    private Long currentStopID;
    private Helper.liveTripStatus status;
    private int arrivesInMinute;
    private int toCampusMinutes;
    private LocalDateTime timeStamp;

    public LiveTrip(){}
    public LiveTrip(Builder builder){
        this.routeID = builder.routeID;
        this.tripID = builder.tripID;
        this.arrivesInMinute = builder.arrivesInMinute;
        this.currentStopID = builder.currentStopID;
        this.toCampusMinutes = builder.toCampusMinutes;
        this.timeStamp = builder.timeStamp;
        this.status = builder.status;
        this.busID = builder.busID;
    }

    public Long getTripID() {
        return tripID;
    }

    public Long getBusID() {
        return busID;
    }

    public Long getRouteID() {
        return routeID;
    }

    public Long getCurrentStopID() {
        return currentStopID;
    }

    public Helper.liveTripStatus getStatus() {
        return status;
    }

    public int getArrivesInMinute() {
        return arrivesInMinute;
    }

    public int getToCampusMinutes() {
        return toCampusMinutes;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "LiveTrip{" +
                "tripID=" + tripID +
                ", busID=" + busID +
                ", routeID=" + routeID +
                ", currentStopID=" + currentStopID +
                ", status=" + status +
                ", arrivesInMinute=" + arrivesInMinute +
                ", toCampusMinutes=" + toCampusMinutes +
                ", timeStamp=" + timeStamp +
                '}';
    }
    public static class Builder{
        private Long tripID;
        private Long busID;
        private Long routeID;
        private Long currentStopID;
        private Helper.liveTripStatus status;
        private int arrivesInMinute;
        private int toCampusMinutes;
        private LocalDateTime timeStamp;

        public Builder setTripID(Long tripID){
            this.tripID = tripID;
            return this;
        }
        public Builder setBusID(Long busID){
            this.busID = busID;
            return this;
        }
        public Builder setRouteID(Long routeID){
            this.routeID = routeID;
            return this;
        }
        public Builder setCurrentStopID(Long currentStopID){
            this.currentStopID = currentStopID;
            return this;
        }
        public Builder setStatus(Helper.liveTripStatus status){
            this.status = status;
            return this;
        }
        public Builder setSArrivesInMinute(int arrivesInMinute){
            this.arrivesInMinute = arrivesInMinute;
            return this;
        }
        public Builder setToCampusMinutes(int toCampusMinutes){
            this.toCampusMinutes = toCampusMinutes;
            return this;
        }
        public Builder setTimeStamp(LocalDateTime timeStamp){
            this.timeStamp = timeStamp;
            return this;
        }
        public Builder copy(LiveTrip liveTrip){
            this.routeID = liveTrip.routeID;
            this.tripID = liveTrip.tripID;
            this.arrivesInMinute = liveTrip.arrivesInMinute;
            this.currentStopID = liveTrip.currentStopID;
            this.toCampusMinutes = liveTrip.toCampusMinutes;
            this.timeStamp = liveTrip.timeStamp;
            this.status = liveTrip.status;
            this.busID = liveTrip.busID;
            return this;
        }
        public LiveTrip Build(){
            return new LiveTrip(this);
        }
    }
}
