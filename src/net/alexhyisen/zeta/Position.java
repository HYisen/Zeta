package net.alexhyisen.zeta;

/**
 * Created by Alex on 2016/9/23.
 * Position, as what its name shows, is the position of a Node.
 */
class Position {
    private int[] value;
    private int[] limit;

    Position(int[] value, int[] limit) {
        this.value = value;
        this.limit = limit;
    }

    Position move(int axis, int step){
        int result=value[axis];
        result+=step;
        while(result>limit[axis]){
            result-=limit[axis]-1;
        }
        while(result<0){
            result+=limit[axis]+1;
        }
        value[axis]=result;
        return this;
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
        return getClass().getSimpleName()+rtn;
    }

    public static void main(String[] args){
        int[] value={0,0};
        int[] limit={3,3};
        Position p=new Position(value,limit);
        System.out.println(p);
        p.move(0,1);
        System.out.println(p);
        p.move(1,-1);
        System.out.println(p);
        p.move(0,-2);
        System.out.println(p);
        p.move(1,-0);
        System.out.println(p);
    }
}
