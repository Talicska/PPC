import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Prof on 2014.07.06..
 */
public class PPC {

    private static ArrayList<Dye> dyes = new ArrayList<Dye>();
    private static ArrayList<DyeCylinder> dyecyls = new ArrayList<DyeCylinder>();
    private static Etalon etalonObj;
    private static ArrayList<Lakk> lakks = new ArrayList<Lakk>();
    private static ArrayList<Machine> machines = new ArrayList<Machine>();
    private static ArrayList<MagnetCylinder> magnetcylinders = new ArrayList<MagnetCylinder>();
    private static ArrayList<Material> materials = new ArrayList<Material>();
    private static ArrayList<Metal> metals = new ArrayList<Metal>();


    private static boolean init() {      //return true if successful

        PPCDB database = new PPCDB();
        Vector<Vector<Double>> etalonMatrix = new Vector<Vector<Double>>();

        try {
            dyes = database.getDyes();
            dyecyls = database.getDyeCylinders();
            etalonMatrix = database.getEtalon();
            lakks = database.getLakks();
            machines = database.getMachine();
            magnetcylinders = database.getMagnetCylinders();
            materials = database.getMaterials();
            metals = database.getMetals();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        etalonObj = new Etalon(etalonMatrix);

        return true;
    }

    private void listAll(){

    }

    public static void main(String[] args) {

        System.out.println("Hello");
        if (!init()) {
            System.out.println("Initialization error!");
            System.exit(1);
        }




        for (int i = 0; i < etalonObj.getEtalonMatrix().size(); i++) {
            for (int j = 0; j < etalonObj.getEtalonMatrix().get(i).size(); j++) {
                System.out.print(etalonObj.getEtalonMatrix().get(i).get(j) + " ");
            }
            System.out.print("\n");
        }
    }
}
