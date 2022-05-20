package zad1.slownik;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class SlownikRequestHandler extends Thread {

    private Socket connection = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Map<String, String> map;

    private static String msg[] = { "Ok","END"
    };

    public SlownikRequestHandler(Socket connection, Map<String, String> map) {
        this.connection = connection;
        this.map=map;
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
                String resp;
                String[] req = line.split(",");
                if (req.length==1 && req[0].equals("END")) {
                    writeResp(1, null);
                    break;
                }
                else if (req.length==2){
                    String x[] = req[1].split(":");
                    String server[] = x[0].split("/");

                    Socket socket1 = new Socket(server[0], Integer.parseInt(x[1]));
                    PrintWriter out1 = new PrintWriter(socket1.getOutputStream(), true);
                    System.out.println(socket1.isConnected());

                    if (map.containsKey(req[0])) {
                        out1.println(map.get(req[0]));
                    }
                    else {
                        out1.println("brak slowa w slowniku");
                    }
                }
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

    private void writeResp(int rc, String addMsg) {
        out.println(rc + " " + msg[rc]);
        if (addMsg != null) out.println(addMsg);
    }
}
