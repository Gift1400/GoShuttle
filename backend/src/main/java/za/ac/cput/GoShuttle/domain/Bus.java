package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busID;
    private Long busNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Routes routes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private int capacity;

    public Bus(){}
    public Bus(Builder builder){
        this.busID = builder.busID;
        this.busNumber = builder.busNumber;
        this.routes = builder.routes;
        this.driver = builder.driver;
        this.capacity = builder.capacity;
    }

    public Long getBusID() {
        return busID;
    }

    public Long getBusNumber() {
        return busNumber;
    }

    public Routes getRoutes() {
        return routes;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busID=" + busID +
                ", busNumber=" + busNumber +
                ", routes=" + routes +
                ", driver=" + driver +
                ", capacity=" + capacity +
                '}';
    }

    public static class Builder{
        private Long busID;
        private Long busNumber;
        private Routes routes;
        private Driver driver;
        private int capacity;

        public Builder setBusID(Long busID){
            this.busID = busID;
            return this;
        }
        public Builder setBusNumber(Long busNumber){
            this.busNumber = busNumber;
            return this;
        }
        public Builder setRoutes(Routes routes){
            this.routes = routes;
            return this;
        }
        public Builder setDriver(Driver driver){
            this.driver = driver;
            return this;
        }
        public Builder setCapacity(int capacity){
            this.capacity = capacity;
            return this;
        }
        public Builder copy(Bus bus){
            this.busID = bus.busID;
            this.busNumber = bus.busNumber;
            this.routes = bus.routes;
            this.driver = bus.driver;
            this.capacity = bus.capacity;
            return this;
        }
        public Bus Build(){
            return new Bus(this);
        }
    }
}
