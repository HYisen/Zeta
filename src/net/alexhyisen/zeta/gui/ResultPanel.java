package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.model.Area;
import net.alexhyisen.zeta.model.Position;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

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
        list=new JList<>(AreaAdapter.adapt(parent.getResult()));
        add(list);
        list.addListSelectionListener(lsl);
        validate();
        //System.out.println("add new list");
    }

    public void report(){
        java.util.List<Area> selected=list.getSelectedValuesList();
        parent.bp.resetSelected();
        for(Area a:selected){
            //System.out.println("selected "+a);
            for(Position p:a.getAllMembers()){
                parent.bp.setSelected(p.getValue(0)+4*p.getValue(1),true);
            }
        }
        //System.out.println("end");
    }
}
