package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.Boarding;
import za.ac.cput.GoShuttle.util.Helper;

import java.time.LocalDateTime;

public class boardingFactory {
    public static Boarding createBoarding(Long boardingID, Long userID, Long liveTripID, LocalDateTime boardedAt, LocalDateTime alightedAt){
        if(Helper.isEmpty(boardingID) || Helper.isEmpty(userID) || Helper.isEmpty(liveTripID) ){
            return null;
        }
        if(boardedAt == null || alightedAt == null){
            return  null;
        }
        return new Boarding.Builder()
                .setBoardingID(boardingID)
                .setUserID(userID)
                .setLiveTripID(liveTripID)
                .setBoardingAt(boardedAt)
                .setAlightedAt(alightedAt)
                .Build();
    }
}
