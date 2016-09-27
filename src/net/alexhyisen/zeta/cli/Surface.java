package net.alexhyisen.zeta.cli;

import net.alexhyisen.zeta.model.Area;
import net.alexhyisen.zeta.model.Data;
import net.alexhyisen.zeta.model.Elucidator;

import java.io.IOException;

/**
 * Created by Alex on 2016/9/27.
 */
public class Surface {
    public static void run(){
        System.out.println("sample: Ab+BcD");
        System.out.print("law=");
        byte[] input=new byte[128];
        try {
            System.in.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        process(new String(input));
    }

    public static void process(String input){
        Data data=Data.generateSimple(Elucidator.illustrate(input,4));
        data.print();
        System.out.println();
        for(Area a:data.search()){
            System.out.println(a.toMeaning()+"\t"+a);
        }
        System.out.println();
        for(Area a:data.simplify()){
            System.out.println(a.toMeaning()+"\t"+a);
        }
    }
}
