package network.chatroom;

import com.google.gson.Gson;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServer {
    private ServerSocket serverSocket;
    private static ArrayList<Socket> clients = new ArrayList<>();

    public static void removeSocket(Socket socket) {
        ArrayList<Socket> copy = new ArrayList<>(clients);
        for (Socket check :
                copy) {
            if (socket.equals(check)) {
                clients.remove(socket);
            }
        }
    }

    public ChatServer() throws IOException {
        serverSocket = new ServerSocket(8888);
        System.out.println("chat server created");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("connected");
            clients.add(socket);
            new Thread(() -> {
                try {
                    PrintStream dos;
                    Scanner dis = new Scanner(socket.getInputStream());
                    while (dis.hasNext()) {
                        String message = dis.nextLine();
                        for (Socket client : clients) {
                            dos = new PrintStream(client.getOutputStream());
                            dos.println(message);
                            dos.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
