import java.util.ArrayList;

/**
 * Created by Prof on 2014.07.09..
 */
public class Calculator {
    private static ArrayList<Dye> dyes = new ArrayList<Dye>();
    private static ArrayList<DyeCylinder> dyecylinders = new ArrayList<DyeCylinder>();
    private static Etalon etalonObj;
    private static ArrayList<Lakk> lakks = new ArrayList<Lakk>();
    private static ArrayList<Machine> machines = new ArrayList<Machine>();
    private static ArrayList<MagnetCylinder> magnetcylinders = new ArrayList<MagnetCylinder>();
    private static ArrayList<Material> materials = new ArrayList<Material>();
    private static ArrayList<Metal> metals = new ArrayList<Metal>();

    private MagnetCylinder chosenMagnetCylinder;
    private double verticalGap;

    public Calculator(ArrayList<Dye> dyes, ArrayList<DyeCylinder> dyecylinders, Etalon etalonObj, ArrayList<Lakk> lakks, ArrayList<Machine> machines,
                      ArrayList<MagnetCylinder> magnetcylinders, ArrayList<Material> materials, ArrayList<Metal> metals){
        this.dyes = dyes;
        this.dyecylinders = dyecylinders;
        this.etalonObj = etalonObj;
        this.lakks = lakks;
        this.machines = machines;
        this.magnetcylinders = magnetcylinders;
        this.materials = materials;
        this.metals = metals;
    }

    private void filterRolls(int machineIndex, int magnetCylinderIndex){

    }

    private void searchRoll(int machineIndex, double height, int magnetCylinderIndex){

        double min = 1000;
        int pieces = 0;

        if (magnetCylinderIndex == 0){	// Auto: the program chooses the ideal applicable cylinder
                                        // to reach the lowest (but bigger or equal than 2) vertical gap
            if (machines.get(machineIndex).getCylinders().size() > 1){ // more than 1 applicable cylinder for the chosen machine
                for (int i = 0; i < machines.get(machineIndex).getCylinders().size(); i++){
                    pieces = (int) (machines.get(machineIndex).getCylinders().get(i).getGirth()/height);
                    double rest = machines.get(machineIndex).getCylinders().get(i).getGirth()%height;
                    if (rest/pieces >= 2 && rest/pieces < min){
                        min = rest/pieces;
                        this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(i);
                        this.verticalGap = min;
                    }
                }
            }else{ // only 1 applicable cylinder for the chosen machine
                pieces = (int) (machines.get(machineIndex).getCylinders().get(0).getGirth()/height);
                double rest = machines.get(machineIndex).getCylinders().get(0).getGirth()%height;
                this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(0);
                this.verticalGap = rest/pieces;
            }
        }else{ // we want to use a specific applicable cylinder
            pieces = (int) (machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1).getGirth()/height);
            double rest = machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1).getGirth()%height;
            this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1);
            this.verticalGap = rest/pieces;
        }

    }



}
