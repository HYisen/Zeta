package net.alexhyisen.zeta.gui;

/**
 * Created by Alex on 2016/10/2.
 * BorderLine is what we use to show the border of an Area.
 * By the way, it's defined as a struct.
 */
public class BorderLine {
    final BorderType[] types;
    final int radius; //by percent

    public BorderLine(BorderType[] types, int radius) {
        this.types = types;
        this.radius = radius;
    }
}
