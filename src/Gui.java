/**
 * Created by Talicska on 2014.07.08..
 */

import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.EventObject;
import java.util.Locale;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.JTextComponent;

public class Gui extends JFrame {       // ...ne baszd ossze a kodot!

    private Dimension dimension;
    private int width = 1000;
    private int height = 572;
    private int menuheight = 22;
    private int labelHeight = 25;

    private NumberFormat priceformat = NumberFormat.getNumberInstance(Locale.ENGLISH);      //for Etalon table
    private NumberFormat amountformat = DecimalFormat.getIntegerInstance(Locale.ENGLISH);

    private JComboBox comboMaterial;
    private JTextField textFAmount;
    private JTextField textFWidth;
    private JTextField textFHeight;
    private JTextField textFSideGap;
    private JTextField textFBetweenGap;
    private JTextField textFTracks;







    public Gui() {

        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);
        priceformat.setGroupingUsed(false);
        amountformat.setGroupingUsed(false);
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
        tab1.setLayout(null);
        tabbedPane.addTab("<html><body><table width='90'>Egyebek</table></body></html>", tab2);
        tab2.setLayout(null);
        tabbedPane.addTab("<html><body><table width='90'>Etalon</table></body></html>", tab3);
        tab3.setLayout(null);
        tabbedPane.setBounds(0, menuheight, 700, height - menuheight);

        JButton b1 = new JButton("b1");             //buttons
        JButton b2 = new JButton("b2");

        JLabel labelMaterial = new JLabel("Alapanyag");         //labels and co.
        labelMaterial.setBounds(5, 5, 70, 25);
        tab1.add(labelMaterial);
        labelMaterial.setBackground(Color.cyan);
        labelMaterial.setOpaque(true);
        comboMaterial = new JComboBox();
        tab1.add(comboMaterial);
        comboMaterial.setBounds(80,7,250,21);

        JLabel labelAmount = new JLabel("Darabszám");
        labelAmount.setBounds(5, 30, 70, 25);
        tab1.add(labelAmount);
        JLabel labelWidth = new JLabel("Szélesség");
        labelWidth.setBounds(5, 55, 70, 25);
        tab1.add(labelWidth);
        JLabel labelHeight = new JLabel("Magasság");
        labelHeight.setBounds(5, 80, 70, 25);
        tab1.add(labelHeight);
        JLabel labelSideGap = new JLabel("Szélén");
        labelSideGap.setBounds(5, 105, 70, 25);
        tab1.add(labelSideGap);
        JLabel labelBetweenGap = new JLabel("Pályák közt");
        labelBetweenGap.setBounds(5, 130, 70, 25);
        tab1.add(labelBetweenGap);
        JLabel labelTracks = new JLabel("Pályák");
        labelTracks.setBounds(5, 155, 70, 25);
        tab1.add(labelTracks);

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

        InteractiveTableModel model = new InteractiveTableModel(PPC.getEtalonObj().getEtalonMatrix(), columnNames);

        JTable table = new JTable(model){
            @Override
            public boolean editCellAt(int row, int column, EventObject e) {
                boolean result = super.editCellAt(row, column, e);
                final Component editor = getEditorComponent();
                if (editor == null || !(editor instanceof JTextComponent)) {
                    return result;
                }
                if (e instanceof MouseEvent) {
                    EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            ((JTextComponent) editor).selectAll();
                        }
                    });
                } else {
                    ((JTextComponent) editor).selectAll();
                }
                return result;
            }
        };


        tab3.setBackground(Color.blue);
        table.getTableHeader().setBounds(0, 0, 695, 30);
        table.setBounds(0, 30, 695, 320);

        table.setDefaultRenderer(Double.class, new PriceRenderer(priceformat));
        table.setDefaultEditor(Double.class, new PriceEditor(priceformat));
        table.getColumnModel().getColumn(0).setCellRenderer(new AmountRenderer(amountformat));
        table.getColumnModel().getColumn(0).setCellEditor(new AmountEditor(amountformat));

        table.getTableHeader().setFont(new Font("headerFont",Font.BOLD,12));

        tab3.add(table.getTableHeader());
        tab3.add(table);

        this.add(tabbedPane);
        //this.add(panel1);         //not needed anymore, left in just in case
        this.add(panel2);
        this.add(menu);


    }

    private static class PriceRenderer extends DefaultTableCellRenderer {

        private NumberFormat formatter;

        public PriceRenderer(NumberFormat formatter) {
            this.formatter = formatter;
            this.formatter.setMinimumFractionDigits(4);
            this.setHorizontalAlignment(SwingConstants.RIGHT);
        }

        @Override
        public void setValue(Object value) {
            setText((value == null) ? "" : formatter.format(value));
        }
    }

    private static class PriceEditor extends DefaultCellEditor {

        private NumberFormat formatter;
        private JTextField textField;

        public PriceEditor(NumberFormat formatter) {
            super(new JTextField());
            this.formatter = formatter;
            this.formatter.setMinimumFractionDigits(4);
            this.textField = (JTextField) this.getComponent();
            textField.setHorizontalAlignment(JTextField.RIGHT);
            textField.setBorder(null);
        }

        @Override
        public Object getCellEditorValue() {
            try {
                return new Double(Double.parseDouble(textField.getText()));
            } catch (NumberFormatException e) {
                System.out.println("Input error");
                return Double.valueOf(0);
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                                                     Object value, boolean isSelected, int row, int column) {
            textField.setText((value == null)
                    ? "" : formatter.format((Double) value));
            return textField;
        }
    }

    private static class AmountRenderer extends DefaultTableCellRenderer{

        private NumberFormat formatter;

        public AmountRenderer(NumberFormat formatter){
            this.formatter = formatter;
            setHorizontalAlignment(SwingConstants.RIGHT);
            this.formatter.setMinimumFractionDigits(0);
            this.formatter.setMaximumFractionDigits(0);
        }

        @Override
        public void setValue(Object value) {
            setText((value == null) ? "" : formatter.format(value));
        }
    }

    private static class AmountEditor extends DefaultCellEditor{
        private NumberFormat formatter;
        private JTextField textField;

        public AmountEditor(NumberFormat formatter) {
            super(new JTextField());
            this.formatter = formatter;
            this.formatter.setMinimumFractionDigits(0);
            this.formatter.setMaximumFractionDigits(0);
            this.textField = (JTextField) this.getComponent();
            textField.setHorizontalAlignment(JTextField.RIGHT);
            textField.setBorder(null);
        }

        @Override
        public Object getCellEditorValue() {
            try {
                return new Integer((textField.getText()));
            } catch (NumberFormatException e) {
                System.out.println("Input error");
                return Integer.valueOf(0);
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                                                     Object value, boolean isSelected, int row, int column) {
            textField.setText((value == null)
                    ? "" : formatter.format( value));
            return textField;
        }
    }
}

