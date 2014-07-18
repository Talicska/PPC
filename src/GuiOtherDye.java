/**
 * Created by Talicska on 2014.07.16..
 */

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
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

    private JTextField textFName;
    private DoubleField textFPrice;

    private JButton buttonAdd;

    public GuiOtherDye(Gui mainGui){

        this.mainGui = mainGui;

        this.setModal(true);
        this.setAlwaysOnTop(true);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);

        this.setResizable(false);
        this.setPreferredSize(dimension);
        this.setLocation(400, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Direkt szín felvitele");

        JLabel labelName = new JLabel("Név");
        labelName.setBounds(10,0,30,25);
        this.add(labelName);
        textFName = new JTextField("");
        textFName.setBounds(40,2,200,21);
        textFName.setDocument(new JTextFieldLimit(20));
        this.add(textFName);

        JLabel labelPrice = new JLabel("Ár");
        labelPrice.setBounds(10,25,30,25);
        this.add(labelPrice);
        textFPrice = new DoubleField("");
        textFPrice.setBounds(40,27,100,21);
        this.add(textFPrice);
        JLabel labelFt = new JLabel("Ft");
        labelFt.setBounds(145,27,30,20);
        this.add(labelFt);

        buttonAdd = new JButton("OK");
        buttonAdd.setBounds(10,200,85,21);
        buttonAdd.addActionListener(this);
        this.add(buttonAdd);







        this.pack();
        this.setVisible(true);

    }





    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class JTextFieldLimit extends PlainDocument {
        private int limit;
        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        JTextFieldLimit(int limit, boolean upper) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

}


