package net.alexhyisen.zeta;

import net.alexhyisen.zeta.cli.Surface;

public class Main {
    public static void main(String[] args) {
	// write your code here
        System.out.println("Project Zeta");
        if(args.length==0){
            Surface.run();
        }
    }
}
