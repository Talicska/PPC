/**
 * Created by Talicska on 2014.07.08..
 */

import java.awt.*;
import java.awt.Color;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class Gui extends JFrame {       // ...ne baszd ossze a kodot!

    private Dimension dimension;
    private int width = 1000;
    private int height = 572;
    private int menuheight = 22;

    public Gui() {

        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);
        this.setPreferredSize(dimension);
        this.setTitle("PPC - Print Price Calculator");

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
        panel1.setBounds(0, 25, 700, height-menuheight);
        panel1.setBackground(Color.red);*/

        JPanel panel2 = new JPanel();               //right panel
        panel2.setBounds(700, 25, 300, height - menuheight);
        panel2.setBackground(Color.yellow);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, 0);      //tabs
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        JComponent tab3 = new JPanel();
        tabbedPane.addTab("<html><body><table width='90'>Alapanyagok</table></body></html>", tab1);
        tabbedPane.addTab("<html><body><table width='90'>Egyebek</table></body></html>", tab2);
        tabbedPane.addTab("<html><body><table width='90'>Etalon</table></body></html>", tab3);
        tabbedPane.setBounds(0, menuheight, 700, height - menuheight);

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

        Vector<String> columnNames = new Vector<String>();        //Etalon
        columnNames.addElement("Mennyiség");
        columnNames.addElement("0 szín");
        columnNames.addElement("1 szín");
        columnNames.addElement("2 szín");
        columnNames.addElement("3 szín");
        columnNames.addElement("4 szín");
        columnNames.addElement("5 szín");
        columnNames.addElement("6 szín");
        columnNames.addElement("7 szín");

        JTable table = new JTable(new InteractiveTableModel(PPC.getEtalonObj().getEtalonMatrix(), columnNames));
        //table.getModel().addTableModelListener(InteractiveTableModel());
        tab3.setLayout(null);
        tab3.setBackground(Color.blue);
        table.getTableHeader().setBounds(0, 0, 695, 30);
        table.setBounds(0, 30, 695, 320);               //legyen nagy vagy legyen alatta hely?

        /*DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();        //jobbra igazit
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getTableHeader().setForeground(Color.blue);                               //formázott cellába nem enged beleírni, error
        for (int i = 0; i < 9; i++)                                                         //kell saját TableModel hogy a táblából visszaírás menjen, félig kész
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
*/
        tab3.add(table.getTableHeader());
        tab3.add(table);

        panel2.add(labelAmount);
        this.add(tabbedPane);
        //this.add(panel1);         //not needed anymore, left in just in case
        this.add(panel2);
        this.add(menu);

    }

}

