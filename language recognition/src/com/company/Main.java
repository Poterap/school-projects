package com.company;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    static ArrayList<Perceptron> perseptrons = new ArrayList();
    static double stalaUczenia = 0.1;
    static Map<String, List<Path>> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
	Path trainDirectory = Paths.get("C:\\Studia\\NAI\\projekt2_NAI\\train");
//	Files.walkFileTree(trainDirectory, new MySimpleFileVisitorImpl());
        Files.walkFileTree(trainDirectory, new SimpleFileVisitor<>(){
            Perceptron p;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!dir.getFileName().toString().equals("train")) {
                    Perceptron perceptron = new Perceptron(dir.getFileName().toString(), stalaUczenia);
                    perseptrons.add(perceptron);
                    p = perceptron;
                }
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    //                System.out.println(p.getLanguage()+" "+file.getFileName());
                if (map.containsKey(file.getParent().getFileName().toString())){
                    map.get(file.getParent().getFileName().toString()).add(file);
                }
                else{
                    map.put(file.getParent().getFileName().toString(),new ArrayList<Path>());
                    map.get(file.getParent().getFileName().toString()).add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        for (int l = 0; l < 35; l++) {
            System.out.println("------------------");
            for (Map.Entry<String, List<Path>> entry : map.entrySet()) {
//                System.out.println(entry.getKey() + ":" + entry.getValue());
                for (int i = 0; i < perseptrons.size(); i++) {
                    int d;
                    if (perseptrons.get(i).getLanguage().equals(entry.getKey()))
                        d=1;
                    else
                        d=0;
                    for (int j = 0; j < entry.getValue().size(); j++) {
//                        System.out.println(entry.getKey());
                        perseptrons.get(i).train(entry.getValue().get(i), d);
                    }
                }
            }
        }




        View view = new View();
        Controler controler = new Controler(view,perseptrons);

    }
}
