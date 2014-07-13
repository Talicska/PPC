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

    public void actionPerformed(ActionEvent e) {

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

        tablePane.getViewport().add(table);
        this.add(tablePane);


    }
}
