/**
 * Created by Talicska on 2014.07.31..
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GuiDyePresetName extends JDialog implements ActionListener {

    private Dimension dimension;
    private int width = 251;
    private int height = 85;

    private Gui mainGui;
    private JTextField textFName;
    private JButton buttonSave;

    private String name;


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonSave){
            if ( ! textFName.getText().isEmpty() ) {
                name = textFName.getText();
                this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
            }else{
                flashMyField(textFName,Color.RED,200);
            }
        }

    }

    GuiDyePresetName(Gui mainGui){
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
        this.setTitle("Színmodell neve");

        textFName = new JTextField("");
        textFName.setBounds(7,5,233,21);
        textFName.setDocument(new JTextFieldLimit(24));
        this.add(textFName);

        buttonSave = new JButton("OK");
        buttonSave.setBounds(81,31,85,21);
        buttonSave.addActionListener(this);
        this.add(buttonSave);

        name = "";

        this.pack();
        this.setVisible(true);
    }

    public String getPresetName() {
        return name;
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
