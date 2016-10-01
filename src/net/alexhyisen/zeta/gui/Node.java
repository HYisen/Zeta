package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.model.State;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * Created by Alex on 2016/9/29.
 * The rebirth of Node! Now Node is just a graph item to represent a 1*1 Area.
 */
public class Node extends JPanel {
    private String msg;
    private JLabel label=new JLabel("default");
    private Boolean isSelected=false;
    private State value=State.MULTIPLE;
    private Set<BorderLine> borders;

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
        drawBorders(g);
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

    public void setBorders(Set<BorderLine> borders) {
        this.borders = borders;
    }

    private void drawBorders(Graphics g){
        for(BorderLine b:borders){
            int[] values=new int[]{
                    (100-b.radius)*getWidth()/100,
                    (100-b.radius)*getHeight()/100,
                    b.radius*getWidth()/100,
                    b.radius*getHeight()/100};;
            for(int k=0;k!=4;++k){
                switch (b.types[k]) {
                    case QUADRANT:
                        g.drawArc(values[0],values[1],values[2],values[3],k*90,(k+1)*90);
                        break;
                    case HORIZONTAL:
                        switch (k){
                            case 0:
                                g.drawLine(getWidth()/2,values[1],getWidth(),values[2]);
                                break;
                            case 1:
                                g.drawLine(getWidth()/2,getHeight()-values[1],getWidth(),getHeight()-values[1]);
                                break;
                            case 2:
                                g.drawLine(0,getHeight()-values[1],getWidth()/2,getHeight()-values[1]);
                                break;
                            case 3:
                                g.drawLine(0,values[1],getWidth()/2,values[1]);
                                break;
                        }//as far as I am concerned, it's better than separately define values.
                        break;
                    case VERTICAL:
                        switch (k){
                            case 0:
                                g.drawLine(getWidth()-values[0],0,getWidth()-values[0],getHeight()/2);
                                break;
                            case 1:
                                g.drawLine(getWidth()-values[0],getHeight()/2,getWidth()-values[0],getHeight());
                                break;
                            case 2:
                                g.drawLine(values[0],getHeight()/2,values[0],getHeight());
                                break;
                            case 3:
                                g.drawLine(values[0],0,values[0],getHeight()/2);
                                break;
                        }
                        break;
                }
            }
        }
    }

}

