package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.model.Area;
import net.alexhyisen.zeta.model.State;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 2016/9/29.
 * The rebirth of Node! Now Node is just a graph item to represent a 1*1 Area.
 */
public class Node extends JPanel {
    private String msg;
    private JLabel label=new JLabel("default");
    private Boolean isSelected=false;
    private State value=State.MULTIPLE;


    public Node() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(label);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setValue(State value) {
        this.value = value;
    }

    public void refresh(){
        label.setText(msg);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isSelected){
            Color c=g.getColor();
            g.setColor(Color.GRAY);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(c);
        }
        switch (value) {
            case POSITIVE:
                draw(1,g);
                break;
            case NEGATIVE:
                draw(0,g);
                break;
            case DONTCARE:
                draw(0,g);
                draw(1,g);
                break;
            case MULTIPLE:
                break;
        }
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
        repaint();
    }

    private void draw(int style,Graphics g){
        if(style==0){
            g.drawOval(getWidth()/3,getHeight()/3,getWidth()/3,getHeight()/3);
        }else if(style==1){
            g.drawLine(getWidth()/2,getHeight()/4,getWidth()/2,3*getWidth()/4);
        }
    }
}
