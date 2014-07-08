/**
 * Created by Talicska on 2014.07.08..
 */

import java.awt.*;
import javax.swing.*;

public class Gui extends JFrame {       // ...ne baszd ossze a kodot!

    private Dimension dimension;

    public Gui() {

        this.getContentPane().setLayout(null);
        dimension= new Dimension(800,600);

        JMenu fileMenu = new JMenu ("Fájl");
        JMenuItem newItem = new JMenuItem ("Új");
        fileMenu.add (newItem);
        JMenuItem saveItem = new JMenuItem ("Mentés");
        fileMenu.add (saveItem);
        JMenuItem loadItem = new JMenuItem ("Betöltés");
        fileMenu.add (loadItem);
        JMenuItem exitItem = new JMenuItem ("Kilépés");
        fileMenu.add (exitItem);
        JMenu aboutMenu = new JMenu ("About");
        JMenuItem aboutItem = new JMenuItem ("A PPC névjegye");
        aboutMenu.add (aboutItem);
        JMenuBar menu = new JMenuBar();
        menu.add(fileMenu);
        menu.add(aboutMenu);

        JPanel panel1 = new JPanel();   //left panel
        panel1.setBounds(0, 0, 600, 600);
        panel1.setBackground(Color.red);

        JPanel panel2 = new JPanel();   //right panel
        panel2.setBounds(600, 0, 200, 600);
        panel2.setBackground(Color.yellow);

        JButton b1 = new JButton("b1");
        JButton b2 = new JButton("b2");

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        JComponent tab3 = new JPanel();
        tabbedPane.addTab("Alapanyagok", tab1);
        tabbedPane.addTab("Tab2", tab2);
        tabbedPane.addTab("Tab2", tab3);
        tabbedPane.setSize(600, 600);

        tab1.add(b1);
        panel2.add(b2);

        //this.add(tabbedPane);
        //this.add(panel1);
        //this.add(panel2);
        this.add(menu);

    }

}

