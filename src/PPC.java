import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Prof on 2014.07.06..
 */
public class PPC {

    public static void main(String[] args) {

        System.out.println("Hello");

        PPCDB database = new PPCDB();

        ArrayList<Material> materials;
        materials = new ArrayList<Material>();
        try {
            materials = database.getMaterials();
        }catch (SQLException e){
            e.printStackTrace();
        }

        //
        int i=materials.size();
        while(i-->0)
            System.out.println(materials.get(i).getName() +" "+ materials.get(i).getPrice());

    }


}
