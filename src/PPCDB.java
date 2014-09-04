/**
 * Created by Prof on 2014.07.06..
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class PPCDB {
    // "jdbc:sqlite:D:/IntelliJP/PPC/PPCDB"
    // "jdbc:sqlite:D:/Users/Prof/IdeaProjects/PPC/PPCDB"

    public static final String DATABASE = "jdbc:sqlite:PPCDB";     //your own
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

    public static ArrayList<DyeParent> getDyeParents() throws SQLException {

        ArrayList<DyeParent> dyeParents = new ArrayList<DyeParent>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select d.name_dyeparent, d.price_dyeparent, dt.name_dyetype from DyeParent d " +
                "INNER JOIN DyeType dt ON d.id_dyetype = dt.id_dyetype");

        while (rs.next()) {
            if (rs.getString("name_dyetype").equals("Dye")){
                dyeParents.add(new Dye(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"), null, 0));     //parameters given by user
            }else if (rs.getString("name_dyetype").equals("Lakk")){
                dyeParents.add(new Lakk(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"), null, 0));
            }else if (rs.getString("name_dyetype").equals("Metal")){
                dyeParents.add(new Metal(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"), null, 0));
            }else if (rs.getString("name_dyetype").equals("Fluo")){
                dyeParents.add(new Fluo(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"), null, 0));
            }

        }

        stm.close();
        rs.close();
        return dyeParents;
    }

    public static void addDyeParent(DyeParent dyeParent) throws SQLException {

        Statement stm = conn.createStatement();

        int dyeTypeId = getDyeTypeIdFromTypeName(dyeParent.getClass().getTypeName());
        stm.execute("INSERT INTO DyeParent (name_dyeparent, price_dyeparent, id_dyetype) values ('" + dyeParent.getName() + "','" + dyeParent.getPrice() + "','" + dyeTypeId + "' )");

        stm.close();
    }

    public static int getDyeTypeIdFromTypeName(String dyeType) throws SQLException {

        System.out.println(dyeType);

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select id_dyetype from DyeType where name_dyetype = '" + dyeType + "'");

        int dyeTypeId = rs.getInt("id_dyetype");

        stm.close();
        rs.close();
        return dyeTypeId;
    }

    public static void clearDyeParents() throws SQLException {

        Statement stm = conn.createStatement();
        //stm.execute("Truncate table DyeParent;");

        stm.execute("DELETE FROM DyeParent;");
        stm.execute("DELETE FROM sqlite_sequence WHERE name = 'DyeParent';");

        //DELETE * FROM table_name;
        //ALTER TABLE mytable AUTO_INCREMENT = 1

        stm.close();
    }

    //refill the table with the modified dyeparent values - for update and delete also
    public static void refreshDyeParents(Vector<DyeParent> dyeParents) {
        try {
            clearDyeParents();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            for (int i = 0; i < dyeParents.size(); i++) {
                addDyeParent(dyeParents.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Vector<DyePreset> getDyePresets() throws SQLException {

        Vector<DyePreset> dyePresets = new Vector<DyePreset>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select dpr.name_dyepreset, dpr.cover, dpa.name_dyeparent, dpa.price_dyeparent, " +
                "dc.volume, dc.percent, dt.name_dyetype from DyePreset dpr " +
                "INNER JOIN DyeParent dpa ON dpr.id_dyeparent = dpa.id_dyeparent " +
                "INNER JOIN DyeCylinder dc ON dpr.id_dyecylinder = dc.id_dye_cylinder " +
                "INNER JOIN DyeType dt ON dpa.id_dyetype = dt.id_dyetype");

        while (rs.next()) {
            int i;
            for (i = 0; i < dyePresets.size(); i++) {
                if (dyePresets.get(i).getName().equals(rs.getString("name_dyepreset"))) {

                    DyeParent tmpDyeParent = new DyeParent("", 0, null, 0);
                    if (rs.getString("name_dyetype").equals("Dye")){
                        dyePresets.get(i).addDyeParent(new Dye(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"),
                                new DyeCylinder(rs.getDouble("volume"), rs.getInt("percent")), rs.getInt("cover")));
                    }else if (rs.getString("name_dyetype").equals("Lakk")){
                        dyePresets.get(i).addDyeParent(new Lakk(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"),
                                new DyeCylinder(rs.getDouble("volume"), rs.getInt("percent")), rs.getInt("cover")));
                    }else if (rs.getString("name_dyetype").equals("Metal")){
                        dyePresets.get(i).addDyeParent(new Metal(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"),
                                new DyeCylinder(rs.getDouble("volume"), rs.getInt("percent")), rs.getInt("cover")));
                    }
                    break;
                }
            }
            if (i == dyePresets.size()){
                Vector<DyeParent> dyes = new Vector<DyeParent>();

                if (rs.getString("name_dyetype").equals("Dye")){
                    Dye newDye = new Dye(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"),
                            new DyeCylinder(rs.getDouble("volume"), rs.getInt("percent")), rs.getInt("cover"));
                    dyes.add(newDye);
                }else if (rs.getString("name_dyetype").equals("Lakk")){
                    Lakk newLakk = new Lakk(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"),
                            new DyeCylinder(rs.getDouble("volume"), rs.getInt("percent")), rs.getInt("cover"));
                    dyes.add(newLakk);
                }else if (rs.getString("name_dyetype").equals("Metal")){
                    Metal newMetal = new Metal(rs.getString("name_dyeparent"), rs.getDouble("price_dyeparent"),
                            new DyeCylinder(rs.getDouble("volume"), rs.getInt("percent")), rs.getInt("cover"));
                    dyes.add(newMetal);
                }

                dyePresets.add(new DyePreset(rs.getString("name_dyepreset"), dyes));
            }
        }

        stm.close();
        rs.close();
        return dyePresets;
    }

    public static void addDyePreset(DyePreset dyePreset) throws SQLException {

        Statement stm = conn.createStatement();

        for (int i = 0; i < dyePreset.getDyes().size(); i++){
            int dyeParentId = getDyeParentIdFromName(dyePreset.getDyes().get(i).getName());
            int dyeCylinderId = getDyeCylinderIdFromVolume(dyePreset.getDyes().get(i).getDyeCylinder().getVolume());
            int cover = dyePreset.getDyes().get(i).getCover();
            stm.execute("INSERT INTO DyePreset (name_dyepreset, id_dyeparent, id_dyecylinder, cover) " +
                    "values ('" + dyePreset.getName() + "','" + dyeParentId + "','" + dyeCylinderId + "','" + cover + "' )");
        }

        stm.close();
    }

    public static int getDyeParentIdFromName(String dyeParentName) throws SQLException {

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select id_dyeparent from DyeParent where name_dyeparent = '" + dyeParentName + "'");

        int dyeParentId = rs.getInt("id_dyeparent");

        stm.close();
        rs.close();
        return dyeParentId;
    }

    public static int getDyeCylinderIdFromVolume(double volume) throws SQLException {

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select id_dye_cylinder from DyeCylinder where volume = '" + volume + "'");

        int dyeCylinderId = rs.getInt("id_dye_cylinder");

        stm.close();
        rs.close();
        return dyeCylinderId;
    }

    public static void clearDyePresets() throws SQLException {

        Statement stm = conn.createStatement();
        //stm.execute("Truncate table DyePreset;");

        stm.execute("DELETE FROM DyePreset;");
        stm.execute("DELETE FROM sqlite_sequence WHERE name = 'DyePreset';");

        //DELETE * FROM table_name;
        //ALTER TABLE mytable AUTO_INCREMENT = 1

        stm.close();
    }

    //refill the table with the modified dyepreset values - for update and delete also
    public static void refreshDyePresets(Vector<DyePreset> dyePresets){
        try {
            clearDyePresets();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            for (int i = 0; i < dyePresets.size(); i++) {
                addDyePreset(dyePresets.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Vector<DyeCylinder> getDyeCylinders() throws SQLException {

        Vector<DyeCylinder> dyeCylinders = new Vector<DyeCylinder>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select volume, percent from DyeCylinder");

        while (rs.next()) {
            dyeCylinders.add(new DyeCylinder(rs.getDouble("volume"), rs.getInt("percent")));
        }

        stm.close();
        rs.close();
        return dyeCylinders;
    }

    public static void clearEtalon() throws SQLException {

        Statement stm = conn.createStatement();
        //stm.execute("Truncate table DyeParent;");

        stm.execute("DELETE FROM Etalon;");
        stm.execute("DELETE FROM sqlite_sequence WHERE name = 'Etalon';");

        //DELETE * FROM table_name;
        //ALTER TABLE mytable AUTO_INCREMENT = 1

        stm.close();
    }

    public static void addEtalonRow(Vector<Double> etalonRow) throws SQLException {

        Statement stm = conn.createStatement();

        stm.execute("INSERT INTO Etalon (amount, dye0_price, dye1_price, dye2_price, dye3_price, dye4_price, dye5_price," +
                " dye6_price, dye7_price) values ('" + etalonRow.get(0) + "','" + etalonRow.get(1) + "','" +
                etalonRow.get(2) + "','" + etalonRow.get(3) + "','" + etalonRow.get(4) + "','" +
                etalonRow.get(5) + "','" + etalonRow.get(6) + "','" + etalonRow.get(7) + "','"
                + etalonRow.get(8) + "' )");

        stm.close();
    }

    public static void refreshEtalon(Etalon etalonObj) {
        try {
            clearEtalon();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            for (int i = 0; i < etalonObj.getEtalonMatrix().size(); i++) {
                addEtalonRow(etalonObj.getEtalonMatrix().get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Vector<Vector<Double>> getEtalon() throws SQLException {

        Vector<Vector<Double>> etalonMatrix = new Vector<Vector<Double>>();

        Vector<Double> line = new Vector<Double>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select amount, dye0_price, dye1_price, dye2_price, " +
                "dye3_price, dye4_price, dye5_price, dye6_price, dye7_price from Etalon");

        /*while (rs.next()) {
            for (int i = 0; i < 9; i++) {
                line.add(rs.getDouble(i + 1));
            }
            etalonMatrix.add((Vector) line.clone());
            line.clear();
        }*/

        while (rs.next()) {
            for (int i = 0; i < 9; i++) {
                line.add(rs.getDouble(i + 1));
            }
            Vector<Double> tmp = new Vector<Double>(line);
            etalonMatrix.add(tmp);
            line.clear();
        }

        stm.close();
        rs.close();
        return etalonMatrix;
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

    public static void addMagnetCylinder (int teeth, double girth) throws SQLException {

        Statement stm = conn.createStatement();
        stm.execute("INSERT INTO MagnetCylinder (teeth, girth) values ('" + teeth + "','" + girth +"' )");

        stm.close();
    }

    public static Vector<Material> getMaterials() throws SQLException {

        Vector<Material> materials = new Vector<Material>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_mat, price_mat from Material");

        while (rs.next()) {
            materials.add(new Material(rs.getString("name_mat"), rs.getDouble("price_mat")));
        }

        stm.close();
        rs.close();
        return materials;
    }

    public static void addMaterial (String materialName, double materialPrice) throws SQLException {

        Statement stm = conn.createStatement();
        stm.execute("INSERT INTO Material (name_mat, price_mat) values ('" + materialName + "','" + materialPrice + "' )");

        stm.close();
    }

    public static void clearMaterials() throws SQLException {

        Statement stm = conn.createStatement();
        //stm.execute("TRUNCATE Material");
        stm.execute("DELETE FROM Material;");
        stm.execute("DELETE FROM sqlite_sequence WHERE name = 'Material';");
        //DELETE * FROM table_name;
        //ALTER TABLE mytable AUTO_INCREMENT = 1

        stm.close();
    }

    //refill the table with the modified material values - for update and delete also
    public static void refreshMaterials(Vector<Material> materials) {
        try {
            clearMaterials();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        for (int i = 0; i < materials.size(); i++){

                addMaterial(materials.get(i).getName(), materials.get(i).getPrice());

        }} catch (SQLException e) {
        e.printStackTrace();
    }

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
