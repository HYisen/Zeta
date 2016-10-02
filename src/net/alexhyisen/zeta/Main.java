package net.alexhyisen.zeta;

import net.alexhyisen.zeta.cli.Surface;
import net.alexhyisen.zeta.gui.MainWindow;
import net.alexhyisen.zeta.utility.SwingWindow;

public class Main {
    public static void main(String[] args) {
	// write your code here
        System.out.println("Project Zeta");
        if(args.length==0){
            Surface.run();
        }else if(args[0].equals("-g")||args[0].equals("--gui")){
            SwingWindow.run(new MainWindow(),800,600);
        }
    }
}
