/**
 * Created by Talicska on 2014.07.08..
 */

import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.EventObject;
import java.util.Locale;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.JTextComponent;

public class Gui extends JFrame implements ActionListener {       // ...ne baszd ossze a kodot!

    private Dimension dimension;                                                            //dimensions
    private int width = 1000;
    private int height = 572;
    private int menuheight = 22;

    private NumberFormat priceformat = NumberFormat.getNumberInstance(Locale.ENGLISH);      //for Etalon table
    private NumberFormat amountformat = DecimalFormat.getIntegerInstance(Locale.ENGLISH);

    private JComboBox comboMaterial;                                                        //objects
    private JComboBox comboMachine;
    private JComboBox comboCylinder;
    private JComboBox comboDyePreset;
    private JComboBox comboDyeType;
    private JComboBox comboDyeCylinder;


    private JTextField textFAmount;
    private JTextField textFWidth;
    private JTextField textFHeight;
    private JTextField textFSideGap;
    private JTextField textFBetweenGap;
    private JTextField textFTracks;
    private JTextField textFDyeCover;
    private JTextField textFPregCover;
    private JTextField textFDomborCost;
    private JTextField textFTitle;
    private JTextField textFClient;
    private JTextField textFDiscount;
    private JTextField textFOtherCost;
    private JTextField textFEuro;

    private JButton buttonaddDyePreset;
    private JButton buttonAddDye;
    private JButton buttonGetEuro;

    private List listDyeType;

    private JCheckBox checkPreg;
    private JCheckBox checkDombor;


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonGetEuro) {
            textFEuro.setText(String.valueOf(new CurrencyConverter().convert()));
        }else if (e.getSource() == buttonAddDye){
            PPC.calcObj.addDye(comboDyeType.getSelectedIndex(), comboDyeCylinder.getSelectedIndex(), Integer.parseInt(textFDyeCover.getText()) ,"", 0);
            listDyeType.removeAll();
            for (int i = 0; i < PPC.calcObj.getAddedDyes().size(); i++){
                listDyeType.add(PPC.calcObj.getAddedDyes().get(i).getName() + "  " +
                                PPC.calcObj.getAddedDyes().get(i).getDyeCylinder().getVolume() + " g/m2  " +
                                PPC.calcObj.getAddedDyes().get(i).getCover() + " %"
                );
            }
        }
    }


    public Gui() {

        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);
        priceformat.setGroupingUsed(false);
        amountformat.setGroupingUsed(false);
        this.setPreferredSize(dimension);
        this.setTitle("PPC - Print Price Calculator");

        JMenu fileMenu = new JMenu("Fájl");                                             //menu
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

        /*JPanel panel1 = new JPanel();                                                 //left panel
        panel1.setBounds(0, 25, 700, height-menuheight);
        panel1.setBackground(Color.red);*/

        //Color mainColor = new Color(116,174,153);
        //Color borderColor = new Color(69, 116, 133);
        //Color MiscColor = new Color(39,88,107);
        //UIManager.put("TabbedPane.selected", mainColor);
        //UIManager.put("TabbedPane.contentAreaColor", borderColor);

        JPanel panel2 = new JPanel();                                                   //right panel
        panel2.setBounds(700, 25, 300, height - menuheight);
        //panel2.setBackground(mainColor);


        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, 0);                   //tabs
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


        //Color mybackground = new Color(200,225,255);
        //tabbedPane.setBackground(new Color(39,88,107));
        //tabbedPane.setBackgroundAt(1,Color.BLUE);
        //tab1.setBackground(mainColor);
        //tab2.setBackground(mainColor);
        //tab3.setBackground(mainColor);


        //SwingUtilities.updateComponentTreeUI(tabbedPane);

        JLabel labelMaterial = new JLabel("Alapanyag");                                 //labels and co.
        labelMaterial.setBounds(5, 5, 70, 25);
        tab1.add(labelMaterial);
        comboMaterial = new JComboBox();
        for (int i = 0; i < PPC.calcObj.getMaterials().size(); i++)
            comboMaterial.addItem(PPC.calcObj.getMaterials().get(i).getName());
        tab1.add(comboMaterial);
        comboMaterial.setBounds(80, 7, 250, 21);

        JLabel labelAmount = new JLabel("Darabszám");
        labelAmount.setBounds(5, 30, 70, 25);
        tab1.add(labelAmount);
        textFAmount = new JTextField();
        textFAmount.setBounds(80, 32, 80, 21);
        textFAmount.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFAmount);
        JLabel labelDb1 = new JLabel("db");
        labelDb1.setBounds(165, 32, 30, 20);
        tab1.add(labelDb1);

        JLabel labelWidth = new JLabel("Szélesség");
        labelWidth.setBounds(5, 55, 70, 25);
        tab1.add(labelWidth);
        textFWidth = new JTextField();
        textFWidth.setBounds(80, 57, 80, 21);
        textFWidth.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFWidth);
        JLabel labelMm1 = new JLabel("mm");
        labelMm1.setBounds(165, 57, 30, 20);
        tab1.add(labelMm1);

        JLabel labelHeight = new JLabel("Magasság");
        labelHeight.setBounds(5, 80, 70, 25);
        tab1.add(labelHeight);
        textFHeight = new JTextField();
        textFHeight.setBounds(80, 82, 80, 21);
        textFHeight.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFHeight);
        JLabel labelMm2 = new JLabel("mm");
        labelMm2.setBounds(165, 82, 30, 20);
        tab1.add(labelMm2);

        JLabel labelTracks = new JLabel("Pályák");
        labelTracks.setBounds(5, 105, 70, 25);
        tab1.add(labelTracks);
        textFTracks = new JTextField();
        textFTracks.setBounds(80, 107, 80, 21);
        textFTracks.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFTracks);
        JLabel labelDb2 = new JLabel("db");
        labelDb2.setBounds(165, 107, 30, 20);
        tab1.add(labelDb2);

        JLabel labelGaps = new JLabel("Rések");
        labelGaps.setBounds(5, 130, 70, 25);
        tab1.add(labelGaps);

        JLabel labelSideGap = new JLabel("Szélén");
        labelSideGap.setBounds(15, 155, 70, 25);
        tab1.add(labelSideGap);
        textFSideGap = new JTextField("5");
        textFSideGap.setBounds(90, 157, 70, 21);
        textFSideGap.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFSideGap);
        JLabel labelMm3 = new JLabel("mm");
        labelMm3.setBounds(165, 157, 30, 20);
        tab1.add(labelMm3);

        JLabel labelBetweenGap = new JLabel("Pályák közt");
        labelBetweenGap.setBounds(15, 180, 70, 25);
        tab1.add(labelBetweenGap);
        textFBetweenGap = new JTextField("3");
        textFBetweenGap.setBounds(90, 182, 70, 21);
        textFBetweenGap.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFBetweenGap);
        JLabel labelMm4 = new JLabel("mm");
        labelMm4.setBounds(165, 182, 30, 20);
        tab1.add(labelMm4);

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(5, 212, 325, 2);
        tab1.add(separator1);

        JLabel labelMachine = new JLabel("Géptípus");
        labelMachine.setBounds(5, 220, 70, 25);
        tab1.add(labelMachine);
        comboMachine = new JComboBox();
        for (int i = 0; i < PPC.calcObj.getMachines().size(); i++)
            comboMachine.addItem(PPC.calcObj.getMachines().get(i).getName());
        tab1.add(comboMachine);
        comboMachine.setBounds(80, 222, 130, 21);
        comboCylinder = new JComboBox();
        tab1.add(comboCylinder);
        for (int i = 0; i < PPC.calcObj.getMachines().get(0).getCylinders().size(); i++)
            comboCylinder.addItem(PPC.calcObj.getMachines().get(0).getCylinders().get(i).getTeeth());
        comboCylinder.setBounds(230, 222, 100, 21);

        //géptípus alatti rész
        //
        //

        JSeparator separator2 = new JSeparator();                                           //tab1 vertical separator
        separator2.setOrientation(SwingConstants.VERTICAL);
        separator2.setBounds(348, 5, 2, 500);
        tab1.add(separator2);
        JSeparator separator3 = new JSeparator();
        separator3.setOrientation(SwingConstants.VERTICAL);
        separator3.setBounds(352, 5, 2, 500);
        tab1.add(separator3);


        JLabel labelDyePreset = new JLabel("Összeállítás betöltése");
        labelDyePreset.setBounds(370, 5, 200, 25);
        tab1.add(labelDyePreset);
        comboDyePreset = new JComboBox();
        comboDyePreset.setBounds(370, 32, 225, 21);
        tab1.add(comboDyePreset);

        //fel kell tölteni comboDyePreset-et

        buttonaddDyePreset = new JButton("Betölt");
        buttonaddDyePreset.setBounds(600, 32, 85, 21);
        tab1.add(buttonaddDyePreset);

        JLabel labelDyeAdd = new JLabel("Festéktípus hozzáadása");
        labelDyeAdd.setBounds(370, 65, 200, 25);
        tab1.add(labelDyeAdd);
        comboDyeType = new JComboBox();
        for (int i = 0; i < PPC.calcObj.getAllDyeTypes().size(); i++)
            comboDyeType.addItem(PPC.calcObj.getAllDyeTypes().get(i).getName());
        comboDyeType.addItem("Egyéb szín");
        tab1.add(comboDyeType);
        comboDyeType.setBounds(370, 92, 225, 21);

        buttonAddDye = new JButton("Hozzáad");
        buttonAddDye.setBounds(600, 92, 85, 21);
        buttonAddDye.addActionListener(this);
        tab1.add(buttonAddDye);

        JLabel labelDyeCylinder = new JLabel("Henger");
        labelDyeCylinder.setBounds(380, 115, 70, 25);
        tab1.add(labelDyeCylinder);
        comboDyeCylinder = new JComboBox();
        for (int i = 0; i < PPC.calcObj.getDyecylinders().size(); i++)
            comboDyeCylinder.addItem(PPC.calcObj.getDyecylinders().get(i).getVolume());
        tab1.add(comboDyeCylinder);
        comboDyeCylinder.setBounds(455, 117, 65, 21);
        JLabel labelGM = new JLabel("g/m2");
        labelGM.setBounds(525, 117, 30, 20);
        tab1.add(labelGM);

        JLabel labelDyeCover = new JLabel("Lefedettség");
        labelDyeCover.setBounds(380, 140, 70, 25);
        tab1.add(labelDyeCover);
        textFDyeCover = new JTextField();
        textFDyeCover.setBounds(455, 142, 65, 21);
        textFDyeCover.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFDyeCover);
        JLabel labelPercent1 = new JLabel("%");
        labelPercent1.setBounds(525, 142, 30, 20);
        tab1.add(labelPercent1);

        listDyeType = new List();
        listDyeType.setBounds(370, 175, 225, 125);
        tab1.add(listDyeType);
        for (int i = 0; i < 8; i++)
            listDyeType.add("Festek   asdas   qwertz");

        checkPreg = new JCheckBox("Prégelés", false);
        checkPreg.setBounds(370, 310, 120, 25);
        tab1.add(checkPreg);
        JLabel labelPregCover = new JLabel("Lefedettség", SwingConstants.RIGHT);
        labelPregCover.setBounds(520, 310, 75, 25);
        tab1.add(labelPregCover);
        textFPregCover = new JTextField();
        textFPregCover.setBounds(600, 312, 65, 21);
        textFPregCover.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFPregCover);
        JLabel labelPercent2 = new JLabel("%");
        labelPercent2.setBounds(670, 312, 30, 20);
        tab1.add(labelPercent2);

        checkDombor = new JCheckBox("Dombornyomás", false);
        checkDombor.setBounds(370, 335, 120, 25);
        tab1.add(checkDombor);
        JLabel labelDomborCost = new JLabel("Költség", SwingConstants.RIGHT);
        labelDomborCost.setBounds(520, 335, 75, 25);
        tab1.add(labelDomborCost);
        textFDomborCost = new JTextField("5000");
        textFDomborCost.setBounds(600, 337, 65, 21);
        textFDomborCost.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFDomborCost);
        JLabel labelFt1 = new JLabel("Ft");
        labelFt1.setBounds(670, 337, 30, 20);
        tab1.add(labelFt1);

        JSeparator separator4 = new JSeparator();
        separator4.setBounds(370, 365, 315, 2);
        tab1.add(separator4);

        JLabel labelTitle = new JLabel("Címke neve");
        labelTitle.setBounds(370, 375, 80, 25);
        tab1.add(labelTitle);
        textFTitle = new JTextField("");
        textFTitle.setBounds(455, 377, 230, 21);
        tab1.add(textFTitle);

        JLabel labelClient = new JLabel("Megrendelő");
        labelClient.setBounds(370, 400, 80, 25);
        tab1.add(labelClient);
        textFClient = new JTextField("");
        textFClient.setBounds(455, 402, 230, 21);
        tab1.add(textFClient);

        JLabel labelDiscount = new JLabel("Kedvezmény");
        labelDiscount.setBounds(370, 425, 80, 25);
        tab1.add(labelDiscount);
        textFDiscount = new JTextField("0");
        textFDiscount.setBounds(455, 427, 65, 21);
        textFDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFDiscount);
        JLabel labelPercent3 = new JLabel("%");
        labelPercent3.setBounds(525, 427, 30, 20);
        tab1.add(labelPercent3);

        JSeparator separator5 = new JSeparator();
        separator5.setBounds(370, 460, 315, 2);
        tab1.add(separator5);

        JLabel labelEuro = new JLabel("Euro árfolyam");
        labelEuro.setBounds(370, 470, 80, 25);
        tab1.add(labelEuro);
        textFEuro = new JTextField("300");
        textFEuro.setBounds(455, 472, 65, 21);
        textFEuro.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFEuro);
        JLabel labelFt2 = new JLabel("Ft");
        labelFt2.setBounds(525, 472, 30, 20);
        tab1.add(labelFt2);
        buttonGetEuro = new JButton("Lekér");
        buttonGetEuro.addActionListener(this);
        buttonGetEuro.setBounds(600, 472, 85, 21);
        tab1.add(buttonGetEuro);


        JLabel labelOtherCost = new JLabel("Egyéb költség");

        JLabel labelClicheCost = new JLabel("Kliséköltség");
        JLabel labelStancCost = new JLabel("Stancköltség");
        JLabel labelPackingCost = new JLabel("Kiszerelési költség");
        JLabel labelPackingTime = new JLabel("Kiszerelési idő");
        JLabel labelRollWidth = new JLabel("Tekercs szélesség");
        JLabel labelAmountPerRoll = new JLabel("Db/tekercs");


        JLabel labelSummary = new JLabel("Összesítés");

        Vector<String> columnNames = new Vector<String>();                                      //tab3
        columnNames.addElement("Mennyiség");
        columnNames.addElement("0 szín");
        columnNames.addElement("1 szín");
        columnNames.addElement("2 szín");
        columnNames.addElement("3 szín");
        columnNames.addElement("4 szín");
        columnNames.addElement("5 szín");
        columnNames.addElement("6 szín");
        columnNames.addElement("7 szín");

        InteractiveTableModel model = new InteractiveTableModel(PPC.calcObj.getEtalonObj().getEtalonMatrix(), columnNames);

        JTable table = new JTable(model) {
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
        table.setRowHeight(20);
        table.getTableHeader().setBounds(0, 0, 695, 30);
        table.setBounds(0, 30, 695, 400);
        table.setDefaultRenderer(Double.class, new PriceRenderer(priceformat));
        table.setDefaultEditor(Double.class, new PriceEditor(priceformat));
        table.getColumnModel().getColumn(0).setCellRenderer(new AmountRenderer(amountformat));
        table.getColumnModel().getColumn(0).setCellEditor(new AmountEditor(amountformat));
        table.getTableHeader().setFont(new Font("headerFont", Font.BOLD, 12));

        tab3.add(table.getTableHeader());
        tab3.add(table);

        JLabel labelEtalonSelfCost = new JLabel("Etalon önköltség:  " + PPC.calcObj.getEtalonObj().getEtalonSelfCost() + " Ft");
        labelEtalonSelfCost.setBounds(5, 445, 300, 20);
        tab3.add(labelEtalonSelfCost);
        JLabel labelEtalonMaterialPrice = new JLabel("Etalon anyagköltség:  " + PPC.calcObj.getEtalonObj().getEtalonMaterialPrice() + " Ft");
        labelEtalonMaterialPrice.setBounds(5, 470, 300, 20);
        tab3.add(labelEtalonMaterialPrice);
        JLabel labelEtalonSizeX = new JLabel("Etalon szélesség:  " + (int) PPC.calcObj.getEtalonObj().getEtalonSizeX() + " mm");
        labelEtalonSizeX.setBounds(347, 445, 300, 20);
        tab3.add(labelEtalonSizeX);
        JLabel labelEtalonSizeY = new JLabel("Etalon magasság:  " + (int) PPC.calcObj.getEtalonObj().getEtalonSizeY() + " mm");
        labelEtalonSizeY.setBounds(347, 470, 300, 20);
        tab3.add(labelEtalonSizeY);


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

    private static class AmountRenderer extends DefaultTableCellRenderer {

        private NumberFormat formatter;

        public AmountRenderer(NumberFormat formatter) {
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

    private static class AmountEditor extends DefaultCellEditor {
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
                    ? "" : formatter.format(value));
            return textField;
        }
    }

    private static class CurrencyConverter {

        private String url = "http://rate-exchange.appspot.com/currency?from=EUR&to=HUF";

        public Double convert() {

            String answer = "";
            System.out.println("converting..");

            try {
                URL convert = new URL(url);
                BufferedReader in = new BufferedReader(new InputStreamReader(convert.openStream()));
                answer = in.readLine();
                in.close();
                answer = answer.replaceFirst("\\D*", "");
                answer = answer.replaceFirst(",\\D*", "");
            } catch (MalformedURLException mue) {
                System.out.println("1.");
            } catch (IOException ioe) {
                System.out.println("2.");
            }

            if (answer != "")
                return Double.valueOf(answer);
            return new Double(-1);                                          //errorhandling level: MacGyver

        }
    }

}

