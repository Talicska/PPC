/**
 * Created by Talicska on 2014.07.13..
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GuiMaterials extends JFrame implements ActionListener {

    private Dimension dimension;
    private int width = 600;
    private int height = 400;

    private JTextField textFMatName;
    private JTextField textFMatPrice;

    private JButton buttonAddMat;
    private JButton buttonDelMat;

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonAddMat){

            if( ! ( textFMatName.getText().isEmpty() || textFMatPrice.getText().isEmpty())){
                String name = textFMatName.getText();
                double price = Double.valueOf(textFMatPrice.getText());
                PPC.calcObj.addMaterial(new Material(name,price));
                textFMatName.setText("");
                textFMatPrice.setText("");
            }else {
                if(textFMatName.getText().isEmpty()){
                    flashMyField(textFMatName,Color.RED,200);
                }if(textFMatPrice.getText().isEmpty()){
                    flashMyField(textFMatPrice,Color.RED,200);
                }
            }
        }

    }

    public GuiMaterials() {
        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(dimension);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Alapanyagok");

        JScrollPane tablePane = new JScrollPane();
        tablePane.setBounds(0,0,320,375);

        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Név");
        columnNames.addElement("Ár");

        MaterialTableModel model = new MaterialTableModel(PPC.calcObj.getMaterials(),columnNames);
        JTable table = new JTable(model);

        table.setRowHeight(20);
        //table.getTableHeader().setBounds(0, 0, 300, 30);
        table.setBounds(0, 0, 300, 1000);
        table.getTableHeader().setFont(new Font("headerFont", Font.BOLD, 12));
        table.getColumnModel().getColumn(0).setPreferredWidth(220);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);

        JLabel labelNewMat = new JLabel("Alapanyag hozzáadása");
        labelNewMat.setBounds(330,0,200,25);
        this.add(labelNewMat);

        JLabel labelMatName = new JLabel("Név");
        labelMatName.setBounds(340,25,30,25);
        this.add(labelMatName);
        textFMatName = new JTextField();
        textFMatName.setBounds(370,27,220,21);
        this.add(textFMatName);

        JLabel labelMatPrice = new JLabel("Ár");
        labelMatPrice.setBounds(340,50,30,25);
        this.add(labelMatPrice);
        textFMatPrice = new JTextField();
        textFMatPrice.setBounds(370,52,85,21);
        this.add(textFMatPrice);
        JLabel labelEurM = new JLabel("EUR");
        labelEurM.setBounds(460,52,45,20);
        this.add(labelEurM);

        buttonAddMat = new JButton("Hozzáad");
        buttonAddMat.setBounds(505,52,84,21);
        buttonAddMat.addActionListener(this);
        this.add(buttonAddMat);

        buttonDelMat = new JButton("Töröl");
        buttonDelMat.setBounds(330,300,85,21);
        buttonDelMat.addActionListener(this);
        this.add(buttonDelMat);





        tablePane.getViewport().add(table);
        this.add(tablePane);




    }


    public void flashMyField(final JTextField field, final Color flashColor, final int timerDelay) {
        final int totalCount = 1;
        javax.swing.Timer timer = new javax.swing.Timer(timerDelay, new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent evt) {
                if (count % 2 == 0) {
                    field.setBackground(flashColor);
                } else {
                    field.setBackground(null);
                    if (count >= totalCount) {
                        ((Timer) evt.getSource()).stop();
                    }
                }
                count++;
            }
        });
        timer.start();
    }
}

