/**
 * Created by Talicska on 2014.07.12..
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiDyePreset extends JFrame implements ActionListener {

    private Dimension dimension;
    private int width = 600;
    private int height = 400;

    public void actionPerformed(ActionEvent e) {

    }

    public GuiDyePreset() {
        this.getContentPane().setLayout(null);
        dimension = new Dimension(width, height);
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(dimension);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("GuiDyePreset");

    }
}
