package sample;

import java.io.Serializable;

public class Gracz implements Serializable {

    public String nick;
    public int wynik;

    public Gracz(String nick, int wynik) {
        this.wynik = wynik;
        this.nick = nick;
    }

    @Override
    public String toString() {
        return nick+":"+wynik+":";
    }

    public int getWynik() {
        return wynik;
    }
}
