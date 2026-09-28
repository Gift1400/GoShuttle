package za.ac.cput.GoShuttle.factory;

import za.ac.cput.GoShuttle.domain.Pass;
import za.ac.cput.GoShuttle.domain.Routes;
import za.ac.cput.GoShuttle.util.Helper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PassFactory {

    public static Pass createPassFactory(
            User userId, Pass.PassType passType,
            Date validUntil, int daysLeft, int tripsUsed, int amountSaved,
            List<Routes> preferredRouteId
    ) {

        if(Helper.isNull(userId) || Helper.isNull(passType) || Helper.isNull(daysLeft)){
            return null;
        }

        if(daysLeft <= 0 || tripsUsed <= 0 || amountSaved <= 0){
            return null;
        }

        String passId = UUID.randomUUID().toString();

        return new Pass.Builder()
                .setPassId(passId)
                .setUserId(userId)
                .setPassId(passType)
                .setDaysLeft(daysLeft)
                .setTripsUsed(tripsUsed)
                .setValidUntil(validUntil)
                .setAmountSaved(amountSaved)
                .setPreferredRouteId(preferredRouteId)
                .build();
    }
}
