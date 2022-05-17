package com.company;

import java.util.Comparator;

public class Neighbour implements Comparable<Neighbour> {
    private String name;
    private double distance;

    public Neighbour(String name, double distance) {
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Neighbour o) {
        if (distance<o.distance)
        return 1;
        else if (distance>o.distance)
            return -1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return "Neighbour{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                '}';
    }
}
