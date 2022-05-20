package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

public class Serwer {
    public static void main(String[] args) throws IOException {
        serwerStart();
    }
    static private List topiclist = new ArrayList();
    static private Map<SocketChannel,List<String>> socketChannelsKlienci = new HashMap<>();
    static private Map<String,List<String>> wiadomosc = new HashMap<>();
    private static Charset charset  = Charset.forName("ISO-8859-2");

    public static void addNewTopic(String str) {
        topiclist.add(str);
        socketChannelsKlienci.forEach((socket,topics)->{
            try {
                System.out.println(socket.isOpen());
                if (socket.isConnected()) {
                    System.out.println("sending" + socket);
                    socket.write(charset.encode(CharBuffer.wrap("Temat;new;"+str)));
                }
                else{
                    socketChannelsKlienci.remove(socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Object[] getTopicsArray() {
        return topiclist.toArray();
    }

    public static void sendMessage(Object top, String text) {
        String topic = (String) top;
        if (wiadomosc.containsKey(topic)){
            wiadomosc.get(topic).add(text);
        }
        else {
            List tmp = new ArrayList();
            tmp.add(text);
            wiadomosc.put(topic,tmp);

        }
        socketChannelsKlienci.forEach((socket,topics)->{
            try {
                System.out.println(socket.isOpen());
                if (socket.isConnected()) {
                    if ( topics.contains(topic)) {
                        System.out.println("sending" + socket);
                        socket.write(charset.encode(CharBuffer.wrap("news;"+text)));
                    }
                }
                else{
                    socketChannelsKlienci.remove(socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        System.out.println(topic);
        System.out.println("---------------");
        System.out.println(text);
    }

    public static void deleteTopic(String str) {
        topiclist.remove(str);
        socketChannelsKlienci.forEach((socket,topics)->{
            try {
                System.out.println(socket.isOpen());
                if (socket.isConnected()) {
                    System.out.println("sending" + socket);
                    socket.write(charset.encode(CharBuffer.wrap("Temat;del;"+str)));
                }
                else{
                    socketChannelsKlienci.remove(socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void serwerStart() throws IOException {
        // Utworzenie kanału gniazda serwera
        // i związanie go z konkretnym adresem (host+port)
        String host = "localhost";
        int port = 12345;
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(host, port));

        // Ustalenie trybu nieblokującego
        // dla kanału serwera gniazda
        serverChannel.configureBlocking(false);

        // Utworzenie selektora
        Selector selector = Selector.open();

        // Rejestracja kanału gniazda serwera u selektora
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Serwer: czekam ... ");

        // Selekcja gotowych operacji do wykonania i ich obsługa
        // w pętli dzialania serwera
        while (true) {

            // Selekcja gotowej operacji
            // To wywolanie jest blokujące
            // Czeka aż selektor powiadomi o gotowości jakiejś operacji na jakimś kanale
            selector.select();

            // Teraz jakieś operacje są gotowe do wykonania
            // Zbiór kluczy opisuje te operacje (i kanały)
            Set<SelectionKey> keys = selector.selectedKeys();

            // Przeglądamy "gotowe" klucze
            Iterator<SelectionKey> iter = keys.iterator();

            while(iter.hasNext()) {

                // pobranie klucza
                SelectionKey key = iter.next();

                // musi być usunięty ze zbioru (nie ma autonatycznego usuwania)
                // w przeciwnym razie w kolejnym kroku pętli "obsłużony" klucz
                // dostalibyśmy do ponownej obsługi
                iter.remove();

                // Wykonanie operacji opisywanej przez klucz
                if (key.isAcceptable()) { // połaczenie klienta gotowe do akceptacji

                    System.out.println("Serwer: ktoś się połączył ..., akceptuję go ... ");
                    // Uzyskanie kanału do komunikacji z klientem
                    // accept jest nieblokujące, bo już klient czeka
                    SocketChannel cc = serverChannel.accept();

                    // Kanał nieblokujący, bo będzie rejestrowany u selektora
                    cc.configureBlocking(false);

                    // rejestrujemy kanał komunikacji z klientem
                    // do monitorowania przez ten sam selektor
                    cc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                    continue;
                }

                if (key.isReadable()) {  // któryś z kanałów gotowy do czytania

                    // Uzyskanie kanału na którym czekają dane do odczytania
                    SocketChannel cc = (SocketChannel) key.channel();

                    serviceRequest(cc);

                    // obsługa zleceń klienta
                    // ...
                    continue;
                }
                if (key.isWritable()) {  // któryś z kanałów gotowy do pisania

                    // Uzyskanie kanału
                    //SocketChannel cc = (SocketChannel) key.channel();

                    // pisanie do kanału
                    // ...
                    continue;
                }

            }
        }
    }


    // Strona kodowa do kodowania/dekodowania buforów
    private static final int BSIZE = 1024;

    // Bufor bajtowy - do niego są wczytywane dane z kanału
    static private ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);

    // Tu będzie zlecenie do pezetworzenia
    static private StringBuffer reqString = new StringBuffer();


    private static void serviceRequest(SocketChannel sc) {
        if (!sc.isOpen()) return; // jeżeli kanał zamknięty

        System.out.print("Serwer: czytam komunikat od klienta ... ");
        // Odczytanie zlecenia
        reqString.setLength(0);
        bbuf.clear();

        try {
            readLoop:                    // Czytanie jest nieblokujące
            while (true) {               // kontynujemy je dopóki
                int n = sc.read(bbuf);   // nie natrafimy na koniec wiersza
                if (n > 0) {
                    bbuf.flip();
                    CharBuffer cbuf = charset.decode(bbuf);
                    while(cbuf.hasRemaining()) {
                        char c = cbuf.get();
                        //System.out.println(c);
                        if (c == '\r' || c == '\n') break readLoop;
                        else {
                            //System.out.println(c);
                            reqString.append(c);
                        }
                    }
                }
            }

            String cmd = reqString.toString();
            System.out.println(reqString);

            if (cmd.equals("Hi")) {
                System.out.println("podlaczam klienta "+sc);
                socketChannelsKlienci.put(sc,new ArrayList<>());
                System.out.println(socketChannelsKlienci.keySet());
                sc.write(charset.encode(CharBuffer.wrap("availableTopics;"+topiclist)));
            }
            else if (cmd.startsWith("Admin;Temat;new;")) {
                addNewTopic(cmd.replaceFirst("Admin;Temat;new;",""));
            }
            else if (cmd.startsWith("Admin;Temat;del;")) {
                deleteTopic(cmd.replaceFirst("Admin;Temat;del;",""));
            }
            else if (cmd.startsWith("Admin;news;")) {
                String[] topicText = cmd.replaceFirst("Admin;news;","").split(";");
                sendMessage(topicText[0],topicText[1]);
            }
            else if (cmd.startsWith("Klient;Topic;")){
                String[] cmdLine = cmd.split(";");
                if (cmdLine[2].equals("Plus")){
                    System.out.println("ok");
                    socketChannelsKlienci.get(sc).add(cmdLine[3]);
                    if (wiadomosc.containsKey(cmdLine[3]))
                    if (!wiadomosc.get(cmdLine[3]).isEmpty())
                    for (int i = 0; i < wiadomosc.get(cmdLine[3]).size(); i++) {
                        sc.write(charset.encode(CharBuffer.wrap("news;"+wiadomosc.get(cmdLine[3]).get(i))));
                    }
                    System.out.println(cmdLine[2]+" "+cmdLine[3]);
                }else if (cmdLine[2].equals("Minus")){
                    socketChannelsKlienci.get(sc).remove(cmdLine[3]);
                    System.out.println(cmdLine[2]+" "+cmdLine[3]);
                }
            }else if (cmd.equals("Bye")) {

                sc.write(charset.encode(CharBuffer.wrap("Bye")));
                System.out.println("Serwer: mówię \"Bye\" do klienta ...\n");
                System.out.println(socketChannelsKlienci.keySet());
                socketChannelsKlienci.remove(sc);
                System.out.println(socketChannelsKlienci.keySet());
                sc.close();
                sc.socket().close();

            } else
                sc.write(charset.encode(CharBuffer.wrap(reqString)));


        } catch (Exception exc) {
            exc.printStackTrace();
            try { sc.close();
                sc.socket().close();
            } catch (Exception e) {}
        }

    }
}
