
public class Material {
	private String name;
	private double price;

    public Material(String name, double price) {
        this.name = name;
        this.price = price;
    }

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
}
