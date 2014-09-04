/**
 * Created by Prof on 2014.08.07..
 */

import java.io.File;

public class Utils {

    public final static String ppc="ppc";
    public final static String pdf="pdf";

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
