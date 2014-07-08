/**
 * Created by Talicska on 2014.07.08..
 */

import java.awt.*;
import javax.swing.*;

public class Gui extends JFrame {       // ...ne baszd ossze a kodot!

    private Dimension dimension;
    private int width=800;
    private int height=572;
    private int menuheight=22;

    public Gui() {

        this.getContentPane().setLayout(null);
        dimension= new Dimension(width,height);
        this.setPreferredSize(dimension);

        JMenu fileMenu = new JMenu ("Fájl");        //menu
        JMenuItem newItem = new JMenuItem ("Új");
        fileMenu.add (newItem);
        JMenuItem saveItem = new JMenuItem ("Mentés");
        fileMenu.add (saveItem);
        JMenuItem loadItem = new JMenuItem ("Betöltés");
        fileMenu.add (loadItem);
        JMenuItem exitItem = new JMenuItem ("Kilépés");
        fileMenu.add (exitItem);
        JMenu aboutMenu = new JMenu ("Névjegy");
        JMenuItem aboutItem = new JMenuItem ("A PPC névjegye");
        aboutMenu.add (aboutItem);
        JMenuBar menu = new JMenuBar();
        menu.setBounds(0,0,width,menuheight);
        menu.add(fileMenu);
        menu.add(aboutMenu);

        /*JPanel panel1 = new JPanel();               //left panel
        panel1.setBounds(0, 25, 600, height-menuheight);
        panel1.setBackground(Color.red);

        JPanel panel2 = new JPanel();               //right panel
        panel2.setBounds(600, 25, 200, height-menuheight);
        panel2.setBackground(Color.yellow);*/

        JButton b1 = new JButton("b1");             //buttons
        JButton b2 = new JButton("b2");

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);      //tabs
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        JComponent tab3 = new JPanel();
        tabbedPane.addTab("<html><body><table width='90'>Alapanyagok</table></body></html>", tab1);
        tabbedPane.addTab("<html><body><table width='90'>Egyebek</table></body></html>", tab2);
        tabbedPane.addTab("<html><body><table width='90'>Etalon</table></body></html>", tab3);
        tabbedPane.setBounds(0, menuheight, 600, height-menuheight);

        tab1.add(b1);

        this.add(tabbedPane);
        //this.add(panel1);         //not needed anymore, left in just in case
        //this.add(panel2);
        this.add(menu);

    }

}

