package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Driver {
    @Id
    private Long driverID;
    private Long driverName;

    public Driver(){}
    public Driver(Builder builder){
        this.driverID = builder.driverID;
        this.driverName = builder.driverName;
    }

    public Long getDriverID() {
        return driverID;
    }

    public Long getDriverName() {
        return driverName;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverID=" + driverID +
                ", driverName=" + driverName +
                '}';
    }
    public static class Builder{
        private Long driverID;
        private Long driverName;

        public Builder setDriverID(Long driverID){
            this.driverID = driverID;
            return this;
        }
        public Builder setDriverName(Long driverName){
            this.driverName = driverName;
            return this;
        }
        public Builder copy(Driver driver){
            this.driverID = driver.driverID;
            this.driverName = driver.driverName;
            return this;
        }
        public Driver Build(){
            return new Driver(this);
        }
    }
}
