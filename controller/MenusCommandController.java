package controller;

import model.AllDatas;
import model.collection.Account;

import java.util.Collection;
import java.util.Scanner;

public interface MenusCommandController {
    public static void accountController(Scanner scanner) throws Exception {
        String command = scanner.nextLine();
        String[] commandSplitted = command.split(" ");
        AccountController.createAccount(commandSplitted, scanner);
        AccountController.login(commandSplitted, scanner);
        AccountController.showLeaderboard(commandSplitted, scanner);
        AccountController.save(commandSplitted);
        AccountController.logout(commandSplitted);
        AccountController.help("Account", commandSplitted);
        if (commandSplitted.length == 1 && commandSplitted[0].compareToIgnoreCase("exit") == 0) {
            AllDatas.gameBoolean = false;
            AllDatas.hasEnteredAccount = true;
        }
        if (!AllDatas.hasEnteredAccount) {
            System.out.println("Command is not supported in this menu");
        }
        AllDatas.hasEnteredAccount = false;
    }

    public static void leaderboardController(Scanner scanner) throws Exception {
        String command = scanner.nextLine();
        if (command.compareToIgnoreCase("exit") == 0) {
            AllDatas.leaderboard.setNowInThisMenu(false);
            AllDatas.account.setNowInThisMenu(true);
        } else {
            System.out.println("Command is not supported in this menu");
        }
    }

    public static void helpController(Scanner scanner) throws Exception {
        System.out.println("Type \"exit\" to return");
        String command = scanner.nextLine();
        if (command.compareToIgnoreCase("exit") == 0) {
            AllDatas.help.setNowInThisMenu(false);
            AllDatas.help.getParent().setNowInThisMenu(true);
        } else {
            System.out.println("Command is not supported in this menu");
        }
    }

    public static void commandLineController(Scanner scanner) throws Exception {
        for (int i = 1; i <= AllDatas.commandLine.getCommandsForHelp().size(); i++) {
            System.out.println(AllDatas.commandLine.getCommandsForHelp().get(i - 1));
        }
        String command = scanner.nextLine();
        String[] commandsSplitted = command.split(" ");
        if (commandsSplitted.length == 2 && commandsSplitted[0].compareToIgnoreCase("enter") == 0) {
            CommandLineController.enterMenu(commandsSplitted[1]);
        } else if (commandsSplitted.length == 1 && commandsSplitted[0].compareToIgnoreCase("exit") == 0) {
            AllDatas.gameBoolean = false;
        } else if (commandsSplitted.length == 1 && commandsSplitted[0].compareToIgnoreCase("help") == 0) {
            CommandLineController.help("Command Line");
        } else {
            System.out.println("Command is not supported in this menu");
        }
    }

    public static void collectionController(Scanner scanner) throws Exception {
        String command = scanner.nextLine();
        String[] commandsSplitted = command.split(" ");
        CollectionController.show(commandsSplitted);
        CollectionController.search(commandsSplitted);
        CollectionController.save(commandsSplitted);
        CollectionController.createDeck(commandsSplitted);
        CollectionController.deleteDeck(commandsSplitted);
        CollectionController.addToDeck(commandsSplitted, command);
        CollectionController.remove(commandsSplitted, command);
        CollectionController.validateDeck(commandsSplitted);
        CollectionController.selectDeck(commandsSplitted);
        CollectionController.showAllDecks(commandsSplitted);
        CollectionController.showDeckByName(commandsSplitted);
        CollectionController.help("Collection", commandsSplitted);
        if (commandsSplitted.length == 1 && commandsSplitted[0].compareToIgnoreCase("exit") == 0) {
            AllDatas.collection.setNowInThisMenu(false);
            AllDatas.commandLine.setNowInThisMenu(true);
            AllDatas.hasEnteredCollection = true;
        }
        if (!AllDatas.hasEnteredCollection) {
            System.out.println("Command is not supported in this menu");
        }
        AllDatas.hasEnteredCollection = false;
    }
}