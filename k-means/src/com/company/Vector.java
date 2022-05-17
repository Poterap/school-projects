package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector {

    private String name;
    private List<Double> coords = new ArrayList<>();
    private int c = -1;
    static int num = 0;

    public Vector(String line) {
        String[] sline = line.split(",");
        for (int i = 0; i < sline.length; i++) {
            coords.add(Double.valueOf(sline[i]));
        }
        this.name = String.valueOf(num);
        num++;
    }

    public Vector(List<Double> coords, int c) {
        this.coords=coords;
        this.name = "c"+c;
    }

    public double euclid(Vector vector) {
        double dis = 0;
        for (int i = 0; i < coords.size(); i++) {
            dis += Math.pow(coords.get(i)-vector.getCoords().get(i), 2);
        }
        return dis;
    }

    public List<Double> getCoords() {
        return coords;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void setCoords(double[] coords) {
        this.coords = new ArrayList<>();
        for (int i = 0; i < coords.length; i++) {
            this.coords.add(coords[i]);
        }
    }

    @Override
    public String toString() {
        return name
                + ", coords=" + coords
                ;
    }
}
