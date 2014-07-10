import org.omg.DynamicAny._DynEnumStub;

import java.util.ArrayList;

/**
 * Created by Prof on 2014.07.09..
 */
public class Calculator {

    public static int MAX_NUMBER_OF_DYES = 7;
    public static int MAX_NUMBER_OF_LAKKS = 1;
    public static int MAX_NUMBER_OF_METALS = 2;
    //public static double TEKERCSAR = 2.1;

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

    private ArrayList<DyeParent> allDyeTypes;
    private ArrayList<DyeParent> addedDyes = new ArrayList<DyeParent>();
    private int dyeNum = 0;
    private int lakkNum = 0;
    private int metalNum = 0;

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

        ArrayList<DyeParent> allDyeTypes = new ArrayList<DyeParent>();
        allDyeTypes.addAll(dyes);
        allDyeTypes.addAll(lakks);
        allDyeTypes.addAll(metals);
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

    public boolean addDye(int chosenDyeIndex, double cylinderVolume, int cover, String otherColorName, double otherColorPrice){
        if (chosenDyeIndex < (dyes.size() + metals.size() + lakks.size())){// Dye from the list
            if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Lakk.class) && lakkNum < MAX_NUMBER_OF_LAKKS) {
                DyeParent chosenLakk = allDyeTypes.get(chosenDyeIndex);
                chosenLakk.setDyeCylinder(searchDyeCylinder(cylinderVolume));
                chosenLakk.setCover(cover);
                addedDyes.add(chosenLakk);
                lakkNum++;
                return true;
            }else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Metal.class) && metalNum < MAX_NUMBER_OF_METALS && metalNum+dyeNum < MAX_NUMBER_OF_DYES){
                DyeParent chosenMetal = allDyeTypes.get(chosenDyeIndex);
                chosenMetal.setDyeCylinder(searchDyeCylinder(cylinderVolume));
                chosenMetal.setCover(cover);
                addedDyes.add(chosenMetal);
                metalNum++;
                return true;
            }else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Dye.class) && metalNum+dyeNum < MAX_NUMBER_OF_DYES){
                DyeParent chosenDye = allDyeTypes.get(chosenDyeIndex);
                chosenDye.setDyeCylinder(searchDyeCylinder(cylinderVolume));
                chosenDye.setCover(cover);
                addedDyes.add(chosenDye);
                dyeNum++;
                return true;
            }
        }else { // other color - direct color
            if (dyeNum+metalNum < MAX_NUMBER_OF_DYES){
                Dye otherDye = new Dye(otherColorName, otherColorPrice, searchDyeCylinder(cylinderVolume), cover);
                addedDyes.add(otherDye);
                dyeNum++;
                return true;
            }
        }
        return false;
    }

    public void removeDye(int chosenDyeIndex){
        if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Lakk.class)){
            lakkNum--;
        }else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Metal.class)){
            metalNum--;
        }else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Dye.class)){
            dyeNum--;
        }
        addedDyes.remove(chosenDyeIndex);
    }

    public DyeCylinder searchDyeCylinder(double cylinderVolume){
        for (int i = 0; i < dyecylinders.size(); i++) {
            if (dyecylinders.get(i).getVolume() == cylinderVolume){
                return dyecylinders.get(i);
            }
        }
        return null;
    }

    public void calculate() {
        //Choosing magnet cylinder
        searchMagnetCylinder(1, 150, 0);
        System.out.println(chosenMagnetCylinder.getTeeth() + " " + chosenMagnetCylinder.getGirth());
        System.out.println(verticalGap);

        //Material cost

        //-----temp: guiból olvassuk ki--------
        int materialIndex = 1;
        double euro = 300;
        int amount = 10000;
        double width = 100;
        double height = 150;
        double sideGap = 5;
        double betweenGap = 3;
        int tracks = 2;
        //-------------------------------------

        double materialPrice = materials.get(materialIndex).getPrice() * euro;

        double materialWidth = sideGap + (tracks * width)+ ((tracks - 1) * betweenGap) + sideGap; // sidegap + labelwidth + betweengap + labelwidth + ... + labelwidth + betweengap + labelwidth + sidegap
        double materialHeight = ((double)amount/tracks) * height + (amount/tracks)*verticalGap;

        double materialSize = (materialHeight*materialWidth)/1000000;
        double materialSelfCost = materialSize * materialPrice;

        System.out.println(materialSelfCost);

        //Dye cost
        double dyeSelfCost=0;
        double actual=0;

        if (addedDyes == null){
            addedDyes = new ArrayList<DyeParent>();
        }

        for (int i=0; i < addedDyes.size(); i++){
            actual = addedDyes.get(i).getPrice() / 1000;
            dyeSelfCost = dyeSelfCost + actual * addedDyes.get(i).getDyeCylinder().getVolume() *
                    ((double)addedDyes.get(i).getDyeCylinder().getPercent()/100)*((width*height)/1000000) *
                    ((double)addedDyes.get(i).getCover()/100)*amount;

        }

        ArrayList<DyeParent> dyes = new ArrayList<DyeParent>();
        dyes.add(new Lakk("szilikonos",100,null,0));
        if (dyes.get(0).getClass() == Lakk.class) System.out.println("lakk");

        //Self cost of embossing (dombornyomas)
        /*domborSelfCost=0;
        if (domborPrice != 0){
            domborCost=(int)(amount/1000) * domborPrice * 0.2 + domborPrice;
            domborCheck=1;
        }else{
            domborCheck=0;
        }*/

        //Self cost of pregeles
        /*pregSumPrice=0;
        if (pregCover > 0){
            pregSumPrice=((width*height)/1000000)*((double)pregCover/100)*amount*pregPrice;
            pregCheck=1;
        }else{
            pregCheck=0;
        }*/

    }

    public ArrayList<Material> getMaterials(){
        return materials;
    }

    public static Etalon getEtalonObj() {
        return etalonObj;
    }

    public static void setEtalonObj(Etalon etalonObj) {
        Calculator.etalonObj = etalonObj;
    }
}
