/**
 * Created by Prof on 2014.07.06..
 */

import java.sql.*;
import java.util.ArrayList;

public class PPCDB {


    private static Connection conn;

    public PPCDB() {
    }

    public static ArrayList<Material> getMaterials() throws SQLException{

        ArrayList<Material> materials = new ArrayList<Material>();

        try{
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException cnfe){
            System.out.println("Could not find the JDBC driver!");
            System.exit(1);
        }

        conn = DriverManager.getConnection("jdbc:sqlite:D:/IntelliJP/PPC/PPCDB");   //your own

        if(conn==null)
            System.out.println("Error: Can't connect to the database!");

        Statement stm = conn.createStatement();
        ResultSet rs=stm.executeQuery("select name_mat, price_mat from Material");

        while(rs.next()){
            materials.add(new Material(rs.getString("name_mat"), rs.getDouble("price_mat")));
        }

        return materials;
    }
}
