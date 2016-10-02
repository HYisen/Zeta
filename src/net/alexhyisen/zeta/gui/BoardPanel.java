package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.model.Area;
import net.alexhyisen.zeta.model.Position;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

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
        List<Set<BorderLine>> borders=getAreaBorders();
        for(int k=0;k!=16;k++){
            Position p=new Position(new int[]{k%4,k/4},new int[]{3,3});
            nodes[k].setMsg(p.toString()+parent.getData().get(p));
            nodes[k].setValue(parent.getData().get(p));
            nodes[k].setBorders(borders.get(k));
            nodes[k].refresh();
        }
    }

    public void setSelected(int index,boolean value){
        nodes[index].setSelected(value);
    }

    public void resetSelected(){
        for(Node node:nodes){
            node.setSelected(false);
        }
    }

    private java.util.List<Set<BorderLine>> getAreaBorders(){
        java.util.List<Set<BorderLine>> rtn=new ArrayList<>(16);
        for(int k=0;k!=16;k++){
            rtn.add(new HashSet<>());
        }

        int count=0;
        for(Area a:parent.getData().simplify()){
            int r=80-10*(++count);
            Set<Position> members=a.getMembers();
            for(Position p:members){
                Set<BorderLine> target=rtn.get(p.getValue(0)+4*p.getValue(1));
                BitSet link=new BitSet(4);
                link.set(0,members.contains(p.seek(0,1)));//East
                link.set(1,members.contains(p.seek(1,1)));//South
                link.set(2,members.contains(p.seek(0,-1)));//West
                link.set(3,members.contains(p.seek(1,-1)));//North

                /*
                String str="";
                for(int k=0;k!=4;k++){
                    if(link.get(k)){
                        str+="1";
                    }else{
                        str+="0";
                    }
                }
                System.out.println(p+" link "+str);
                */

                switch (link.cardinality()){
                    case 0:
                        target.add(new BorderLine(new BorderType[]{BorderType.QUADRANT,
                        BorderType.QUADRANT,BorderType.QUADRANT,BorderType.QUADRANT},r));
                        break;
                    case 1:
                        switch (link.nextSetBit(0)){
                            case 0:
                                target.add(new BorderLine(new BorderType[]{BorderType.HORIZONTAL,
                                        BorderType.HORIZONTAL,BorderType.QUADRANT,BorderType.QUADRANT},r));
                                break;
                            case 1:
                                target.add(new BorderLine(new BorderType[]{BorderType.QUADRANT,
                                        BorderType.VERTICAL,BorderType.VERTICAL,BorderType.QUADRANT},r));
                                break;
                            case 2:
                                target.add(new BorderLine(new BorderType[]{BorderType.QUADRANT,
                                        BorderType.QUADRANT,BorderType.HORIZONTAL,BorderType.HORIZONTAL},r));
                                break;
                            case 3:
                                target.add(new BorderLine(new BorderType[]{BorderType.VERTICAL,
                                        BorderType.QUADRANT,BorderType.QUADRANT,BorderType.VERTICAL},r));
                                break;
                        }
                        break;
                    case 2:
                        if(link.get(0)){
                            if(link.get(1)){
                                target.add(new BorderLine(new BorderType[]{BorderType.HORIZONTAL,
                                        BorderType.EMPTY,BorderType.VERTICAL,BorderType.QUADRANT},r));
                            }else{
                                if(link.get(3)){
                                    target.add(new BorderLine(new BorderType[]{BorderType.EMPTY,
                                            BorderType.HORIZONTAL,BorderType.QUADRANT,BorderType.VERTICAL},r));
                                }
                                else{
                                    target.add(new BorderLine(new BorderType[]{BorderType.HORIZONTAL,
                                            BorderType.HORIZONTAL,BorderType.HORIZONTAL,BorderType.HORIZONTAL},r));
                                }
                            }
                        }else if(!link.get(2)){
                            target.add(new BorderLine(new BorderType[]{BorderType.VERTICAL,
                                    BorderType.VERTICAL,BorderType.VERTICAL,BorderType.VERTICAL},r));
                        }else{
                            if (link.get(1)){
                                target.add(new BorderLine(new BorderType[]{BorderType.QUADRANT,
                                        BorderType.VERTICAL,BorderType.EMPTY,BorderType.HORIZONTAL},r));
                            }else{
                                target.add(new BorderLine(new BorderType[]{BorderType.VERTICAL,
                                       BorderType.QUADRANT,BorderType.HORIZONTAL,BorderType.EMPTY},r));
                            }
                        }
                        break;
                    case 3:
                        switch (link.nextClearBit(0)){
                            case 0:
                                target.add(new BorderLine(new BorderType[]{BorderType.VERTICAL,
                                        BorderType.VERTICAL,BorderType.EMPTY,BorderType.EMPTY},r));
                                break;
                            case 1:
                                target.add(new BorderLine(new BorderType[]{BorderType.EMPTY,
                                        BorderType.HORIZONTAL,BorderType.HORIZONTAL,BorderType.EMPTY},r));
                                break;
                            case 2:
                                target.add(new BorderLine(new BorderType[]{BorderType.EMPTY,
                                        BorderType.EMPTY, BorderType.VERTICAL, BorderType.VERTICAL},r));
                                break;
                            case 3:
                                target.add(new BorderLine(new BorderType[]{BorderType.HORIZONTAL,
                                        BorderType.EMPTY,BorderType.EMPTY,BorderType.HORIZONTAL},r));
                                break;
                        }
                        break;
                    case 4:
                        break;
                }
                //I adopt that I should have used one switch(int type=link.toInt) instead of such ugly switches.
            }
        }
        return rtn;
    }//to avoid new HashSet<BorderLine>[16], use List
}
