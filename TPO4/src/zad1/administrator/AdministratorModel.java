package zad1.administrator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

public class AdministratorModel {

    private Set list = new HashSet();
    private static Charset charset = Charset.forName("ISO-8859-2");
    SocketChannel channel = null;

    public void addNewTopic(String str) {
        list.add(str);
            try {
                    channel.write(charset.encode(CharBuffer.wrap("Admin;Temat;new;" + str+"\n")));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public Object[] getTopicsArray() {
        return list.toArray();
    }

    public void sendMessage(Object top, String text) {
        try {
            channel.write(charset.encode(CharBuffer.wrap("Admin;news;"+top+";"+text+"\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteTopic(String str) {
        list.remove(str);
        try {
            channel.write(charset.encode(CharBuffer.wrap("Admin;Temat;del;" + str+"\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public AdministratorModel() {
        new Thread(() -> {
            try {
                administrator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void administrator() throws IOException {
        String server = "localhost";
        int port = 12345;

        try {
            channel = SocketChannel.open();

            channel.configureBlocking(false);

            channel.connect(new InetSocketAddress(server, port));

            System.out.print("Klient: łączę się z serwerem ...");

            while (!channel.finishConnect()) {
            }

        } catch (UnknownHostException exc) {
            System.err.println("Uknown host " + server);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        System.out.println("\nKlient: jestem połączony z serwerem ...");

        int rozmiar_bufora = 1024;
        ByteBuffer inBuf = ByteBuffer.allocateDirect(rozmiar_bufora);
        CharBuffer cbuf = null;


        System.out.println("Klinet: wysyłam - Hi");
        while (true) {

            inBuf.clear();
            int readBytes = channel.read(inBuf);

            if (readBytes == 0) {
                continue;

            } else if (readBytes == -1) {

                break;
            } else {
                System.out.println("coś jest od serwera");

                inBuf.flip();

                cbuf = charset.decode(inBuf);

                String odSerwera = cbuf.toString();

                System.out.println("Klient: serwer właśnie odpisał ... " + odSerwera);
                cbuf.clear();


                if (odSerwera.equals("Bye")) {
                    break;
                }

            }

        }

    }
    public void endconnection() {
        System.out.println("wysyłam bye");
        try {
            channel.write(charset.encode("Bye\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
