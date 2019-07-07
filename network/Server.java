package network;

import model.collection.Card;
import model.collection.HandleFiles;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static ArrayList<String> clientNames = new ArrayList<>();
    private static ArrayList<String> players = new ArrayList<>();
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<Socket> clients = new ArrayList<>();
    private static ArrayList<String> onlinePlayers = new ArrayList<>();
    private ServerSocket serverSocket;

    public static ArrayList<String> getClientNames() {
        return clientNames;
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
                while(true) {
                    if (dis.hasNext()) {
                        String instruction = dis.nextLine();
                        Message.stringToMessage(instruction).handleMessageReceivedByServer(dos);
                    }
                }
            }).start();
        }
    }
    public static void changeCardNumInShop(String cardName, int changeValue){
        System.out.println("Even HERE???????");
        for (Card card : cards){
            System.out.println("### card name : " + card.getName() + " ,  this card : " + cardName);
            if (card.getName().equals(cardName)){
                card.setNumInShopProperty(card.getNumInShopProperty() + changeValue);
                card.setNumInShop(card.getNumInShop() + changeValue);
                System.out.println("____________ card name : " + card.getNumInShopProperty());
            }
        }
    }
}
