package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "routes")
public class Routes {
    @Id
    private int routeId;
    private String code;
    private String name;
    private int frequencyMinutes;
    private int durationMinutes;
    private Time serviceStart;
    private Time serviceEnd;

    protected Routes(){}
    public Routes(Builder builder){
        this.routeId = builder.routeId;
        this.code = builder.name;
        this.frequencyMinutes = builder.frequencyMinutes;
        this.durationMinutes = builder.durationMinutes;
        this.serviceStart = builder.serviceStart;
        this.serviceEnd = builder.serviceEnd;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getFrequencyMinutes() {
        return frequencyMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public Time getServiceStart() {
        return serviceStart;
    }

    public Time getServiceEnd() {
        return serviceEnd;
    }

    @Override
    public String toString() {
        return "Routes{" +
                "routeId=" + routeId +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", frequencyMinutes=" + frequencyMinutes +
                ", durationMinutes=" + durationMinutes +
                ", serviceStart=" + serviceStart +
                ", serviceEnd=" + serviceEnd +
                '}';
    }

    public static class Builder{
        private int routeId;
        private String code;
        private String name;
        private int frequencyMinutes;
        private int durationMinutes;
        private Time serviceStart;
        private Time serviceEnd;

        public Builder setRouteId(int routeId) {
            this.routeId = routeId;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFrequencyMinutes(int frequencyMinutes) {
            this.frequencyMinutes = frequencyMinutes;
            return this;
        }

        public Builder setDurationMinutes(int durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public Builder setServiceStart(Time serviceStart) {
            this.serviceStart = serviceStart;
            return this;
        }

        public Builder setServiceEnd(Time serviceEnd) {
            this.serviceEnd = serviceEnd;
            return this;
        }

        public Routes build(){
            return new Routes(this);
        }
    }
}
