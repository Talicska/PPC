import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Talicska on 2014.07.13..
 */
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
        this.setTitle("GuiMaterials");

        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Név");
        columnNames.addElement("Ár");

        MaterialTableModel model = new MaterialTableModel(PPC.calcObj.getMaterials(),columnNames);
        JTable table = new JTable(model);


        table.getTableHeader().setBounds(0, 0, 600, 30);
        table.setBounds(0, 30, 600, 400);
        table.setRowHeight(20);

        this.getContentPane().add(table.getTableHeader());
        this.getContentPane().add(table);

    }
}
