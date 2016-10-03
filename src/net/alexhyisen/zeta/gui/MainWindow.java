package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.model.Area;
import net.alexhyisen.zeta.model.Data;
import net.alexhyisen.zeta.model.Elucidator;
import net.alexhyisen.zeta.utility.SwingWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * Created by Alex on 2016/9/27.
 * It's just what its class name means.
 */
public class MainWindow extends JFrame{
    private Data data=Data.generateRandom(4,4);
    private Set<Area> areas=data.simplify();
    private Area[] result=areas.toArray(new Area[areas.size()]);

    InputPanel ip=new InputPanel(this);
    ResultPanel rp=new ResultPanel(this);
    BoardPanel bp=new BoardPanel(this);

    public MainWindow() throws HeadlessException {
        add(BorderLayout.NORTH,ip);
        add(BorderLayout.EAST,rp);
        add(BorderLayout.CENTER,bp);
    }

    public void input(String[] orig){
        //System.out.println("get input "+orig);
        if(orig[1].isEmpty()){
            data= Data.generateSimple(Elucidator.illustrate(orig[0],4));
        }else{
            data=Data.generateNormal(Elucidator.illustrate(orig[0],4),
                    Elucidator.illustrate(orig[1],4));
        }
        areas=data.simplify();
        result=areas.toArray(new Area[areas.size()]);
        rp.refresh();
        //System.out.println("dealt"+result[0]);
        bp.refresh();
    }

    public Data getData() {
        return data;
    }

    public Area[] getResult() {
        return result;
    }

    public static void main(String[] args){
        SwingWindow.run(new MainWindow(),800,600);
    }
}
