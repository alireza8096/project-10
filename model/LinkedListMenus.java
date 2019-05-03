package model;

import java.util.ArrayList;

public class LinkedListMenus {
    public static ArrayList<LinkedListMenus> allMenus = new ArrayList<>();
    private LinkedListMenus parent;
    private ArrayList<LinkedListMenus> childs = new ArrayList<>();
    private ArrayList<String> commandsForHelp = new ArrayList<>();
    private String menuName;
    private boolean nowInThisMenu;

    public String getMenuName() {
        return menuName;
    }

    public ArrayList<String> getCommandsForHelp() {
        return commandsForHelp;
    }

    public ArrayList<LinkedListMenus> getChilds() {
        return childs;
    }

    public LinkedListMenus(String name, boolean bool) {
        this.menuName = name;
        this.nowInThisMenu = bool;
    }

    public LinkedListMenus getParent() {
        return parent;
    }

    public void setNowInThisMenu(boolean nowInThisMenu) {
        this.nowInThisMenu = nowInThisMenu;
    }

    public void setParent(LinkedListMenus parent) {
        this.parent = parent;
    }

    public void setCommandsForHelp(String... helpCommands) {
        for (int i = 1; i <= helpCommands.length; i++) {
            commandsForHelp.add(i + "." + helpCommands[i - 1]);
        }
    }

    public static LinkedListMenus whichMenuNow() {
        for (LinkedListMenus menu :
                allMenus) {
            if (menu.nowInThisMenu) {
                return menu;
            }
        }
        return null;
    }
    public static LinkedListMenus findMenuByName(String name){
        for (LinkedListMenus menu:
             allMenus) {
            if(menu.getMenuName().matches(name))
                return menu;
        }
        return null;
    }
}
