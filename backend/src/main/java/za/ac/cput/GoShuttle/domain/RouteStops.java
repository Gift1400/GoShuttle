package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "routes_stops")
public class RouteStops {
    @Id
    private int routeStopsId;
    private int sequenceOrder;
    private int offsetMinutes;
    private String kind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route")
    private Routes routes;

    protected RouteStops(){}
    public RouteStops(Builder builder){
        this.routeStopsId = builder.routeStopsId;
        this.sequenceOrder = builder.sequenceOrder;
        this.offsetMinutes = builder.offsetMinutes;
        this.kind = builder.kind;
        this.routes = builder.routes;
    }

    public int getRouteStopsId() {
        return routeStopsId;
    }

    public int getSequenceOrder() {
        return sequenceOrder;
    }

    public int getOffsetMinutes() {
        return offsetMinutes;
    }

    public String getKind() {
        return kind;
    }

    public Routes getRoutes() {
        return routes;
    }

    @Override
    public String toString() {
        return "RouteStops{" +
                "routeStopId=" + routeStopsId +
                ", sequenceOrder=" + sequenceOrder +
                ", offsetMinutes=" + offsetMinutes +
                ", kind='" + kind + '\'' +
                ", routes=" + routes +
                '}';
    }

    public static class Builder{
        private int routeStopsId;
        private int sequenceOrder;
        private int offsetMinutes;
        private String kind;
        private Routes routes;

        public Builder copy(RouteStops routeStops){
            this.routeStopsId = routeStops.routeStopsId;
            this.sequenceOrder = routeStops.sequenceOrder;
            this.offsetMinutes = routeStops.offsetMinutes;
            this.kind = routeStops.kind;
            this.routes = routeStops.routes;
            return this;
        }

        public Builder setRouteStopsId(int routeStopId) {
            this.routeStopsId = routeStopId;
            return this;
        }

        public Builder setSequenceOrder(int sequenceOrder) {
            this.sequenceOrder = sequenceOrder;
            return this;
        }

        public Builder setOffsetMinutes(int offsetMinutes) {
            this.offsetMinutes = offsetMinutes;
            return this;
        }

        public Builder setKind(String kind) {
            this.kind = kind;
            return this;
        }

        public Builder setRoutes(Routes routes) {
            this.routes = routes;
            return this;
        }

        public RouteStops build(){
            return new RouteStops(this);
        }
    }
}
