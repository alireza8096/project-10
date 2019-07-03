package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.collection.*;

import java.util.ArrayList;
import java.lang.String;
public class Player implements Cloneable{

    private transient ArrayList<Deck> decksOfPlayer = new ArrayList<>();
    private transient Deck mainDeck;
    private String userName;
    private String password;
    private transient IntegerProperty daricProperty = new SimpleIntegerProperty();
    private int daric;
    private transient Game lastGame;
    private int numOfMana;
    private int numOfWins;
    private  boolean addingManaBuffIsActive;//Todo maybe have problems when applying several buffs
    private transient ArrayList<Card> cardsInCollection = new ArrayList<>();
    private transient ArrayList<Item> itemsInCollection = new ArrayList<>();
    private transient ArrayList<Hero> heroesInCollection = new ArrayList<>();
    private  int numberOfTurnsThatPlayerHasFlag;

    public int getNumberOfTurnsThatPlayerHasFlag() {
        return numberOfTurnsThatPlayerHasFlag;
    }

    public void setNumberOfTurnsThatPlayerHasFlag(int numberOfTurnsThatPlayerHasFlag) {
        this.numberOfTurnsThatPlayerHasFlag = numberOfTurnsThatPlayerHasFlag;
    }

    public Player(String name){
        this.setUserName(name);
    }

    public Player(String userName, String password){
        this.setUserName(userName);
        this.setPassword(password);
    }

    public ArrayList<Card> getCardsInCollection() {
        return cardsInCollection;
    }

    public void setCardsInCollection(ArrayList<Card> cardsInCollection) {
        this.cardsInCollection = cardsInCollection;
    }

    public ArrayList<Item> getItemsInCollection() {
        return itemsInCollection;
    }

    public void setItemsInCollection(ArrayList<Item> itemsInCollection) {
        this.itemsInCollection = itemsInCollection;
    }

    public ArrayList<Hero> getHeroesInCollection() {
        return heroesInCollection;
    }

    public void setHeroesInCollection(ArrayList<Hero> heroesInCollection) {
        this.heroesInCollection = heroesInCollection;
    }

    public boolean isAddingManaBuffIsActive() {
        return addingManaBuffIsActive;
    }

    public void setAddingManaBuffIsActive(boolean addingManaBuffIsActive) {
        this.addingManaBuffIsActive = addingManaBuffIsActive;
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

    public int getDaricProperty() {
        return daricProperty.get();
    }

    public IntegerProperty daricPropertyProperty() {
        return daricProperty;
    }

    public void setDaricProperty(int daricProperty) {
        this.daricProperty.set(daricProperty);
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

    public int getNumOfMana() {
        return numOfMana;
    }

    public void setNumOfMana(int numOfMana) {
        this.numOfMana = numOfMana;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public void setNumOfWins(int numOfWins) {
        this.numOfWins = numOfWins;
    }

//    public int getCoordinateX() {
//        return coordinateX;
//    }

//    public void setCoordinateX(int coordinateX) {
//        this.coordinateX = coordinateX;
//    }

//    public int getCoordinateY() {
//        return coordinateY;
//    }

//    public void setCoordinateY(int coordinateY) {
//        this.coordinateY = coordinateY;
//    }

    public boolean thisCardOrItemIsAvailableInCollection(String name){
        if(Minion.thisCardIsMinion(name) || Spell.thisCardIsSpell(name) ||
        Hero.thisCardIsHero(name) || Item.thisCardIsItem(name)) {
            for (Card card : this.getCardsInCollection()) {
                if (card.getName().equals(name))
                    return true;
            }

            for (Item itemName : this.getItemsInCollection()) {
                if (itemName.getName().equals(name))
                    return true;
            }

            for (Hero heroName : this.getHeroesInCollection()) {
                if (heroName.getName().equals(name))
                    return true;
            }
            System.out.println("Card is not available in collection");
            return false;
        }
        System.out.println("This name is not valid for card");
        return false;
    }

    public void handleManaAtTheFirstOfTurn(){
        int turnNumber = Game.getInstance().getNumOfRound();
        int key = turnNumber / 2;
        if (turnNumber <= 14){
            Game.getInstance().getPlayer1().setNumOfMana(2 + key);
        }else{
            Game.getInstance().getPlayer1().setNumOfMana(9);
        }
    }

}
