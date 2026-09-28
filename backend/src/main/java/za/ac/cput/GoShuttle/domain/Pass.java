package za.ac.cput.GoShuttle.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Pass {
    @Id
    private String passId;

    @OneToMany
    @JoinColumn(name = "userId")
    private User userId;

    @Enumerated(EnumType.STRING)
    private PassType PassType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date  validUntil;

    private int daysLeft;
    private int tripsUsed;
    private int amountSaved;

    @OneToMany(mappedBy = "pass", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Routes> preferredRouteId;

    public enum PassType {
        DAILY,
        WEEKLY,
        MONTHLY
    }

    protected Pass() {}

    public Pass(Builder builder){
        this.passId = builder.passId;
        this.userId = builder.userId;
        this.PassType = builder.PassType;
        this.validUntil = builder.validUntil;
        this.daysLeft = builder.daysLeft;
        this.tripsUsed = builder.tripsUsed;
        this.amountSaved = builder.amountSaved;
        this.preferredRouteId = builder.preferredRouteId;
    }

    public String getPassId() {return passId;}

    public User getUserId() {return userId;}

    public PassType getPassType() {return PassType;}

    public Date getValidUntil() {return validUntil;}

    public int getDaysLeft() {return daysLeft;}

    public int getTripsUsed() {return tripsUsed;}

    public int getAmountSaved() {return amountSaved;}

    public List<Routes> getPreferredRouteId() {return preferredRouteId;}

    @Override
    public String toString() {
        return "PassId: " + passId +
                "UserId: " + userId +
                "PassType: " + PassType +
                "ValidUntil: " + validUntil +
                "DaysLeft: " + daysLeft +
                "TripsUsed: " + tripsUsed +
                "AmountSaved: " + amountSaved +
                "PreferredRouteId: " + preferredRouteId;
    }

    public static class Builder{
        private String passId;
        private User userId;
        private PassType PassType;
        private Date validUntil;
        private int daysLeft;
        private int tripsUsed;
        private int amountSaved;
        private List<Routes> preferredRouteId;


        public Builder setPassId(String passId) {this.passId = passId; return this;}
        public Builder setUserId(User userId) {this.userId = userId; return this;}
        public Builder setPassType(PassType PassType) {this.PassType = PassType; return this;}
        public Builder setValidUntil(Date validUntil) {this.validUntil = validUntil; return this;}
        public Builder setDaysLeft(int daysLeft) {this.daysLeft = daysLeft; return this;}
        public Builder setTripsUsed(int tripsUsed) {this.tripsUsed = tripsUsed; return this;}
        public Builder setAmountSaved(int amountSaved) {this.amountSaved = amountSaved; return this;}
        public Builder setPreferredRouteId(List<Routes> preferredRouteId) {this.preferredRouteId= preferredRouteId; return this;}

        public Builder copy(Pass pass) {
            this.passId = pass.passId;
            this.userId = pass.userId;
            this.PassType = pass.PassType;
            this.validUntil = pass.validUntil;
            this.daysLeft = pass.daysLeft;
            this.tripsUsed = pass.tripsUsed;
            this.amountSaved = pass.amountSaved;
            this.preferredRouteId= pass.preferredRouteId;
            return this;
        }

        public Pass build() {
            return new Pass(this);
        }
    }

}
