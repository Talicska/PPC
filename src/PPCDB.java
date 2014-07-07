/**
 * Created by Prof on 2014.07.06..
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class PPCDB {

    public static final String DATABASE = "jdbc:sqlite:D:/IntelliJP/PPC/PPCDB";     //your own
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
            dyes.add(new Dye(rs.getString("name_dye"), rs.getDouble("price_dye"), 0, 0));     //parameters given by user
        }

        return dyes;
    }

    public static ArrayList<DyeCylinder> getDyeCylinders() throws SQLException {

        ArrayList<DyeCylinder> dyecyls = new ArrayList<DyeCylinder>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select volume, percent from DyeCylinder");

        while (rs.next()) {
            dyecyls.add(new DyeCylinder(rs.getInt("volume"), rs.getInt("percent")));
        }

        return dyecyls;
    }

    public static Vector<Vector<Double>> getEtalon() throws SQLException {

    }

    public static ArrayList<Lakk> getLakks() throws SQLException {

        ArrayList<Lakk> lakks = new ArrayList<Lakk>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_lakk, price_lakk from Lakk");

        while (rs.next()) {
            lakks.add(new Lakk(rs.getString("name_lakk"), rs.getDouble("price_lakk"), 0, 0));
        }

        return lakks;
    }

    public static ArrayList<Machine> getMachine() throws SQLException {

        ArrayList<Machine> machines = new ArrayList<Machine>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_machine from Machine");

        while (rs.next()) {
            machines.add(new Machine(rs.getString("name_machine"), null));
        }

        return machines;
    }

    //machine magnet cyl ............

    public static ArrayList<MagnetCylinder> getMagnetCylinders() throws SQLException {

        ArrayList<MagnetCylinder> magcyls = new ArrayList<MagnetCylinder>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select teeth, girth from MagnetCylinder");

        while (rs.next()) {
            magcyls.add(new MagnetCylinder(rs.getInt("teeth"), rs.getDouble("girth")));
        }

        return magcyls;
    }

    public static ArrayList<Material> getMaterials() throws SQLException {

        ArrayList<Material> materials = new ArrayList<Material>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_mat, price_mat from Material");

        while (rs.next()) {
            materials.add(new Material(rs.getString("name_mat"), rs.getDouble("price_mat")));
        }

        return materials;
    }

    public static ArrayList<Metal> getMetals() throws SQLException {

        ArrayList<Metal> metals = new ArrayList<Metal>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_metal, price_metal from Metal");

        while (rs.next()) {
            metals.add(new Metal(rs.getString("name_metal"), rs.getDouble("price_metal"), 0, 0));
        }

        return metals;
    }


}
