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
        ps = conn.prepareCall("select name_mat, price_mat from Material");
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while(rs.next()){
            materials.add(new Material(rs.getString(1), rs.getDouble(1)));
        }

        return materials;
    }

}
