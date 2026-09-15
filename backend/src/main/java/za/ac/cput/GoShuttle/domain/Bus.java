package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Bus {
    @Id
    private Long busID;
    private Long busNumber;
    private Long routeID;
    private Long driverID;
    private int capacity;

    public Bus(){}
    public Bus(Builder builder){
        this.busID = builder.busID;
        this.busNumber = builder.busNumber;
        this.routeID = builder.routeID;
        this.driverID = builder.driverID;
        this.capacity = builder.capacity;
    }

    public Long getBusID() {
        return busID;
    }

    public Long getBusNumber() {
        return busNumber;
    }

    public Long getRouteID() {
        return routeID;
    }

    public Long getDriverID() {
        return driverID;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busID=" + busID +
                ", busNumber=" + busNumber +
                ", routeID=" + routeID +
                ", driverID=" + driverID +
                ", capacity=" + capacity +
                '}';
    }
    public static class Builder{
        private Long busID;
        private Long busNumber;
        private Long routeID;
        private Long driverID;
        private int capacity;

        public Builder setBusID(Long busID){
            this.busID = busID;
            return this;
        }
        public Builder setBusNumber(Long busNumber){
            this.busNumber = busNumber;
            return this;
        }
        public Builder setRouteID(Long routeID){
            this.routeID = routeID;
            return this;
        }
        public Builder setDriverID(Long driverID){
            this.driverID = driverID;
            return this;
        }
        public Builder setCapacity(int capacity){
            this.capacity = capacity;
            return this;
        }
        public Builder copy(Bus bus){
            this.busID = bus.busID;
            this.busNumber = bus.busNumber;
            this.routeID = bus.routeID;
            this.driverID = bus.driverID;
            this.capacity = bus.capacity;
            return this;
        }
        public Bus Build(){
            return new Bus(this);
        }
    }
}
