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

    private void searchMagnetCylinder(int machineIndex, double height, int magnetCylinderIndex){

        double min = 1000;
        int pieces = 0;
        this.verticalGap = 1;

        if (magnetCylinderIndex == 0){	// Auto: the program chooses the ideal applicable cylinder
                                        // to reach the lowest (but bigger or equal than 2) vertical gap
            if (machines.get(machineIndex).getCylinders().size() > 1){ // more than 1 applicable cylinder for the chosen machine
                int j = 0;
                while(min >= 1000 && this.verticalGap < 2) {
                    for (int i = 0; i < machines.get(machineIndex).getCylinders().size(); i++) {
                        System.out.println(machines.get(machineIndex).getName());
                        System.out.println(machines.get(machineIndex).getCylinders().size());
                        pieces = (int) (machines.get(machineIndex).getCylinders().get(i).getGirth() / height) - j;
                        double rest = machines.get(machineIndex).getCylinders().get(i).getGirth() % height;
                        if (rest / pieces >= 2 && rest / pieces < min) {
                            System.out.println("min: " + min);
                            min = rest / pieces;
                            this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(i);
                            this.verticalGap = min;
                            System.out.println("Vertikalis heza valtozas: " + this.verticalGap);
                            System.out.println("darab per henger: " + pieces);
                        }
                    }
                    j++;
                }
            }else{ // only 1 applicable cylinder for the chosen machine
                int j = 0;
                while(this.verticalGap < 2) {
                    pieces = (int) (machines.get(machineIndex).getCylinders().get(0).getGirth() / height) - j;
                    double rest = machines.get(machineIndex).getCylinders().get(0).getGirth() % height;
                    this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(0);
                    this.verticalGap = rest / pieces;
                    j++;
                }
            }
        }else{ // we want to use a specific applicable cylinder
            int j = 0;
            while(this.verticalGap < 2) {
                pieces = (int) (machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1).getGirth()/height) - j;
                double rest = machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1).getGirth()%height;
                this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1);
                this.verticalGap = rest/pieces;
                j++;
                System.out.println("darab per henger: " + pieces);
            }
        }
    }

    public void calculate() {

        //Choosing magnet cylinder
        searchMagnetCylinder(1, 25, 0);
        System.out.println(chosenMagnetCylinder.getTeeth() + " " + chosenMagnetCylinder.getGirth());
        System.out.println(verticalGap);
    }
}
