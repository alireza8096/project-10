package network;

import model.collection.Card;
import model.collection.HandleFiles;
import network.battle.BattleThread;
import view.ServerView;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static ArrayList<String> players = new ArrayList<>();
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<Socket> clients = new ArrayList<>();
    private static ArrayList<String> onlinePlayers = new ArrayList<>();
    private ServerSocket serverSocket;
    private static BattleThread currentBattleThread;

    public static BattleThread getCurrentBattleThread() {
        return currentBattleThread;
    }

    public static void setCurrentBattleThread(BattleThread currentBattleThread) {
        Server.currentBattleThread = currentBattleThread;
    }

    public static ArrayList<String> getOnlinePlayers() {
        return onlinePlayers;
    }

    public static ArrayList<String> getPlayers() {
        return players;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public static ArrayList<Socket> getClients() {
        return clients;
    }

    public Server() throws Exception {
        HandleFiles.createStringOfPlayers();

        serverSocket = new ServerSocket(7766);

        System.out.println("main server created");
        while (true) {
            Socket client = serverSocket.accept();
            clients.add(client);
            System.out.println("client accepted");
            Scanner dis = new Scanner(client.getInputStream());
            PrintStream dos = new PrintStream(client.getOutputStream());
            new Thread(() -> {
                while (true) {
                    if (dis.hasNext()) {
                        String instruction = dis.nextLine();
                        Message.stringToMessage(instruction).handleMessageReceivedByServer(dos);
                    }
                }
            }).start();
        }
    }
}
