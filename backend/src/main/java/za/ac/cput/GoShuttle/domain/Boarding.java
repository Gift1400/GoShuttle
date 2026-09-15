package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
@Entity
public class Boarding {
    @Id
    private Long boardingID;
    private Long userID ;
    private Long liveTripID;
    private LocalDateTime boardedAt;
    private LocalDateTime alightedAt;

    public Boarding(){}
    public Boarding(Builder builder){
        this.boardingID = builder.boardingID;
        this.userID = builder.userID;
        this.liveTripID = builder.liveTripID;
        this.boardedAt = builder.boardedAt;
        this.alightedAt = builder.alightedAt;
    }

    public Long getBoardingID() {
        return boardingID;
    }

    public Long getUserID() {
        return userID;
    }

    public Long getLiveTripID() {
        return liveTripID;
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
                ", liveTripID=" + liveTripID +
                ", boardedAt=" + boardedAt +
                ", alightedAt=" + alightedAt +
                '}';
    }

    public static class Builder{
        private Long boardingID;
        private Long userID ;
        private Long liveTripID;
        private LocalDateTime boardedAt;
        private LocalDateTime alightedAt;

        public Builder setBoardingID(Long boardingID){
            this.boardingID = boardingID;
            return this;
        }
        public Builder setUserID(Long userID){
            this.userID = userID;
            return this;
        }
        public Builder setLiveTripID(Long liveTripID){
            this.liveTripID = liveTripID;
            return this;
        }
        public Builder setBoardingAt(LocalDateTime boardedAt){
            this.boardedAt = boardedAt;
            return this;
        }
        public Builder setAlightedAt(LocalDateTime alightedAt){
            this.alightedAt = alightedAt;
            return this;
        }
        public Builder copy(Boarding boarding){
            this.boardingID = boarding.boardingID;
            this.userID = boarding.userID;
            this.boardedAt = boarding.boardedAt;
            this.liveTripID = boarding.liveTripID;
            this.alightedAt = boarding.alightedAt;
            return this;
        }
        public Boarding Build(){
            return new Boarding(this);
        }
    }
}
