package zad1.klient;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    private static KlientView view = new KlientView();
    private static Socket socket;
    private static String server = "localhost"; // adres hosta serwera
    private static int port = 400; // numer portu
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {

        try {
            socket = new Socket(server, port);
//            socket.connect(new InetSocketAddress(server, port));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(UnknownHostException exc) {
            System.err.println("Uknown host " + server);
        } catch(Exception exc) {
            exc.printStackTrace();
        }

        view.button.addActionListener(e -> {
            if (socket!=null && socket.isConnected())
            wyslijDoSerwera();
        });
    }

    private static void wyslijDoSerwera() {
        try {
            serverSocket = new ServerSocket(0);
            out.println(view.jTextAreaSlowo.getText()+","+view.jTextAreaKod.getText()+","+serverSocket.getLocalSocketAddress());
            System.out.println("czekam na "+serverSocket.getLocalSocketAddress());
            String line;
            line = in.readLine();
            if (!line.equals("OK")){
                JOptionPane.showMessageDialog(new JOptionPane(),line);
            }
            else {
                waitForResponse(serverSocket.accept());
            }
        } catch (IOException e) {
            System.out.println("Communication error");
            System.exit(-1);
        }
    }

    private static void waitForResponse(Socket accept) {
        Thread thread = new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                String line = in.readLine();
                System.out.println(line);
                view.jLabel.setText(line);
                accept.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

}
