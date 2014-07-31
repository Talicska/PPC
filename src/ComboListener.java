/**
 * Created by Talicska on 2014.07.31..
 */

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;

public class ComboListener implements PopupMenuListener {

    private Component c;

    public ComboListener(Component c){
        this.c = c;
    }
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        c.revalidate();
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }
}
