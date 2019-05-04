package model;

import model.Deck;
import model.Game;
import model.GraveYard;
import model.collection.Hero;
import model.collection.Item;
import model.collection.Minion;
import model.collection.Spell;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.String;
public class Player {

    private static ArrayList<Player> players = new ArrayList<>();

    private ArrayList<Deck> decksOfPlayer = new ArrayList<>();
    private Deck mainDeck;
    private String userName;
    private String password;
    private int daric;
    private Game lastGame;
    private GraveYard graveYard = new GraveYard();
    private int numOfMana;
    private int numOfwins;
    private int coordinateX;
    private int coordinateY;
    private ArrayList<String> cardsInCollectionNames = new ArrayList<>();
    private ArrayList<String> itemsInCollectionNames = new ArrayList<>();
    private ArrayList<String> heroesInCollectionName = new ArrayList<>();

    public Player(String name){
        this.setUserName(name);
    }

    public Player(String userName, String password,int daric){
        this.setUserName(userName);
        this.setPassword(password);
        this.setDaric(daric);
    }

    public ArrayList<String> getCardsInCollectionNames() {
        return cardsInCollectionNames;
    }

    public void setCardsInCollectionNames(ArrayList<String> cardsInCollectionNames) {
        this.cardsInCollectionNames = cardsInCollectionNames;
    }

    public ArrayList<String> getItemsInCollectionNames() {
        return itemsInCollectionNames;
    }

    public void setItemsInCollectionNames(ArrayList<String> itemsInCollectionNames) {
        this.itemsInCollectionNames = itemsInCollectionNames;
    }

    public ArrayList<Deck> getDecksOfPlayer() {
        return decksOfPlayer;
    }

    public void setDecksOfPlayer(ArrayList<Deck> decksOfPlayer) {
        this.decksOfPlayer = decksOfPlayer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Player.players = players;
    }

    public ArrayList<String> getHeroesInCollectionName() {
        return heroesInCollectionName;
    }

    public int getDaric() {
        return daric;
    }

    public void setDaric(int daric) {
        this.daric = daric;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public Game getLastGame() {
        return lastGame;
    }

    public void setLastGame(Game lastGame) {
        this.lastGame = lastGame;
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    public void setGraveYard(GraveYard graveYard) {
        this.graveYard = graveYard;
    }

    public int getNumOfMana() {
        return numOfMana;
    }

    public void setNumOfMana(int numOfMana) {
        this.numOfMana = numOfMana;
    }

    public int getNumOfwins() {
        return numOfwins;
    }

    public void setNumOfwins(int numOfwins) {
        this.numOfwins = numOfwins;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Player findPlayerByName(String playerName)
    {
        for (Player player : players) {
            if (player.getUserName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public boolean thisCardOrItemIsAvailableInCollection(String name){
        if(Minion.thisCardIsMinion(name) || Spell.thisCardIsSpell(name) ||
        Hero.thisCardIsHero(name) || Item.thisCardIsItem(name)) {
            for (String cardName : this.getCardsInCollectionNames()) {
                if (cardName.equals(name))
                    return true;
            }

            for (String itemName : this.getItemsInCollectionNames()) {
                if (itemName.equals(name))
                    return true;
            }

            for (String heroName : this.getHeroesInCollectionName()) {
                if (heroName.equals(name))
                    return true;
            }
            System.out.println("Card is not available in collection");
            return false;
        }
        System.out.println("This name is not valid for crad");
        return false;
    }
}
