import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Prof on 2014.07.06..
 */
public class PPC {

    public static void main(String[] args) {

        System.out.println("Hello");

        PPCDB database = new PPCDB();

        ArrayList<Dye> dyes = new ArrayList<Dye>();
        ArrayList<DyeCylinder> dyecyls = new ArrayList<DyeCylinder>();
        Vector<Vector<Double>> etalonMatrix = new Vector<Vector<Double>>();
        ArrayList<Lakk> lakks = new ArrayList<Lakk>();
        ArrayList<Machine> machines = new ArrayList<Machine>();
        ArrayList<MagnetCylinder> magnetcylinders = new ArrayList<MagnetCylinder>();
        ArrayList<Material> materials = new ArrayList<Material>();
        ArrayList<Metal> metals = new ArrayList<Metal>();

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
        }

        Etalon etalonObj = new Etalon(etalonMatrix);

        for (int i = 0; i < etalonObj.getEtalonMatrix().size();i++){
            for(int j=0;j< etalonObj.getEtalonMatrix().get(i).size();j++) {
                System.out.print(etalonObj.getEtalonMatrix().get(i).get(j) + " ");
            }
            System.out.print("\n");
        }
    }
}
