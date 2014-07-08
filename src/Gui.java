/**
 * Created by Talicska on 2014.07.08..
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Gui extends JFrame {


    public Gui() {



        // set grid layout for the frame
        this.getContentPane().setLayout(null);

        //
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        p1.setSize(600, 600);
        p1.setBackground(Color.red);
        p2.setLayout(new GridBagLayout());
        p2.setSize(200, 600);
        p2.setBackground(Color.yellow);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);


        JButton b1 = new JButton("asdas");

        b1.setBounds(20, 20, 500, 50);
        //p2.add(b1);

        //p1.add(tabbedPane);

        JComponent panel1 = new JPanel();
        JButton button1 = new JButton("OK");
        panel1.add(button1);

        JComponent panel2 = new JPanel();
        tabbedPane.addTab("Tab1", panel1);
        tabbedPane.addTab("Tab2", panel2);
        tabbedPane.setSize(600, 600);
        //this.add(tabbedPane);
        this.add(p1);
        this.add(p2);
        p2.add(b1);
    }

}

