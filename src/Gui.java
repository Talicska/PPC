/**
 * Created by Talicska on 2014.07.08..
 */

import java.awt.*;
import javax.swing.*;

public class Gui extends JFrame {       // ...ne baszd ossze a kodot!


    public Gui() {

        this.getContentPane().setLayout(null);

        JPanel panel1 = new JPanel();   //left panel
        panel1.setBounds(0,0,600, 600);
        panel1.setBackground(Color.red);

        JPanel panel2 = new JPanel();   //right panel
        panel2.setBounds(600,0,200, 600);
        panel2.setBackground(Color.yellow);

        JButton b1 = new JButton("b1");
        JButton b2 = new JButton("b2");

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        JComponent tab1 = new JPanel();
        JComponent tab2 = new JPanel();
        tabbedPane.addTab("Tab1", tab1);
        tabbedPane.addTab("Tab2", tab2);
        tabbedPane.setSize(600, 600);

        tab1.add(b1);
        panel2.add(b2);

        this.add(tabbedPane);
        this.add(panel1);
        this.add(panel2);

    }

}

