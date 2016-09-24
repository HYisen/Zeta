package net.alexhyisen.zeta;

import java.util.*;

/**
 * Created by Alex on 2016/9/23.
 * Area is something that all of the Node it included have a same state and expands when simplifying.
 * node is the left-up position of all the members
 */
class Area {
    private Map<Position,State> data;
    private Position node;
    private int[] size;
    private State state;

    public Area(Map<Position, State> data,Position node) {
        this.node = node;
        this.data = data;
        this.node = node;
        size=new int[node.getDimension()];
        Arrays.fill(size,1);
        state=data.get(node);
    }

    private void setState(){
        State rtn=data.get(node);
        Function<State,State> func=new Function<State, State>() {
            private State state=State.DONTCARE;

            @Override
            public void process(State orig) {
                if(orig!=state&&orig!=State.DONTCARE){
                    state=State.MULTIPLE;
                }else if(orig!=State.DONTCARE){
                    state=orig;
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

    private void traverse(int[] size, LinkedList<Integer> log, Function<State,?> func){
        if(size.length==1){
            for(int k=0;k!=size[0];++k){
                log.push(k+1);
                Position p=node.getCopy();
                int axis=0;
                while(log.isEmpty()){
                    p.move(axis++,log.pop());
                }
                func.process(data.get(p));
            }
        }else{
            for(int k=0;k!=size[size.length-1];++k){
                log.push(k+1);
                traverse(Arrays.copyOf(size,size.length-1),log,func);
            }
        }
    }

    public Area(Map<Position, State> data, Position node, int[] size) {
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
        for(int one:size){
            one*=2;
        }
        Position node=this.node.getCopy();
        //node should move for the size of the original area
        node.move(axis,step*this.size[axis]);

        return new Area(data,node,size);
    }

    private Area tryMerge(int axis,int step){
        if(getState()==seek(axis,step).getState()){
            return merge(axis,step);
        }else{
            return null;
        }
    }

    public Area[] expand(){
        Area[] rtn=new Area[2*size.length];
        //as axis op size.length, use < rather than != in case of length==0
        for(int axis=0;axis<size.length;++axis){
            rtn[2*axis]=tryMerge(axis,1);
            rtn[2*axis+1]=tryMerge(axis,-1);
        }

        return rtn;
    }

    public static void main(String[] args){
        Map<Position,State> data=new HashMap<>();

    }
}
