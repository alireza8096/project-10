package model;

import controller.Controller;
import controller.MenusCommandController;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import view.MenuView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LinkedListMenus {
    public static ArrayList<LinkedListMenus> allMenus = new ArrayList<>();
    private LinkedListMenus parent;
    private ArrayList<LinkedListMenus> childs = new ArrayList<>();
    private ArrayList<String> commandsForHelp = new ArrayList<>();
    private String menuName;
    private boolean nowInThisMenu;
    private Scene scene;
    private Pane root;
//
//    static {
//        allMenus.add(AllDatas.commandLine);
//        allMenus.add()
//    }

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
        this.root = new Pane();
        this.scene = new Scene(root, MenuView.WINDOW_WIDTH, MenuView.WINDOW_HEIGHT);
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

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
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

    public void backFromThisMenu() throws FileNotFoundException {
        System.out.println(this.menuName);
        AllDatas.currentRoot = this.parent.root;
        AllDatas.currentScene = this.parent.scene;

        LinkedListMenus currentMenu = whichMenuNow();
        currentMenu.nowInThisMenu = false;
        currentMenu.getParent().nowInThisMenu = true;

        MenusCommandController.enterThisMenu(whichMenuNow());
    }
}
