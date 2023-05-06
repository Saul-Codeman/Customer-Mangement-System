package com.example.WGUSoftware2.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneConverter {

    public static ZonedDateTime localToUtc(LocalDateTime localDateTime) {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localZonedDateTime = localDateTime.atZone(localZoneId);
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZonedDateTime;
    }

    public static LocalDateTime estToUtc(LocalDateTime estDateTime) {
        ZoneId estZone = ZoneId.of("America/New_York");
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime estZonedDateTime = estDateTime.atZone(estZone);
        ZonedDateTime utcZonedDateTime = estZonedDateTime.withZoneSameInstant(utcZone);
        return utcZonedDateTime.toLocalDateTime();
    }

    public static LocalDateTime utcToLocal(LocalDateTime utcDateTime) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId systemZone = ZoneId.systemDefault();
        ZonedDateTime utcZonedDateTime = utcDateTime.atZone(utcZone);
        ZonedDateTime systemZonedDateTime = utcZonedDateTime.withZoneSameInstant(systemZone);
        return systemZonedDateTime.toLocalDateTime();
    }

    public static LocalDateTime localToEst(LocalDateTime localDateTime) {
        ZoneId systemZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime systemZonedDateTime = localDateTime.atZone(systemZone);
        ZonedDateTime estZonedDateTime = systemZonedDateTime.withZoneSameInstant(estZone);
        return estZonedDateTime.toLocalDateTime();
    }
}
