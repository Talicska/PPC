/**
 * Created by Talicska on 2014.07.31..
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

public class GuiManageDyes extends JFrame implements ActionListener {

    private Dimension dimension;
    private int width = 600;
    private int height = 400;

    private NumberFormat priceformat = NumberFormat.getNumberInstance(Locale.ENGLISH);

    private Gui mainGui;

    private JTable table;

    private JTextField textFDyeName;
    private DoubleField textFDyePrice;

    private JRadioButton radioDye;
    private JRadioButton radioMetal;
    private JRadioButton radioLakk;

    private JButton buttonAddDye;
    private JButton buttonDelDye;

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonAddDye){
            if ( ! ( textFDyeName.getText().isEmpty() || textFDyePrice.getText().isEmpty() ) ){
                DyeParent newDye;
                if (radioDye.isSelected()){
                    newDye = new Dye(textFDyeName.getText(),Double.parseDouble(textFDyePrice.getText()),null,0);
                }else if (radioMetal.isSelected()){
                    newDye = new Metal(textFDyeName.getText(),Double.parseDouble(textFDyePrice.getText()),null,0);
                }else newDye = new Lakk(textFDyeName.getText(),Double.parseDouble(textFDyePrice.getText()),null,0);

                mainGui.getComboDyeType().addItem(newDye);
                table.addNotify();
                textFDyeName.setText("");
                textFDyePrice.setText("");
                mainGui.getComboDyeType().setSelectedIndex(0);
            }else{
                if (textFDyeName.getText().isEmpty()){
                    flashMyField(textFDyeName,Color.RED,200);
                }
                if (textFDyePrice.getText().isEmpty()){
                    flashMyField(textFDyePrice,Color.RED,200);
                }
            }
        }

        else if (e.getSource() == buttonDelDye){
            int index = table.getSelectedRow();
            if ( index>=0 && index<table.getRowCount() && PPC.calcObj.getAllDyeTypes().size()>1) {
                PPC.calcObj.removeDye(index);
                table.addNotify();
                mainGui.getComboDyeType().setSelectedIndex(0);
            }
        }

    }

    public GuiManageDyes(Gui mainGui) {

        this.mainGui = mainGui;

        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);
        this.setResizable(false);
        this.setSize(dimension);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Festékek");

        JScrollPane tablePane = new JScrollPane();
        tablePane.setBounds(0,0,320,375);

        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Név");
        columnNames.addElement("Ár");

        final DyeTableModel model = new DyeTableModel(PPC.calcObj.getAllDyeTypes(),columnNames);
        table = new JTable(model){
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = model.getDataVector().get(rowIndex).getClass().toString();
                } catch (RuntimeException e1) {
                    //
                }
                return tip;
            }
        };
        table.setRowHeight(20);
        //table.getTableHeader().setBounds(0, 0, 300, 30);
        table.setBounds(0, 0, 300, 1000);
        table.getTableHeader().setFont(new Font("headerFont", Font.BOLD, 12));
        table.getColumnModel().getColumn(0).setPreferredWidth(220);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setCellRenderer(new PriceRenderer(priceformat));
        table.getColumnModel().getColumn(1).setCellEditor(new PriceEditor(priceformat));

        JLabel labelNewDye = new JLabel("Festéktípus hozzáadása");
        labelNewDye.setBounds(330,20,200,25);
        this.add(labelNewDye);

        JLabel labelDyeName = new JLabel("Név");
        labelDyeName.setBounds(340,45,30,25);
        this.add(labelDyeName);
        textFDyeName = new JTextField();
        textFDyeName.setBounds(370,47,220,21);
        textFDyeName.setColumns(20);
        textFDyeName.setDocument(new JTextFieldLimit(20));
        this.add(textFDyeName);

        JLabel labelDyePrice = new JLabel("Ár");
        labelDyePrice.setBounds(340,70,30,25);
        this.add(labelDyePrice);
        textFDyePrice = new DoubleField();
        textFDyePrice.setBounds(370,72,85,21);
        this.add(textFDyePrice);
        JLabel labelEurM = new JLabel("EUR");
        labelEurM.setBounds(460,72,45,20);
        this.add(labelEurM);

        radioDye = new JRadioButton("1");
        radioDye.setBounds(340,95,50,21);
        radioDye.setSelected(true);
        radioMetal = new JRadioButton("2");
        radioMetal.setBounds(340,116,50,21);
        radioLakk = new JRadioButton("3");
        radioLakk.setBounds(340,137,50,21);
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioDye);
        radioGroup.add(radioMetal);
        radioGroup.add(radioLakk);
        this.add(radioDye);
        this.add(radioMetal);
        this.add(radioLakk);

        buttonAddDye = new JButton("Hozzáad");
        buttonAddDye.setBounds(415,160,85,21);
        buttonAddDye.addActionListener(this);
        this.add(buttonAddDye);

        buttonDelDye = new JButton("Töröl");
        buttonDelDye.setBounds(330,330,85,21);
        buttonDelDye.addActionListener(this);
        this.add(buttonDelDye);

        tablePane.getViewport().add(table);
        this.add(tablePane);
        this.setVisible(true);

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
            this.formatter.setGroupingUsed(false);
            this.formatter.setMinimumFractionDigits(3);
            this.formatter.setMaximumFractionDigits(3);
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
            this.formatter.setGroupingUsed(false);
            this.formatter.setMinimumFractionDigits(3);
            this.formatter.setMaximumFractionDigits(3);
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
}
