import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Prof on 2014.07.06..
 */
public class PPC {

    private static ArrayList<Dye> dyes = new ArrayList<Dye>();
    private static ArrayList<DyeCylinder> dyecylinders = new ArrayList<DyeCylinder>();
    private static Etalon etalonObj;
    private static ArrayList<Lakk> lakks = new ArrayList<Lakk>();
    private static ArrayList<Machine> machines = new ArrayList<Machine>();
    private static ArrayList<MagnetCylinder> magnetcylinders = new ArrayList<MagnetCylinder>();
    private static ArrayList<Material> materials = new ArrayList<Material>();
    private static ArrayList<Metal> metals = new ArrayList<Metal>();


    private static boolean init() {      //return true if successful

        PPCDB database = new PPCDB();
        Vector<Vector<Double>> etalonMatrix;

        try {
            dyes = database.getDyes();
            dyecylinders = database.getDyeCylinders();
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

    private static void listAll(){

        System.out.println("\nDyes");
        for(int i=0;i<dyes.size();i++){
            System.out.println(dyes.get(i).getName()+" "+dyes.get(i).getPrice());
        }
        System.out.println("\nDyecylinders");
        for(int i=0;i<dyecylinders.size();i++){
            System.out.println(dyecylinders.get(i).getVolume()+" "+dyecylinders.get(i).getPercent());
        }
        System.out.println("\nEtalon");
        for (int i = 0; i < etalonObj.getEtalonMatrix().size(); i++) {
            for (int j = 0; j < etalonObj.getEtalonMatrix().get(i).size(); j++) {
                System.out.print(etalonObj.getEtalonMatrix().get(i).get(j) + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\nLakks");
        for(int i=0;i<lakks.size();i++){
            System.out.println(lakks.get(i).getName()+" "+lakks.get(i).getPrice());
        }
        System.out.println("\nMachines");
        for(int i=0;i<machines.size();i++){
            System.out.println(machines.get(i).getName());
        }
        System.out.println("\nMagnetcylinders");
        for(int i=0;i<magnetcylinders.size();i++){
            System.out.println(magnetcylinders.get(i).getTeeth()+" "+magnetcylinders.get(i).getGirth());
        }
        System.out.println("\nMaterials");
        for(int i=0;i<materials.size();i++){
            System.out.println(materials.get(i).getName()+" "+materials.get(i).getPrice());
        }
        System.out.println("\nMetals");
        for(int i=0;i<metals.size();i++){
            System.out.println(metals.get(i).getName()+" "+metals.get(i).getPrice());
        }
    }

    public static void main(String[] args) {

        System.out.println("Starting...");
        if (!init()) {
            System.out.println("Initialization error!");
            System.exit(1);
        }
        System.out.println("Welcome!");

        listAll();



    }
}
