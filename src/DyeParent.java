
public class DyeParent {
	protected String name;
	protected int price;
	protected int cylinder_volume;
	protected int cover;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCylinder_volume() {
		return cylinder_volume;
	}
	public void setCylinder_volume(int cylinderVolume) {
		cylinder_volume = cylinderVolume;
	}
	public int getCover() {
		return cover;
	}
	public void setCover(int cover) {
		this.cover = cover;
	}

    public DyeParent(String name, int price, int cylinder_volume, int cover) {
        this.name = name;
        this.price = price;
        this.cylinder_volume = cylinder_volume;
        this.cover = cover;
    }
}
