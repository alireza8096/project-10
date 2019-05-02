package controller;

import model.collection.Account;

import java.util.Scanner;

public class MenusCommandController{
    public static boolean hasEnteredAccount = false;
    public static void accountController(Scanner scanner) throws Exception {
        String command = scanner.nextLine();
        String[] commandSplitted = command.split(" ");
        if (!hasEnteredAccount)
            AccountController.createAccount(commandSplitted, scanner);
        if (!hasEnteredAccount)
            AccountController.login(commandSplitted, scanner);
        if (!hasEnteredAccount)
            AccountController.showLeaderboard(commandSplitted, scanner);
        if (!hasEnteredAccount)
            AccountController.save(commandSplitted);
        if (!hasEnteredAccount)
            AccountController.logout(commandSplitted);
        if (!hasEnteredAccount)
            AccountController.help("Account", commandSplitted, scanner);
        if (!hasEnteredAccount) {
            System.out.println("Command is not supported in this menu");
        }
        hasEnteredAccount = false;
    }
    public static void leaderboardController(Scanner scanner) throws Exception {
        String command = scanner.nextLine();
        if (command.compareToIgnoreCase("exit") == 0) {
            Controller.leaderboard.setNowInThisMenu(false);
            Controller.account.setNowInThisMenu(true);
        }
        else{
            System.out.println("Command is not supported in this menu");
        }
    }
    public static void helpController(Scanner scanner) throws Exception{
        String command = scanner.nextLine();
        if(command.compareToIgnoreCase("exit") == 0){
            Controller.help.setNowInThisMenu(false);
            Controller.help.getParent().setNowInThisMenu(true);
        }
    }
}
