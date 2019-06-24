package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class AllDatas {

    public static Scene currentScene;
    public static Pane currentRoot;

    public static boolean gameBoolean = true;
    public static LinkedListMenus account;
    public static LinkedListMenus leaderboard;
    public static LinkedListMenus commandLine;
    public static LinkedListMenus collection;
    public static LinkedListMenus shop;
    public static LinkedListMenus battle;
    public static LinkedListMenus help;

//    public static LinkedListMenus cardsInCollection;
//    public static LinkedListMenus decksInCollection;


    public static boolean hasEnteredAccount = false;
    public static boolean hasEnteredCollection = false;
    public static boolean hasEnteredShop = false;
    public static boolean hasEnteredBattle = false;
    public static boolean didAction = false;
}
