package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.model.Area;
import net.alexhyisen.zeta.model.Position;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Alex on 2016/9/27.
 * BoardPanel is where to put the information of the matrix.
 */
public class BoardPanel extends JPanel {
    MainWindow parent;
    Node[] nodes=new Node[16];

    public BoardPanel(MainWindow parent) {
        this.parent = parent;
        setLayout(new GridLayout(4,4));
        for(int k=0;k!=16;k++){
            nodes[k]=new Node();
            add(nodes[k]);
        }
        refresh();
    }

    public void refresh(){
        for(int k=0;k!=16;k++){
            Position p=new Position(new int[]{k%4,k/4},new int[]{3,3});
            nodes[k].setMsg(p.toString()+parent.getData().get(p));
            nodes[k].refresh();
        }
    }
}
