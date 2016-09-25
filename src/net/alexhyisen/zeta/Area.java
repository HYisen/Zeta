package net.alexhyisen.zeta;

import java.util.*;

/**
 * Created by Alex on 2016/9/23.
 * Area is something that all of the Node it included have a same state and expands when simplifying.
 * node is the left-up position of all the members
 */
class Area {
    private Data data;
    private Position node;
    private int[] size;
    private State state;

    public Area(Data data,Position node) {
        this.node = node;
        this.data = data;
        this.node = node;
        size=new int[node.getDimension()];
        Arrays.fill(size,1);
        state=data.get(node);
    }

    private void setState(){
        Function<Position,State> func=new Function<Position, State>() {
            private State state=State.DONTCARE;

            @Override
            public void process(Position orig) {
                /*
                System.out.println("\t\tcompare "+orig+" to "+state+" M="+
                        ((!(state.equals(orig)))&&(!(orig.equals(State.DONTCARE)))));
                */
                //how I wish I could use != and == to Enums
                if(state.equals(State.DONTCARE)){
                    state=data.get(orig);
                } else if((!(state.equals(data.get(orig))))&&(!((data.get(orig)).equals(State.DONTCARE)))){
                    state=State.MULTIPLE;
                }
            }

            @Override
            public State getData() {
                return state;
            }
        };
        traverse(size, new LinkedList<>(),func);
        state=func.getData();
    }

    interface Function<SrcType,RtnType>{
        void process(SrcType orig);
        RtnType getData();
    }

    //the problem is, that it's the pointer rather than a copy of log transfers
    private void traverse(int[] size, LinkedList<Integer> log, Function<Position,?> func){
        if(size.length==1){
            for(int k=0;k<size[0];++k){
                //nextLog should be initiated every time as its content will be destroyed in every loop
                LinkedList<Integer> nextLog=new LinkedList<>(log);
                nextLog.push(k);
                Position p=node.getCopy();
                int axis=0;
                while(!nextLog.isEmpty()){
                    int step=nextLog.pop();
                    //System.out.print(" "+axis+"#"+step);
                    p.move(axis,step);
                    axis++;
                }
                //System.out.println(" process "+p);
                func.process(p);
            }
        }else{
            for(int k=0;k<size[size.length-1];++k){
                LinkedList<Integer> nextLog=new LinkedList<>(log);
                /*
                String str="list@";
                for(Integer i:nextLog){
                    str+=i;
                }
                System.out.println("d"+size.length+" push "+k+" to "+str+"="+nextLog.size());
                */
                nextLog.push(k);
                traverse(Arrays.copyOf(size,size.length-1),nextLog,func);
            }
        }
    }

    public Area(Data data, Position node, int[] size) {
        this.size = size.clone();
        this.node = node.getCopy();
        this.data = data;
        setState();
    }

    private Area seek(int axis,int step){
        return new Area(data,node.seek(axis,step*size[axis]),size);
    }

    public State getState() {
        return state;
    }

    //merge() still need step=+1,-1 to define the direction
    private Area merge(int axis,int step){
        int[] size=this.size.clone();
        size[axis]*=2;
        Position node=this.node.getCopy();
        //node should move for the size of the original area if merge step is positive
        if(step==-1){
            node.move(axis,step*this.size[axis]);
        }

        return new Area(data,node,size);
    }

    private Area tryMerge(int axis,int step){
        //to forced the word success and fail to have a same length is hard
        if(getState().equals(seek(axis,step).getState())){
            //System.out.println("succeed to merge "+this+" to "+seek(axis,step));
            return merge(axis,step);
        }else{
            //System.out.println("failure to merge "+this+" to "+seek(axis,step));
            return null;
        }
    }

    public Area[] expandOnce(){
        Area[] rtn=new Area[2*size.length];

        //as axis op size.length, use < rather than != in case of length==0
        for(int axis=0;axis<size.length;++axis){
            if(size[axis]!=data.getSize()[axis]){
                rtn[2*axis]=tryMerge(axis,1);
                rtn[2*axis+1]=tryMerge(axis,-1);
            }
        }

        return rtn;
    }

    //recurse() return whether the parent Area is furthest expanded
    private boolean recurse(Set<Area> data,Area[] orig){
        boolean isFinal=true;
        for(Area area:orig){
            if(area!=null){
                isFinal=false;
                //System.out.println("try to expand "+area);
                Area[] next=area.expandOnce();
                /*
                for(Area a:next){
                    System.out.println("\tget "+a);
                }
                */
                if(recurse(data,next)){
                    //System.out.println(area+" is final");
                    data.add(area);
                }
            }
        }
        return isFinal;
    }

    public Area[] expandMost(){
        Set<Area> rtn=new HashSet<>();
        if(recurse(rtn,expandOnce())){
            //System.out.println(this+" is final");
            rtn.add(this);
        }
        return rtn.toArray(new Area[rtn.size()]);
    }

    @Override
    public String toString() {
        String rtn="[";
        for(int value:size){
            rtn+=value+"*";
        }
        return getClass().getSimpleName()+rtn.substring(0,rtn.length()-1)+"]"+node+getState();
    }

    //quite difficult to hash well, because the difference of node means nothing
    @Override
    public int hashCode() {
        int rtn=0;
        for(int i:size){
            rtn+=i;
        }
        return rtn;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(getClass())){
            return getMembers().equals(((Area)obj).getMembers());
        }else{
            return false;
        }
    }

    public Set<Position> getMembers(){
        Function<Position,Set<Position>> func=new Function<Position,Set<Position>>() {
            private Set<Position> rtn=new HashSet<>();
            @Override
            public void process(Position orig) {
                rtn.add(orig);
            }

            @Override
            public Set<Position> getData() {
                return rtn;
            }
        };

        traverse(size,new LinkedList<>(),func);
        /*
        for(Position p:func.getData()){
            System.out.print(p);
        }
        System.out.println("getMembers()");
        */
        return func.getData();
    }

    public static void main(String[] args){
        Data data=Data.generateRandom(4,4);
        Area area=new Area(data,new Position(new int[]{0,0},new int[]{3,3}));
        System.out.println(area);
        data.print();
        for(Area one:area.expandMost()){
            System.out.println(one);
        }
        data.print();
    }
}
