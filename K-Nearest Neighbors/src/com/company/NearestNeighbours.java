package com.company;

import java.util.HashMap;
import java.util.Map;

public class NearestNeighbours {
    private final int k;
    private int y = 0;
    Neighbour[] neighbourTab;
    int max = 0;
    String ans;

    public NearestNeighbours(int k) {
        this.k = k;
        this.neighbourTab = new Neighbour[k];
    }

    public void add(Neighbour neighbour) {
        if (y<k){
            neighbourTab[y] = neighbour;
            y++;
        }
        else {
            for (int i = 0; i < neighbourTab.length; i++) {
                if (neighbourTab[i].getDistance()>neighbour.getDistance()){
                    neighbourTab[i] = neighbour;
                    break;
                }
            }
        }
    }


    public String result() {
//        System.out.println("wynik:");
//        for (int i = 0; i < neighbourTab.length; i++) {
//            System.out.println(neighbourTab[i]);
//        }

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < neighbourTab.length; i++) {
            String key = neighbourTab[i].getName();
            if (map.containsKey(key)){
                int freq = map.get(key);
                freq++;
                map.put(key,freq);
            }
            else
            {
                map.put(key,1);
            }
            }
            map.forEach((key, value) -> {
                if (max < value)
                    ans = key;
                    max = value; });
//        System.out.println(ans);
        return ans;
        }
    }
