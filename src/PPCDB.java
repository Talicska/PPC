/**
 * Created by Prof on 2014.07.06..
 */

import java.sql.*;
import java.util.ArrayList;

public class PPCDB {


    private static Connection conn;


    public static ArrayList<Material> getMaterials() throws SQLException{
        PreparedStatement ps;
        ArrayList<Material> materials;
        materials = new ArrayList<Material>();
        try{
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException cnfe){
            System.out.println("Could not find the JDBC driver!");
            System.exit(1);
        }

        conn = DriverManager.getConnection("jdbc:sqlite:D:/IntelliJP/PPC/PPCDB");

        if(conn==null)
            System.out.println("conn 0");
        //ps = conn.prepareCall("select name_mat, price_mat from Material");

        Statement stm= conn.createStatement();

        ResultSet rs=stm.executeQuery("select name_mat, price_mat from Material");


        //ps.execute();
        //System.out.println("asd4");
        //ResultSet rs = ps.getResultSet();

        while(rs.next()){
            materials.add(new Material(rs.getString("name_mat"), rs.getDouble("price_mat")));

        }

        return materials;
    }

}
