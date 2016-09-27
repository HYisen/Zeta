package net.alexhyisen.zeta.model;

import java.util.Random;

/**
 * Created by Alex on 2016/9/23.
 * State is state
 * POSITIVE=1;
 * NEGATIVE=0;
 * DONTCARE=X;
 * MULTIPLE mean the area is not coherent.
 */
public enum State {
    POSITIVE,
    NEGATIVE,
    DONTCARE,
    MULTIPLE;

    public static Random rand=new Random();
    public static State next(int pScale,int nScale,int dScale){
        int roll=rand.nextInt(pScale+nScale+dScale);
        if(roll<pScale){
            return POSITIVE;
        }else if(roll<(pScale+nScale)){
            return NEGATIVE;
        }else{
            return DONTCARE;
        }
    }

    public static void main(String[] args){
        int[] data=new int[3];
        for(int k=0;k<1000;k++)
        {
            State state=next(1,4,0);
            switch (state){
                case POSITIVE:
                    data[0]++;
                    break;
                case NEGATIVE:
                    data[1]++;
                    break;
                case DONTCARE:
                    data[2]++;
                    break;
                case MULTIPLE:
                    break;
                default:
                    throw new RuntimeException();
            }
        }
        for (int count:data){
            System.out.println(count);
        }
    }
}
