package zad1.serwerGlowny;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

class RequestHandler extends Thread {

    private Socket connection = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private SerwerGlowny serwerGlowny;

    private static String msg[] = { "OK", "END", "Bledny kod kraju", "Błędny komunikat"
    };

    public RequestHandler(Socket connection, SerwerGlowny serwerGlowny) {
        this.connection = connection;
        this.serwerGlowny=serwerGlowny;
        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            out = new PrintWriter(
                    connection.getOutputStream(), true);
        } catch (Exception exc) {
            exc.printStackTrace();
            try { connection.close(); } catch(Exception e) {}
            return;
        }
    }

    public void run() {
        try {
            for (String line; (line = in.readLine()) != null; ) {
                String[] req = line.split(",");
                if (req.length==1 && req[0].equals("END")) {
                    writeResp(1);
                    break;
                }
                else if (req.length==3 && req[0].equals("connect")){
                    System.out.println(req[1]+" connected on "+req[2]);
                    serwerGlowny.slowniki.put(req[1],req[2]);
                    System.out.println(serwerGlowny.slowniki.keySet());
                }
                else if (req.length==3){
                    if (serwerGlowny.slowniki.containsKey(req[1])){
                        writeResp(0);
                        System.out.println("connecting to "+req[1]+" on "+serwerGlowny.slowniki.get(req[1]));
                        String[] x =  serwerGlowny.slowniki.get(req[1]).split(":");
                        String[] a =  x[0].split("/");
                        ask(a[0], Integer.parseInt(x[1]),req[0]+","+req[2]);
                    }
                    else {
                        writeResp(2);
                    }
                }
                else writeResp(3);
            }
        } catch (Exception exc) {
            exc.printStackTrace();

        } finally {
            try {
                connection.close();
                connection = null;
            } catch (Exception exc) { }
        }
    }

    private void ask(String server, int port, String message) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            socket = new Socket(server, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(UnknownHostException exc) {
            System.err.println("Uknown host " + server);
        } catch(Exception exc) {
            exc.printStackTrace();
        }
        out.println(message);
    }

    private void writeResp(int rc) {
        System.out.println(msg[rc]);
        out.println(msg[rc]);
    }
}
