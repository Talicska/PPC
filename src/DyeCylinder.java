
public class DyeCylinder {
    private int volume;
    private int percent;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public DyeCylinder(int volume, int percent) {
        this.volume = volume;
        this.percent = percent;
    }
}
