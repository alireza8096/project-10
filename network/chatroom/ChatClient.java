package network.chatroom;

import com.google.gson.Gson;
import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Player;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private Socket socket;
    private Scanner dis;
    private PrintStream dos;
    private Button send;
    private TextField message;
    private static Text[] texts = new Text[10];


    public static Text[] getTexts() {
        return texts;
    }

    public void handleEvent(String name, PrintStream dos) {
        send.setOnMouseClicked(event -> {
            String str = message.getText();
            dos.println(name + " : " + str);
            dos.flush();
            message.clear();
        });
        message.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String str = message.getText();
                dos.println( name + " : " + str);
                dos.flush();
                message.clear();
            }
        });
    }

    public ChatClient(Player player, Button send, TextField message, VBox textScenes,Button back) {
        this.message = message;
        this.send = send;

        for (int i = 0; i < 10; i++) {
            texts[i] = new Text();
            textScenes.getChildren().add(texts[i]);
        }
        try {
            this.socket = new Socket("46.209.82.52", 8888);
            this.dis = new Scanner(socket.getInputStream());
            this.dos = new PrintStream(socket.getOutputStream());
            back.setOnMouseClicked(event -> {
                Thread.currentThread().interrupt();
                dos.close();
                try {
                    Controller.enterMainMenu();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            new Thread(() -> {
                new Thread(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        handleEvent(player.getUserName(), dos);
                    }
                }).start();
                new Thread(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        if (dis.hasNext()) {
                            for (int i = 0; i < 10; i++) {
                                if (i != 9) {
                                    texts[i].setText(texts[i + 1].getText());
                                } else {
                                    texts[9].setText(dis.nextLine());
                                }
                            }
                        }
                    }
                }).start();

            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}