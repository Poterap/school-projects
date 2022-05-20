package zad1.serwerGlowny;

import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class SerwerGlowny {

    private ServerSocket ss = null;
    protected Map<String, String> slowniki = new HashMap<>();

    public SerwerGlowny(ServerSocket ss) {

        this.ss = ss;
        System.out.println("Server started");
        System.out.println("listening at port: " + ss.getLocalPort());
        System.out.println("bind address: " + ss.getInetAddress());

        serviceConnections();
    }


    private void serviceConnections() {
        boolean serverRunning = true;
        while (serverRunning) {
            try {
                Socket conn = ss.accept();
                System.out.println("Connection established");

                new RequestHandler(conn,this).start();

            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        try { ss.close(); } catch (Exception exc) {}
    }


}
