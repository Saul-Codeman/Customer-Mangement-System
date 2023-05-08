package com.example.WGUSoftware2.model;

import com.example.WGUSoftware2.utility.UserSessionInfo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * TimeZoneConverter class that changes the time based on a localdatetime given
 */
public class TimeZoneConverter {

    /**
     * Changes user local time to UTC
     * @param localDateTime local time given
     * @return utc zoned datetime
     */
    public static ZonedDateTime localToUtc(LocalDateTime localDateTime) {
        ZoneId localZoneId = UserSessionInfo.getCurrentUserTimeZone();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime localZonedDateTime = localDateTime.atZone(localZoneId);
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(utcZoneId);
        return utcZonedDateTime;
    }

    /**
     * Changes eastern time to UTC
     * @param estDateTime eastern datetime provided
     * @return utc zoned datetime
     */
    public static ZonedDateTime estToUtc(LocalDateTime estDateTime) {
        ZoneId estZone = ZoneId.of("America/New_York");
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime estZonedDateTime = estDateTime.atZone(estZone);
        ZonedDateTime utcZonedDateTime = estZonedDateTime.withZoneSameInstant(utcZone);
        return utcZonedDateTime;
    }

    /**
     * Changes UTC time to user local time
     * @param utcDateTime UTC time provided
     * @return local datetime
     */
    public static ZonedDateTime utcToLocal(LocalDateTime utcDateTime) {
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZoneId localZoneId = UserSessionInfo.getCurrentUserTimeZone();
        ZonedDateTime utcZonedDateTime = utcDateTime.atZone(utcZoneId);
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(localZoneId);
        return localZonedDateTime;
    }


    /**
     * Changes local time to eastern time
     * @param localDateTime local datetime provided
     * @return eastern time
     */
    public static LocalDateTime localToEst(LocalDateTime localDateTime) {
        ZoneId localZoneId = UserSessionInfo.getCurrentUserTimeZone();
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZonedDateTime localZonedDateTime = localDateTime.atZone(localZoneId);
        ZonedDateTime estZonedDateTime = localZonedDateTime.withZoneSameInstant(estZoneId);
        return estZonedDateTime.toLocalDateTime();
    }
}
