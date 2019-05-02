package controller;

import model.Game;
import model.LinkedListMenus;
import model.Menu;
import model.collection.HandleFiles;

import java.util.Scanner;

public class Controller {
    public static LinkedListMenus account;
    public static LinkedListMenus leaderboard;
    public static LinkedListMenus commandLine;
    public static LinkedListMenus collection;
    public static LinkedListMenus shop;
    public static LinkedListMenus battle;
    public static LinkedListMenus help;
    public static Game gameBeingPlayed;

    public static void createAll(){
        createAllMenus();
        createAllDataFromJSON();
    }
    public static void createAllDataFromJSON(){
        HandleFiles.createStringOfHeroes();
        HandleFiles.createStringOfItems();
        HandleFiles.createStringOfMinions();
        HandleFiles.createStringOfPlayers();
        HandleFiles.createStringOfSpells();
    }
    public static void createAllMenus(){
        account= new LinkedListMenus("Account",true);
        leaderboard = new LinkedListMenus("Leaderboard",false);
        commandLine = new LinkedListMenus("Command Line",false);
        collection = new LinkedListMenus("Collection",false);
        shop = new LinkedListMenus("Shop",false);
        battle = new LinkedListMenus("Battle",false);
        help = new LinkedListMenus("Help",false);
        account.getChilds().add(commandLine);
        leaderboard.setParent(account);
        commandLine.setParent(account);
        commandLine.getChilds().add(collection);
        commandLine.getChilds().add(shop);
        commandLine.getChilds().add(battle);
        collection.setParent(commandLine);
        shop.setParent(commandLine);
        battle.setParent(commandLine);
        account.setCommandsForHelp("create account [user name]","login [user name]","show leaderboard","save","logout");
        leaderboard.setCommandsForHelp("exit");
        commandLine.setCommandsForHelp("Collection","Shop","Battle","exit","Help");
        collection.setCommandsForHelp("exit","show","search [card name | item name]","save","create deck[deck name]"
        ,"delete deck [deck name]","add [card id | card id | hero id] to deck [deck name]",
                "remove [card id | card id| hero id] from deck [deck name]","validate deck [deck name]","select deck [deck name]"
        ,"show all decks","show deck [deck name]","Help");
        shop.setCommandsForHelp("exit","show collection","search [item name | card name]","search collection [item name | card name]",
                "buy [card name | item name]","sell [card id | card id]","show","Help");
        LinkedListMenus.allMenus.add(account);
        LinkedListMenus.allMenus.add(leaderboard);
        LinkedListMenus.allMenus.add(commandLine);
        LinkedListMenus.allMenus.add(collection);
        LinkedListMenus.allMenus.add(shop);
        LinkedListMenus.allMenus.add(battle);
        LinkedListMenus.allMenus.add(help);

    }

    public static void handleCommands(Scanner scanner) throws Exception{
        switch (LinkedListMenus.whichMenuNow().getMenuName()){
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
                break;
            case "Collection" :
                System.out.println("collection");
                break;
            case "Shop" :
                System.out.println("shop");
                break;
            case "Battle" :
                System.out.println("battle");
                break;
            case "Help" :
                System.out.println("help");
                MenusCommandController.helpController(scanner);
                break;
        }
    }
}
