package net.alexhyisen.zeta;

import java.util.Arrays;

/**
 * Created by Alex on 2016/9/23.
 * Position, as what its name shows, is the position of a Node.
 * What is the difference between seek() and move()?
 * Well, seek() will return a new Position while move() return itself,
 * which means whether the caller changes or not.
 */
class Position {
    private int[] value;
    private int[] limit;

    public Position(int[] value, int[] limit) {
        this.value = value.clone();
        this.limit = limit.clone();
        //using deep copy to allow multiple use of source
    }

    //if the limit is't defined, will throw UnsupportedOperationException where it's needed
    public Position(int[] value) {
        this.value = value.clone();
        limit=null;
    }

    public Position getCopy(){
        return new Position(value.clone(),
                limit.clone());
    }

    private int change(int axis, int step){
        if(limit==null){
            throw new UnsupportedOperationException();
        }
        int result=value[axis];
        result+=step;
        while(result>limit[axis]){
            result-=limit[axis]+1;
        }
        while(result<0){
            result+=limit[axis]+1;
        }
        return result;
    }

    public Position move(int axis, int step) {
        value[axis]=change(axis,step);
        return this;
    }

    public Position seek(int axis, int step){
        int[] rtn= value.clone();
        rtn[axis]=change(axis,step);
//        System.out.println("confirm "+rtn[axis]+" != "+value[axis]);
        return new Position(rtn,limit);
    }

    @Override
    public String toString() {
        String rtn="(";
        for(int k=0;k!=value.length;++k){
            if(k!=0){
                rtn+=",";
            }
            rtn+=value[k];
        }
        rtn+=")";
        return rtn;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()!=getClass()){
            return false;
        }else{

            return Arrays.equals(value, ((Position) obj).value)
                    && Arrays.equals(limit, ((Position) obj).limit);
        }
    }

    @Override
    public int hashCode() {
        int rtn=0;
        for(int v:value){
            rtn+=v;
        }
        return rtn;
    }

    public int getDimension(){
        return value.length;
    }

    public static void main(String[] args){
        int[] value={0,0};
        int[] limit={3,3};
        Position p=new Position(value,limit);
        System.out.println(new Position(value,limit));
        System.out.println(p);
        p.move(0,1);
        System.out.println(p);
        System.out.println(p.seek(0,1)+" "+p.seek(0,1).equals(p));
        System.out.println(p);
        p.move(1,-1);
        System.out.println(p);
        p.move(0,-2);
        System.out.println(p+" "+p.seek(0,1).seek(1,1));
        p.move(1,-0);
        Position q=new Position(value,limit);
        System.out.println(q);
        System.out.println(p+" "+p.seek(0,1).seek(1,1).equals(q)+" q="+q);
        p.move(0,1).move(1,1);
        System.out.println(p+" "+p.equals(q));
    }
}
