package net.alexhyisen.zeta.gui;

import net.alexhyisen.zeta.model.Area;
import net.alexhyisen.zeta.model.Data;
import net.alexhyisen.zeta.model.Position;

/**
 * Created by Alex on 2016/10/2.
 */
public class AreaAdapter extends Area{

    public AreaAdapter(Data data, Position node, int[] size) {
        super(data, node, size);
    }

    @Override
    public String toString() {
        return super.toMeaning()+"  "+super.toString().replaceFirst(getClass().getSimpleName(),"");
    }

    public static AreaAdapter[] adapt(Area[] orig){
        AreaAdapter[] rtn=new AreaAdapter[orig.length];
        for(int k=0;k!=orig.length;k++){
            rtn[k]=new AreaAdapter(orig[k].getData(),orig[k].getNode(),orig[k].getSize());
        }
        return rtn;
    }
}
