package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.utility.SwingWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 2016/9/27.
 * InputPanel is where to acquire the input message.
 */
public class InputPanel extends JPanel{
    private JTextArea inputTextArea[]=new JTextArea[]{new JTextArea(1,20),new JTextArea(1,20)};
    private JButton submitButton=new JButton("GO");
    private MainWindow parent;

    public InputPanel(MainWindow parent) {
        this.parent = parent;
        init();
    }

    private void init(){
        setLayout(new FlowLayout());
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Clicked ");
                parent.input(new String[]{inputTextArea[0].getText(),inputTextArea[1].getText()});
            }
        });
        add(new JLabel("Positives"));
        add(inputTextArea[0]);
        add(new JLabel("Don'tcare"));
        add(inputTextArea[1]);
        add(submitButton);
    }
}
