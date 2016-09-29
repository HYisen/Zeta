package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.model.Area;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Alex on 2016/9/27.
 * ResultPanel is where to show the possible Area
 */
public class ResultPanel extends JPanel{
    private JList<Area> list;
    private MainWindow parent;
    private ListSelectionListener lsl=new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            report();
        }
    };

    public ResultPanel(MainWindow parent) {
        this.parent = parent;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        refresh();
    }

    public void refresh(){
        if(list!=null){
            remove(list);
            repaint();
            //System.out.println("remove list");
        }
        list=new JList<>(parent.getResult());
        add(list);
        list.addListSelectionListener(lsl);
        validate();
        //System.out.println("add new list");
    }

    public void report(){
        java.util.List<Area> selected=list.getSelectedValuesList();
        for(Area a:selected){
            System.out.println("selected "+a);
        }
        System.out.println("end");
    }
}
