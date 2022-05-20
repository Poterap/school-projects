package zad1.serwerGlowny;

import java.io.IOException;
import java.net.ServerSocket;

public class MainSerwer {
    public static void main(String[] args) throws IOException {
        SerwerGlowny serwerGlowny = new SerwerGlowny(new ServerSocket(400));
    }
}
