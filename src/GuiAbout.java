/**
 * Created by Talicska on 2014.08.05..
 */

import javax.swing.*;
import java.awt.*;

public class GuiAbout extends JFrame {

    private Dimension dimension;
    private int width = 280;
    private int height = 230;

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
        aboutText.setText("PPC - Print Price Calculator\n" +
                "Verzió: 2.0\n\n" +
                "Apostol Gergely\nSzalai Bence\nSzőllős Zsolt\nTompos Ádám\n\n"+
                "E-mail: mib@vipmail.hu\n\n2012-2014");
        aboutText.setEditable(false);
        this.add(aboutText);
        this.setVisible(true);

    }

}
