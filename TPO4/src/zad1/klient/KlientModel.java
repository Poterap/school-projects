package zad1.klient;

// property java bins

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class KlientModel {

    private List topiclist = new ArrayList();
    private List<String> subTopics = new ArrayList<>();
    private KlientView klientView;
    private Charset charset  = Charset.forName("ISO-8859-2");
    SocketChannel channel = null;

    public void addNewTopic(String str) {
        if (!subTopics.contains(str)) {
            subTopics.add(str);
            try {
                channel.write(charset.encode("Klient;Topic;Plus;"+str+"\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object[] getTopiclist() {
        List tmp = topiclist;
        tmp.removeAll(subTopics);
        return tmp.toArray();
    }

    public List<String> getSubTopics() {
        return subTopics;
    }

    public void deleteTopic(String str) {
        try {
            channel.write(charset.encode("Klient;Topic;Minus;"+str+"\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        subTopics.remove(str);
    }

    public Object[] getTopicsArray() {
        return subTopics.toArray();
    }

    public KlientModel(KlientView klientView) {
        this.klientView = klientView;
        new Thread(() -> {
            try {
                klient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void klient() throws IOException {

        String server = "localhost";
        int port = 12345;

        try {
            channel = SocketChannel.open();

            channel.configureBlocking(false);

            channel.connect(new InetSocketAddress(server, port));

            System.out.print("Klient: łączę się z serwerem ...");

            while (!channel.finishConnect()) {

            }

        } catch(UnknownHostException exc) {
            System.err.println("Uknown host " + server);
        } catch(Exception exc) {
            exc.printStackTrace();
        }

        System.out.println("\nKlient: jestem połączony z serwerem ...");
        int rozmiar_bufora = 1024;
        ByteBuffer inBuf = ByteBuffer.allocateDirect(rozmiar_bufora);
        CharBuffer cbuf = null;


        System.out.println("Klinet: wysyłam - Hi");
        channel.write(charset.encode("Hi\n"));

        while (true) {

            inBuf.clear();
            int readBytes = channel.read(inBuf);

            if (readBytes == 0) {

                continue;

            }
            else if (readBytes == -1) {
                break;
            }
            else {
                System.out.println("coś jest od serwera");

                inBuf.flip();

                cbuf = charset.decode(inBuf);

                String odSerwera = cbuf.toString();

                System.out.println("Klient: serwer właśnie odpisał ... " + odSerwera);
                cbuf.clear();

                if (odSerwera.equals("Bye")) {
                    break;
                }
                else if (odSerwera.startsWith("availableTopics;")){
                    String topics = odSerwera.replace("availableTopics;","");
                    System.out.println(topics);
                    if (topics.length()>2) {
                        String[] topic = topics.substring(1,topics.length()-1).split(", ");
                        for (int i = 0; i < topic.length; i++) {
                            topiclist.add(topic[i]);
                        }
                    }
                }
                else if (odSerwera.startsWith("news;")){
                    System.out.println(odSerwera);
                    klientView.jTextArea.append(odSerwera.replaceFirst("news;","")+"\n");
                }
                else if (odSerwera.startsWith("Temat;new;")){
                    String temat = odSerwera.replaceFirst("Temat;new;", "");
                    topiclist.add(temat);
                    System.out.println("+"+odSerwera);
                    klientView.jTextArea.append("Dodano temat "+temat+"\n");
                }
                else if (odSerwera.startsWith("Temat;del;")){
                    String temat = odSerwera.replaceFirst("Temat;del;","");
                    topiclist.remove(temat);
                    subTopics.remove(temat);
                    klientView.subscriptions.setText("Subscriptions: "+subTopics);
                    System.out.println("-"+odSerwera);
                    klientView.jTextArea.append("Usunieto temat "+temat+"\n");
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
