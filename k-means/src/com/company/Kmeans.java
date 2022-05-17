package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Kmeans {

    private List<Vector> allList = new ArrayList<>();
    private List<Vector> cs = new ArrayList<>();
    private Map<Integer,List<Vector>> groups = new HashMap<>();
    private boolean ready;
    private int k;
    private int count = 1;

    public Kmeans(String path, int k) throws IOException {
        this.k = k;
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = bufferedReader.readLine();
        while (line != null){
         allList.add(new Vector(line));
         line = bufferedReader.readLine();
        }
        for (int i = 0; i < k; i++) {
            Vector v = new Vector(allList.get((allList.size()/k)*i).getCoords(), i);
            cs.add(v);
        }

        System.out.println("iteration"+count++);
        assingVtoC();
        tomap();
        do {
            ready = true;
            System.out.println("iteration"+count++);
            newCs();
            assingVtoC();
            tomap();
        }while (!ready);

    }


    private void tomap() {
        groups = new HashMap<>();
        for (int i = 0; i < k; i++) {
            groups.put(i, new ArrayList<>());
        }
        for (int i = 0; i < allList.size(); i++) {
            groups.get(allList.get(i).getC()).add(allList.get(i));
        }
        groups.forEach((c,list)->{
            double distanceSum = 0;
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    distanceSum += Math.sqrt(list.get(i).euclid(list.get(j)));
                }
            }
            System.out.println(cs.get(c)+" distanceSum:"+distanceSum);
            System.out.println(list);
        });

    }

    private void assingVtoC() {
        for (int i = 0; i < allList.size(); i++) {
            int c = 0;
            double dis = allList.get(i).euclid(cs.get(0));
            for (int j = 1; j < cs.size(); j++) {
                double tdis = allList.get(i).euclid(cs.get(j));
                if (dis > tdis) {
                    dis = tdis;
                    c = j;
                }
            }
            if (allList.get(i).getC()!=-1){
                if (allList.get(i).getC()!=c)
                    ready = false;
            }
            allList.get(i).setC(c);
        }
    }

    private void newCs() {
        groups.forEach((c,list) -> {
            double[] coord = new double[list.get(0).getCoords().size()];
            Arrays.fill(coord,0);
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < coord.length; j++) {
                    coord[j]=coord[j]+list.get(i).getCoords().get(j);
                }
            }
            for (int i = 0; i < coord.length; i++) {
                coord[i] = coord[i] / list.size();
            }
            cs.get(c).setCoords(coord);
        });
            
        }
    }

