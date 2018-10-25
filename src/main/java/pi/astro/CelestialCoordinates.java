package pi.astro;

public class CelestialCoordinates {

    private final double rightAscention;
    private final double declination;

    public CelestialCoordinates(double rightAscention, double declination) {
        this.rightAscention = rightAscention;
        this.declination = declination;
    }

    public double getRightAscention() {
        return rightAscention;
    }

    public double getDeclination() {
        return declination;
    }
}
