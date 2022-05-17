package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KNN {

    private final int k;
    private int dobre = 0;
    private int elements;
    private final Map<String,List<ArrayList<Double>>> trainSet;

    public KNN(int k, String testSetf, String trainSetf) {
        this.k = k;
        this.trainSet = read(trainSetf);
        test(testSetf);
    }

    private void test(String test) {
        ArrayList<Neighbour> neighbourArrayList = new ArrayList<>();
        int x = 0;
        try {
            FileReader testFileReader = new FileReader(test);
            BufferedReader testBufferedReader = new BufferedReader(testFileReader);
            String testLine = testBufferedReader.readLine();
            while (testLine !=null){
                Scanner testScanner = new Scanner(testLine).useDelimiter(",");
                ArrayList<Double> list = new ArrayList<>();
                while (testScanner.hasNextDouble()){
                    list.add(testScanner.nextDouble());
                }
                String ans = testScanner.next();

                NearestNeighbours nearestNeighbours = new NearestNeighbours(k);
                trainSet.forEach((key, value) -> {
                    for (int i = 0; i < value.size(); i++) {
                        double distance = 0;
                        for (int j = 0; j < value.get(i).size(); j++) {
                            distance = distance + Math.pow(list.get(j) - value.get(i).get(j), 2);
                        }
                        nearestNeighbours.add(new Neighbour(key, distance));
                    }
                });
                String res = nearestNeighbours.result();

                if (res.equals(ans)){
                    dobre++;
                }
                x++;
                testLine = testBufferedReader.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println((dobre*100/x)+"% dobrych odpowiedzi dla k="+k);
    }

    private Map<String,List<ArrayList<Double>>> read(String file){
        Map<String,List<ArrayList<Double>>> map = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while (line!=null){
                Scanner scanner = new Scanner(line).useDelimiter(",");
                ArrayList<Double> list = new ArrayList<>();
                while (scanner.hasNextDouble()){
                        list.add(scanner.nextDouble());
                }
                String odp = scanner.next();

                line = bufferedReader.readLine();

                if (map.containsKey(odp))
                    map.get(odp).add(list);
                else {
                    List<ArrayList<Double>> al = new LinkedList<>();
                    al.add(list);
                    map.put(odp, al);
                }
                elements = list.size();
            }

//            map.entrySet().forEach(entry -> {
//                System.out.println(entry.getKey()+" - "+ entry.getValue().toString());
//            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public String use(List<Double> list) {
        NearestNeighbours nearestNeighbours = new NearestNeighbours(k);
        trainSet.forEach((key, value) -> {
            for (int i = 0; i < value.size(); i++) {
                double distance = 0;
                for (int j = 0; j < value.get(i).size(); j++) {
                    distance = distance + Math.pow(list.get(j) - value.get(i).get(j), 2);
                }
                nearestNeighbours.add(new Neighbour(key, distance));
            }
        });
        return nearestNeighbours.result();
    }

    public int getElements() {
        return elements;
    }

}
