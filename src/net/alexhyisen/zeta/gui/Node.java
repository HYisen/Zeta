package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.model.Area;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 2016/9/29.
 * The rebirth of Node! Now Node is just a graph item to represent a 1*1 Area.
 */
public class Node extends JPanel {
    private String msg;
    private JLabel label=new JLabel("default");

    public Node() {
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        //label.setBorder(BorderFactory.createMatteBorder(1,1,2,2,Color.BLACK));
        add(BorderLayout.CENTER,label);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void refresh(){
        label.setText(msg);
        repaint();
    }
}
