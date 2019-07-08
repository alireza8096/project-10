package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.collection.Card;
import model.collection.HandleFiles;
import model.collection.Hero;
import model.collection.Item;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Game {
    private static Game currentGame;
    private static ArrayList<Game> games = new ArrayList<>();
    private boolean isPlayer1Turn;
    private static boolean isSet = false;
    private String winnerName;
    private Player player1;
    private Player player2;
    private int numOfRound = 1;
    private GameMode gameMode;
    private int finishTime;
    private int numOfPlayers;
    private Map map;
    private boolean isBeingPlayed;
    private GameType gameType;
    private GraveYard graveYard = new GraveYard();
    private static ImageView[] playerMana = new ImageView[9];
    private static ImageView[] enemyMana = new ImageView[9];
    private static HBox yourHbox = new HBox();
    private static HBox enemyHbox = new HBox();

    private int numOfTurnsNeededToHoldFlag;
    private int numOfFlagsInGame;

    public int getNumOfTurnsNeededToHoldFlag() {
        return numOfTurnsNeededToHoldFlag;
    }

    public void setNumOfTurnsNeededToHoldFlag(int numOfTurnsNeededToHoldFlag) {
        this.numOfTurnsNeededToHoldFlag = numOfTurnsNeededToHoldFlag;
    }

    public int getNumOfFlagsInGame() {
        return numOfFlagsInGame;
    }

    public void setNumOfFlagsInGame(int numOfFlagsInGame) {
        this.numOfFlagsInGame = numOfFlagsInGame;
    }

    {
        for (int i = 0; i < 9 ; i++) {
            try {
                playerMana[i] = new ImageView();
                yourHbox.getChildren().add(playerMana[i]);
                playerMana[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/icon_mana_inactive.png")));
                enemyMana[i] = new ImageView();
                enemyMana[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/icon_mana_inactive.png")));
                enemyHbox.getChildren().add(enemyMana[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for(int i=0; i<2; i++){
            try {
                playerMana[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/icon_mana.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        yourHbox.setLayoutX(240);
        yourHbox.setLayoutY(177);
        enemyHbox.setLayoutX(860);
        enemyHbox.setLayoutY(177);
        yourHbox.setScaleX(0.7);
        yourHbox.setScaleY(0.7);
        enemyHbox.setScaleX(0.7);
        enemyHbox.setScaleY(0.7);
    }

    public Game(){

    }

    public static HBox getEnemyHbox() {
        return enemyHbox;
    }

    public static HBox getYourHbox() {
        return yourHbox;
    }

    public static ImageView[] getPlayerMana() {
        return playerMana;
    }

    public static ImageView[] getEnemyMana() {
        return enemyMana;
    }
    //
//    public Game(GameMode gameMode){
//        this.setGameMode(gameMode);
//        this.setPlayer1Turn(true);
//    }

//    public ArrayList<Item> getPlayer1Flags() {
//        return player1Flags;
//    }
//
//    public void setPlayer1Flags(ArrayList<Item> player1Flags) {
//        this.player1Flags = player1Flags;
//    }
//
//    public ArrayList<Item> getPlayer2Flags() {
//        return player2Flags;
//    }
//
//    public void setPlayer2Flags(ArrayList<Item> player2Flags) {
//        this.player2Flags = player2Flags;
//    }
//    public ArrayList<Item> getFlags() {
//        return flags;
//    }
//
//    public void setFlags(ArrayList<Item> flags) {
//        this.flags = flags;
//    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    public void setGraveYard(GraveYard graveYard) {
        this.graveYard = graveYard;
    }

    public Game(Player player1, Player player2){
        this.setPlayer1(player1);
        this.setPlayer2(player2);
    }

    public static Game getInstance(){
        return currentGame;
    }

    public static void setCurrentGame(Game game) {
        Game.currentGame = game;
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void setGames(ArrayList<Game> games) {
        Game.games = games;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        isPlayer1Turn = player1Turn;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getNumOfRound() {
        return numOfRound;
    }

    public void setNumOfRound(int numOfRound) {
        this.numOfRound = numOfRound;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isBeingPlayed() {
        return isBeingPlayed;
    }

    public void setBeingPlayed(boolean beingPlayed) {
        isBeingPlayed = beingPlayed;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public static boolean isIsSet() {
        return isSet;
    }

    public static void setIsSet(boolean isSet) {
        Game.isSet = isSet;
    }

//    public static void createNewGame(){
//        Game game = new Game();
//        Map map = new Map();
//        game.setMap(map);
//        game.setPlayer1Turn(true);
//        game.setNumOfRound(1);
//        Game.setCurrentGame(game);
//        isSet = true;
//    }
}
