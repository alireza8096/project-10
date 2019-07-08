package network;

import com.google.gson.Gson;
import controller.Controller;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.AllDatas;
import model.Game;
import model.Player;
import model.Shop;
import model.collection.Account;
import model.collection.Card;
import model.collection.HandleFiles;
import network.battle.BattleThread;
import network.battle.ClientForBattle;
import view.GameView;
import view.MainView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;

public class Message {
    private String jsonString;
    private String jsonType;
    private String functionName;

    public Message(String jsonString, String jsonType, String functionName) {
        this.jsonString = jsonString;
        this.jsonType = jsonType;
        this.functionName = functionName;
    }

    public void handleMessageReceivedByServer(PrintStream dos) {
        Gson gson = new Gson();
        switch (jsonType) {
            case "Player":
                Player player = gson.fromJson(jsonString, Player.class);
                functionsOfPlayerForServer(player, dos);
                break;
            case "Card":
                Card card = gson.fromJson(jsonString, Card.class);
                functionsOfCardForServer(card, dos);
                break;
            case "String":
                String message = gson.fromJson(jsonString, String.class);
                try {
                    functionsOfStringForServer(message, dos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void handleMessageReceivedByClient() {
        Gson gson = new Gson();
        switch (jsonType) {
            case "String":
                String str = gson.fromJson(jsonString, String.class);
                functionsOfStringForClient(str);
                break;
            case "Player":
                Player player = gson.fromJson(jsonString, Player.class);
                functionsOfPlayerForClient(player);
                break;
            case "Card":
                Card card = gson.fromJson(jsonString, Card.class);
                functionsOfCardForClient(card);
                break;
            case "ArrayList":
                ArrayList<String> users = gson.fromJson(jsonString, ArrayList.class);
                Platform.runLater(() -> {
                    VBox names = new VBox();
                    Text[] states = new Text[users.size()];
                    for (int i = 0; i < states.length; i++) {
                        states[i] = new Text(users.get(i));
                        names.getChildren().add(states[i]);
                    }
                    names.setSpacing(7);
//                    Rectangle backImage = new Rectangle(names.getWidth(), names.getHeight(),Color.RED);
                    System.out.println(names.getMaxWidth());
                    System.out.println(names.getMaxHeight());
                    Rectangle backImage = new Rectangle(names.getMaxWidth(), names.getMaxHeight(), Color.RED);

                    backImage.setArcWidth(30);
                    backImage.setArcHeight(30);
                    StackPane stack = new StackPane(backImage, names);
                    stack.setLayoutX(AllDatas.currentScene.getWidth() / 2 - stack.getWidth() / 2);
                    stack.setLayoutY(AllDatas.currentScene.getHeight() / 2 - stack.getHeight() / 2);
                    stack.setOnMouseClicked(event -> stack.setOpacity(0));
                    AllDatas.currentRoot.getChildren().add(stack);
                });
                break;
        }
    }

    public void functionsOfStringForClient(String str) {
        switch (functionName) {
            case "printAlert":
                Platform.runLater(() -> GameView.printInvalidCommandWithThisContent(str));
                break;
            case "sendToAll":
                if (MainView.getClient().isInChat()) {
                    MainView.getClient().getChatClient().handleMessagesReceived(str);
                }
                break;
            case "getName":
                sendClientNameToServer();
                break;
        }
    }

    public void sendClientNameToServer() {
        String name = Game.getInstance().getPlayer1().getUserName();

        Message message = new Message(name, "String", "showNameInServer");
        MainView.getClient().getDos().println(message.messageToString());
        MainView.getClient().getDos().flush();
    }

    public void functionsOfCardForClient(Card card) {
        switch (functionName) {
            case "buyCard":
                try {
                    Shop.buyCardAndAddToCollection(card.getName());
                } catch (CloneNotSupportedException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "sellCard":
                try {
                    Shop.sellCardAndRemoveFromCollection(card.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void functionsOfPlayerForClient(Player player) {
        switch (functionName) {
            case "setPlayer":
                Game createGame = new Game();
                Platform.runLater(() -> {
                    try {
                        Controller.enterMainMenu();
                        Game.setCurrentGame(createGame);
                        GameView.setAllImagesForCards(player);
                        Game.getInstance().setPlayer1(player);
                        Game.getInstance().setPlayer1Turn(true);
                        AllDatas.account.setNowInThisMenu(false);
                        AllDatas.commandLine.setNowInThisMenu(true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                break;
        }
    }

    public void functionsOfPlayerForServer(Player player, PrintStream dos) {
        switch (functionName) {
            case "login":
                try {
                    Account.login(player.getUserName(), player.getPassword(), dos);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "createAccount":
                try {
                    Account.createAccount(player.getUserName(), player.getPassword(), dos);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "save":
                Account.savePlayer(player, dos);
                break;
            case "enterBattle":
                handleEnterBattleCommandFromClient(player, dos);
                break;
        }
    }

    public void handleEnterBattleCommandFromClient(Player player, PrintStream dos){
        if (BattleThread.getBattleThreads()[0] == null){
            BattleThread.getBattleThreads()[0] = new ClientForBattle(player, dos);
        }else{
            new BattleThread(BattleThread.getBattleThreads()[0], new ClientForBattle(player, dos)).;
            BattleThread.getBattleThreads()[0] = null;
            BattleThread.getBattleThreads()[1] = null;
        }
    }

    public void functionsOfStringForServer(String str, PrintStream dos) throws IOException {
        Gson gson = new Gson();
        switch (functionName) {
            case "sendToChatroom":
                for (Socket socket : Server.getClients()) {
                    String sendToAll = gson.toJson(str, String.class);
                    PrintStream output = new PrintStream(socket.getOutputStream());
                    output.println(new Message(sendToAll, "String", "sendToAll").messageToString());
                    output.flush();
                }
                break;
            case "logout":
                ArrayList<String> copyOnline = new ArrayList<>(Server.getOnlinePlayers());
                for (String name : copyOnline) {
                    if (name.matches(str)) {
                        Server.getOnlinePlayers().remove(name);
                    }
                }
                break;
            case "showOnlines":
                ArrayList<String> states = new ArrayList<>();
                for (String name : Server.getPlayers()) {
                    if (nameIsOnline(name)) {
                        states.add(name + " is online");
                    } else states.add(name + " is offline");
                }
                String arrayList = gson.toJson(states, ArrayList.class);
                dos.println(new Message(arrayList, "ArrayList", "users").messageToString());
                dos.flush();
                break;
        }
    }

    public void functionsOfCardForServer(Card card, PrintStream dos) {
        Gson gson = new Gson();
        switch (functionName) {
            case "checkBuy":
                for (Card card1 : Server.getCards()) {
                    System.out.println("!@!@!@!@ : " + card1.getName());
                    if (card1.getName().equals(card.getName())) {
                        if (card1.getNumInShop() > 0) {
//                            Server.changeCardNumInShop(card1.getName(), -1);

//                            card1.setNumInShopProperty(card1.getNumInShopProperty() - 1);
//                            card1.setNumInShop(card1.getNumInShop() - 1);

//                            card1.setNumInShop(card1.getNumInShop() - 1);
                            HandleFiles.writeChangesToJson(card1);
                            String jsonString = gson.toJson(card, Card.class);
                            Message message = new Message(jsonString, "Card", "buyCard");
                            dos.println(message.messageToString());
                            dos.flush();
                        } else {
                            String jsonString = gson.toJson("Shop does not have this card!", String.class);
                            Message message = new Message(jsonString, "String", "printAlert");
                            dos.println(message.messageToString());
                            dos.flush();
                        }
                        break;
                    }
                }
                break;
            case "sellCard":
                for (Card card1 : Server.getCards()) {
                    if (card1.getName().equals(card.getName())) {
//                        Server.changeCardNumInShop(card1.getName(), -1);

//                        card1.setNumInShop(card1.getNumInShop() + 1);
//                        card1.setNumInShopProperty(card1.getNumInShopProperty() + 1);

//                        card1.setNumInShop(card.getNumInShop() + 1);
                        HandleFiles.writeChangesToJson(card1);
                        String jsonString = gson.toJson(card, Card.class);
                        Message message = new Message(jsonString, "Card", "buyCard");
                        dos.println(message.messageToString());
                        dos.flush();
                    }
                }
                break;

        }
    }

    public static Message stringToMessage(String toConvert) {
        String[] parts = toConvert.split("@");
        System.out.println(toConvert);
        System.out.println(parts[0]);
        System.out.println(parts[1]);
        System.out.println(parts[2]);
        return new Message(parts[0], parts[1], parts[2]);
    }

    public String messageToString() {
        return jsonString + "@" + jsonType + "@" + functionName;
    }

    public static boolean nameIsOnline(String name) {
        for (String check : Server.getOnlinePlayers()) {
            if (name.matches(check)) {
                return true;
            }
        }
        return false;
    }
}
