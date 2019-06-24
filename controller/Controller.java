package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import model.collection.HandleFiles;
import model.collection.Hero;
import view.MenuView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;

import static javafx.application.Application.launch;

public class Controller {
    public static void createAll() throws Exception{
        createAllMenus();
        createAllDataFromJSON();
    }
    public static void createAllDataFromJSON() throws Exception{
        HandleFiles.createHeroes();
        HandleFiles.createItems();
        HandleFiles.createMinions();
        HandleFiles.createSpells();
        HandleFiles.createStringOfPlayers();
    }



    public static void createAllMenus() throws FileNotFoundException {
        AllDatas.account= new LinkedListMenus("Account",true);
        AllDatas.leaderboard = new LinkedListMenus("Leaderboard",false);
        AllDatas.commandLine = new LinkedListMenus("Command Line",false);
        AllDatas.collection = new LinkedListMenus("Collection",false);
        AllDatas.shop = new LinkedListMenus("Shop",false);
        AllDatas.battle = new LinkedListMenus("Battle",false);
        AllDatas.help = new LinkedListMenus("Help",false);
        AllDatas.account.getChilds().add(AllDatas.commandLine);
        AllDatas.leaderboard.setParent(AllDatas.account);
        AllDatas.commandLine.setParent(AllDatas.account);
        AllDatas.commandLine.getChilds().add(AllDatas.collection);
        AllDatas.commandLine.getChilds().add(AllDatas.shop);
        AllDatas.commandLine.getChilds().add(AllDatas.battle);
        AllDatas.collection.setParent(AllDatas.commandLine);
        AllDatas.shop.setParent(AllDatas.commandLine);
        AllDatas.battle.setParent(AllDatas.commandLine);

   //     Controller.enterLoginMenu();
//        AllDatas.account.setCommandsForHelp("create account [user name]","login [user name]","show leaderboard","save");
//        AllDatas.leaderboard.setCommandsForHelp("exit");
//        AllDatas.commandLine.setCommandsForHelp("Collection","Shop","Battle","exit","Help","logout");
//        AllDatas.collection.setCommandsForHelp("exit","show","search [card name | item name]","save","create deck[deck name]"
//        ,"delete deck [deck name]","add [card id | card id | hero id] to deck [deck name]",
//                "remove [card id | card id| hero id] from deck [deck name]","validate deck [deck name]","select deck [deck name]"
//        ,"show all decks","show deck [deck name]","Help");
//        AllDatas.shop.setCommandsForHelp("exit","show collection","search [item name | card name]","search collection [item name | card name]",
//                "buy [card name | item name]","sell [card id | card id]","show","Help");
        LinkedListMenus.allMenus.add(AllDatas.account);
        LinkedListMenus.allMenus.add(AllDatas.leaderboard);
        LinkedListMenus.allMenus.add(AllDatas.commandLine);
        LinkedListMenus.allMenus.add(AllDatas.collection);
        LinkedListMenus.allMenus.add(AllDatas.shop);
        LinkedListMenus.allMenus.add(AllDatas.battle);
        LinkedListMenus.allMenus.add(AllDatas.help);

    }
    public static void handleCommands(Scanner scanner) throws Exception{
        switch (LinkedListMenus.whichMenuNow().getMenuName()) {
            case "Account":
                System.out.println("account");
                MenusCommandController.accountController(scanner);
                break;
            case "Leaderboard":
                System.out.println("leaderboard");
                MenusCommandController.leaderboardController(scanner);
                break;
            case "Command Line":
                System.out.println("command line");
                MenusCommandController.commandLineController(scanner);
                break;
            case "Collection":
                System.out.println("collection");
                MenusCommandController.collectionController(scanner);
                break;
            case "Shop":
                System.out.println("shop");
                MenusCommandController.shopController(scanner);
                break;
            case "Battle":
                System.out.println("battle");
                if (Game.getInstance().isPlayer1Turn()) {
                    MenusCommandController.battleController(scanner);
                    System.out.println(Game.getInstance().getPlayer1().getNumOfMana());
                }else{
//                    System.out.println("AIAIAIAIAAI");
                    System.out.println(Game.getInstance().getPlayer2().getNumOfMana());
                    AI.insertCardTillPossible();
                    AI.moveTillPossible();
                    System.out.println(Game.getInstance().getPlayer2().getNumOfMana());
                }
                Map.show();
                Hand.showHand();


                break;
            case "Help":
                System.out.println("help");
                MenusCommandController.helpController(scanner);
                break;
        }
    }

    public static void enterLoginMenu() throws FileNotFoundException {
        AllDatas.currentRoot = AllDatas.account.getRoot();
        AllDatas.currentScene = AllDatas.account.getScene();
        AllDatas.account.setNowInThisMenu(true);

        MenuView.showLoginMenu();
    }

    public static void enterMainMenu() throws FileNotFoundException {
        AllDatas.currentRoot = AllDatas.commandLine.getRoot();
        AllDatas.currentScene = AllDatas.commandLine.getScene();
        AllDatas.commandLine.setNowInThisMenu(true);
        AllDatas.account.setNowInThisMenu(false);

        MenuView.showMainMenu();
    }

    public static void enterShop() throws FileNotFoundException {
        AllDatas.currentRoot = AllDatas.shop.getRoot();
        AllDatas.currentScene = AllDatas.shop.getScene();
        AllDatas.shop.setNowInThisMenu(true);
        AllDatas.commandLine.setNowInThisMenu(false);

        MenuView.showShop();
    }

    public static void enterColllection(){
        AllDatas.currentRoot = AllDatas.collection.getRoot();
        AllDatas.currentScene = AllDatas.collection.getScene();
    }

    public static void enterBattle() throws FileNotFoundException, CloneNotSupportedException {
        Game.getInstance().setMap(new Map());
        Hero.insertHeroInMap();
        Hand.setHand();
        AI.createAIPlayer();
        AllDatas.currentRoot = AllDatas.battle.getRoot();
        AllDatas.currentScene = AllDatas.battle.getScene();
        AllDatas.battle.setNowInThisMenu(true);
        AllDatas.commandLine.setNowInThisMenu(false);
        MenuView.showBattle();
    }

    public static void enterLeaderBoard(){
        AllDatas.currentRoot = AllDatas.leaderboard.getRoot();
        AllDatas.currentScene = AllDatas.leaderboard.getScene();
    }



}
