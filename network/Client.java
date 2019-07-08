package network;

import network.chatroom.ChatClient;
import view.MainView;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

//    private MainView mainView;
    private Socket socket;
    private Scanner dis;
    private PrintStream dos;
    private Thread clientThread;
    private ChatClient chatClient;
    private boolean inChat;

    public boolean isInChat() {
        return inChat;
    }

    public void setInChat(boolean inChat) {
        this.inChat = inChat;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

    public Thread getClientThread() {
        return clientThread;
    }

    public PrintStream getDos() {
        return dos;
    }

    public Scanner getDis() {
        return dis;
    }

    public Socket getSocket() {
        return socket;
    }

    public Client() throws IOException {
        this.socket = new Socket("localhost", 7766);
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dis = new Scanner(socket.getInputStream());
                    dos = new PrintStream(socket.getOutputStream());
                    while (!Thread.currentThread().isInterrupted()){
                        if (dis.hasNext()){
                            Message.stringToMessage(dis.nextLine()).handleMessageReceivedByClient();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        clientThread.start();
    }

}
