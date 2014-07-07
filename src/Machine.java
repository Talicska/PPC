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

    public void addCylinder(MagnetCylinder cylinder) { this.cylinders.add(cylinder); }

    public Machine(String name) {
        this.name = name;
        //this.cylinders = new ArrayList<MagnetCylinder>();
    }
}
