package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.Boarding;
import za.ac.cput.GoShuttle.domain.LiveTrip;
import za.ac.cput.GoShuttle.util.Helper;

import java.time.LocalDateTime;

public class boardingFactory {
    public static Boarding createBoarding(Long boardingID, Long userID, LiveTrip liveTripID, LocalDateTime boardedAt, LocalDateTime alightedAt){
        if(Helper.isEmpty(boardingID) || Helper.isEmpty(userID) ){
            return null;
        }
        if(boardedAt == null || alightedAt == null){
            return  null;
        }
        return new Boarding.Builder()
                .setBoardingID(boardingID)
                .setUser(userID)
                .setLiveTrip(liveTripID)
                .setBoardedAt(boardedAt)
                .setAlightedAt(alightedAt)
                .build();
    }
}
