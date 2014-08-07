/**
 * Created by Talicska on 2014.07.16..
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GuiDirectDye extends JDialog implements ActionListener {

    private Dimension dimension;
    private int width = 251;
    private int height = 175;

    private Gui mainGui;
    private JTextField textFName;
    private DoubleField textFPrice;
    private JButton buttonAdd;
    private JRadioButton radioDye;
    private JRadioButton radioLakk;
    private JRadioButton radioMetal;
    private JRadioButton radioFluo;

    private DyeParent newDye;

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonAdd){
            if ( ! ( textFName.getText().isEmpty() || textFPrice.getText().isEmpty() ) ){
                if (radioDye.isSelected()){
                    newDye = new Dye(textFName.getText(),Double.parseDouble(textFPrice.getText()),null,0);
                }else if (radioMetal.isSelected()){
                    newDye = new Metal(textFName.getText(),Double.parseDouble(textFPrice.getText()),null,0);
                }else if (radioLakk.isSelected()){
                    newDye = new Lakk(textFName.getText(),Double.parseDouble(textFPrice.getText()),null,0);
                }else newDye = new Fluo(textFName.getText(),Double.parseDouble(textFPrice.getText()),null,0);

                newDye.setDyeCylinder((DyeCylinder)(mainGui.getComboDyeCylinder().getSelectedItem()));
                newDye.setCover(Integer.parseInt(mainGui.getTextFDyeCover().getText()));
                this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
            }else{
                if (textFName.getText().isEmpty()){
                    flashMyField(textFName,Color.RED,200);
                }
                if (textFPrice.getText().isEmpty()){
                    flashMyField(textFPrice,Color.RED,200);
                }
            }
        }

    }

    public GuiDirectDye(Gui mainGui){

        this.mainGui = mainGui;

        this.setModal(true);
        this.setAlwaysOnTop(true);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);

        this.setResizable(false);
        this.setPreferredSize(dimension);
        this.setLocation(MouseInfo.getPointerInfo().getLocation());
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

        radioDye = new JRadioButton("Process/direkt");
        radioDye.setBounds(10,55,160,21);
        radioDye.setSelected(true);
        radioLakk = new JRadioButton("Lakk");
        radioLakk.setBounds(10,76,100,21);
        radioMetal = new JRadioButton("Metál");
        radioMetal.setBounds(10,97,100,21);
        radioFluo = new JRadioButton("Fluo");
        radioFluo.setBounds(10,118,100,21);
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioDye);
        radioGroup.add(radioLakk);
        radioGroup.add(radioMetal);
        radioGroup.add(radioFluo);
        this.add(radioDye);
        this.add(radioLakk);
        this.add(radioMetal);
        this.add(radioFluo);

        buttonAdd = new JButton("OK");
        buttonAdd.setBounds(155,120,85,21);
        buttonAdd.addActionListener(this);
        this.add(buttonAdd);

        this.pack();
        this.setVisible(true);

    }

    public DyeParent getNewDye(){
        if (newDye != null){
            return newDye;
        }
        return null;
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

}


