package model;

import model.collection.Card;
import model.collection.Hero;

import java.util.ArrayList;

public class Game {
    private static Game currentGame;
    private static ArrayList<Game> games = new ArrayList<>();
    private boolean isPlayer1Turn;
    private String winnerName;
    private Player player1;
    private Player player2;
    private int numOfRound;
    private GameMode gameMode;
    private int finishTime;
    private int numOfPlayers;
    private Map map;
    private boolean isBeingPlayed;
    private GameType gameType;
    private ArrayList<Card> player1CardsInField = new ArrayList<>();
    private ArrayList<Card> player2CardsInField = new ArrayList<>();
    private Hero heroOfPlayer1;
    private Hero heroOfPlayer2;
    private Hand hand;
    private GraveYard graveYard = new GraveYard();


    public GraveYard getGraveYard() {
        return graveYard;
    }

    public void setGraveYard(GraveYard graveYard) {
        this.graveYard = graveYard;
    }

    public Game(){

    }

    public Hero getHeroOfPlayer1() {
        return heroOfPlayer1;
    }

    public Hero getHeroOfPlayer2() {
        return heroOfPlayer2;
    }

    public void setHeroOfPlayer1(Hero heroOfPlayer1) {
        this.heroOfPlayer1 = heroOfPlayer1;
    }

    public void setHeroOfPlayer2(Hero heroOfPlayer2) {
        this.heroOfPlayer2 = heroOfPlayer2;
    }

    public Game(Player player1, Player player2){
        this.setPlayer1(player1);
        this.setPlayer2(player2);
    }
    public ArrayList<Card> getPlayer1CardsInField() {
        return player1CardsInField;
    }

    public void setPlayer1CardsInField(ArrayList<Card> player1CardsInField) {
        this.player1CardsInField = player1CardsInField;
    }

    public ArrayList<Card> getPlayer2CardsInField() {
        return player2CardsInField;
    }

    public void setPlayer2CardsInField(ArrayList<Card> player2CardsInField) {
        this.player2CardsInField = player2CardsInField;
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

    public static void createNewGame(String player1Name, String player2Name){

        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);

        Game game = new Game(player1, player2);
        Game.getGames().add(game);
    }


    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
}
