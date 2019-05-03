package controller;

import model.AllDatas;
import model.LinkedListMenus;

import java.util.LinkedList;

public interface CommandLineController {
    public static void enterMenu(String menuName){
        if(menuName.compareToIgnoreCase("collection") == 0){
            AllDatas.collection.setNowInThisMenu(true);
            AllDatas.commandLine.setNowInThisMenu(false);
        }
        else if(menuName.compareToIgnoreCase("shop") == 0){
            AllDatas.shop.setNowInThisMenu(true);
            AllDatas.commandLine.setNowInThisMenu(false);
        }
        else if(menuName.compareToIgnoreCase("battle") == 0){
            AllDatas.battle.setNowInThisMenu(true);
            AllDatas.commandLine.setNowInThisMenu(false);
        }
        else {
            System.out.println("Menu name is not correct");
        }
    }
    public static void help(String parentName){
        AllDatas.help.setParent(AllDatas.commandLine);
        AllDatas.help.setNowInThisMenu(true);
        LinkedListMenus.findMenuByName(parentName).setNowInThisMenu(false);
        for (String commandName:
             LinkedListMenus.findMenuByName(parentName).getCommandsForHelp()) {
            System.out.println(commandName);
        }
    }
}
