package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        KNN knn = new KNN(3, "test-set.csv", "train-set.csv");

        String s;
        do {
            Scanner in = new Scanner(System.in);
            System.out.println();
            System.out.println("aby podac wartosci i przetestowac wpisac 't'");
            System.out.println("aby zakonczyc program wpisac 'x'");
            s=in.nextLine();
            if (s.equals("t")) {
                System.out.println("Podaj wartości :");
                List<Double> list = new ArrayList<>();
                for (int i = 0; i < knn.getElements(); i++) {
                    System.out.print("pierwsza wartość: ");
                    list.add(in.nextDouble());
                }
                System.out.println(knn.use(list));
            }
        }while(!s.equals("x"));

    }
}
