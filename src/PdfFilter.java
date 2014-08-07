/**
 * Created by Prof on 2014.08.07..
 */
import java.io.File;
import javax.swing.filechooser.*;

public class PdfFilter extends FileFilter {

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.pdf)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public String getDescription() {
        return ".pdf";
    }
}
