package controller;

import model.AllDatas;
import model.LinkedListMenus;

import java.util.LinkedList;

public interface CommandLineController {
    static void enterMenu(String menuName){
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
    static void help(){
        AllDatas.help.setParent(AllDatas.commandLine);
        AllDatas.help.setNowInThisMenu(true);
        AllDatas.commandLine.setNowInThisMenu(false);
        for (String commandName: AllDatas.commandLine.getCommandsForHelp()) {
            System.out.println(commandName);
        }
    }
}
