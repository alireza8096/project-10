package network;

import model.collection.HandleFiles;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static ArrayList<String> players = new ArrayList<>();
    private static ArrayList<Socket> clients = new ArrayList<>();
    private ServerSocket serverSocket;
//    private Scanner dis;
//    private PrintStream dos;
//
//    public void setDis(Scanner dis) {
//        this.dis = dis;
//    }

//    public void setDos(PrintStream dos) {
//        this.dos = dos;
//    }

//    public PrintStream getDos() {
//        return dos;
//    }

//    public Scanner getDis() {
//        return dis;
//    }

    public static ArrayList<String> getPlayers() {
        return players;
    }

    public Server() throws Exception {
        HandleFiles.createStringOfPlayers();
        serverSocket = new ServerSocket(7766);
        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("client accepted");
            Scanner dis = new Scanner(client.getInputStream());
            PrintStream dos = new PrintStream(client.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        if (dis.hasNext()) {
                            Message.stringToMessage(dis.nextLine()).handleMessageReceivedByServer(dos);
                        }
                    }
                }
            }).start();
        }
    }


    public static void main(String[] args) throws Exception {
        new Server();
    }
}
