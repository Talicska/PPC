import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Prof on 2014.07.06..
 */
public class PPC {

    public static void main(String[] args) {

        System.out.println("Hello");

        PPCDB database = new PPCDB();

        ArrayList<Dye> dyes = new ArrayList<Dye>();
        ArrayList<DyeCylinder> dyecyls = new ArrayList<DyeCylinder>();
        ArrayList<Lakk> lakks = new ArrayList<Lakk>();
        ArrayList<Machine> machines = new ArrayList<Machine>();
        ArrayList<MagnetCylinder> magnetcylinders = new ArrayList<MagnetCylinder>();
        ArrayList<Material> materials = new ArrayList<Material>();
        ArrayList<Metal> metals = new ArrayList<Metal>();

        try {
            dyes = database.getDyes();
            dyecyls = database.getDyeCylinders();
            lakks = database.getLakks();
            machines = database.getMachine();
            magnetcylinders = database.getMagnetCylinders();
            materials = database.getMaterials();
            metals = database.getMetals();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < materials.size(); i++) {
            System.out.println(materials.get(i).getName() + " " + materials.get(i).getPrice());

        }

    }


}
