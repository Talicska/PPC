
public class DyeCylinder {
    private double volume;
    private int percent;

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public DyeCylinder(double volume, int percent) {
        this.volume = volume;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return String.valueOf(volume);
    }
}
