/**
 * Created by Talicska on 2014.07.07..
 */

import java.util.Vector;

public class Etalon {
    public final double etalonSelfCost = 1.5;       //subject to change; put in database ??
    public final double etalonSizeX = 100;
    public final double etalonSizeY = 150;
    public final double etalonMaterialPrice = 0.305;
    private Vector<Vector<Double>> etalonMatrix = new Vector<Vector<Double>>();

    public double getEtalonSelfCost() {
        return etalonSelfCost;
    }

    public double getEtalonSizeX() {
        return etalonSizeX;
    }

    public double getEtalonSizeY() {
        return etalonSizeY;
    }

    public double getEtalonMaterialPrice() {
        return etalonMaterialPrice;
    }

    public Vector<Vector<Double>> getEtalonMatrix() {
        return etalonMatrix;
    }

    public Etalon(Vector<Vector<Double>> etalonMatrix) {
        this.etalonMatrix = etalonMatrix;
    }

    public void setElement(Double value, int row,int column){
        etalonMatrix.get(row).set(column, value);
    }
}
