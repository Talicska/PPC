/**
 * Created by Prof on 2014.07.09..
 */

import java.util.ArrayList;
import java.util.Vector;

public class Calculator {

    private static final int MAX_NUMBER_OF_DYES = 7000;
    private static final int MAX_NUMBER_OF_LAKKS = 1000;
    private static final int MAX_NUMBER_OF_METALS = 2000;
    private static final int MAX_NUMBER_OF_FLUOS = 1000;
    private static final double ROLL_PRICE = 0.21;
    private static final double PREG_PRICE = 120;

    private static ArrayList<Dye> dyes = new ArrayList<Dye>();
    private static Vector<DyeCylinder> dyeCylinders = new Vector<DyeCylinder>();
    private static Etalon etalonObj;
    private static ArrayList<Lakk> lakks = new ArrayList<Lakk>();
    private static ArrayList<Machine> machines = new ArrayList<Machine>();
    private static ArrayList<MagnetCylinder> magnetCylinders = new ArrayList<MagnetCylinder>();
    private static Vector<Material> materials = new Vector<Material>();
    private static ArrayList<Metal> metals = new ArrayList<Metal>();
    private static ArrayList<Fluo> fluos = new ArrayList<Fluo>();

    private Material material;
    private int amount;
    private double width;
    private double height;
    private int colorNum;
    private double stancCost;
    private double clicheCost;
    private double otherCost; // added to the sale price (cliche and stanc NOT)
    private String title;

    private MagnetCylinder chosenMagnetCylinder;
    private double verticalGap;
    private double materialWidth;
    private double materialHeight;
    private double materialSize;
    private double materialSelfCost;
    private double dyeSelfCost;
    private double packingSelfCost;
    private int numberOfRolls = 0;
    private double domborSelfCost = 0;
    private double pregSelfCost = 0;
    private double sumPrice;
    private double profitOnPiece;

    private Vector<DyeParent> allDyeTypes;
    private static Vector<DyePreset> dyePresets = new Vector<DyePreset>();
    private static Vector<DyeParent> addedDyes = new Vector<DyeParent>();

    private int dyeNum = 0;
    private int lakkNum = 0;
    private int metalNum = 0;
    private int fluoNum = 0;

    private Vector<Vector<Double>> profitMatrix = new Vector<Vector<Double>>();

    private PdfExporter pdfExporterObj;

    public Calculator(ArrayList<Dye> dyes, Vector<DyeCylinder> dyecylinders, Etalon etalonObj, ArrayList<Lakk> lakks, ArrayList<Machine> machines,
                      ArrayList<MagnetCylinder> magnetcylinders, Vector<Material> materials, ArrayList<Metal> metals, Vector<DyePreset> dyePresets,
                      ArrayList<Fluo> fluos) {
        Calculator.dyes = dyes;
        Calculator.dyeCylinders = dyecylinders;
        Calculator.etalonObj = etalonObj;
        Calculator.lakks = lakks;
        Calculator.machines = machines;
        Calculator.magnetCylinders = magnetcylinders;
        Calculator.materials = materials;
        Calculator.metals = metals;
        Calculator.dyePresets = dyePresets;
        Calculator.fluos = fluos;

        allDyeTypes = new Vector<DyeParent>();
        allDyeTypes.addAll(dyes);
        allDyeTypes.addAll(lakks);
        allDyeTypes.addAll(metals);
        allDyeTypes.addAll(fluos);

    }

    private void debugDyePresets(){
        for (int i = 0; i < dyePresets.size(); i++){
            System.out.println(dyePresets.get(i).getName());
            for (int j = 0; j < dyePresets.get(i).getDyes().size(); j++){
                System.out.println("    " + dyePresets.get(i).getDyes().get(j).getName() + " " +
                        dyePresets.get(i).getDyes().get(j).getPrice() + " " +
                        dyePresets.get(i).getDyes().get(j).getDyeCylinder().getVolume() + " " +
                        dyePresets.get(i).getDyes().get(j).getCover());
            }
        }
    }
/*
    public boolean Tompi(int pieces,int machineIndex,double height){
        double min=1000;
        boolean found=false;
        for (int i = 0; i < machines.get(machineIndex).getCylinders().size(); i++) {
            //pieces = (int) (machines.get(machineIndex).getCylinders().get(i).getGirth() / height);
            double rest = machines.get(machineIndex).getCylinders().get(i).getGirth() - pieces*height;
            System.out.println(rest+" "+pieces+" "+rest / pieces+" "+machines.get(machineIndex).getCylinders().get(i).getGirth());
            if (rest/pieces >= 2 && rest/pieces < min && machines.get(machineIndex).getCylinders().get(i).getGirth()>height) {
                min = rest / pieces;
                this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(i);
                this.verticalGap = min;
                found=true;
            }
        }
        if(found){
            return true;
        }
        if(pieces<1){
            return false;
        }
        Tompi( pieces-1, machineIndex, height);

        if(verticalGap!=1)
            return true;
        return false;
    }

    private void searchMagnetCylinder(int machineIndex, double height, int magnetCylinderIndex) {

        double min = 1000;
        int pieces = 0;
        this.verticalGap = 1;

        if (magnetCylinderIndex == 0) {    // Auto: the program chooses the ideal applicable cylinder
            // to reach the lowest (but bigger or equal than 2) vertical gap
            if (machines.get(machineIndex).getCylinders().size() > 1) { // more than 1 applicable cylinder for the chosen machine

                pieces = (int) (machines.get(machineIndex).getCylinders().get(machines.get(machineIndex).getCylinders().size()-1).getGirth() / height);
                System.out.println("Kezdeti pieces: "+pieces );

                if(Tompi(pieces,machineIndex,  height)==false){
                    System.out.println("FALSE");
                }




                /*int j = 0;
                while (min >= 1000 && this.verticalGap < 2) {
                    System.out.println(min+this.verticalGap);
                    for (int i = 0; i < machines.get(machineIndex).getCylinders().size(); i++) {
                        //System.out.println(machines.get(machineIndex).getName());
                        //System.out.println(machines.get(machineIndex).getCylinders().size());
                        pieces = (int) (machines.get(machineIndex).getCylinders().get(i).getGirth() / height) - j;
                        double rest = machines.get(machineIndex).getCylinders().get(i).getGirth() % height;
                        if (rest / pieces >= 2 && rest / pieces < min) {
                            //System.out.println("min: " + min);
                            min = rest / pieces;
                            this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(i);
                            this.verticalGap = min;
                            //System.out.println("Vertikalis heza valtozas: " + this.verticalGap);
                            //System.out.println("darab per henger: " + pieces);
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
            pieces = (int) (machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1).getGirth() / height);
            while (this.verticalGap < 2) {
                double rest = machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1).getGirth() - pieces*height;
                this.verticalGap = rest / pieces;
                pieces--;
            }
            this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1);
        }
    }*/

    private void searchMagnetCylinder(int machineIndex, double height, int magnetCylinderIndex){
        this.verticalGap=0;

        if(magnetCylinderIndex == 0){    // Auto: the program chooses the ideal applicable cylinder
            // to reach the lowest (but bigger or equal than 2) vertical gap
            int chosenMagnetCylinderIndex = 0;
            double absoluteMinVerticalGap = machines.get(machineIndex).getCylinders().get(machines.get(machineIndex).getCylinders().size()-1).getGirth();
            for(int i=0; i<machines.get(machineIndex).getCylinders().size(); i++) {
                MagnetCylinder cylinder = machines.get(machineIndex).getCylinders().get(i);
                int maxpieces = (int) (cylinder.getGirth() / height);
                int pieces = 1;
                double minVerticalGap = cylinder.getGirth();
                double rest = cylinder.getGirth() - pieces * height;
                while (pieces <= maxpieces && rest >= 2 * pieces) {
                    double verticalGap = rest / pieces;
                    if (verticalGap < minVerticalGap && verticalGap >= 2)
                        minVerticalGap = verticalGap;
                    pieces++;
                    rest = cylinder.getGirth() - pieces * height;
                }
                if(minVerticalGap != cylinder.getGirth() && minVerticalGap < absoluteMinVerticalGap) {
                    absoluteMinVerticalGap = minVerticalGap;
                    chosenMagnetCylinderIndex = i;
                }
            }
            if(absoluteMinVerticalGap == machines.get(machineIndex).getCylinders().get(machines.get(machineIndex).getCylinders().size()-1).getGirth()) {
                System.out.println("Nem fér bele csak a fele");
            }else{
                this.verticalGap = absoluteMinVerticalGap;
                this.chosenMagnetCylinder = machines.get(machineIndex).getCylinders().get(chosenMagnetCylinderIndex);
            }
        }else{ // we want to use a specific applicable cylinder
            this.chosenMagnetCylinder=machines.get(machineIndex).getCylinders().get(magnetCylinderIndex - 1);
            int maxpieces=(int)(chosenMagnetCylinder.getGirth() / height);
            int pieces=1;
            double minVerticalGap=chosenMagnetCylinder.getGirth();
            double rest=chosenMagnetCylinder.getGirth() - pieces*height;
            while(pieces <= maxpieces && rest >= 2*pieces){
                double verticalGap=rest/pieces;
                if(verticalGap < minVerticalGap && verticalGap >= 2)
                    minVerticalGap=verticalGap;
                pieces++;
                rest=chosenMagnetCylinder.getGirth() - pieces*height;
            }
            if(minVerticalGap == chosenMagnetCylinder.getGirth()){
                System.out.println("Nem fér bele csak a fele");
            }else{
                this.verticalGap=minVerticalGap;
            }
        }
    }

    public boolean addDye(int chosenDyeIndex, int chosenDyeCylinderIndex, int cover, String otherColorName, double otherColorPrice) {

        if (chosenDyeIndex < allDyeTypes.size()) {// Dye from the list
            if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Lakk.class) && lakkNum < MAX_NUMBER_OF_LAKKS) {
                DyeParent chosenLakk = allDyeTypes.get(chosenDyeIndex);
                if (!isDyeAlreadyAdded(chosenLakk.getName())) {
                    chosenLakk.setDyeCylinder(dyeCylinders.get(chosenDyeCylinderIndex));
                    chosenLakk.setCover(cover);
                    addedDyes.add(chosenLakk);
                    lakkNum++;
                    return true;
                }
                return false;
            } else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Metal.class) && metalNum < MAX_NUMBER_OF_METALS && metalNum + dyeNum < MAX_NUMBER_OF_DYES) {
                DyeParent chosenMetal = allDyeTypes.get(chosenDyeIndex);
                if (!isDyeAlreadyAdded(chosenMetal.getName())) {
                    chosenMetal.setDyeCylinder(dyeCylinders.get(chosenDyeCylinderIndex));
                    chosenMetal.setCover(cover);
                    addedDyes.add(chosenMetal);
                    metalNum++;
                    return true;
                }
                return false;
            } else if (allDyeTypes.get(chosenDyeIndex).getClass().equals(Dye.class) && metalNum + dyeNum < MAX_NUMBER_OF_DYES) {
                DyeParent chosenDye = allDyeTypes.get(chosenDyeIndex);
                if (!isDyeAlreadyAdded(chosenDye.getName())) {
                    chosenDye.setDyeCylinder(dyeCylinders.get(chosenDyeCylinderIndex));
                    chosenDye.setCover(cover);
                    addedDyes.add(chosenDye);
                    dyeNum++;
                    return true;
                }
                return false;
            }
        } else { // other color - direct color
            if (dyeNum + metalNum < MAX_NUMBER_OF_DYES) {
                Dye otherDye = new Dye(otherColorName, otherColorPrice, dyeCylinders.get(chosenDyeCylinderIndex), cover);
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

    public void resetAddedDyes (){
        addedDyes.removeAllElements();
        dyeNum = 0;
        lakkNum = 0;
        metalNum = 0;
        fluoNum = 0;
    }

    public boolean addDye(DyeParent dyeParent) {
        if (dyeParent.getClass().equals(Lakk.class) && lakkNum < MAX_NUMBER_OF_LAKKS) {
            if (!isDyeAlreadyAdded(dyeParent.getName())) {
                addedDyes.add(dyeParent);
                lakkNum++;
                return true;
            }
            return false;
        } else if (dyeParent.getClass().equals(Metal.class) && metalNum < MAX_NUMBER_OF_METALS && metalNum + dyeNum < MAX_NUMBER_OF_DYES) {
            if (!isDyeAlreadyAdded(dyeParent.getName())) {
                addedDyes.add(dyeParent);
                metalNum++;
                return true;
            }
            return false;
        } else if (dyeParent.getClass().equals(Dye.class) && metalNum + dyeNum < MAX_NUMBER_OF_DYES) {
            if (!isDyeAlreadyAdded(dyeParent.getName())) {
                addedDyes.add(dyeParent);
                dyeNum++;
                return true;
            }
            return false;
        } else if (dyeParent.getClass().equals(Fluo.class)) {
            if (!isDyeAlreadyAdded(dyeParent.getName())) {
                addedDyes.add(dyeParent);
                fluoNum++;
                return true;
            }
            return false;
        }
        return false;
    }

    public void saveDyePreset(DyePreset dyePreset){
        dyePresets.add(dyePreset);
    }

    public Vector<DyePreset> getDyePresets() {
        return dyePresets;
    }

    public void addDyePreset(int chosenDyePreset) {
        System.out.println(dyePresets.get(chosenDyePreset).getName());
        System.out.println(dyePresets.get(chosenDyePreset).getDyes().size());
        for (int i = 0; i < dyePresets.get(chosenDyePreset).getDyes().size(); i++){
            //addedDyes.add(dyePresets.get(chosenDyePreset).getDyes().get(i));
            addDye(dyePresets.get(chosenDyePreset).getDyes().get(i));
        }
    }

    public void removeDyePreset(int chosenDyePreset) {
        dyePresets.remove(chosenDyePreset);
    }

    public void removeAddedDye(int chosenDyeIndex) {
        System.out.println(addedDyes.size()+ " " + chosenDyeIndex);
        if (addedDyes.get(chosenDyeIndex).getClass().equals(Lakk.class)) {
            lakkNum--;
        } else if (addedDyes.get(chosenDyeIndex).getClass().equals(Metal.class)) {
            metalNum--;
        } else if (addedDyes.get(chosenDyeIndex).getClass().equals(Dye.class)) {
            dyeNum--;
        } else if (addedDyes.get(chosenDyeIndex).getClass().equals(Fluo.class)) {
            fluoNum--;
        }
        addedDyes.remove(chosenDyeIndex);
    }

    public void removeDye(int index) {
        allDyeTypes.remove(index);
    }

    public DyeCylinder searchDyeCylinder(double cylinderVolume) {
        for (int i = 0; i < dyeCylinders.size(); i++) {
            if (dyeCylinders.get(i).getVolume() == cylinderVolume) {
                return dyeCylinders.get(i);
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

    public void sortDyes(){
        Vector<DyeParent> newDyes=new Vector<DyeParent>();
        Vector<DyeParent> newLakks =new Vector<DyeParent>();
        Vector<DyeParent> newMetals=new Vector<DyeParent>();
        Vector<DyeParent> newFluos=new Vector<DyeParent>();

        for(int i=0;i<PPC.calcObj.getAllDyeTypes().size();i++){
            if(PPC.calcObj.getAllDyeTypes().get(i).getClass() == Dye.class){
                newDyes.addElement(PPC.calcObj.getAllDyeTypes().get(i));
            } else if(PPC.calcObj.getAllDyeTypes().get(i).getClass() == Lakk.class){
                newLakks.addElement(PPC.calcObj.getAllDyeTypes().get(i));
            }else if(PPC.calcObj.getAllDyeTypes().get(i).getClass() == Metal.class){
                newMetals.addElement(PPC.calcObj.getAllDyeTypes().get(i));
            }else if(PPC.calcObj.getAllDyeTypes().get(i).getClass() == Fluo.class){
                newFluos.addElement(PPC.calcObj.getAllDyeTypes().get(i));
            }
        }
        PPC.calcObj.getAllDyeTypes().removeAllElements();
        PPC.calcObj.getAllDyeTypes().addAll(newDyes);
        PPC.calcObj.getAllDyeTypes().addAll(newLakks);
        PPC.calcObj.getAllDyeTypes().addAll(newMetals);
        PPC.calcObj.getAllDyeTypes().addAll(newFluos);
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

        /*for (int i = 0; i < profitMatrix.size(); i++) {
            for (int j = 0; j < profitMatrix.get(i).size(); j++) {
                System.out.print(profitMatrix.get(i).get(j) + " ");
            }
            System.out.println();
        }*/
    }

    private double calcProfitOnPiece(int amount, double width, double height, int colorNum, double materialSelfCost, int discount) {
        double newAmount = ((width * height) * amount) / (etalonObj.getEtalonSizeX() * etalonObj.getEtalonSizeY());

        int newAmountIndex = 0;
        while (etalonObj.getEtalonMatrix().get(newAmountIndex).get(0) < newAmount && newAmountIndex < etalonObj.getEtalonMatrix().size() - 1) {
            newAmountIndex++;
        }
        if (newAmountIndex == 0) {
            newAmountIndex++;
        }

        double lowerAmount = etalonObj.getEtalonMatrix().get(newAmountIndex - 1).get(0);
        double higherAmount = etalonObj.getEtalonMatrix().get(newAmountIndex).get(0);
        double lowerAmountPrice = profitMatrix.get(newAmountIndex - 1).get(colorNum);
        double higherAmountPrice = profitMatrix.get(newAmountIndex).get(colorNum);

        return ((lowerAmountPrice * Math.pow((higherAmountPrice / lowerAmountPrice),((newAmount - lowerAmount) / (higherAmount - lowerAmount)))) +
                (materialSelfCost / amount)) * ((double) (100 - discount) / 100);
    }

    public void calculateMaterialCost(int materialIndex, int amount, double width, double height, int tracks, double sideGap, double betweenGap, double euro){
        double materialPrice = materials.get(materialIndex).getPrice() * euro;

        materialWidth = sideGap + (tracks * width) + ((tracks - 1) * betweenGap) + sideGap; // sidegap + labelwidth + betweengap + labelwidth + ... + labelwidth + betweengap + labelwidth + sidegap
        materialHeight = ((double) amount / tracks) * height + (amount / tracks) * verticalGap;

        materialSize = (materialHeight * materialWidth) / 1000000;
        materialSelfCost = materialSize * materialPrice;
    }

    public void calculateDyeCost(double width, double height){
        dyeSelfCost = 0;
        double actual = 0;

        if (addedDyes == null) {
            addedDyes = new Vector<DyeParent>();
        }

        for (int i = 0; i < addedDyes.size(); i++) {
            actual = addedDyes.get(i).getPrice() / 1000;
            dyeSelfCost = dyeSelfCost + actual * addedDyes.get(i).getDyeCylinder().getVolume() *
                    ((double) addedDyes.get(i).getDyeCylinder().getPercent() / 100) * ((width * height) / 1000000) *
                    ((double) addedDyes.get(i).getCover() / 100) * amount;

        }
    }

    public double calculatePackingCost(int amount, double packingCost, double packingTime, double rollWidth, int amountPerRoll) {
        double packingSelfCost = 0;
        if (amountPerRoll != 0 && rollWidth != 0) {
            //numberOfRolls = (double) amount / amountPerRoll;
            if (amount % amountPerRoll == 0) numberOfRolls = amount/amountPerRoll;
            else numberOfRolls = amount/amountPerRoll + 1;
            packingSelfCost = packingTime * packingCost + numberOfRolls * ROLL_PRICE * rollWidth;
        }
        return packingSelfCost;
    }

    public void printfSelfCosts(double clicheCost, double stancCost, double otherCost){
        System.out.println("Festék költség: " + dyeSelfCost);
        System.out.println("Alapanyagköltség: " + materialSelfCost);
        System.out.println("Dombornyomás ktg: " + domborSelfCost);
        System.out.println("Prégelés ktg: " + pregSelfCost);
        System.out.println("Klisé ktg: " + clicheCost);
        System.out.println("Stanc ktg: " + stancCost);
        System.out.println("Egyéb ktg: " + otherCost);
        System.out.println("Kiszerelés ktg: " + packingSelfCost);
        System.out.println("-- Önköltség --: " + sumPrice);
    }

    public void calculate(int materialIndex, int amount, double width, double height, int tracks, double sideGap, double betweenGap,
                          int machineIndex, int magnetCylinderIndex, int pregCover, double domborPrice, double clicheCost,
                          double stancCost, double packingCost, double packingTime, double rollWidth, int amountPerRoll,
                          String title, String client, int discount, double euro, double otherCost) {

        this.material = materials.get(materialIndex);
        this.amount = amount;
        this.width = width;
        this.height = height;
        this.stancCost = stancCost;
        this.clicheCost = clicheCost;
        this.otherCost = otherCost;
        this.title = title;

        //Choosing magnet cylinder
        searchMagnetCylinder(machineIndex, height, magnetCylinderIndex);
        System.out.println("Mágneshenger: " + chosenMagnetCylinder.getTeeth() + " fogas, kerület: " + chosenMagnetCylinder.getGirth());
        System.out.println("Vertikális hézag: " + verticalGap);

        //Material cost
        calculateMaterialCost(materialIndex, amount, width, height, tracks, sideGap, betweenGap, euro);

        //Dye cost
        calculateDyeCost(width, height);

        //Selfcost of embossing (dombornyomas)
        domborSelfCost = (int) (amount / 1000) * domborPrice * 0.2 + domborPrice;

        //Self cost of pregeles
        pregSelfCost = ((width * height) / 1000000) * ((double) pregCover / 100) * amount * PREG_PRICE;

        //Packing
        packingSelfCost = calculatePackingCost(amount, packingCost, packingTime, rollWidth, amountPerRoll);

        //SUM self cost
        sumPrice = dyeSelfCost + materialSelfCost + domborSelfCost + pregSelfCost + clicheCost + stancCost +
                packingSelfCost + otherCost;
        printfSelfCosts(clicheCost, stancCost, otherCost);

        //Profit - margin
        colorNum = countColors();
        transformEtalon(width, height);
        profitOnPiece = calcProfitOnPiece(amount, width, height, colorNum, materialSelfCost, discount);
        if (pregCover != 0){
            profitOnPiece = profitOnPiece * 2.0 * ((double)(100 + pregCover) / 100);
        }
        System.out.println("Darabár: " + profitOnPiece);
        System.out.println("-------------------------------------");

        /*if (pregCheck == 0){
            arresDbPrice = calcArresDbPrice(amount, colorNum);
        }else{
            arresDbPrice = calcArresDbPrice(amount, colorNum);
            System.out.println(this.pregCover);
            arresDbPrice = arresDbPrice * 2.0 * ((double)(100 + pregCover) / 100);
        }*/

        //debugDyePresets();

    }

    public void exportToPdf(String fileName){
        pdfExporterObj = new PdfExporter(fileName, title, material, width, height, colorNum, lakkNum,
                clicheCost, stancCost, amount, profitOnPiece);
    }

    public void addMaterial(Material newMaterial){
        materials.add(newMaterial);
    }

    public void removeMaterial(int materialIndex){
        materials.remove(materialIndex);
    }

    public Vector<Material> getMaterials() {
        return materials;
    }

    public static ArrayList<Machine> getMachines() {
        return machines;
    }

    public static ArrayList<Metal> getMetals() {
        return metals;
    }

    public static ArrayList<MagnetCylinder> getMagnetCylinders() {
        return magnetCylinders;
    }

    public static ArrayList<Lakk> getLakks() {
        return lakks;
    }

    public static Vector<DyeCylinder> getDyeCylinders() {
        return dyeCylinders;
    }

    public static ArrayList<Dye> getDyes() {
        return dyes;
    }

    public Vector<DyeParent> getAllDyeTypes() {
        return allDyeTypes;
    }

    public Vector<DyeParent> getAddedDyes() {
        return addedDyes;
    }

    public static Etalon getEtalonObj() {
        return etalonObj;
    }

    public static void setEtalonObj(Etalon etalonObj) {
        Calculator.etalonObj = etalonObj;
    }

    // printing in GUI after calculation

    // Mágneshenger (fogas)
    public MagnetCylinder getChosenMagnetCylinder() {
        return chosenMagnetCylinder;
    }

    // Vertikális hézag (mm)
    public double getVerticalGap() {
        return verticalGap;
    }

    // Pályaszélesség (mm)
    public double getMaterialWidth() { return materialWidth; }

    // Alapanyaghossz (m)
    public double getMaterialHeight() { return materialHeight/1000; }

    // Alapanyagmennyiség (m2)
    public double getMaterialSize() { return materialSize; }

    // Alapanyagköltség (Ft)
    public double getMaterialSelfCost() { return materialSelfCost; }

    // Alapanyagköltség - darabár (Ft)
    public double getMaterialSelfCostOnPiece() { return materialSelfCost / amount; }

    // Festékköltség (Ft)
    public double getDyeSelfCost(){ return dyeSelfCost; }

    // Festékköltség (Ft) - darabár
    public double getDyeSelfCostOnPiece(){ return dyeSelfCost / amount; }

    // Prégelés önköltsége (Ft)
    public double getPregSelfCost(){ return pregSelfCost; }

    // Prégelés önköltsége (Ft) - darabár
    public double getPregSelfCostOnPiece(){ return pregSelfCost / amount; }

    // Dombornyomás önköltsége (Ft)
    public double getDomborSelfCost(){ return domborSelfCost; }

    // Dombornyomás önköltsége (Ft) - darabár
    public double getDomborSelfCostOnPiece(){ return domborSelfCost / amount; }

    // Kiszerelés költsége (Ft)
    public double getPackingPrice(){ return packingSelfCost; }

    // Tekercsek száma (db)
    public double getNumberOfRolls(){ return numberOfRolls; }

    // Darabonkénti önköltség (Ft)
    public double getSumPriceOnPiece(){ return sumPrice/amount; }

    // Összesített önköltség (Ft)
    public double getSumPrice(){ return sumPrice; }

    // Eladási darabár (Ft)
    public double getProfitOnPiece(){ return profitOnPiece + (packingSelfCost + otherCost ) / amount; }

    // Eladási összár (Ft)
    public double getSumProfit(){ return profitOnPiece * amount + packingSelfCost + otherCost; }

}
