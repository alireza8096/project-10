package network.chatroom;

import com.google.gson.Gson;
import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.AllDatas;
import model.Player;
import model.collection.HandleFiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private Socket socket;
    private Scanner dis;
    private PrintStream dos;
    private ImageView send;
    private TextField message;
    private Text[] texts = new Text[15];
    private Rectangle[] textScenes = new Rectangle[15];

    {
        for (int i = 0; i < 15; i++) {
            texts[i] = new Text();
            textScenes[i] = new Rectangle();
            textScenes[i].setArcHeight(50);
            textScenes[i].setArcWidth(50);
            textScenes[i].setX(700);
            textScenes[i].setY(100 + 40 * i);
            textScenes[i].setHeight(20);
            textScenes[i].setFill(null);
            texts[i].setX(705);
            texts[i].setY(115 + 40 * i);
            texts[i].setFont(Font.loadFont(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 15));
            textScenes[i].setScaleX(1.7);
            textScenes[i].setScaleY(1.7);
            texts[i].setScaleX(1.5);
            texts[i].setScaleY(1.5);
            AllDatas.currentRoot.getChildren().add(textScenes[i]);
            AllDatas.currentRoot.getChildren().add(texts[i]);
        }
    }

    public void handleEvent(String name, PrintStream dos) {
        send.setOnMouseClicked(event -> {
            String str = message.getText();
            dos.println(name + ":" + str);
            dos.flush();
            message.clear();
        });
        send.setOnMouseEntered(event -> send.setEffect(new Glow(0.6)));
        send.setOnMouseExited(event -> send.setEffect(null));
        message.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String str = message.getText();
                dos.println(name + ":" + str);
                dos.flush();
                message.clear();
            }
        });
    }

    public ChatClient(Player player, ImageView send, TextField message, ImageView back) throws FileNotFoundException {
        this.message = message;
        this.send = send;
        try {
            this.socket = new Socket("localhost", 8888);
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
            back.setOnMouseEntered(event -> back.setEffect(new Glow(0.6)));
            back.setOnMouseExited(event -> back.setEffect(null));
            new Thread(() -> {
                new Thread(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        handleEvent(player.getUserName(), dos);
                    }
                }).start();
                new Thread(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        if (dis.hasNext()) {
                            for (int i = 0; i < 15; i++) {
                                if (i != 14) {
                                    texts[i].setText(texts[i + 1].getText());
                                } else {
                                    texts[14].setText(dis.nextLine());//, player.getUserName()));
                                }
                            }
                            for (int i = 0; i < 15; i++) {
                                if (texts[i].getText().contains(player.getUserName())) {
                                    textScenes[i].setX(1040);
                                    textScenes[i].setFill(Color.rgb(186, 179, 196));
                                    texts[i].setX(1045);
                                    texts[i].setFill(Color.rgb(52, 45, 63));
                                    textScenes[i].setWidth(texts[i].getBoundsInLocal().getMaxX() - texts[i].getBoundsInLocal().getMinX() + 10);
                                } else {
                                    if (texts[i].getText().length() != 0) {
                                        textScenes[i].setX(635);
                                        textScenes[i].setFill(Color.rgb(96, 138, 184));
                                        texts[i].setX(640);
                                        texts[i].setFill(Color.rgb(186, 252, 253));
                                        textScenes[i].setWidth(texts[i].getBoundsInLocal().getMaxX() - texts[i].getBoundsInLocal().getMinX() + 10);
                                    }
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

    public static String stringToShow(String str, String name) {
        String returnStr = "";
        String[] parts = str.split(":");
        for (int i = 1; i < parts.length; i++) {
            returnStr = returnStr + parts[i];
        }
        if (str.contains(name)) {
            return returnStr + " : " + parts[0];
        } else {
            return parts[0] + " : " + returnStr;
        }
    }
}