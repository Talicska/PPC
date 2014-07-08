/**
 * Created by Talicska on 2014.07.08..
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Gui extends JFrame {
    private JButton but1;
    private JButton jcomp2;
    private JButton jcomp3;
    private JButton jcomp4;
    private JMenuBar jcomp5;
    private JLabel jcomp6;
    private JTextField jcomp7;

    public Gui() {
        //construct preComponents
        /*JMenu fileMenu = new JMenu ("File");
        JMenuItem printItem = new JMenuItem ("Print");
        fileMenu.add (printItem);
        JMenuItem exitItem = new JMenuItem ("Exit");
        fileMenu.add (exitItem);
        JMenu helpMenu = new JMenu ("Help");
        JMenuItem contentsItem = new JMenuItem ("Contents");
        helpMenu.add (contentsItem);
        JMenuItem aboutItem = new JMenuItem ("About");
        helpMenu.add (aboutItem);*/

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set grid layout for the frame
        this.getContentPane().setLayout(new BorderLayout(1,1));

        //
        JPanel p1 =new JPanel();
        JPanel p2=new JPanel();
        p1.setSize(600,600);
        p1.setBackground(Color.red);
        p2.setSize(200,600);
        p2.setBackground(Color.yellow);




        //

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);


        JButton b1=new JButton("asdas");
        //b1.set
        p2.add(b1);
        //p1.add(tabbedPane);

        JComponent panel1 = new JPanel();
        JButton button1 = new JButton("OK");
        panel1.add(button1);

        JComponent panel2 = new JPanel();
        tabbedPane.addTab("Tab1", panel1);
        tabbedPane.addTab("Tab2", panel2);
        tabbedPane.setSize(600,600);
        this.add(tabbedPane);
        this.add(p1);
        this.add(p2);
        p2.add(b1);

        /*JComponent panel1 = new JComponent()
        tabbedPane.addTab("Tab 1", panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = new JPanel();
        tabbedPane.addTab("Tab 2", panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);*/

        /*JComponent panel3 = makeTextPanel("Panel #3");
        tabbedPane.addTab("Tab 3", icon, panel3,
                "Still does nothing");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JComponent panel4 = makeTextPanel(
                "Panel #4 (has a preferred size of 410 x 50).");
        panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Tab 4", icon, panel4,
                "Does nothing at all");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);*/

        //construct components
       /* but1 = new JButton ("Button 1");
        jcomp2 = new JButton ("Button 2");
        jcomp3 = new JButton ("Button 3");
        jcomp4 = new JButton ("newButton");
        jcomp5 = new JMenuBar();
        jcomp5.add (fileMenu);
        jcomp5.add (helpMenu);
        jcomp6 = new JLabel ("newLabel");
        jcomp7 = new JTextField (5);

        //adjust size and set layout
        setPreferredSize (new Dimension (784, 455));
        setLayout (null);

        //add components
        add (but1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);
        add (jcomp7);

        //set component bounds (only needed by Absolute Positioning)
        but1.setBounds (110, 35, 100, 20);
        jcomp2.setBounds (110, 60, 100, 20);
        jcomp3.setBounds (255, 50, 140, 20);
        jcomp4.setBounds (40, 135, 100, 25);
        jcomp5.setBounds (0, 0, 785, 30);
        jcomp6.setBounds (25, 200, 100, 25);
        jcomp7.setBounds (125, 200, 100, 25);*/
    }

    protected Component makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}

