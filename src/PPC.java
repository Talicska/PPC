/**
 * Created by Prof on 2014.07.06..
 */

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class PPC {

    private static Gui guiObj;
    public static Calculator calcObj;
    public static PPCDB database;

    private static boolean init() {      //return true if successful

        database = new PPCDB();
        Vector<Vector<Double>> etalonMatrix;

        ArrayList<DyeParent> dyeParents;
        ArrayList<Dye> dyes = new ArrayList<Dye>();
        ArrayList<Lakk> lakks = new ArrayList<Lakk>();
        ArrayList<Metal> metals = new ArrayList<Metal>();
        Vector<DyePreset> dyePresets = new Vector<DyePreset>();
        Vector<DyeCylinder> dyeCylinders;
        Etalon etalonObj;
        ArrayList<Machine> machines;
        ArrayList<MagnetCylinder> magnetCylinders;
        Vector<Material> materials;


        try {
            dyeParents = database.getDyeParents();
            for (int i = 0; i < dyeParents.size(); i++){
                if (dyeParents.get(i).getClass().equals(Dye.class)){
                    dyes.add((Dye)dyeParents.get(i));
                }else if (dyeParents.get(i).getClass().equals(Lakk.class)){
                    lakks.add((Lakk)dyeParents.get(i));
                }else if (dyeParents.get(i).getClass().equals(Metal.class)){
                    metals.add((Metal)dyeParents.get(i));
                }
            }
            dyePresets = database.getDyePresets();
            //classifyDyeParents(dyeParents);
            dyeCylinders = database.getDyeCylinders();
            etalonMatrix = database.getEtalon();
            machines = database.getMachines();
            magnetCylinders = database.getMagnetCylinders();
            materials = database.getMaterials();

            machines = database.fillMachineMagCylinders(machines, magnetCylinders);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        etalonObj = new Etalon(etalonMatrix);
        calcObj = new Calculator(dyes, dyeCylinders, etalonObj, lakks, machines, magnetCylinders, materials, metals, dyePresets);

        //dyePresets = new Vector<DyePreset>();                                           //feltölteni adatbázisból

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

        /*System.out.println("\nDyes");
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
        }*/
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


        int materialIndex = 1;
        int amount = 10000;
        double width = 100;
        double height = 150;
        double sideGap = 5;
        double betweenGap = 3;
        int tracks = 2;
        int machineIndex = 1;
        int magnetCylinderIndex = 0;

        int pregCover = 0;
        double domborPrice = 0;

        double clicheCost = 0;
        double stancCost = 0;

        double packingCost = 6000;
        double packingTime = 0;
        double rollWidth = 2.2;
        int amountPerRoll = 0;

        String title = "Bor ÁSZ";
        String client = "Damjanich maffia";
        int discount = 0;

        double euro = 300;

        double otherCost = 0;

        calcObj.calculate(materialIndex, amount, width, height, tracks, sideGap, betweenGap, machineIndex, magnetCylinderIndex,
                pregCover, domborPrice, clicheCost, stancCost, packingCost, packingTime, rollWidth, amountPerRoll,
                title, client, discount, euro, otherCost);

        /*try{
            database.addMaterial("Tompos", 1.234);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        /*try{
            int teeth = 100;
            database.addMagnetCylinder(teeth, 3.175 * teeth);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        /*try{
            database.addDyeType(new Dye("fosbarna", 1, null, 0));
            database.addDyeType(new Metal("acélkék", 3000, null, 0));
            database.addDyeType(new Lakk("nitrohigító", 1000, null, 0));
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        /*try{
            database.addDyeParent(new Lakk("newlakk", 1000, null, 0));
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        //listAll();

    }
}
