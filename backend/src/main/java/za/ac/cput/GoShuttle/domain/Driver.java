package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverID;

    private String driverName;

    protected Driver() {}

    public Driver(Builder builder) {
        this.driverID = builder.driverID;
        this.driverName = builder.driverName;
    }

    public Long getDriverID() {
        return driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverID=" + driverID +
                ", driverName='" + driverName + '\'' +
                '}';
    }

    public static class Builder {
        private Long driverID;
        private String driverName;

        public Builder setDriverID(Long driverID) {
            this.driverID = driverID;
            return this;
        }

        public Builder setDriverName(String driverName) {
            this.driverName = driverName;
            return this;
        }

        public Builder copy(Driver driver) {
            this.driverID = driver.driverID;
            this.driverName = driver.driverName;
            return this;
        }

        public Driver build() {
            return new Driver(this);
        }
    }
}