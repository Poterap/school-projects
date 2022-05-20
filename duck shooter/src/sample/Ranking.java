package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Ranking extends ArrayList<Gracz> implements Serializable {

    public Ranking() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("ranking.txt"));
        while (scanner.hasNext()){
//            System.out.println(scanner.next());
            add(new Gracz(scanner.next(),scanner.nextInt()));
        }
    }

    @Override
    public boolean add(Gracz gracz) {
        return super.add(gracz);
    }

    @Override
    public void sort(Comparator<? super Gracz> c) {
        super.sort(c);
    }

    @Override
    public String toString() {
        String s="";
        for (int i = 0; i < this.size(); i++) {
            s=s+this.get(i).nick+" "+this.get(i).wynik+"\n";
        }
        return s;
    }

    public void save() throws IOException {
        Files.write(Paths.get("ranking.txt"), this.toString().getBytes());
    }
}
