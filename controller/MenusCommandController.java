package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.AllDatas;
import model.Game;
import model.LinkedListMenus;
import model.Shop;
import model.collection.Account;
import view.BattleView;
import view.GameView;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

public class MenusCommandController {
    public static void accountController(Scanner scanner) throws Exception {
        String command = scanner.nextLine();
        String[] commandsSplitted = command.split(" ");
        AccountController.createAccount(commandsSplitted, scanner);
        AccountController.login(commandsSplitted, scanner);
        AccountController.showLeaderboard(commandsSplitted);
        AccountController.save(commandsSplitted);
        AccountController.help(commandsSplitted);
        if (commandsSplitted.length == 1 && commandsSplitted[0].compareToIgnoreCase("exit") == 0) {
            AllDatas.gameBoolean = false;
//            AllDatas.hasEnteredAccount = true;
        }
        if (!AllDatas.hasEnteredAccount) {
            System.out.println("Command is not supported in this menu");
        }
        AllDatas.hasEnteredAccount = false;
    }

    public static void leaderboardController(Scanner scanner){
        String command = scanner.nextLine();
        if (command.compareToIgnoreCase("exit") == 0) {
            AllDatas.leaderboard.setNowInThisMenu(false);
            AllDatas.account.setNowInThisMenu(true);
        } else {
            System.out.println("Command is not supported in this menu");
        }
    }

    public static void helpController(Scanner scanner){
        System.out.println("Type \"exit\" to return");
        String command = scanner.nextLine();
        if (command.compareToIgnoreCase("exit") == 0) {
            AllDatas.help.setNowInThisMenu(false);
            AllDatas.help.getParent().setNowInThisMenu(true);
        } else {
            System.out.println("Command is not supported in this menu");
        }
    }

    public static void commandLineController(Scanner scanner) {
        for (String command: AllDatas.commandLine.getCommandsForHelp()) {
            System.out.println(command);
        }
        String command = scanner.nextLine();
        String[] commandsSplitted = command.split(" ");
        if (commandsSplitted.length == 2 && commandsSplitted[0].compareToIgnoreCase("enter") == 0) {
            CommandLineController.enterMenu(commandsSplitted[1]);
        } else if (commandsSplitted.length == 1 && commandsSplitted[0].compareToIgnoreCase("exit") == 0) {
            AllDatas.gameBoolean = false;
        } else if (commandsSplitted.length == 1 && commandsSplitted[0].compareToIgnoreCase("help") == 0) {
            CommandLineController.help();
        }
        else if(commandsSplitted.length ==1 && commandsSplitted[0].compareToIgnoreCase("logout") == 0){
            CommandLineController.logout(commandsSplitted);
        }
        else {
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
        CollectionController.help(commandsSplitted);
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

    public static void shopController(Scanner scanner) throws Exception {
        String command = scanner.nextLine();
        String[] commandsSplitted = command.split(" ");
        ShopController.showCollection(commandsSplitted);
        ShopController.searchInShop(commandsSplitted);
        ShopController.searchInCollection(commandsSplitted);
        ShopController.buy(commandsSplitted);
        ShopController.sell(commandsSplitted);
        ShopController.showShop(commandsSplitted);
        ShopController.help(commandsSplitted);
        if (commandsSplitted.length == 1 && commandsSplitted[0].compareToIgnoreCase("exit") == 0) {
            AllDatas.shop.setNowInThisMenu(false);
            AllDatas.commandLine.setNowInThisMenu(true);
            AllDatas.hasEnteredShop = true;
        }
        if (!AllDatas.hasEnteredShop) {
            System.out.println("Command is not supported in this menu");
        }
        AllDatas.hasEnteredShop = false;
    }

    public static void battleController(Scanner scanner) throws Exception{
        String command = scanner.nextLine();
        String[] commandsSplitted = command.split(" ");
        if(Game.getInstance().isPlayer1Turn()) {
            BattleView.showGameInfo(commandsSplitted);
            BattleView.showMyMinions(commandsSplitted);
            BattleView.showOpponentMinions(commandsSplitted);
            BattleView.showCardInfo(commandsSplitted);
            BattleView.showHand(commandsSplitted);
            BattleController.insertCardInFieldCommand(command);
            BattleController.startGameCommand(commandsSplitted);
            BattleController.endTurnCommand(commandsSplitted);
//            BattleController.enterGraveyard(commandsSplitted);

            if (commandsSplitted.length == 2 && commandsSplitted[0].compareToIgnoreCase("select") == 0
                    && commandsSplitted[1].matches("[\\d]+")) {
                BattleController.selectCardById(commandsSplitted, scanner);
            }
            if (commandsSplitted.length == 1 && commandsSplitted[0].compareToIgnoreCase("exit") == 0) {
                AllDatas.battle.setNowInThisMenu(false);
                AllDatas.commandLine.setNowInThisMenu(true);
                AllDatas.hasEnteredBattle = true;
            }
            if (!AllDatas.hasEnteredBattle) {
                System.out.println("Command is not supported in this menu");
            }
            AllDatas.hasEnteredBattle= false;

        }
//        else{
//            System.out.println("AIAIAIAIAIAIAIAIAIAI");
////            AI.moveTillPossible();
////            AI.attckTillPossible();
////            BattleController.changeTurn();
//        }
        
    }

    public static void loginMenuEventHandler(Button loginButton, Button createAccountButton, TextField username, TextField password){
        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    String name = username.getText();
                    String passWord = password.getText();
                    Account.login(name, passWord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        createAccountButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    String name = username.getText();
                    String passWord = password.getText();
                    Account.createAccount(name, passWord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void handleEventsOfMainMenu(Hyperlink shop, Hyperlink collection, Hyperlink battle,
                                              Hyperlink help, Hyperlink exit, Hyperlink logout){

        shop.setOnAction(event -> Controller.enterShop());

        collection.setOnAction(event -> Controller.enterColllection());

        battle.setOnAction(event -> Controller.enterBattle());

        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        exit.setOnAction(event -> System.exit(0));

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    LinkedListMenus.whichMenuNow().backFromThisMenu();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}