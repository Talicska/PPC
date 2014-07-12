/**
 * Created by Prof on 2014.07.06..
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class PPCDB {
    // "jdbc:sqlite:D:/IntelliJP/PPC/PPCDB"
    // "jdbc:sqlite:D:/Users/Prof/IdeaProjects/PPC/PPCDB"

    public static final String DATABASE = "jdbc:sqlite:D:/Users/Prof/IdeaProjects/PPC/PPCDB";     //your own
    private static Connection conn = null;

    public void open() {
        try {
            conn = DriverManager.getConnection(DATABASE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public PPCDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Could not find the JDBC driver!");
            System.exit(1);
        }
        open();
    }

    public static ArrayList<Dye> getDyes() throws SQLException {

        ArrayList<Dye> dyes = new ArrayList<Dye>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_dye, price_dye from Dye");

        while (rs.next()) {
            dyes.add(new Dye(rs.getString("name_dye"), rs.getDouble("price_dye"), null, 0));     //parameters given by user
        }

        stm.close();
        rs.close();
        return dyes;
    }

    public static ArrayList<DyeCylinder> getDyeCylinders() throws SQLException {

        ArrayList<DyeCylinder> dyeCylinders = new ArrayList<DyeCylinder>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select volume, percent from DyeCylinder");

        while (rs.next()) {
            dyeCylinders.add(new DyeCylinder(rs.getDouble("volume"), rs.getInt("percent")));
        }

        stm.close();
        rs.close();
        return dyeCylinders;
    }

    public static Vector<Vector<Double>> getEtalon() throws SQLException {

        Vector<Vector<Double>> etalonMatrix = new Vector<Vector<Double>>();

        Vector<Double> line = new Vector<Double>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select amount, dye0_price, dye1_price, dye2_price, " +
                "dye3_price, dye4_price, dye5_price, dye6_price, dye7_price from Etalon");

        while (rs.next()) {
            for (int i = 0; i < 9; i++)
                line.add(rs.getDouble(i + 1));
            etalonMatrix.add((Vector) line.clone());
            line.clear();
        }

        stm.close();
        rs.close();
        return etalonMatrix;
    }

    public static ArrayList<Lakk> getLakks() throws SQLException {

        ArrayList<Lakk> lakks = new ArrayList<Lakk>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_lakk, price_lakk from Lakk");

        while (rs.next()) {
            lakks.add(new Lakk(rs.getString("name_lakk"), rs.getDouble("price_lakk"), null, 0));
        }

        stm.close();
        rs.close();
        return lakks;
    }

    public static ArrayList<Machine> getMachines() throws SQLException {

        ArrayList<Machine> machines = new ArrayList<Machine>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_machine from Machine");

        while (rs.next()) {
            machines.add(new Machine(rs.getString("name_machine")));
        }

        stm.close();
        rs.close();
        return machines;
    }

    public static ArrayList<MagnetCylinder> getMagnetCylinders() throws SQLException {

        ArrayList<MagnetCylinder> magcyls = new ArrayList<MagnetCylinder>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select teeth, girth from MagnetCylinder");

        while (rs.next()) {
            magcyls.add(new MagnetCylinder(rs.getInt("teeth"), rs.getDouble("girth")));
        }

        stm.close();
        rs.close();
        return magcyls;
    }

    public static ArrayList<Material> getMaterials() throws SQLException {

        ArrayList<Material> materials = new ArrayList<Material>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_mat, price_mat from Material");

        while (rs.next()) {
            materials.add(new Material(rs.getString("name_mat"), rs.getDouble("price_mat")));
        }

        stm.close();
        rs.close();
        return materials;
    }

    public static ArrayList<Metal> getMetals() throws SQLException {

        ArrayList<Metal> metals = new ArrayList<Metal>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_metal, price_metal from Metal");

        while (rs.next()) {
            metals.add(new Metal(rs.getString("name_metal"), rs.getDouble("price_metal"), null, 0));
        }

        stm.close();
        rs.close();
        return metals;
    }

    public static ArrayList<Machine> fillMachineMagCylinders(ArrayList<Machine> machines, ArrayList<MagnetCylinder> magnetCylinders) throws SQLException {

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select m.name_machine, mc.teeth from Machine m " +
                "INNER JOIN Machine_MagnetCylinder mmc ON m.id_machine = mmc.id_machine " +
                "INNER JOIN MagnetCylinder mc ON mmc.id_magnet_cylinder = mc.id_magnet_cylinder ORDER BY mc.teeth");

        while (rs.next()) {
            for (int i = 0; i < machines.size(); i++) {
                if (machines.get(i).getName().equals(rs.getString("name_machine"))) {
                    for (int j = 0; j < magnetCylinders.size(); j++) {
                        if (magnetCylinders.get(j).getTeeth() == rs.getInt("teeth")) {
                            machines.get(i).addCylinder(magnetCylinders.get(j));
                            break;
                        }
                    }
                    break;
                }
            }
        }

        stm.close();
        rs.close();
        return machines;
    }

}
