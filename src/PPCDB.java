/**
 * Created by Prof on 2014.07.06..
 */

import java.sql.*;
import java.util.ArrayList;

public class PPCDB {

    public static final String DATABASE = "jdbc:sqlite:D:/IntelliJP/PPC/PPCDB";     //your own
    private static Connection conn=null;

    public void open(){
        try {
            conn = DriverManager.getConnection(DATABASE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void close(){
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public PPCDB() {
        try{
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException cnfe){
            System.out.println("Could not find the JDBC driver!");
            System.exit(1);
        }
        open();
    }

    public static ArrayList<Material> getMaterials() throws SQLException{

        ArrayList<Material> materials = new ArrayList<Material>();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select name_mat, price_mat from Material");

        while(rs.next()){
            materials.add(new Material(rs.getString("name_mat"), rs.getDouble("price_mat")));
        }

        return materials;
    }
}
