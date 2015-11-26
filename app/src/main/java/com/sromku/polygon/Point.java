package com.sromku.polygon;

/**
 * Point on 2D landscape
 *
 * @author Roman Kushnarenko (sromku@gmail.com)</br>
 */
public class Point {

    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%.5f,%.5f)", x, y);
    }
}
