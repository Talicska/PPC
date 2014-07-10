
public class DyeParent {
    protected String name;
    protected double price;
    protected DyeCylinder dyeCylinder;
    protected int cover;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DyeCylinder getDyeCylinder() {
        return dyeCylinder;
    }

    public void setDyeCylinder(DyeCylinder dyeCylinder) {
        this.dyeCylinder = dyeCylinder;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public DyeParent(String name, double price, DyeCylinder dyeCylinder, int cover) {
        this.name = name;
        this.price = price;
        this.dyeCylinder = dyeCylinder;
        this.cover = cover;
    }
}
