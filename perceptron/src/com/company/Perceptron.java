package com.company;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;

public class Perceptron {

    private String language;
    private double[] wektorwag;
    private double stalaUczenia;
    private double prog;

    public Perceptron(String language, double a) {
        this.language = language;
        this.wektorwag = new double[26];
        for (int i = 0; i < wektorwag.length; i++) {
            wektorwag[i]=Math.random();
        }
        this.stalaUczenia = a;
        this.prog = 1-Math.random()*2;
        System.out.println(prog);
    }

    public void train(Path file, int d) throws IOException {
        double trainv[] = toVector(file);
        double iloczyn = 0;
        for (int i = 0; i < trainv.length; i++) {
            iloczyn += trainv[i]*wektorwag[i];
        }
        int y = decyzja(iloczyn);
        if ((d-y)!=0){
            double dlugoscW = 0;
            for (int i = 0; i < trainv.length; i++) {
                wektorwag[i] = wektorwag[i] + (d-y)*stalaUczenia * trainv[i];
                dlugoscW += Math.pow(wektorwag[i], 2);
            }
            for (int i = 0; i < wektorwag.length; i++) {
                wektorwag[i]=wektorwag[i]/dlugoscW;
            }
            prog = prog + (d-y)*stalaUczenia * (-1);
            System.out.println(d+language+" "+prog);
        }
    }

    private int decyzja(double iloczyn) {
        if (iloczyn >= prog)
            return 1;
        else
            return 0;
    }

    private double[] toVector(Path file) throws IOException {
        FileReader fr=new FileReader(String.valueOf(file));
        return makeV(fr);
    }

    private double[] makeV(Reader reader) throws IOException {
        double v[] = new double[26];
        Arrays.fill(v,0);
        BufferedReader br = new BufferedReader(reader);
        int count = 0;
        int c;
        while ((c = br.read()) != -1){
            if (c>=65 && c<=90) {
                v[c-65]=v[c-65]+1;
                count++;
            }
            else if (c>=97 && c<=122){
                v[c-97]=v[c-97]+1;
                count++;
            }
        }
        for (int i = 0; i < v.length; i++) {
            v[i]=v[i]/count;
        }
        return v;
    }

    public double use(String text) throws IOException {
        StringReader sr = new StringReader(text);
        double v[] = makeV(sr);
        double iloczyn = 0;
        for (int i = 0; i < v.length; i++) {
            iloczyn += v[i]*wektorwag[i];
        }
     return iloczyn;
    }

    public double getProg() {
        return prog;
    }

    public String getLanguage() {
        return language;
    }
}
