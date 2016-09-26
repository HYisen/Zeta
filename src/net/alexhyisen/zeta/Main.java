package net.alexhyisen.zeta;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
	// write your code here
        System.out.println("Project Zeta");
        if(args.length==0){
            System.out.print("law=");
            byte[] input=new byte[128];
            try {
                System.in.read(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Data data=Data.generateSimple(Elucidator.illustrate(new String(input),4));
            data.print();
            for(Area a:data.search()){
                System.out.println(a.toMeaning()+"\t"+a);
            }
            System.out.println();
            for(Area a:data.simplify()){
                System.out.println(a.toMeaning()+"\t"+a);
            }
        }
    }
}
