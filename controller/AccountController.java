package controller;

import com.google.gson.Gson;
import model.AllDatas;
import model.LinkedListMenus;
import model.collection.Account;
import model.collection.HandleFiles;
import network.Message;
import network.Server;
import org.json.simple.JSONObject;

import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.security.KeyStore;
import java.util.*;
import java.util.function.IntBinaryOperator;

public class AccountController{
    public static void getLeaderBoard(PrintStream dos){
        ArrayList<String> playersAndWins = new ArrayList<>();
        if (Server.getPlayers().size() != 0) {
            HashMap<String,Integer> leaderBoard = new HashMap<>();
            for (int i = 1; i <= Server.getPlayers().size(); i++) {
                String name = Server.getPlayers().get(i-1);
                JSONObject json = null;
                try {
                    json = (JSONObject) Account.readPlayerFromFile(HandleFiles.BEFORE_RELATIVE + "model/collection/players/" +name+".json");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                leaderBoard.put(name,Integer.parseInt(json.get("numOfWins").toString()));
            }
            Object[] sorted = leaderBoard.entrySet().toArray();
            Arrays.sort(sorted, (o1, o2) -> ((Map.Entry<String,Integer>) o2).getValue()
                    .compareTo(((Map.Entry<String,Integer>)o1).getValue()));
            int counter =1;
            for (Object e : sorted) {
                playersAndWins.add(counter +" - " + ((Map.Entry<String, Integer>) e).getKey() + " - Wins : "
                        + ((Map.Entry<String, Integer>) e).getValue());
                counter++;
            }
            Gson gson = new Gson();
            String leaderBoardArray = gson.toJson(playersAndWins,ArrayList.class);
            dos.println(new Message(leaderBoardArray,"ArrayList","showLeaderBoard").messageToString());
            dos.flush();
        } else {
            Gson gson = new Gson();
            String alert = gson.toJson("No Player exists in leader board",String.class);
            dos.println(new Message(alert,"String","printAlert").messageToString());
            dos.flush();
        }
    }
     public static void showLeaderboard(String[] commands) throws Exception {
         if (commands.length == 2 && commands[0].compareToIgnoreCase("show") == 0
                 && commands[1].compareToIgnoreCase("leaderboard") == 0) {
             AllDatas.leaderboard.setNowInThisMenu(true);
             AllDatas.leaderboard.getParent().setNowInThisMenu(false);
             if (Server.getPlayers().size() != 0) {
                 HashMap<String,Integer> leaderBoard = new HashMap<>();
                 for (int i = 1; i <= Server.getPlayers().size(); i++) {
                     String name = Server.getPlayers().get(i-1);
                     JSONObject json =(JSONObject) Account.readPlayerFromFile(Account.PLAYERS_FOLDER+name+".json");
                     leaderBoard.put(name,Integer.parseInt(json.get("numOfWins").toString()));
                 }
                 Object[] sorted = leaderBoard.entrySet().toArray();
                 Arrays.sort(sorted, (o1, o2) -> ((Map.Entry<String,Integer>) o2).getValue()
                         .compareTo(((Map.Entry<String,Integer>)o1).getValue()));
                 int counter =1;
                 for (Object e : sorted) {
                     System.out.println(counter +" - UserName : " + ((Map.Entry<String, Integer>) e).getKey() + " - Wins : "
                             + ((Map.Entry<String, Integer>) e).getValue());
                     counter++;
                 }
             } else {
                 System.out.println("no player exists in leaderboard");
             }
             AllDatas.hasEnteredAccount = true;
         }
     }

     public static void save(String[] commands){
         if(commands.length == 1 && commands[0].compareToIgnoreCase("save") == 0){
             System.out.println("Saved!");
             AllDatas.hasEnteredAccount = true;
         }
     }

    public static void help(String[] commands){
         if(commands.length == 1 && commands[0].compareToIgnoreCase("help") == 0){
             AllDatas.help.setParent(AllDatas.account);
             AllDatas.help.setNowInThisMenu(true);
             AllDatas.account.setNowInThisMenu(false);
             for (String commandName: AllDatas.account.getCommandsForHelp()) {
                 System.out.println(commandName);
             }
             AllDatas.hasEnteredAccount = true;
         }
    }

}
