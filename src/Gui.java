/**
 * Created by Talicska on 2014.07.08..
 */

import java.awt.*;
import java.util.Vector;
import javax.swing.*;

public class Gui extends JFrame {       // ...ne baszd ossze a kodot!

    private Dimension dimension;
    private int width = 800;
    private int height = 572;
    private int menuheight = 22;

    public Gui() {

        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);
        this.setPreferredSize(dimension);

        JMenu fileMenu = new JMenu("Fájl");        //menu
        JMenuItem newItem = new JMenuItem("Új");
        fileMenu.add(newItem);
        JMenuItem saveItem = new JMenuItem("Mentés");
        fileMenu.add(saveItem);
        JMenuItem loadItem = new JMenuItem("Betöltés");
        fileMenu.add(loadItem);
        JMenuItem exitItem = new JMenuItem("Kilépés");
        fileMenu.add(exitItem);
        JMenu aboutMenu = new JMenu("Névjegy");
        JMenuItem aboutItem = new JMenuItem("A PPC névjegye");
        aboutMenu.add(aboutItem);
        JMenuBar menu = new JMenuBar();
        menu.setBounds(0, 0, width, menuheight);
        menu.add(fileMenu);
        menu.add(aboutMenu);

        /*JPanel panel1 = new JPanel();               //left panel
        panel1.setBounds(0, 25, 600, height-menuheight);
        panel1.setBackground(Color.red);*/

        JPanel panel2 = new JPanel();               //right panel
        panel2.setBounds(600, 25, 200, height - menuheight);
        panel2.setBackground(Color.yellow);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, 0);      //tabs
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        JComponent tab3 = new JPanel();
        tabbedPane.addTab("<html><body><table width='90'>Alapanyagok</table></body></html>", tab1);
        tabbedPane.addTab("<html><body><table width='90'>Egyebek</table></body></html>", tab2);
        tabbedPane.addTab("<html><body><table width='90'>Etalon</table></body></html>", tab3);
        tabbedPane.setBounds(0, menuheight, 600, height - menuheight);

        JButton b1 = new JButton("b1");             //buttons
        JButton b2 = new JButton("b2");

        JLabel labelMaterial = new JLabel("Alapanyag");         //labels
        JLabel labelAmount = new JLabel("Darabszám");
        JLabel labelWidth = new JLabel("Szélesség");
        JLabel labelHeight = new JLabel("Magasság");
        JLabel labelSideGap = new JLabel("Szélén");
        JLabel labelBetweenGap = new JLabel("Pályák közt");
        JLabel labelTracks = new JLabel("Pályák");

        JLabel labelDyeType = new JLabel("Festéktípus");
        JLabel labelDyeCylinder = new JLabel("Henger");
        JLabel labelDyeCover = new JLabel("Lefedettség");
        JLabel labelPregCover = new JLabel("Lefedettség");
        JLabel labelDomborCost = new JLabel("Költség");

        JLabel labelClicheCost = new JLabel("Kliséköltség");
        JLabel labelStancCost = new JLabel("Stancköltség");
        JLabel labelPackingCost = new JLabel("Kiszerelési költség");
        JLabel labelPackingTime = new JLabel("Kiszerelési idő");
        JLabel labelRollWidth = new JLabel("Tekercs szélesség");
        JLabel labelAmountPerRoll = new JLabel("Db/tekercs");

        JLabel labelMachine = new JLabel("Géptípus");
        JLabel labelTitle = new JLabel("Címke neve");
        JLabel labelClient = new JLabel("Megrendelő");
        JLabel labelDiscount = new JLabel("Kedvezmény");
        JLabel labelEuro = new JLabel("Euro árfolyam");
        JLabel labelOtherCost = new JLabel("Egyéb költség");
        JLabel labelSummary = new JLabel("Összesítés");


        //JTable table = new JTable(5,3);                    //táblát helyben kell tölteni
        //public JTable(Object rowData[][], Object columnNames[])

        Object rowData[][] = {{"Row1-Column1", "Row1-Column2", "Row1-Column3"},
                {"Row2-Column1", "Row2-Column2", "Row2-Column3"}};
        Object columnNames[] = {"Mennyiség", "0", "1", "2", "3", "4", "5", "6", "7"};
        JTable table = new JTable(rowData, columnNames);
        tab3.setLayout(new GridBagLayout());
        tab3.setBackground(Color.blue);
        //table.setBounds(10, 50, 300, 300);
        tab3.add(table.getTableHeader());
        tab3.add(table);


        panel2.add(labelAmount);
        this.add(tabbedPane);
        //this.add(panel1);         //not needed anymore, left in just in case
        this.add(panel2);
        this.add(menu);


    }

}

