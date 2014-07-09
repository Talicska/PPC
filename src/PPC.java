/**
 * Created by Prof on 2014.07.06..
 */

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class PPC {

    private static ArrayList<Dye> dyes = new ArrayList<Dye>();
    private static ArrayList<DyeCylinder> dyecylinders = new ArrayList<DyeCylinder>();
    private static Etalon etalonObj;
    private static ArrayList<Lakk> lakks = new ArrayList<Lakk>();
    private static ArrayList<Machine> machines = new ArrayList<Machine>();
    private static ArrayList<MagnetCylinder> magnetcylinders = new ArrayList<MagnetCylinder>();
    private static ArrayList<Material> materials = new ArrayList<Material>();
    private static ArrayList<Metal> metals = new ArrayList<Metal>();

    public static Etalon getEtalonObj() {
        return etalonObj;
    }

    private static Gui guiObj;
    private static Calculator calcObj;

    private static boolean init() {      //return true if successful

        PPCDB database = new PPCDB();
        Vector<Vector<Double>> etalonMatrix;

        try {
            dyes = database.getDyes();
            dyecylinders = database.getDyeCylinders();
            etalonMatrix = database.getEtalon();
            lakks = database.getLakks();
            machines = database.getMachines();
            magnetcylinders = database.getMagnetCylinders();
            materials = database.getMaterials();
            metals = database.getMetals();

            machines = database.fillMachineMagCylinders(machines, magnetcylinders);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        etalonObj = new Etalon(etalonMatrix);

        return true;
    }

    private static void loadGui(int sizeX, int sizeY, int locX, int locY) {
        guiObj = new Gui();
        guiObj.setSize(sizeX, sizeY);
        guiObj.setLocation(locX, locY);
        guiObj.setVisible(true);
        guiObj.setResizable(false);
        guiObj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void listAll() {

        System.out.println("\nDyes");
        for (int i = 0; i < dyes.size(); i++) {
            System.out.println(dyes.get(i).getName() + " " + dyes.get(i).getPrice());
        }
        System.out.println("\nDyecylinders");
        for (int i = 0; i < dyecylinders.size(); i++) {
            System.out.println(dyecylinders.get(i).getVolume() + " " + dyecylinders.get(i).getPercent());
        }
        System.out.println("\nEtalon");
        for (int i = 0; i < etalonObj.getEtalonMatrix().size(); i++) {
            for (int j = 0; j < etalonObj.getEtalonMatrix().get(i).size(); j++) {
                System.out.print(etalonObj.getEtalonMatrix().get(i).get(j) + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\nLakks");
        for (int i = 0; i < lakks.size(); i++) {
            System.out.println(lakks.get(i).getName() + " " + lakks.get(i).getPrice());
        }
        System.out.println("\nMachines and applicable magnet cylinders");
        for (int i = 0; i < machines.size(); i++) {
            System.out.println(machines.get(i).getName());
            for (int j = 0; j < machines.get(i).getCylinders().size(); j++) {
                System.out.print(machines.get(i).getCylinders().get(j).getTeeth() + " ");
            }
            System.out.println();
        }
        System.out.println("\nMagnetcylinders");
        for (int i = 0; i < magnetcylinders.size(); i++) {
            System.out.println(magnetcylinders.get(i).getTeeth() + " " + magnetcylinders.get(i).getGirth());
        }
        System.out.println("\nMaterials");
        for (int i = 0; i < materials.size(); i++) {
            System.out.println(materials.get(i).getName() + " " + materials.get(i).getPrice());
        }
        System.out.println("\nMetals");
        for (int i = 0; i < metals.size(); i++) {
            System.out.println(metals.get(i).getName() + " " + metals.get(i).getPrice());
        }
    }

    public static void main(String[] args) {

        System.out.println("Starting...");
        if (!init()) {
            System.out.println("Initialization error!");
            System.exit(1);
        }
        System.out.println("Database loaded!");

        loadGui(1000, 600, 60, 60);
        System.out.println("User interface loaded!");

        calcObj = new Calculator(dyes, dyecylinders, etalonObj, lakks, machines, magnetcylinders, materials, metals);
        calcObj.calculate();

        //listAll();

    }
}
