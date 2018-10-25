package pi.astro;

public class HorizontalCoordinates {

    private final double azimuth;
    private final double altitude;

    public HorizontalCoordinates(double azimuth, double altitude) {
        this.azimuth = azimuth;
        this.altitude = altitude;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public double getAltitude() {
        return altitude;
    }
}
