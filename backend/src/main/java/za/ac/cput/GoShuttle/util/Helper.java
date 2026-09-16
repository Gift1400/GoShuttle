package za.ac.cput.GoShuttle.util;

import za.ac.cput.GoShuttle.domain.Boarding;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Helper {

    public static boolean isNullorEmpty(String value){
        if(value == null){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Long value) {
        return value == null;
    }

    public static int calculateOccupancy(Long liveTripId, List<Boarding> allBoardings) {
        return (int) allBoardings.stream()
                .filter(b -> b.getLiveTripID().equals(liveTripId))
                .filter(b -> b.getAlightedAt() == null)
                .count();
    }

    public static String formatCapacityPercentage(int occupied, int capacity) {
        if (capacity <= 0) {
            return "0%";
        }
        int percentage = Math.round(((float) occupied / capacity) * 100);
        return Math.min(percentage, 100) + "%";
    }

    public static String getCapacityStatus(int occupied, int capacity) {
        if (capacity <= 0) {
            return "Unknown";
        }
        double ratio = (double) occupied / capacity;
        if (ratio >= 1.0) {
            return "Full";
        }
        if (ratio >= 0.75) {
            return "Filling Up";
        }
        return "Available";
    }
    private static final double DEFAULT_AVG_SPEED_KMH = 30.0;

    public static LocalDateTime estimateArrivalTime(double distanceKm, double avgSpeedKmh) {
        if (distanceKm <= 0) {
            return LocalDateTime.now();
        }
        double hoursToArrival = distanceKm / avgSpeedKmh;
        long minutesToArrival = Math.round(hoursToArrival * 60);
        return LocalDateTime.now().plusMinutes(minutesToArrival);
    }

    public static LocalDateTime estimateArrivalTime(double distanceKm) {
        return estimateArrivalTime(distanceKm, DEFAULT_AVG_SPEED_KMH);
    }

    public static String formatEta(LocalDateTime arrivalTime) {
        long minutesUntil = ChronoUnit.MINUTES.between(LocalDateTime.now(), arrivalTime);
        if (minutesUntil <= 0) {
            return "Arriving now";
        }
        return minutesUntil + " min";
    }
    public enum liveTripStatus{
        ON_TIME,
        DELAYED,
        ARRIVED
    }
    public static boolean isNull(int value){
        return value == 0;
    }
}