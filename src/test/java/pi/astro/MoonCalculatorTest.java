package pi.astro;


import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class MoonCalculatorTest {

    @Test
    public void testGetMoonPositionInTheSky() {
        ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.of(2013, Month.MARCH, 5, 0, 0), ZoneId.of("UTC"));
        HorizontalCoordinates coordinates = new MoonCalculator().getMoonPositionInTheSky(dateTime, 50.5, 30.5);
        Assert.assertEquals(-0.9783999522438226, coordinates.getAzimuth(), 0.001);
        Assert.assertEquals(0.014551482243892251, coordinates.getAltitude(), 0.001);
    }

    @Test
    public void testToDaysSince2000() {
        ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.of(2018, Month.AUGUST, 19, 18, 39), ZoneId.of("UTC"));
        double days = new MoonCalculator().toDaysSince2000(dateTime);
        Assert.assertEquals(6805.77, days, 0.01);
    }

    @Test
    public void testDays() {
        LocalDate beginning = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate today = LocalDate.of(2018, Month.AUGUST, 19);
        long days = ChronoUnit.DAYS.between(beginning, today);
        Assert.assertEquals(days, 0);
    }
}