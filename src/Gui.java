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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.JTextComponent;

public class Gui extends JFrame implements ActionListener {

    private Dimension dimension;                                                            //dimensions
    private int width = 1000;
    private int height = 572;
    private int menuheight = 22;

    private NumberFormat priceformat = NumberFormat.getNumberInstance(Locale.ENGLISH);      //for Etalon table
    private NumberFormat amountformat = DecimalFormat.getIntegerInstance(Locale.ENGLISH);

    private JComboBox<Material> comboMaterial;                                                        //objects
    private JComboBox<String> comboMachine;
    private JComboBox<String> comboCylinder;
    private JComboBox<DyePreset> comboDyePreset;
    private JComboBox<DyeParent> comboDyeType;
    private JComboBox<DyeCylinder> comboDyeCylinder;

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
    private JTextField textFClicheCost;
    private JTextField textFStancCost;
    private JTextField textFOtherCost;
    private JTextField textFPackingCost;
    private JTextField textFPackingTime;
    private JTextField textFRollWidth;
    private JTextField textFAmountPerRoll;
    private JTextField textFPackingSumCost;
    private JTextField textFEuro;

    private JButton buttonLoadPreset;
    private JButton buttonAddDye;
    private JButton buttonManageDyes;
    private JButton buttonDirectDye;
    private JButton buttonDelDye;
    private JButton buttonManageMaterials;
    private JButton buttonDelPreset;
    private JButton buttonSavePreset;
    private JButton buttonResetDye;
    private JButton buttonGetEuro;
    private JButton buttonCalculate;

    private List listDyeType;

    private JCheckBox checkPreg;
    private JCheckBox checkDombor;

    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    private JMenuItem exitItem;
    private JMenuItem aboutItem;

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonGetEuro) {
            textFEuro.setText(String.valueOf(new CurrencyConverter().convert()));
        }

        else if (e.getSource() == buttonManageMaterials){
            GuiManageMaterials materialEditor = new GuiManageMaterials(this);
        }

        else if (e.getSource() == buttonAddDye) {
            if ((!textFDyeCover.getText().isEmpty()) && (comboDyeType.getSelectedIndex() >= 0) && (comboDyeCylinder.getSelectedIndex() >= 0)) {
                DyeParent newDye = (DyeParent)comboDyeType.getSelectedItem();
                newDye.setDyeCylinder((DyeCylinder)comboDyeCylinder.getSelectedItem());
                newDye.setCover(Integer.valueOf(textFDyeCover.getText()));
                PPC.calcObj.addDye(newDye);
                String string ="";
                String name;
                String volume;
                String cover;
                listDyeType.removeAll();
                for (int i = 0; i < PPC.calcObj.getAddedDyes().size(); i++) {
                    name = PPC.calcObj.getAddedDyes().get(i).getName().trim();
                    volume = String.valueOf(PPC.calcObj.getAddedDyes().get(i).getDyeCylinder().getVolume());
                    cover = String.valueOf(PPC.calcObj.getAddedDyes().get(i).getCover());
                    listDyeType.add(string.format("%-20s %4sg %3s%%", name, volume, cover));
                }
            } else if (textFDyeCover.getText().isEmpty()) {
                flashMyField(textFDyeCover, Color.RED, 200);
            }
        }

        else if (e.getSource() == buttonDirectDye) {
            if ((!textFDyeCover.getText().isEmpty()) &&  (comboDyeCylinder.getSelectedIndex() >= 0)) {
                GuiDirectDye otherDye = new GuiDirectDye(this);
                DyeParent newDye = otherDye.getNewDye();
                if (newDye != null){
                    PPC.calcObj.addDye(newDye);
                }
                String string ="";
                String name;
                String volume;
                String cover;
                listDyeType.removeAll();
                for (int i = 0; i < PPC.calcObj.getAddedDyes().size(); i++) {
                    name = PPC.calcObj.getAddedDyes().get(i).getName().trim();
                    volume = String.valueOf(PPC.calcObj.getAddedDyes().get(i).getDyeCylinder().getVolume());
                    cover = String.valueOf(PPC.calcObj.getAddedDyes().get(i).getCover());
                    listDyeType.add(string.format("%-20s %4sg %3s%%", name, volume, cover));
                }
            } else if (textFDyeCover.getText().isEmpty()) {
                flashMyField(textFDyeCover, Color.RED, 200);
            }
        }

        else if (e.getSource() == buttonManageDyes ){
            GuiManageDyes guiDyes = new GuiManageDyes(this);
        }

        else if (e.getSource() == comboMachine) {
            comboCylinder.removeAllItems();
            int index = comboMachine.getSelectedIndex();
            comboCylinder.addItem("Auto");
            for (int i = 0; i < PPC.calcObj.getMachines().get(index).getCylinders().size(); i++)
                comboCylinder.addItem(String.valueOf(PPC.calcObj.getMachines().get(index).getCylinders().get(i).getTeeth()));
        }

        else if (e.getSource() == buttonDelDye) {
            int index = listDyeType.getSelectedIndex();
            if (index >= 0) {
                listDyeType.remove(index);
                PPC.calcObj.removeAddedDye(index);
            }
        }

        else if (e.getSource() == buttonResetDye) {
            if ( listDyeType.getItemCount() > 0){
                listDyeType.removeAll();
                PPC.calcObj.resetAddedDyes();
            }
        }

        else if (e.getSource() == buttonSavePreset) {
            if(listDyeType.getItemCount() > 0) {
                GuiDyePresetName guiName = new GuiDyePresetName(this);
                String name = guiName.getPresetName();
                if ( ! name.isEmpty() ){
                    comboDyePreset.addItem(new DyePreset(name,PPC.calcObj.getAddedDyes()));
                    comboDyePreset.setSelectedIndex(0);
                }
            }
        }

        else if (e.getSource() == buttonDelPreset) {
            if (comboDyePreset.getItemCount() > 0 ) {
                PPC.calcObj.removeDyePreset(comboDyePreset.getSelectedIndex());
                if (comboDyePreset.getItemCount() > 0)
                    comboDyePreset.setSelectedIndex(0);
                else
                    comboDyePreset.setSelectedIndex(-1);
            }
        }

        else if (e.getSource() == buttonLoadPreset) {
            if (comboDyePreset.getItemCount() > 0 && comboDyePreset.getSelectedIndex() >= 0) {
                PPC.calcObj.addDyePreset(comboDyePreset.getSelectedIndex());

                String string ="";
                String name;
                String volume;
                String cover;
                listDyeType.removeAll();
                for (int i = 0; i < PPC.calcObj.getAddedDyes().size(); i++) {
                    name = PPC.calcObj.getAddedDyes().get(i).getName().trim();
                    volume = String.valueOf(PPC.calcObj.getAddedDyes().get(i).getDyeCylinder().getVolume());
                    cover = String.valueOf(PPC.calcObj.getAddedDyes().get(i).getCover());
                    listDyeType.add(string.format("%-20s %4sg %3s%%", name, volume, cover));
                }
            }
        }

        else if (e.getSource() == buttonCalculate) {
            if (checkInput()){



            }
        }

        else if (e.getSource() == exitItem) {
            System.exit(0);
        }

        else if (e.getSource() == aboutItem) {
            new GuiAbout();
        }

        else if (e.getSource() == newItem) {
            this.reset();
        }


    }

    public Gui() {

        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);
        priceformat.setGroupingUsed(false);
        amountformat.setGroupingUsed(false);
        this.setPreferredSize(dimension);
        this.setTitle("PPC - Print Price Calculator");

        DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if ((e.getDocument() == textFAmount.getDocument()) || (e.getDocument() == textFPackingCost.getDocument()) ||
                        (e.getDocument() == textFPackingTime.getDocument()) || (e.getDocument() == textFRollWidth.getDocument()) ||
                        (e.getDocument() == textFAmountPerRoll.getDocument())) {

                    if (!(textFAmount.getText().isEmpty() || textFPackingCost.getText().isEmpty() ||
                            textFPackingTime.getText().isEmpty() || textFRollWidth.getText().isEmpty() ||
                            textFAmountPerRoll.getText().isEmpty())) {

                        int amount = Integer.parseInt(textFAmount.getText());
                        double packingcost = Double.parseDouble(textFPackingCost.getText());
                        double packingtime = Double.parseDouble(textFPackingTime.getText());
                        double rollwidth = Double.parseDouble(textFRollWidth.getText());
                        int amountperroll = Integer.parseInt(textFAmountPerRoll.getText());

                        double cost = PPC.calcObj.calculatePackingCost(amount, packingcost, packingtime, rollwidth, amountperroll);

                        textFPackingSumCost.setText(Integer.toString((int) cost));
                    }else textFPackingSumCost.setText("0");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if ((e.getDocument() == textFAmount.getDocument()) || (e.getDocument() == textFPackingCost.getDocument()) ||
                        (e.getDocument() == textFPackingTime.getDocument()) || (e.getDocument() == textFRollWidth.getDocument()) ||
                        (e.getDocument() == textFAmountPerRoll.getDocument())) {

                    if (!(textFAmount.getText().isEmpty() || textFPackingCost.getText().isEmpty() ||
                            textFPackingTime.getText().isEmpty() || textFRollWidth.getText().isEmpty() ||
                            textFAmountPerRoll.getText().isEmpty())) {

                        int amount = Integer.parseInt(textFAmount.getText());
                        double packingcost = Double.parseDouble(textFPackingCost.getText());
                        double packingtime = Double.parseDouble(textFPackingTime.getText());
                        double rollwidth = Double.parseDouble(textFRollWidth.getText());
                        int amountperroll = Integer.parseInt(textFAmountPerRoll.getText());

                        double cost = PPC.calcObj.calculatePackingCost(amount, packingcost, packingtime, rollwidth, amountperroll);

                        textFPackingSumCost.setText(Integer.toString((int) cost));
                    }else textFPackingSumCost.setText("0");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if ((e.getDocument() == textFAmount.getDocument()) || (e.getDocument() == textFPackingCost.getDocument()) ||
                        (e.getDocument() == textFPackingTime.getDocument()) || (e.getDocument() == textFRollWidth.getDocument()) ||
                        (e.getDocument() == textFAmountPerRoll.getDocument())) {

                    if (!(textFAmount.getText().isEmpty() || textFPackingCost.getText().isEmpty() ||
                            textFPackingTime.getText().isEmpty() || textFRollWidth.getText().isEmpty() ||
                            textFAmountPerRoll.getText().isEmpty())) {

                        int amount = Integer.parseInt(textFAmount.getText());
                        double packingcost = Double.parseDouble(textFPackingCost.getText());
                        double packingtime = Double.parseDouble(textFPackingTime.getText());
                        double rollwidth = Double.parseDouble(textFRollWidth.getText());
                        int amountperroll = Integer.parseInt(textFAmountPerRoll.getText());

                        double cost = PPC.calcObj.calculatePackingCost(amount, packingcost, packingtime, rollwidth, amountperroll);

                        textFPackingSumCost.setText(Integer.toString((int) cost));
                    }else textFPackingSumCost.setText("0");
                }
            }

        };

        JMenu fileMenu = new JMenu("Fájl");                                             //menu
        newItem = new JMenuItem("Új");
        newItem.addActionListener(this);
        fileMenu.add(newItem);
        saveItem = new JMenuItem("Mentés");
        fileMenu.add(saveItem);
        loadItem = new JMenuItem("Betöltés");
        fileMenu.add(loadItem);
        exitItem = new JMenuItem("Kilépés");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);
        JMenu aboutMenu = new JMenu("Névjegy");
        aboutItem = new JMenuItem("A PPC névjegye");
        aboutItem.addActionListener(this);
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
        panel2.setLayout(null);
        //panel2.setBackground(mainColor);


        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, 0);                   //tabs
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        JComponent tab3 = new JPanel();
        tabbedPane.addTab("<html><body><table width='90'>Árképzés</table></body></html>", tab1);
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

        JLabel labelMaterial = new JLabel("Alapanyag kiválasztása");                                 //labels and co.
        labelMaterial.setBounds(5, 5, 200, 25);
        tab1.add(labelMaterial);
        comboMaterial = new JComboBox<Material>(PPC.calcObj.getMaterials());
        tab1.add(comboMaterial);
        comboMaterial.setBounds(5, 27, 325, 21);
        comboMaterial.setMaximumRowCount(12);
        comboMaterial.setFont(new Font(Font.MONOSPACED,Font.ROMAN_BASELINE,12));
        buttonManageMaterials = new JButton("Kezelés");
        buttonManageMaterials.setBounds(245,52,85,21);
        buttonManageMaterials.addActionListener(this);
        tab1.add(buttonManageMaterials);

        JLabel labelAmount = new JLabel("Darabszám");
        labelAmount.setBounds(5, 50, 70, 25);
        tab1.add(labelAmount);
        textFAmount = new IntegerField();
        textFAmount.setBounds(80, 52, 80, 21);
        textFAmount.setHorizontalAlignment(SwingConstants.RIGHT);
        textFAmount.getDocument().addDocumentListener(docListener);

        tab1.add(textFAmount);
        JLabel labelDb1 = new JLabel("db");
        labelDb1.setBounds(165, 52, 30, 20);
        tab1.add(labelDb1);

        JLabel labelWidth = new JLabel("Szélesség");
        labelWidth.setBounds(5, 75, 70, 25);
        tab1.add(labelWidth);
        textFWidth = new DoubleField();
        textFWidth.setBounds(80, 77, 80, 21);
        textFWidth.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFWidth);
        JLabel labelMm1 = new JLabel("mm");
        labelMm1.setBounds(165, 77, 30, 20);
        tab1.add(labelMm1);

        JLabel labelHeight = new JLabel("Magasság");
        labelHeight.setBounds(5, 100, 70, 25);
        tab1.add(labelHeight);
        textFHeight = new DoubleField();
        textFHeight.setBounds(80, 102, 80, 21);
        textFHeight.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFHeight);
        JLabel labelMm2 = new JLabel("mm");
        labelMm2.setBounds(165, 102, 30, 20);
        tab1.add(labelMm2);

        JLabel labelTracks = new JLabel("Pályák");
        labelTracks.setBounds(5, 125, 70, 25);
        tab1.add(labelTracks);
        textFTracks = new IntegerField();
        textFTracks.setBounds(80, 127, 80, 21);
        textFTracks.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFTracks);
        JLabel labelDb2 = new JLabel("db");
        labelDb2.setBounds(165, 127, 30, 20);
        tab1.add(labelDb2);

        JLabel labelGaps = new JLabel("Hézagok");
        labelGaps.setBounds(5, 150, 70, 25);
        tab1.add(labelGaps);

        JLabel labelSideGap = new JLabel("Széleken");
        labelSideGap.setBounds(15, 175, 70, 25);
        tab1.add(labelSideGap);
        textFSideGap = new DoubleField("6");
        textFSideGap.setBounds(90, 177, 70, 21);
        textFSideGap.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFSideGap);
        JLabel labelMm3 = new JLabel("mm");
        labelMm3.setBounds(165, 177, 30, 20);
        tab1.add(labelMm3);

        JLabel labelBetweenGap = new JLabel("Pályák közt");
        labelBetweenGap.setBounds(15, 200, 70, 25);
        tab1.add(labelBetweenGap);
        textFBetweenGap = new DoubleField("3");
        textFBetweenGap.setBounds(90, 202, 70, 21);
        textFBetweenGap.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFBetweenGap);
        JLabel labelMm4 = new JLabel("mm");
        labelMm4.setBounds(165, 202, 30, 20);
        tab1.add(labelMm4);

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(5, 232, 325, 1);
        tab1.add(separator1);

        JLabel labelMachine = new JLabel("Géptípus");
        labelMachine.setBounds(5, 241, 70, 25);
        tab1.add(labelMachine);
        comboMachine = new JComboBox<String>();
        for (int i = 0; i < PPC.calcObj.getMachines().size(); i++)
            comboMachine.addItem(PPC.calcObj.getMachines().get(i).getName());
        comboMachine.addActionListener(this);
        tab1.add(comboMachine);
        comboMachine.setBounds(80, 243, 145, 21);
        comboCylinder = new JComboBox<String>();
        tab1.add(comboCylinder);
        comboCylinder.addItem("Auto");
        for (int i = 0; i < PPC.calcObj.getMachines().get(0).getCylinders().size(); i++)
            comboCylinder.addItem(String.valueOf(PPC.calcObj.getMachines().get(0).getCylinders().get(i).getTeeth()));
        comboCylinder.setBounds(230, 243, 100, 21);

        //géptípus alatti rész

        JSeparator separator5 = new JSeparator();
        separator5.setBounds(5, 274, 325, 1);
        tab1.add(separator5);

        JLabel labelClicheCost = new JLabel("Kliséköltség");
        labelClicheCost.setBounds(5, 287, 80, 25);
        tab1.add(labelClicheCost);
        textFClicheCost = new IntegerField("0");
        textFClicheCost.setBounds(90, 289, 70, 21);
        textFClicheCost.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFClicheCost);
        JLabel labelFt3 = new JLabel("Ft");
        labelFt3.setBounds(165, 289, 30, 20);
        tab1.add(labelFt3);

        JLabel labelStancCost = new JLabel("Stancköltség");
        labelStancCost.setBounds(5, 312, 80, 25);
        tab1.add(labelStancCost);
        textFStancCost = new IntegerField("0");
        textFStancCost.setBounds(90, 314, 70, 21);
        textFStancCost.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFStancCost);
        JLabel labelFt4 = new JLabel("Ft");
        labelFt4.setBounds(165, 314, 30, 20);
        tab1.add(labelFt4);

        JLabel labelOtherCost = new JLabel("Egyéb költség");
        labelOtherCost.setBounds(5, 337, 80, 25);
        tab1.add(labelOtherCost);
        textFOtherCost = new IntegerField("0");
        textFOtherCost.setBounds(90, 339, 70, 21);
        textFOtherCost.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFOtherCost);
        JLabel labelFt5 = new JLabel("Ft");
        labelFt5.setBounds(165, 339, 30, 20);
        tab1.add(labelFt5);

        JSeparator separator6 = new JSeparator();
        separator6.setBounds(5, 372, 325, 1);
        tab1.add(separator6);

        JLabel labelPackingCost = new JLabel("Kiszerelési költség");
        labelPackingCost.setBounds(5, 380, 115, 25);
        tab1.add(labelPackingCost);
        textFPackingCost = new IntegerField("6000");
        textFPackingCost.setBounds(120, 382, 70, 21);
        textFPackingCost.setHorizontalAlignment(SwingConstants.RIGHT);
        textFPackingCost.getDocument().addDocumentListener(docListener);
        tab1.add(textFPackingCost);
        JLabel labelFtH = new JLabel("Ft/óra");
        labelFtH.setBounds(195, 382, 40, 20);
        tab1.add(labelFtH);

        JLabel labelPackingTime = new JLabel("Kiszerelési idő");
        labelPackingTime.setBounds(5, 405, 115, 25);
        tab1.add(labelPackingTime);
        textFPackingTime = new IntegerField("0");
        textFPackingTime.setBounds(120, 407, 70, 21);
        textFPackingTime.setHorizontalAlignment(SwingConstants.RIGHT);
        textFPackingTime.getDocument().addDocumentListener(docListener);
        tab1.add(textFPackingTime);
        JLabel labelHour = new JLabel("óra");
        labelHour.setBounds(195, 407, 40, 20);
        tab1.add(labelHour);

        JLabel labelRollWidth = new JLabel("Tekercsszélesség");
        labelRollWidth.setBounds(5, 430, 115, 25);
        tab1.add(labelRollWidth);
        textFRollWidth = new IntegerField("0");
        textFRollWidth.setBounds(120, 432, 70, 21);
        textFRollWidth.setHorizontalAlignment(SwingConstants.RIGHT);
        textFRollWidth.getDocument().addDocumentListener(docListener);
        tab1.add(textFRollWidth);
        JLabel labelCm = new JLabel("cm");
        labelCm.setBounds(195, 432, 40, 20);
        tab1.add(labelCm);

        JLabel labelAmountPerRoll = new JLabel("Db/tekercs");
        labelAmountPerRoll.setBounds(5, 455, 115, 25);
        tab1.add(labelAmountPerRoll);
        textFAmountPerRoll = new IntegerField("0");
        textFAmountPerRoll.setBounds(120, 457, 70, 21);
        textFAmountPerRoll.setHorizontalAlignment(SwingConstants.RIGHT);
        textFAmountPerRoll.getDocument().addDocumentListener(docListener);
        tab1.add(textFAmountPerRoll);
        JLabel labelDb3 = new JLabel("db");
        labelDb3.setBounds(195, 457, 40, 20);
        tab1.add(labelDb3);

        JLabel labelPackingSumCost = new JLabel("Kiszerelés összköltsége");
        labelPackingSumCost.setBounds(5, 480, 150, 25);
        tab1.add(labelPackingSumCost);
        textFPackingSumCost = new JTextField("0");
        textFPackingSumCost.setBounds(150, 482, 70, 21);
        textFPackingSumCost.setHorizontalAlignment(SwingConstants.RIGHT);
        textFPackingSumCost.setEditable(false);
        tab1.add(textFPackingSumCost);
        JLabel labelFt6 = new JLabel("Ft");
        labelFt6.setBounds(225, 482, 40, 20);
        tab1.add(labelFt6);

        JSeparator separator2 = new JSeparator();                                           //tab1 vertical separator
        separator2.setOrientation(SwingConstants.VERTICAL);
        separator2.setBounds(348, 5, 1, 500);
        tab1.add(separator2);
        JSeparator separator3 = new JSeparator();
        separator3.setOrientation(SwingConstants.VERTICAL);
        separator3.setBounds(352, 5, 1, 500);
        tab1.add(separator3);

        JLabel labelDyePreset = new JLabel("Összeállítás betöltése");
        labelDyePreset.setBounds(370, 5, 200, 25);
        tab1.add(labelDyePreset);
        comboDyePreset = new JComboBox<DyePreset>(PPC.calcObj.getDyePresets());
        comboDyePreset.setBounds(370, 27, 225, 21);
        tab1.add(comboDyePreset);

        buttonLoadPreset = new JButton("Betölt");
        buttonLoadPreset.setBounds(600, 27, 85, 21);
        buttonLoadPreset.addActionListener(this);
        tab1.add(buttonLoadPreset);

        buttonDelPreset = new JButton("Töröl");
        buttonDelPreset.setBounds(600, 52, 85, 21);
        buttonDelPreset.addActionListener(this);
        tab1.add(buttonDelPreset);

        JLabel labelDyeAdd = new JLabel("Festéktípus hozzáadása");
        labelDyeAdd.setBounds(370, 75, 200, 25);
        tab1.add(labelDyeAdd);
        comboDyeType = new JComboBox<DyeParent>(PPC.calcObj.getAllDyeTypes());
        comboDyeType.setMaximumRowCount(10);
        comboDyeType.addPopupMenuListener(new ComboListener(comboDyeType));
        tab1.add(comboDyeType);
        comboDyeType.setBounds(370, 97, 225, 21);

        buttonAddDye = new JButton("Hozzáad");
        buttonAddDye.setBounds(600, 97, 85, 21);
        buttonAddDye.addActionListener(this);
        tab1.add(buttonAddDye);

        buttonDirectDye = new JButton("Direkt");
        buttonDirectDye.setBounds(600,122,85,21);
        buttonDirectDye.addActionListener(this);
        tab1.add(buttonDirectDye);

        buttonManageDyes = new JButton("Kezelés");
        buttonManageDyes.setBounds(600,147,85,21);
        buttonManageDyes.addActionListener(this);
        tab1.add(buttonManageDyes);

        buttonSavePreset = new JButton("Mentés");
        buttonSavePreset.setBounds(600, 180, 85, 21);
        buttonSavePreset.addActionListener(this);
        tab1.add(buttonSavePreset);

        buttonDelDye = new JButton("Töröl");
        buttonDelDye.setBounds(600, 259, 85, 21);
        buttonDelDye.addActionListener(this);
        tab1.add(buttonDelDye);

        buttonResetDye = new JButton("Reset");
        buttonResetDye.setBounds(600,284,85,21);
        buttonResetDye.addActionListener(this);
        tab1.add(buttonResetDye);

        JLabel labelDyeCylinder = new JLabel("Henger");
        labelDyeCylinder.setBounds(380, 120, 70, 25);
        tab1.add(labelDyeCylinder);
        comboDyeCylinder = new JComboBox<DyeCylinder>(PPC.calcObj.getDyeCylinders());
        comboDyeCylinder.addPopupMenuListener(new ComboListener(comboDyeCylinder));
        tab1.add(comboDyeCylinder);
        comboDyeCylinder.setBounds(455, 122, 65, 21);
        JLabel labelGM = new JLabel("g/m2");
        labelGM.setBounds(525, 122, 30, 20);
        tab1.add(labelGM);

        JLabel labelDyeCover = new JLabel("Lefedettség");
        labelDyeCover.setBounds(380, 145, 70, 25);
        tab1.add(labelDyeCover);
        textFDyeCover = new IntegerField("");
        textFDyeCover.setBounds(455, 147, 65, 21);
        textFDyeCover.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFDyeCover);
        JLabel labelPercent1 = new JLabel("%");
        labelPercent1.setBounds(525, 147, 30, 20);
        tab1.add(labelPercent1);

        listDyeType = new List();
        listDyeType.setBounds(370, 180, 225, 125);
        listDyeType.setFont(new Font(Font.MONOSPACED,Font.ROMAN_BASELINE,12));
        tab1.add(listDyeType);

        checkPreg = new JCheckBox("Prégelés", false);
        checkPreg.setBounds(370, 315, 120, 25);
        tab1.add(checkPreg);
        JLabel labelPregCover = new JLabel("Lefedettség", SwingConstants.RIGHT);
        labelPregCover.setBounds(520, 315, 75, 25);
        tab1.add(labelPregCover);
        textFPregCover = new IntegerField();
        textFPregCover.setBounds(600, 317, 65, 21);
        textFPregCover.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFPregCover);
        JLabel labelPercent2 = new JLabel("%");
        labelPercent2.setBounds(670, 317, 30, 20);
        tab1.add(labelPercent2);

        checkDombor = new JCheckBox("Dombornyomás", false);
        checkDombor.setBounds(370, 340, 120, 25);
        tab1.add(checkDombor);
        JLabel labelDomborCost = new JLabel("Költség", SwingConstants.RIGHT);
        labelDomborCost.setBounds(520, 340, 75, 25);
        tab1.add(labelDomborCost);
        textFDomborCost = new IntegerField("5000");
        textFDomborCost.setBounds(600, 342, 65, 21);
        textFDomborCost.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFDomborCost);
        JLabel labelFt1 = new JLabel("Ft");
        labelFt1.setBounds(670, 342, 30, 20);
        tab1.add(labelFt1);

        JSeparator separator4 = new JSeparator();
        separator4.setBounds(370, 372, 315, 1);
        tab1.add(separator4);

        JLabel labelTitle = new JLabel("Címke neve");
        labelTitle.setBounds(370, 380, 80, 25);
        tab1.add(labelTitle);
        textFTitle = new JTextField("");
        textFTitle.setBounds(455, 382, 230, 21);
        tab1.add(textFTitle);

        JLabel labelClient = new JLabel("Megrendelő");
        labelClient.setBounds(370, 405, 80, 25);
        tab1.add(labelClient);
        textFClient = new JTextField("");
        textFClient.setBounds(455, 407, 230, 21);
        tab1.add(textFClient);

        JLabel labelDiscount = new JLabel("Kedvezmény");
        labelDiscount.setBounds(370, 430, 80, 25);
        tab1.add(labelDiscount);
        textFDiscount = new DoubleField("0");
        textFDiscount.setBounds(455, 432, 65, 21);
        textFDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
        tab1.add(textFDiscount);
        JLabel labelPercent3 = new JLabel("%");
        labelPercent3.setBounds(525, 432, 30, 20);
        tab1.add(labelPercent3);

        JSeparator separator7 = new JSeparator();
        separator7.setBounds(370, 462, 315, 1);
        tab1.add(separator7);

        JLabel labelEuro = new JLabel("Euro árfolyam");
        labelEuro.setBounds(370, 470, 80, 25);
        tab1.add(labelEuro);
        textFEuro = new DoubleField("300");
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


        buttonCalculate = new JButton("Összesítés");
        buttonCalculate.setBounds(5,27,283,25);
        buttonCalculate.addActionListener(this);
        panel2.add(buttonCalculate);

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

        EtalonTableModel model = new EtalonTableModel(Calculator.getEtalonObj().getEtalonMatrix(), columnNames);

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

        JLabel labelEtalonSelfCost = new JLabel("Etalon önköltség:  " + Calculator.getEtalonObj().getEtalonSelfCost() + " Ft");
        labelEtalonSelfCost.setBounds(5, 445, 300, 20);
        tab3.add(labelEtalonSelfCost);
        JLabel labelEtalonMaterialPrice = new JLabel("Etalon anyagköltség:  " + Calculator.getEtalonObj().getEtalonMaterialPrice() + " Ft");
        labelEtalonMaterialPrice.setBounds(5, 470, 300, 20);
        tab3.add(labelEtalonMaterialPrice);
        JLabel labelEtalonSizeX = new JLabel("Etalon szélesség:  " + (int) Calculator.getEtalonObj().getEtalonSizeX() + " mm");
        labelEtalonSizeX.setBounds(347, 445, 300, 20);
        tab3.add(labelEtalonSizeX);
        JLabel labelEtalonSizeY = new JLabel("Etalon magasság:  " + (int) Calculator.getEtalonObj().getEtalonSizeY() + " mm");
        labelEtalonSizeY.setBounds(347, 470, 300, 20);
        tab3.add(labelEtalonSizeY);


        this.add(tabbedPane);
        //this.add(panel1);         //not needed anymore, left in just in case
        this.add(panel2);
        this.add(menu);
        this.setVisible(true);

    }

    public void reset() {

        if(comboMaterial.getItemCount() > 0) {
            comboMaterial.setSelectedIndex(0);
        }
        if(comboMachine.getItemCount() > 0) {
            comboMachine.setSelectedIndex(0);
        }
        if(comboCylinder.getItemCount() > 0) {
            comboCylinder.setSelectedIndex(0);
        }
        if(comboDyePreset.getItemCount() > 0) {
            comboDyePreset.setSelectedIndex(0);
        }
        if(comboDyeType.getItemCount() > 0) {
            comboDyeType.setSelectedIndex(0);
        }
        if(comboDyeCylinder.getItemCount() > 0) {
            comboDyeCylinder.setSelectedIndex(0);
        }

        textFAmount.setText("");
        textFWidth.setText("");
        textFHeight.setText("");
        textFSideGap.setText("6");
        textFBetweenGap.setText("3");
        textFTracks.setText("");
        textFDyeCover.setText("");
        textFPregCover.setText("");
        textFDomborCost.setText("5000");
        textFTitle.setText("");
        textFClient.setText("");
        textFDiscount.setText("0");
        textFClicheCost.setText("0");
        textFStancCost.setText("0");
        textFOtherCost.setText("0");
        textFPackingCost.setText("6000");
        textFPackingTime.setText("0");
        textFRollWidth.setText("0");
        textFAmountPerRoll.setText("0");
        textFPackingSumCost.setText("0");
        textFEuro.setText("300");

        listDyeType.removeAll();
        PPC.calcObj.resetAddedDyes();

        checkPreg.setSelected(false);
        checkDombor.setSelected(false);

    }

    public boolean checkInput() {

        boolean ready=true;

        if (textFAmount.getText().isEmpty()){
            ready=false;
            flashMyField(textFAmount,Color.RED,200);
        }
        if (textFWidth.getText().isEmpty()){
            ready=false;
            flashMyField(textFWidth,Color.RED,200);
        }
        if (textFHeight.getText().isEmpty()){
            ready=false;
            flashMyField(textFHeight,Color.RED,200);
        }
        if (textFTracks.getText().isEmpty()){
            ready=false;
            flashMyField(textFTracks,Color.RED,200);
        }
        if (textFSideGap.getText().isEmpty()){
            ready=false;
            flashMyField(textFSideGap,Color.RED,200);
        }
        if (textFBetweenGap.getText().isEmpty()){
            ready=false;
            flashMyField(textFBetweenGap,Color.RED,200);
        }
        if (textFEuro.getText().isEmpty()){
            ready=false;
            flashMyField(textFEuro,Color.RED,200);
        }

        if (ready){
            return true;
        }
        return false;

    }

    public JComboBox<Material> getComboMaterial(){
        return comboMaterial;
    }

    public JComboBox<DyeParent> getComboDyeType(){
        return comboDyeType;
    }

    public JTextField getTextFDyeCover(){
        return textFDyeCover;
    }

    public JComboBox getComboDyeCylinder(){
        return comboDyeCylinder;
    }

    public void flashMyField(final JTextField field, final Color flashColor, final int timerDelay) {
        final int totalCount = 1;
        javax.swing.Timer timer = new javax.swing.Timer(timerDelay, new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent evt) {
                if (count % 2 == 0) {
                    field.setBackground(flashColor);
                } else {
                    field.setBackground(Color.white);
                    if (count >= totalCount) {
                        ((Timer) evt.getSource()).stop();
                    }
                }
                count++;
            }
        });
        timer.start();
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
            return (double)-1;                                          //errorhandling level: MacGyver

        }
    }

}

