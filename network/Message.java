package network;

import com.google.gson.Gson;
import controller.Controller;
import javafx.application.Platform;
import model.AllDatas;
import model.Game;
import model.Player;
import model.collection.Account;
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
        switch (jsonType){
            case "Player" :
                Gson gson = new Gson();
                Player player = gson.fromJson(jsonString,Player.class);
                functionsOfPlayerForServer(player,dos);
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
