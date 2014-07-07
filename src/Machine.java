import java.util.ArrayList;


public class Machine {
    private String name;
    private ArrayList<MagnetCylinder> cylinders = new ArrayList<MagnetCylinder>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MagnetCylinder> getCylinders() {
        return cylinders;
    }

    public void setCylinders(ArrayList<MagnetCylinder> cylinders) {
        this.cylinders = cylinders;
    }

    public void addCylinder(MagnetCylinder cylinder) { cylinders.add(cylinder); }

    public Machine(String name, ArrayList<MagnetCylinder> cylinders) {
        this.name = name;
        this.cylinders = cylinders;
    }
}
