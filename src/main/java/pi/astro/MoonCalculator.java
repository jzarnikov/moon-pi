package pi.astro;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

public class MoonCalculator {

    private static final double RAD = Math.PI / 180;
    private static final double EARTH_OBLIQUITY = RAD * 23.4397;

    public HorizontalCoordinates getMoonPositionInTheSky(ZonedDateTime dateTime, double latitude, double longitude) {
        double lw = RAD * - longitude;
        double phi = RAD * latitude;
        double days = toDaysSince2000(dateTime) - 0.5;
        CelestialCoordinates moonCoordinates = moonCoordinates(days);
        double h = siderealTime(days, lw) - moonCoordinates.getRightAscention();
        double azimuth = azimuth(h, phi, moonCoordinates.getDeclination());
        double altitude = altitude(h, phi, moonCoordinates.getDeclination());
        return new HorizontalCoordinates(azimuth, altitude);
    }

    protected CelestialCoordinates moonCoordinates(double days) {
        double eclipticLogitude = RAD * (218.316 + 13.176396 * days);
        double meanAnomaly = RAD * (134.963 + 13.064993 * days);
        double meanDistance = RAD * (93.272 + 13.229350 * days);
        double longitude = eclipticLogitude + RAD * 6.289 * sin(meanAnomaly);
        double latitude = RAD * 5.128 * sin(meanDistance);
        return new CelestialCoordinates(
                rightAscension(longitude, latitude),
                declination(longitude, latitude));
    }

    private double rightAscension(double longitude, double latitude) {
        return atan2(sin(longitude) * cos(EARTH_OBLIQUITY) - tan(latitude) * sin(EARTH_OBLIQUITY), cos(longitude));
    }

    private double declination(double longitude, double latitude) {
        return asin(sin(latitude) * cos(EARTH_OBLIQUITY) + cos(latitude) * sin(EARTH_OBLIQUITY) * sin(longitude));
    }

    private double siderealTime(double days, double lw) {
        return RAD * (280.16 + 360.9856235 * days) - lw;
    }

    private double altitude(double h, double phi, double dec) {
        return asin(sin(phi) * sin(dec) + cos(phi) * cos(dec) * cos(h));
    }

    private double azimuth(double h, double phi, double dec) {
        return atan2(sin(h), cos(h) * sin(phi) - tan(dec) * cos(phi));
    }

    protected double toDaysSince2000(ZonedDateTime dateTime) {
        LocalDate beginning = LocalDate.of(2000, Month.JANUARY, 1);
        ZonedDateTime dateTimeAtUtc = dateTime.withZoneSameInstant(ZoneId.of("UTC"));
        double days = ChronoUnit.DAYS.between(beginning, dateTimeAtUtc.toLocalDate());
        double millis = ChronoUnit.MILLIS.between(LocalTime.of(0, 0), dateTimeAtUtc.toLocalTime());
        days += millis / (24*60*60*1000);
        return days;
    }
}
