package tizzy.skimapp.ResortModel;

import java.io.Serializable;

public class Coords implements Serializable {

    private double x, y, z;

    public Coords(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
