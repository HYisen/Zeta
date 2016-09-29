package net.alexhyisen.zeta.utility;

import javax.swing.*;

/**
 * Created by Alex on 2016/9/27.
 * This Class is created to ease the burden to write similar initialization codes
 */
public class SwingWindow {
    public static void run(final JFrame f, final int width, final int height){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                f.setTitle(f.getClass().getSimpleName());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(width,height);
                f.setVisible(true);
            }
        });
    }
}
