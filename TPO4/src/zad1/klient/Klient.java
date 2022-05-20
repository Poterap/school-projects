package zad1.klient;

import java.io.IOException;

public class Klient {
    public static void main(String[] args) throws IOException {
        KlientView klientView = new KlientView();
        KlientModel klientModel = new KlientModel(klientView);
        KlientControler klientControler = new KlientControler(klientModel, klientView);
    }
}
