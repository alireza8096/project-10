package network;

import controller.Controller;
import model.collection.Card;
import model.collection.HandleFiles;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static ArrayList<String> players = new ArrayList<>();
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<Socket> clients = new ArrayList<>();
    private ServerSocket serverSocket;
    public static ArrayList<String> getPlayers() {
        return players;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public Server() throws Exception {
        HandleFiles.createStringOfPlayers();
        serverSocket = new ServerSocket(8888);
        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("client accepted");
            Scanner dis = new Scanner(client.getInputStream());
            PrintStream dos = new PrintStream(client.getOutputStream());
            new Thread(() -> {
                while(true) {
                    if (dis.hasNext()) {
                        String instruction = dis.nextLine();
//                        System.out.println("instruction : " + instruction);
                        Message.stringToMessage(instruction).handleMessageReceivedByServer(dos);
                    }
                }
            }).start();
        }
    }


//    public static void main(String[] args) throws Exception {
//        new Server();
//    }
}
