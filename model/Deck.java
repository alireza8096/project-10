package model;

import model.collection.Hero;
import model.collection.Item;
import model.collection.Minion;
import model.collection.Spell;
import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.IOException;
import java.util.ArrayList;

public class Deck {
    private Hand hand;
    private String deckName;
    private String heroInDeckName;
    private ArrayList<String> cardsInDeckNames = new ArrayList<>();
    private ArrayList<String> itemsInDeckNames = new ArrayList<>();
    private boolean deckIsSelected;

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Deck(String deckName){
        this.setDeckName(deckName);
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public ArrayList<String> getCardsInDeckNames() {
        return cardsInDeckNames;
    }

    public void setCardsInDeckNames(ArrayList<String> cardsInDeckNames) {
        this.cardsInDeckNames = cardsInDeckNames;
    }

    public String getHeroInDeckName() {
        return heroInDeckName;
    }

    public void setHeroInDeckName(String heroInDeckName) {
        this.heroInDeckName = heroInDeckName;
    }

    public ArrayList<String> getItemsInDeckNames() {
        return itemsInDeckNames;
    }

    public void setItemsInDeckNames(ArrayList<String> itemsInDeckNames) {
        this.itemsInDeckNames = itemsInDeckNames;
    }

    public boolean isDeckIsSelected() {
        return deckIsSelected;
    }

    public void setDeckIsSelected(boolean deckIsSelected) {
        this.deckIsSelected = deckIsSelected;
    }

    public static void createDeck(String deckName){
        boolean thisNameExists = Deck.checkIfDeckWithThisNameExists(Game.getInstance().getPlayer1(), deckName);
        if (thisNameExists){
            System.out.println("A deck with this name already exists!");
        }else{
            Deck.createNewDeck(deckName);
        }
    }
    public static boolean checkIfDeckWithThisNameExists(Player player, String deckName){
        for (Deck deck : player.getDecksOfPlayer()){
            if (deck.getDeckName().equals(deckName))
                return true;
        }
        return false;
    }
    public static void createNewDeck(String deckName){
        Deck deck = new Deck(deckName);
        Game.getInstance().getPlayer1().getDecksOfPlayer().add(deck);
    }
    public static void deleteDeck(String deckName){
        if(checkIfDeckWithThisNameExists(Game.getInstance().getPlayer1(),deckName)) {
            Deck deck = Deck.findDeckByName(deckName);
            Game.getInstance().getPlayer1().getDecksOfPlayer().remove(deck);
        }
        else
            System.out.println("Deck with this name does not exist");
    }
    public static Deck findDeckByName(String deckName){
        for (Deck deck : Game.getInstance().getPlayer1().getDecksOfPlayer()){
            if (deck.getDeckName().equals(deckName))
                return deck;
        }
        return null;
    }
    public static void addCardOrItemToDeck(int ID, String cardType, String deckName) throws IOException, ParseException {

        //if all conditions are true for adding a new card to deck
        if (allConditionsOfAddingCardToDeckAreRight(ID, cardType, deckName)) {
            Deck deck = Deck.findDeckByName(deckName);
            String cardName;
            switch (cardType) {
                case "item":
                    cardName = Item.findItemNameByID(ID);
                    deck.getItemsInDeckNames().add(cardName);
                    break;
                case "minion":
                    cardName = Minion.findMinionNameByID(ID);
                    deck.getCardsInDeckNames().add(cardName);
                    break;
                case "spell":
                    cardName = Spell.findSpellNameByID(ID);
                    deck.getCardsInDeckNames().add(cardName);
                    break;
                case "hero":
                    cardName = Hero.findHeroNameByID(ID);
                    deck.setHeroInDeckName(cardName);
                    break;
            }
        }
    }
    public static void removeCardOrItemFromDeck(int ID, String cardType, String deckName) throws IOException, ParseException {
        if (!checkIfThisCardIsInThisDeck(deckName, ID, cardType)){
            System.out.println("This " + cardType + " doesn't exist in deck!");
        }else{
            String cardName = findNameOfCardByID(ID, cardType);
            Deck deck = findDeckByName(deckName);
            switch (cardType){
                case "item":
                    deck.removeItemFromDeck(cardName);
                    break;
                case "hero":
                    deck.setHeroInDeckName(null);
                    break;
                default:
                    deck.removeCardFromDeck(cardName);
                    break;
            }
        }
    }
    public void removeItemFromDeck(String itemName){
        for (String name : this.getItemsInDeckNames()){
            if (name.equals(itemName)) {
                this.getItemsInDeckNames().remove(name);
                break;
            }
        }
    }
    public void removeCardFromDeck(String cardName){
        for (String name : this.getCardsInDeckNames()){
            if (name.equals(cardName)){
                this.getCardsInDeckNames().remove(name);
                break;
            }
        }
    }
    public boolean checkIfNumberOfCardsInDeckIsValid(){
        if (this.getCardsInDeckNames().size() >= 20){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkIfDeckHasHero(){
        for (String heroName : Hero.heroNames){
            if (this.getHeroInDeckName().equals(heroName))
                return true;
        }
        return false;
    }
    public static boolean allConditionsOfAddingCardToDeckAreRight(int ID, String cardType, String deckName) throws IOException, ParseException {
        Deck deck = findDeckByName(deckName);

        if (!checkIfThisCardOrItemIsInCollection(ID, cardType)){
            System.out.println("This card isn't available in collection!");
            return false;
        }else{
            if (checkIfThisCardIsInThisDeck(deckName, ID, cardType)){
                System.out.println("This card is already available in deck!");
                return false;
            }else{
                if (!deck.checkIfNumberOfCardsInDeckIsValid()){
                    System.out.println("Number of cards can't be more than 20!");
                    return false;
                }else{
                    if (deck.checkIfDeckHasHero() && cardType.equals("hero")){
                        System.out.println("A hero already exists in deck!");
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static boolean checkIfThisCardIsInThisDeck(String deckName, int cardID, String cardType) throws IOException, ParseException {
        String cardName = findNameOfCardByID(cardID, cardType);
        Deck deck = Deck.findDeckByName(deckName);

        if (deck.getHeroInDeckName().equals(cardName))
            return true;

        for (String name : deck.getCardsInDeckNames()){
            if (name.equals(cardName))
                return true;
        }
        for (String name : deck.getItemsInDeckNames()){
            if (name.equals(cardName))
                return true;
        }
        return false;
    }
    public static boolean checkIfThisCardOrItemIsInCollection(int cardID, String cardType) throws IOException, ParseException {
        String name = findNameOfCardByID(cardID, cardType);

        Player currentPlayer = Game.getInstance().getPlayer1();
        for (String cardName : currentPlayer.getCardsInCollectionNames()){
            if (cardName.equals(name))
                return true;
        }
        for (String itemName : currentPlayer.getItemsInCollectionNames()){
            if (itemName.equals(name))
                return true;
        }
        return false;
    }
    public static String findNameOfCardByID(int id, String cardType) throws IOException, ParseException {
        if (cardType.equals("minion")){
            return Minion.findMinionNameByID(id);
        }else if (cardType.equals("spell")){
            return Spell.findSpellNameByID(id);
        }else if (cardType.equals("hero")){
            return Hero.findHeroNameByID(id);
        }else if (cardType.equals("item")){
            return Item.findItemNameByID(id);
        }
        return null;
    }
    public static boolean validateDeck(String deckName){
        Deck deck = Deck.findDeckByName(deckName);
        if (!deck.checkIfDeckHasHero()){
            return false;
        }else{
            if (deck.getCardsInDeckNames().size() != 20){
                return false;
            }
        }
        return true;
    }
    public static void selectDeck(String deckName){
        if (!validateDeck(deckName)){
            System.out.println("Selected deck in not valid!");
        }else {
            Deck deck = findDeckByName(deckName);
            Game.getInstance().getPlayer1().setMainDeck(deck);
            deck.setDeckIsSelected(true);
        }
    }
    public void deleteCardFromDeck(String cardName){
        this.getCardsInDeckNames().remove(cardName);
    }
    public boolean deckIsEmpty(){
        int deckSize = this.getCardsInDeckNames().size();
        if (deckSize <= 0)
            return true;
        else
            return false;
    }
    public static void showAllDecks(){
        //GameView.showDeck();
    }
}
