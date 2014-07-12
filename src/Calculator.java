import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Prof on 2014.07.09..
 */

public class Calculator {

    private static final int MAX_NUMBER_OF_DYES = 7;
    private static final int MAX_NUMBER_OF_LAKKS = 1;
    private static final int MAX_NUMBER_OF_METALS = 2;
    private static final double ROLL_PRICE = 2.1;

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

    private Vector<Vector<Double>> profitMatrix = new Vector<Vector<Double>>();

    public Calculator(ArrayList<Dye> dyes, ArrayList<DyeCylinder> dyecylinders, Etalon etalonObj, ArrayList<Lakk> lakks, ArrayList<Machine> machines,
                      ArrayList<MagnetCylinder> magnetcylinders, ArrayList<Material> materials, ArrayList<Metal> metals) {
        this.dyes = dyes;
        this.dyecylinders = dyecylinders;
        this.etalonObj = etalonObj;
        this.lakks = lakks;
        this.machines = machines;
        this.magnetcylinders = magnetcylinders;
        this.materials = materials;
        this.metals = metals;

        allDyeTypes = new ArrayList<DyeParent>();
        allDyeTypes.addAll(dyes);
        allDyeTypes.addAll(lakks);
        allDyeTypes.addAll(metals);
    }

    private void filterRolls(int machineIndex, int magnetCylinderIndex) {

    }

    private void searchMagnetCylinder(int machineIndex, double height, int magnetCylinderIndex) {

        double min = 1000;
        int pieces = 0;
        this.verticalGap = 1;

        if (magnetCylinderIndex == 0) {    // Auto: the program chooses the ideal applicable cylinder
            // to reach the lowest (but bigger or equal than 2) vertical gap
            if (machines.get(machineIndex).getCylinders().size() > 1) { // more than 1 applicable cylinder for the chosen machine
                int j = 0;
                while (min >= 1000 && this.verticalGap < 2) {
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
            } else { // only 1 applicable cylinder for the chosen machine
                int j = 0;
                while (this.verticalGap < 2) {
                    pieces = (int) (machines.get(machineIndex).getCylinders().get(0).getGirth() / height) - j;
                    double rest = machines.get(machineIndex).getCylinders().get(0).getGirth() % height;
                    this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(0);
                    this.verticalGap = rest / pieces;
                    j++;
                }
            }
        } else { // we want to use a specific applicable cylinder
            int j = 0;
            while (this.verticalGap < 2) {
                pieces = (int) (machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1).getGirth() / height) - j;
                double rest = machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1).getGirth() % height;
                this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1);
                this.verticalGap = rest / pieces;
                j++;
                System.out.println("darab per henger: " + pieces);
            }
        }
    }

    public boolean addDye(int chosenDyeIndex, int chosenDyeCylinderIndex, int cover, String otherColorName, double otherColorPrice) {

        if (chosenDyeIndex < allDyeTypes.size()) {// Dye from the list
            if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Lakk.class) && lakkNum < MAX_NUMBER_OF_LAKKS) {
                DyeParent chosenLakk = allDyeTypes.get(chosenDyeIndex);
                if (!isDyeAlreadyAdded(chosenLakk.getName())) {
                    chosenLakk.setDyeCylinder(dyecylinders.get(chosenDyeCylinderIndex));
                    chosenLakk.setCover(cover);
                    addedDyes.add(chosenLakk);
                    lakkNum++;
                    return true;
                }
                return false;
            } else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Metal.class) && metalNum < MAX_NUMBER_OF_METALS && metalNum + dyeNum < MAX_NUMBER_OF_DYES) {
                DyeParent chosenMetal = allDyeTypes.get(chosenDyeIndex);
                if (!isDyeAlreadyAdded(chosenMetal.getName())) {
                    chosenMetal.setDyeCylinder(dyecylinders.get(chosenDyeCylinderIndex));
                    chosenMetal.setCover(cover);
                    addedDyes.add(chosenMetal);
                    metalNum++;
                    return true;
                }
                return false;
            } else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Dye.class) && metalNum + dyeNum < MAX_NUMBER_OF_DYES) {
                DyeParent chosenDye = allDyeTypes.get(chosenDyeIndex);
                if (!isDyeAlreadyAdded(chosenDye.getName())) {
                    chosenDye.setDyeCylinder(dyecylinders.get(chosenDyeCylinderIndex));
                    chosenDye.setCover(cover);
                    addedDyes.add(chosenDye);
                    dyeNum++;
                    return true;
                }
                return false;
            }
        } else { // other color - direct color
            if (dyeNum + metalNum < MAX_NUMBER_OF_DYES) {
                Dye otherDye = new Dye(otherColorName, otherColorPrice, dyecylinders.get(chosenDyeCylinderIndex), cover);
                if (!isDyeAlreadyAdded(otherDye.getName())) {
                    addedDyes.add(otherDye);
                    dyeNum++;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public void removeDye(int chosenDyeIndex) {
        if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Lakk.class)) {
            lakkNum--;
        } else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Metal.class)) {
            metalNum--;
        } else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Dye.class)) {
            dyeNum--;
        }
        addedDyes.remove(chosenDyeIndex);
    }

    public DyeCylinder searchDyeCylinder(double cylinderVolume) {
        for (int i = 0; i < dyecylinders.size(); i++) {
            if (dyecylinders.get(i).getVolume() == cylinderVolume) {
                return dyecylinders.get(i);
            }
        }
        return null;
    }

    /*public boolean isDyeAlreadyAdded(DyeParent dyeParent){
        for (int i = 0; i < addedDyes.size(); i++) {
            if (addedDyes.get(i).equals(dyeParent)) {
                System.out.println("already found the dye");
                return true;
            }
        }
        return false;
    }*/

    public boolean isDyeAlreadyAdded(String dyeName) {
        for (int i = 0; i < addedDyes.size(); i++) {
            if (addedDyes.get(i).getName().equals(dyeName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isThereLakk() {
        for (int i = 0; i < addedDyes.size(); i++) {
            if (addedDyes.get(i).getClass().equals(Lakk.class)) {
                return true;
            }
        }
        return false;
    }

    public int countColors() {
        if (isThereLakk()) return addedDyes.size() - 1;
        return addedDyes.size();
    }

    private void transformEtalon(double width, double height) {

        double sizeRate = (width * height) / (etalonObj.getEtalonSizeX() * etalonObj.getEtalonSizeY());

        profitMatrix = new Vector<Vector<Double>>();
        for (int i = 0; i < etalonObj.getEtalonMatrix().size(); i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 1; j < etalonObj.getEtalonMatrix().get(i).size(); j++) { // first column of etalon matrix are the amounts, which we don't transform
                row.add((etalonObj.getEtalonMatrix().get(i).get(j) - etalonObj.getEtalonSelfCost()) * sizeRate);
            }
            profitMatrix.add(row);
        }

        for (int i = 0; i < profitMatrix.size(); i++) {
            for (int j = 0; j < profitMatrix.get(i).size(); j++) {
                System.out.print(profitMatrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    private double calcProfitOnPiece(int amount, double width, double height, int colorNum, double materialSelfCost, int discount) {
        int newAmount = (int) (((width * height) * amount) / (etalonObj.getEtalonSizeX() * etalonObj.getEtalonSizeY()));

        int newAmountIndex = 0;
        while (newAmountIndex < etalonObj.getEtalonMatrix().size() - 1 && etalonObj.getEtalonMatrix().get(newAmountIndex).get(0) < newAmount) {
            newAmountIndex++;
        }

        if (newAmount <= etalonObj.getEtalonMatrix().get(0).get(0) || newAmount >= etalonObj.getEtalonMatrix().get(etalonObj.getEtalonMatrix().size() - 1).get(0)) {
            return (profitMatrix.get(newAmountIndex).get(colorNum) + (materialSelfCost / amount)) * ((double) (100 - discount) / 100);
        } else {
            double highPrice = profitMatrix.get(newAmountIndex).get(colorNum);
            double lowPrice = profitMatrix.get(newAmountIndex - 1).get(colorNum);

            double highDb = etalonObj.getEtalonMatrix().get(newAmountIndex).get(0);
            ;
            double lowDb = etalonObj.getEtalonMatrix().get(newAmountIndex - 1).get(0);
            return ((highPrice * ((newAmount - lowDb) / (highDb - lowDb)) +
                    lowPrice * ((highDb - newAmount) / (highDb - lowDb))) +
                    (materialSelfCost / amount)) * ((double) (100 - discount) / 100);
        }

    }

    public double calculatePackingCost(int amount, double packingCost, double packingTime, double rollWidth, int amountPerRoll) {
        double packingSelfCost = 0;
        int numberOfRolls = 0;
        if (amountPerRoll != 0 && rollWidth != 0) {
            //numberOfRolls = (double) amount / amountPerRoll;
            numberOfRolls = amount/amountPerRoll + 1;
            packingSelfCost = packingTime * packingCost + numberOfRolls * ROLL_PRICE * rollWidth;
        }
        return packingSelfCost;
    }

    public void calculate(int materialIndex, int amount, double width, double height, int tracks, double sideGap, double betweenGap,
                          int machineIndex, int magnetCylinderIndex, int pregCover, double domborPrice, double clicheCost,
                          double stancCost, double packingCost, double packingTime, double rollWidth, int amountPerRoll,
                          String title, String client, int discount, double euro, double otherCost) {

        //Choosing magnet cylinder
        searchMagnetCylinder(machineIndex, height, magnetCylinderIndex);
        System.out.println(chosenMagnetCylinder.getTeeth() + " " + chosenMagnetCylinder.getGirth());
        System.out.println(verticalGap);

        //Material cost
        double materialPrice = materials.get(materialIndex).getPrice() * euro;

        double materialWidth = sideGap + (tracks * width) + ((tracks - 1) * betweenGap) + sideGap; // sidegap + labelwidth + betweengap + labelwidth + ... + labelwidth + betweengap + labelwidth + sidegap
        double materialHeight = ((double) amount / tracks) * height + (amount / tracks) * verticalGap;

        double materialSize = (materialHeight * materialWidth) / 1000000;
        double materialSelfCost = materialSize * materialPrice;

        System.out.println(materialSelfCost);

        //Dye cost
        double dyeSelfCost = 0;
        double actual = 0;

        if (addedDyes == null) {
            addedDyes = new ArrayList<DyeParent>();
        }

        for (int i = 0; i < addedDyes.size(); i++) {
            actual = addedDyes.get(i).getPrice() / 1000;
            dyeSelfCost = dyeSelfCost + actual * addedDyes.get(i).getDyeCylinder().getVolume() *
                    ((double) addedDyes.get(i).getDyeCylinder().getPercent() / 100) * ((width * height) / 1000000) *
                    ((double) addedDyes.get(i).getCover() / 100) * amount;

        }

        ArrayList<DyeParent> dyes = new ArrayList<DyeParent>();
        dyes.add(new Lakk("szilikonos", 100, null, 0));
        if (dyes.get(0).getClass() == Lakk.class) System.out.println("lakk");
        System.out.println("Festékköltség: " + dyeSelfCost);

        //guiból
        int domborCheck = 0;
        //--------------------
        //Self cost of embossing (dombornyomas)
        double domborSelfCost = 0;
        if (domborPrice != 0) {
            domborSelfCost = (int) (amount / 1000) * domborPrice * 0.2 + domborPrice;
            domborCheck = 1;
        } else {
            domborCheck = 0;
        }

        //guiból
        int pregCheck = 0;
        double pregPrice = 120; //adatbázis, vagy beégetés esetleg gui?
        //------------------
        //Self cost of pregeles
        double pregSelfCost = 0;
        if (pregCover > 0) {
            pregSelfCost = ((width * height) / 1000000) * ((double) pregCover / 100) * amount * pregPrice;
            pregCheck = 1;
        } else {
            pregCheck = 0;
        }

        //Packing
        double packingSelfCost = calculatePackingCost(amount, packingCost, packingTime, rollWidth, amountPerRoll);


        //SUM self cost
        double sumPrice = dyeSelfCost + materialSelfCost + domborSelfCost + pregSelfCost + clicheCost + stancCost +
                packingSelfCost + otherCost;
        System.out.println("Önköltség: " + sumPrice);
        System.out.println("Festék költség: " + dyeSelfCost);
        System.out.println("Alapanyagköltség: " + materialSelfCost);
        System.out.println("Dombornyomás ktg: " + domborSelfCost);
        System.out.println("Prégelés ktg: " + pregSelfCost);
        System.out.println("Klisé ktg: " + clicheCost);
        System.out.println("Stanc ktg: " + stancCost);
        System.out.println("Kiszerelés ktg: " + packingSelfCost);
        System.out.println("Egyéb ktg: " + otherCost);


        //Profit - árrés
        int colorNum = countColors();
        transformEtalon(width, height);
        double profitOnPiece = calcProfitOnPiece(amount, width, height, colorNum, materialSelfCost, discount);
        System.out.println("Darabár: " + profitOnPiece);

        /*if (pregCheck == 0){
            arresDbPrice = calcArresDbPrice(amount, colorNum);
        }else{
            arresDbPrice = calcArresDbPrice(amount, colorNum);
            System.out.println(this.pregCover);
            arresDbPrice = arresDbPrice * 2.0 * ((double)(100 + pregCover) / 100);
        }*/

    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public static ArrayList<Machine> getMachines() {
        return machines;
    }

    public double getVerticalGap() {
        return verticalGap;
    }

    public MagnetCylinder getChosenMagnetCylinder() {
        return chosenMagnetCylinder;
    }

    public static ArrayList<Metal> getMetals() {
        return metals;
    }

    public static ArrayList<MagnetCylinder> getMagnetcylinders() {
        return magnetcylinders;
    }

    public static ArrayList<Lakk> getLakks() {
        return lakks;
    }

    public static ArrayList<DyeCylinder> getDyecylinders() {
        return dyecylinders;
    }

    public static ArrayList<Dye> getDyes() {
        return dyes;
    }

    public ArrayList<DyeParent> getAllDyeTypes() {
        return allDyeTypes;
    }

    public ArrayList<DyeParent> getAddedDyes() {
        return addedDyes;
    }


    public static Etalon getEtalonObj() {
        return etalonObj;
    }

    public static void setEtalonObj(Etalon etalonObj) {
        Calculator.etalonObj = etalonObj;
    }
}
