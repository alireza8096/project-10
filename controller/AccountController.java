package controller;

import model.AllDatas;
import model.LinkedListMenus;
import model.collection.Account;
import network.Server;
import org.json.simple.JSONObject;

import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.util.*;
import java.util.function.IntBinaryOperator;

public class AccountController{
//     public static void createAccountCommand(String[] commands, Scanner scanner) throws Exception{
//        if(commands[0].compareToIgnoreCase("create") == 0
//                && commands[1].compareToIgnoreCase("account") == 0
//                && commands.length > 2){
//            String username=CollectionController.createName(commands,2);
//            Account.createAccount(username,scanner);
//            AllDatas.hasEnteredAccount = true;
//        }
//    }

//     public static void login(String[] commands, Scanner scanner) throws Exception{
//        if(commands[0].compareToIgnoreCase("login") == 0 && commands.length>1){
//            String username=CollectionController.createName(commands,2);
//            Account.login(username,scanner);
//            AllDatas.hasEnteredAccount = true;
//        }
//    }
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
                 Arrays.sort(sorted, new Comparator<Object>() {
                     public int compare(Object o1,Object o2){
                         return ((Map.Entry<String,Integer>) o2).getValue()
                                 .compareTo(((Map.Entry<String,Integer>)o1).getValue());
                     }
                 });
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
