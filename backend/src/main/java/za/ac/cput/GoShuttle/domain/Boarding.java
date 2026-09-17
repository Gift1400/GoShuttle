package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "boarding")
public class Boarding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardingID;


    private Long userID ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "live_trip_id", nullable = false)
    private LiveTrip liveTrip;

    private LocalDateTime boardedAt;
    private LocalDateTime alightedAt;

    public Boarding(){}
    public Boarding(Builder builder){
        this.boardingID = builder.boardingID;
        this.userID = builder.userID;
        this.liveTrip = builder.liveTrip;
        this.boardedAt = builder.boardedAt;
        this.alightedAt = builder.alightedAt;
    }

    public Long getBoardingID() {
        return boardingID;
    }

    public Long getUserID() {
        return userID;
    }

    public LiveTrip getLiveTripID() {
        return liveTrip;
    }

    public LocalDateTime getBoardedAt() {
        return boardedAt;
    }

    public LocalDateTime getAlightedAt() {
        return alightedAt;
    }

    @Override
    public String toString() {
        return "Boarding{" +
                "boardingID=" + boardingID +
                ", userID=" + userID +
                ", liveTripID=" + liveTrip +
                ", boardedAt=" + boardedAt +
                ", alightedAt=" + alightedAt +
                '}';
    }

    public static class Builder{
        private Long boardingID;
        private Long userID ;
        private LiveTrip liveTrip;
        private LocalDateTime boardedAt;
        private LocalDateTime alightedAt;

        public Builder setBoardingID(Long boardingID) {
            this.boardingID = boardingID;
            return this;
        }

        public Builder setUser(Long user) {
            this.userID = user;
            return this;
        }

        public Builder setLiveTrip(LiveTrip liveTrip) {
            this.liveTrip = liveTrip;
            return this;
        }

        public Builder setBoardedAt(LocalDateTime boardedAt) {
            this.boardedAt = boardedAt;
            return this;
        }

        public Builder setAlightedAt(LocalDateTime alightedAt) {
            this.alightedAt = alightedAt;
            return this;
        }

        public Builder copy(Boarding boarding) {
            this.boardingID = boarding.boardingID;
            this.userID = boarding.userID;
            this.liveTrip = boarding.liveTrip;
            this.boardedAt = boarding.boardedAt;
            this.alightedAt = boarding.alightedAt;
            return this;
        }

        public Boarding build() {
            return new Boarding(this);
        }
    }
}
