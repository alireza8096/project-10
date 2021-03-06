package model;

import com.sun.management.GarbageCollectorMXBean;
import controller.AI;
import controller.CollectionController;
import model.collection.*;
import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Deck implements Cloneable {
    private Hand hand;
    private String deckName;
    private Hero heroInDeck;
    private ArrayList<Card> cardsInDeck = new ArrayList<>();
    private ArrayList<Item> itemsInDeck = new ArrayList<>();
    private boolean deckIsSelected;

    private static Deck selectedDeck;

    public static Deck getSelectedDeck() {
        try {
            return (Deck)selectedDeck.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setSelectedDeck(Deck selectedDeck) {
        Deck.selectedDeck = selectedDeck;
    }

    public Deck(){

    }

    public void setHeroInDeck(Hero heroInDeck) {
        this.heroInDeck = heroInDeck;
    }

    public ArrayList<Card> getCardsInDeck() {
        return cardsInDeck;
    }

    public void setCardsInDeck(ArrayList<Card> cardsInDeck) {
        this.cardsInDeck = cardsInDeck;
    }

    public ArrayList<Item> getItemsInDeck() {
        return itemsInDeck;
    }

    public void setItemsInDeck(ArrayList<Item> itemsInDeck) {
        this.itemsInDeck = itemsInDeck;
    }

    public boolean isDeckIsSelected() {
        return deckIsSelected;
    }


    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Deck(String deckName) {
        this.setDeckName(deckName);
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public Hero getHeroInDeck() {
        return heroInDeck;
    }

    public void setDeckIsSelected(boolean deckIsSelected) {
        this.deckIsSelected = deckIsSelected;
    }

    public static void createDeck(String deckName) {
        boolean thisNameExists = Deck.checkIfDeckWithThisNameExists(Game.getInstance().getPlayer1(), deckName);
        if (thisNameExists) {
            System.out.println("A deck with this name already exists!");
        } else {
            Deck.createNewDeck(deckName);
        }
    }

    public static boolean checkIfDeckWithThisNameExists(Player player, String deckName) {
        for (Deck deck : player.getDecksOfPlayer()) {
            if (deck.getDeckName().equals(deckName))
                return true;
        }
        return false;
    }

    public static void createNewDeck(String deckName) {
        Deck deck = new Deck(deckName);
        Game.getInstance().getPlayer1().getDecksOfPlayer().add(deck);
    }

    public static void deleteDeck(String deckName) {
        if (checkIfDeckWithThisNameExists(Game.getInstance().getPlayer1(), deckName)) {
            for (Deck deck : Game.getInstance().getPlayer1().getDecksOfPlayer()) {
                if (deck.deckName.matches(deckName))
                    Shop.removeProcess(Game.getInstance().getPlayer1().getDecksOfPlayer(), deck);
            }
        } else
            System.out.println("Deck with this name does not exist");
    }

    public static Deck findDeckByName(String deckName) throws CloneNotSupportedException {
        for (Deck deck : Game.getInstance().getPlayer1().getDecksOfPlayer()) {
            if (deck.getDeckName().equals(deckName))
                return (Deck) deck.clone();
        }
        return null;
    }
    public static Deck exactDeck(String deckName){
        for (Deck deck:
             Game.getInstance().getPlayer1().getDecksOfPlayer()) {
//            System.out.println("$%$%$%$ : " + deck.getDeckName());
            if(deck.deckName.matches(deckName)){
//                System.out.println("__________ : " + deckName);
                return deck;
            }
        }
        return null;
    }

//    public static void addCardOrItemToDeck(int ID, String deckName) throws IOException, ParseException, CloneNotSupportedException {
//        //if all conditions are true for adding a new card to deck
//        if (allConditionsOfAddingCardToDeckAreRight(ID, deckName)) {
//            Deck deck = Deck.exactDeck(deckName);
//            switch (ID / 100) {
//                case 2:
//                    assert deck != null;
//                    deck.itemsInDeck.add(Item.findItemByID(ID));
//                    break;
//                case 3:
//                    assert deck != null;
//                    deck.cardsInDeck.add(Minion.findMinionByID(ID));
//                    break;
//                case 4:
//                    assert deck != null;
//                    deck.cardsInDeck.add(Spell.findSpellByID(ID));
//                    break;
//                case 1:
//                    assert deck != null;
//                    deck.setHeroInDeck(Hero.findHeroByID(ID));
//                    break;
//            }
//        }
//    }


    public static void removeCardOrItemFromDeck(int ID, String deckName) throws IOException, ParseException, CloneNotSupportedException {
        if (!checkIfThisCardIsInThisDeck(deckName, ID)) {
            System.out.println("This " + Shop.returnCardTypeById(ID) + " doesn't exist in deck!");
        } else {
            String cardName = findNameOfCardByID(ID);
            Deck deck = findDeckByName(deckName);
            switch (ID / 100) {
                case 2:
                    Shop.removeProcess(deck.itemsInDeck, Item.findItemByID(ID));
                    break;
                case 1:
                    deck.setHeroInDeck(null);
                    break;
                default:
                    Shop.removeProcess(deck.cardsInDeck, Card.findCardById(ID));
                    break;
            }
        }
    }


    public boolean checkIfNumberOfCardsInDeckIsValid() {
        return this.getCardsInDeck().size() < 20;
    }

    public boolean checkIfDeckHasHero() {
        if (this.getHeroInDeck() != null)
            return true;
        return false;
    }

    public boolean allConditionsOfAddingCardToDeckAreRight(int ID) throws IOException, ParseException, CloneNotSupportedException {
        if (!checkIfThisCardOrItemIsInCollection(ID)) {
            GameView.printInvalidCommandWithThisContent("This card isn't available in collection");
            return false;
        } else {
            if (ID / 100 == 2){
                if (this.getItemsInDeck().size() == 1){
                    GameView.printInvalidCommandWithThisContent("Deck already has an item!");
                    return false;
                }
            }else if (ID / 100 != 1) {
                if (!this.checkIfNumberOfCardsInDeckIsValid()) {
                    GameView.printInvalidCommandWithThisContent("Number of cards can't be more than 20!");
                    return false;
                }
            } else {
                if (this.checkIfDeckHasHero()) {
                    GameView.printInvalidCommandWithThisContent("A hero already exists in deck!");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkIfThisCardIsInThisDeck(String deckName, int cardID) throws IOException, ParseException, CloneNotSupportedException {
        String cardName = findNameOfCardByID(cardID);
        Deck deck = Deck.findDeckByName(deckName);
        if (deck.checkIfDeckHasHero()) {
            if (deck.getHeroInDeck().getName().equals(cardName))
                return true;
        }

        for (Card card : deck.getCardsInDeck()) {
            if (card.getName().equals(cardName))
                return true;
        }
        for (Item item : deck.getItemsInDeck()) {
            if (item.getName().equals(cardName))
                return true;
        }
        return false;
    }

    public static boolean checkIfThisCardOrItemIsInCollection(int cardID) throws CloneNotSupportedException {
        String name = findNameOfCardByID(cardID);

        Player currentPlayer = Game.getInstance().getPlayer1();
        for (Card card : currentPlayer.getCardsInCollection()) {
            if (card.getName().equals(name))
                return true;
        }
        for (Item item : currentPlayer.getItemsInCollection()) {
            if (item.getName().equals(name))
                return true;
        }
        for (Card card : currentPlayer.getHeroesInCollection()) {
            if (card.getName().equals(name))
                return true;
        }
        return false;
    }

    public static String findNameOfCardByID(int id) throws CloneNotSupportedException {
        switch (id / 100) {
            case 1:
                return Hero.findHeroByID(id).getName();
            case 2:
                return Item.findItemByID(id).getName();
            case 3:
                return Minion.findMinionByID(id).getName();
            case 4:
                return Spell.findSpellByID(id).getName();
        }
        return "";
    }

    public boolean validateDeck() throws CloneNotSupportedException {
        Deck deck = Deck.findDeckByName(deckName);
        if (!deck.checkIfDeckHasHero()) {
            return false;
        } else {
            return deck.getCardsInDeck().size() == 20;
        }
    }

    public static void setAllCardsMovable(Deck deck) {
        deck.getHeroInDeck().setCanMove(true);
        for (Card card : deck.getCardsInDeck()) {
            if (card.getId() / 100 == 3) {
                ((Force) card).setCanMove(true);
            }
        }
    }

    public static void selectDeck(String deckName) throws CloneNotSupportedException {
//        if(!validateDeck(deckName)){
//            System.out.println("Selected deck in not valid!");
//        } else {
//            Deck deck = findDeckByName(deckName);
//            setAllCardsMovable(deck);
//            Game.getInstance().getPlayer1().setMainDeck(deck);
////            Map map = new Map();
////            Game.getInstance().setMap(map);
////            AI.createAIPlayer();
////            Hero.insertHeroInMap();
////            Hand.setHand();
//            deck.setDeckIsSelected(true);
//        }
    }

    public Deck returnCopyOfDeck() throws CloneNotSupportedException {
        return (Deck)this.clone();
    }

    public void addCardToDeck(Card card) throws ParseException, CloneNotSupportedException, IOException {
        int ID = card.getId();

        if (allConditionsOfAddingCardToDeckAreRight(ID)) {
            Deck deck = Deck.exactDeck(deckName);
            if (ID / 100 == 1){
                System.out.println("hero");
                assert deck != null;
                deck.setHeroInDeck((Hero)Card.findCardInCollectionByID(ID));
            }else if (ID / 100 == 2){
                System.out.println("item");
                assert deck != null;
                deck.itemsInDeck.add((Item)Card.findCardInCollectionByID(ID));
            }else if (ID / 100 == 3 || ID / 100 == 4){
                System.out.println("minion/spell");
                assert deck != null;
                deck.cardsInDeck.add(Card.findCardInCollectionByID(ID));
            }
            GameView.printInfoMessageWithThisContent(card.getName() + " was added to "
                    + this.getDeckName());
        }
    }


}
