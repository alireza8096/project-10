package controller;

import model.AllDatas;
import model.Game;
import model.LinkedListMenus;
import model.collection.HandleFiles;

import java.util.Scanner;

public interface Controller {
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
        AllDatas.account.setCommandsForHelp("create account [user name]","login [user name]","show leaderboard","save","logout");
        AllDatas.leaderboard.setCommandsForHelp("exit");
        AllDatas.commandLine.setCommandsForHelp("Collection","Shop","Battle","exit","Help");
        AllDatas.collection.setCommandsForHelp("exit","show","search [card name | item name]","save","create deck[deck name]"
        ,"delete deck [deck name]","add [card id | card id | hero id] to deck [deck name]",
                "remove [card id | card id| hero id] from deck [deck name]","validate deck [deck name]","select deck [deck name]"
        ,"show all decks","show deck [deck name]","Help");
        AllDatas.shop.setCommandsForHelp("exit","show collection","search [item name | card name]","search collection [item name | card name]",
                "buy [card name | item name]","sell [card id | card id]","show","Help");
        LinkedListMenus.allMenus.add(AllDatas.account);
        LinkedListMenus.allMenus.add(AllDatas.leaderboard);
        LinkedListMenus.allMenus.add(AllDatas.commandLine);
        LinkedListMenus.allMenus.add(AllDatas.collection);
        LinkedListMenus.allMenus.add(AllDatas.shop);
        LinkedListMenus.allMenus.add(AllDatas.battle);
        LinkedListMenus.allMenus.add(AllDatas.help);

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
                MenusCommandController.commandLineController(scanner);
                break;
            case "Collection" :
                System.out.println("collection");
                MenusCommandController.collectionController(scanner);
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
