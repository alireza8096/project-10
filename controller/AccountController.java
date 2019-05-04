package controller;

import model.AllDatas;
import model.LinkedListMenus;
import model.collection.Account;

import java.util.Scanner;

public interface AccountController{
     static void createAccount(String[] commands, Scanner scanner) throws Exception{
        if(commands[0].compareToIgnoreCase("create") == 0
                && commands[1].compareToIgnoreCase("account") == 0
                && commands.length > 2){
            String username=CollectionController.createName(commands,2);
            Account.createAccount(username,scanner);
            AllDatas.hasEnteredAccount = true;
        }
    }
     static void login(String[] commands, Scanner scanner) throws Exception{
        if(commands[0].compareToIgnoreCase("login") == 0 && commands.length>1){
            String username=CollectionController.createName(commands,2);
            Account.login(username,scanner);
            AllDatas.hasEnteredAccount = true;
        }
    }
    //showLeaderBoard does not have num of wins yet
     static void showLeaderboard(String[] commands, Scanner scanner) throws Exception {
         if (commands.length == 2 && commands[0].compareToIgnoreCase("show") == 0
                 && commands[1].compareToIgnoreCase("leaderboard") == 0) {
             AllDatas.leaderboard.setNowInThisMenu(true);
             AllDatas.leaderboard.getParent().setNowInThisMenu(false);
             if (Account.getPlayers().size() != 0) {
                 for (int i = 1; i <= Account.getPlayers().size(); i++) {
                     System.out.println(i + " -UserName :" + Account.getPlayers().get(i - 1));
                 }
             } else {
                 System.out.println("no player exists in leaderboard");
             }
             AllDatas.hasEnteredAccount = true;
         }
     }
     //save is bullshit :)
     static void save(String[] commands){
         if(commands.length == 1 && commands[0].compareToIgnoreCase("save") == 0){
             System.out.println("Saved!");
             AllDatas.hasEnteredAccount = true;
         }
     }
     //logout is more bullshit than save :))
    static void logout(String[] commands){
         if(commands.length == 1 && commands[0].compareToIgnoreCase("logout") == 0){
             System.out.println("logout :/ ");
             AllDatas.hasEnteredAccount = true;
         }
    }
    static void help(String[] commands){
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
