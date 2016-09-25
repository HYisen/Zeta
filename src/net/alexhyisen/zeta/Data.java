package net.alexhyisen.zeta;

import java.util.*;

/**
 * Created by Alex on 2016/9/25.
 */
public class Data {
    private Map<Position,State> data=new HashMap<>();
    private int[] size;

    public Data(Map<Position, State> data, int[] size) {
        this.data = data;
        this.size = size;
    }

    public State get(Position key){
        return data.get(key);
    }

    public static Data generateRandom(int row,int col){
        Map<Position,State> data=new HashMap<>();
        int[] limit={row-1,col-1};
        Random rand=new Random();
        for(int i=0;i!=row;++i){
            for(int j=0;j!=col;++j){
                int[] value={i,j};
                data.put(new Position(value,limit),State.next(1,4,0));
            }
        }
        limit[0]++;
        limit[1]++;
        return new Data(data,limit);
    }

    public static Data generateSimple(int[][] orig){
        Map<Position,State> data=new HashMap<>();
        int row=orig.length;
        int col=orig[0].length;
        int[] limit={row-1,col-1};
        for(int i=0;i!=row;++i){
            for(int j=0;j!=col;++j){
                int[] value={i,j};
                State state=State.DONTCARE;
                if(orig[i][j]==0){
                    state=State.NEGATIVE;
                }else if(orig[i][j]==1){
                    state=State.POSITIVE;
                }
                data.put(new Position(value,limit),state);
            }
        }
        limit[0]++;
        limit[1]++;
        return new Data(data,limit);
    }

    public void print(){
        if(size.length!=2){
            System.out.println("Not 2-Dimension Data");
        }else {
            int[] limit={size[0]-1,size[1]-1};
            for(int i=0;i!=size[0];++i){
                String str="";
                for (int j=0;j!=size[1];++j){
                    int[] value={i,j};
                    Position p=new Position(value,limit);
                    str+=p+"="+get(p)+"\t";
                }
                System.out.println(str);
            }
        }
    }

    public int[] getSize() {
        return size;
    }

    public Area[] search(){
        Set<Area> rtn=new HashSet<>();
        Area whole=new Area(this,new Position(new int[]{0,0},new int[]{size[0]-1,size[1]-1}),size);
        for(Position p:whole.getMembers()){
            Area a=new Area(this,p);
            if(a.getState().equals(State.POSITIVE)){
                for(Area unit:a.expandMost()){
                    rtn.add(unit);
                }
            }
        }
        return rtn.toArray(new Area[rtn.size()]);
    }

    public static void main(String[] args){
        //Data data=Data.generateSimple(new int[][]{{1,0},{1,1}});
        Data data=Data.generateRandom(4,4);
        data.print();
        for(Area one:data.search()){
            System.out.println(one);
        }
    }
}
