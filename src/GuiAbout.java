/**
 * Created by Talicska on 2014.08.05..
 */

import javax.swing.*;
import java.awt.*;

public class GuiAbout extends JFrame {

    private Dimension dimension;
    private int width = 300;
    private int height = 300;

    public GuiAbout(){

        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);
        this.setResizable(false);
        this.setSize(dimension);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("A PPC névjegye");

        JTextArea aboutText = new JTextArea();
        aboutText.setBackground(null);
        aboutText.setBounds(25, 15, 183, 270);
        aboutText.setText("PPC - Print Price Calculator\n\n" +
                "Tompos Ádám\nSzalai Bence\nApostol Gergely\n\n"+
                "E-mail: raszal90@gmail.com\n\n2012");
        aboutText.setEditable(false);
        this.add(aboutText);

        this.setVisible(true);

    }

}
