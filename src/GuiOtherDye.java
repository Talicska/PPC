/**
 * Created by Talicska on 2014.07.16..
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class GuiOtherDye extends JDialog implements ActionListener {

    private Dimension dimension;
    private int width = 600;
    private int height = 400;

    private NumberFormat priceformat = NumberFormat.getNumberInstance(Locale.ENGLISH);

    private Gui mainGui;

    private JTable table;

    private JTextField textFName;
    private DoubleField textFPrice;

    private JButton buttonAdd;

    public GuiOtherDye(Gui mainGui){

        this.mainGui = mainGui;

        this.setModal (true);
        this.setAlwaysOnTop (true);
        this.setModalityType (ModalityType.APPLICATION_MODAL);
        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);

        //this.setResizable(false);
        this.setPreferredSize(dimension);
        this.setLocation(400, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("asd");

        JLabel labelName = new JLabel("adasdasd");
        labelName.setBounds(0,0,100,25);
        this.add(labelName);
        this.pack();
        this.setVisible(true);

    }





    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
