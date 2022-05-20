package zad1.slownik;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SerwerSlownik {
    private static ServerSocket ss = null;
    private static String languageCode;
    private static Map<String, String> map;
    public static void main(String[] args) throws IOException {
        map = new HashMap<>();
        File file = new File(args[0]);
        languageCode= file.getName();
        Scanner scanner = new Scanner(file);
        System.out.println("Słownik "+languageCode+":");
        while (scanner.hasNextLine()){
            String[] x = scanner.nextLine().split("-");
            map.put(x[0],x[1]);
            System.out.println(x[0]+" "+x[1]);
        }
        System.out.println();

        ss = new ServerSocket(0);
        System.out.println("Server started");
        System.out.println("listening at port: " + ss.getLocalPort());
        System.out.println("bind address: " + ss.getInetAddress());

        connectToProxy();
        serviceConnections();


    }

    private static void connectToProxy(){
        Socket socket = null;
        String server = "localhost";
        int port = 400;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            socket = new Socket(server,port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(UnknownHostException exc) {
            System.err.println("Uknown host " + server);
            // ...
        } catch(Exception exc) {
            exc.printStackTrace();
            // ...
        }
        try {
            out.println("connect,"+languageCode+","+ss.getLocalSocketAddress());
            String line;
            out.println("END");
            line = in.readLine();
            if (line != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Communication error");
            System.exit(-1);
        }
    }

    private static void serviceConnections() {
        boolean serverRunning = true;
        while (serverRunning) {
            try {
                Socket conn = ss.accept();
                System.out.println("Connection established");

                new SlownikRequestHandler(conn,map).start();

            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        try { ss.close(); } catch (Exception exc) {}
    }
}
