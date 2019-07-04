package network;

import com.google.gson.Gson;
import controller.Controller;
import javafx.application.Platform;
import model.AllDatas;
import model.Game;
import model.Player;
import model.Shop;
import model.collection.Account;
import model.collection.Card;
import model.collection.HandleFiles;
import view.GameView;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.Socket;

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
        switch (jsonType){
            case "Player" :
                Player player = gson.fromJson(jsonString,Player.class);
                functionsOfPlayerForServer(player,dos);
                break;
            case "Card":
                Card card = gson.fromJson(jsonString, Card.class);
                functionsOfCardForServer(card, dos);
                break;
        }
    }

    public void handleMessageReceivedByClient(){
        Gson gson = new Gson();
        switch (jsonType){
            case "String" :
                String str = gson.fromJson(jsonString,String.class);
                System.out.println(str);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        GameView.printInvalidCommandWithThisContent(str);
                    }
                });
                break;
            case "Player" :
                Player player = gson.fromJson(jsonString,Player.class);
                functionsOfPlayerForClient(player);
                break;
            case "Card":
                Card card = gson.fromJson(jsonString, Card.class);
                functionsOfCardForClient(card);
                break;
        }
    }

    public void functionsOfCardForClient(Card card){
        switch (functionName){
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

    public void functionsOfPlayerForClient(Player player){
        switch (functionName){
            case "setPlayer" :
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
        }
    }
    public void functionsOfPlayerForServer(Player player,PrintStream dos){
        switch (functionName){
            case "login" :
                try {
                    Account.login(player.getUserName(),player.getPassword(),dos);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "createAccount" :
                try {
                    Account.createAccount(player.getUserName(),player.getPassword(),dos);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "save" :
                Account.savePlayer(player,dos);
        }
    }

    public void functionsOfCardForServer(Card card, PrintStream dos){
        Gson gson = new Gson();
        switch (functionName){
            case "checkBuy":
                for (Card card1 : Server.getCards()){
                    if (card1.getName().equals(card.getName())){
                        if (card1.getNumInShop() > 0){
                            card1.setNumInShop(card1.getNumInShop() - 1);
                            HandleFiles.writeChangesToJson(card1);
                            String jsonString = gson.toJson(card, Card.class);
                            Message message = new Message(jsonString, "Card", "buyCard");
                            dos.println(message.messageToString());
                            dos.flush();
                        }
                        else{
                            String jsonString = gson.toJson("Shop does not have this card!", String.class);
                            Message message = new Message(jsonString, "String", "printAlert");
                            dos.println(message.messageToString());
                            dos.flush();
                        }
                    }
                }
                break;
            case "sellCard":
                for (Card card1 : Server.getCards()){
                    if (card1.getName().equals(card.getName())){
                        card1.setNumInShop(card1.getNumInShop() + 1);
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
        System.out.println(parts[0]);
        System.out.println(parts[1]);
        System.out.println(parts[2]);
        return new Message(parts[0], parts[1],parts[2]);
    }

    public String messageToString() {
        return jsonString + "@" + jsonType + "@" + functionName;
    }
}
