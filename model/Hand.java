package model;

import model.collection.Card;
import model.collection.Minion;
import model.collection.Spell;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    private ArrayList<Card> cardsInHand = new ArrayList<>();
    private Card nextCard;

    public Card getNextCard() {
        return nextCard;
    }

    public void setNextCard(Card nextCard) {
        this.nextCard = nextCard;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ArrayList<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public static void showHand(){
        for (Card card:
             Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand()) {
            System.out.println(card.getName() + " : " + card.getCardType());
        }
    }
    public static void setHand(){
        Hand hand = new Hand();
        ArrayList<Card> cardsInDeck = Game.getInstance().getPlayer1().getMainDeck().getCardsInDeck();
        Collections.shuffle(cardsInDeck);
        for (int i = 0; i < 5; i++) {
            Card card = cardsInDeck.get(i);
            hand.getCardsInHand().add(card);
            Game.getInstance().getPlayer1().getMainDeck().getCardsInDeck().remove(card);
        }
        Game.getInstance().getPlayer1().getMainDeck().setHand(hand);
    }

    public boolean checkIfNumberOfCardsInHandIsValid(){
        int numberOfCardsInHand = this.getCardsInHand().size();
        return numberOfCardsInHand <= 5;
    }

//    public void deleteCardFromHand(Card card){
//        this.getCardsInHand().remove(card);
//    }

    public void addCardToHandFromDeck() throws IOException, ParseException {
        if (this.checkIfNumberOfCardsInHandIsValid()) {
            Deck mainDeck = Game.getInstance().getPlayer1().getMainDeck();
            Collections.shuffle(mainDeck.getCardsInDeck());
            nextCard = mainDeck.getCardsInDeck().get(0);
            mainDeck.getCardsInDeck().remove(nextCard);
            this.getCardsInHand().add(nextCard);
        }
    }

    public boolean checkIfCardIsInHand(String cardName){
        for (Card card : this.getCardsInHand()) {
            if (card.getName().equals(cardName))
                return true;
        }
        return false;
    }

//    public void insertCardFromHandInMap(String cardName, int x, int y) throws IOException, ParseException {
//        if (!checkIfCardIsInHand(cardName)){
//            System.out.println("Invalid card name!");
//        }else{
//            Card card = Card.getCardByName(cardName);
//            int playerMana = Game.getInstance().getPlayer1().getNumOfMana();
//            if (card.getMana() > playerMana){
//                System.out.println("You don't have enough mana!");
//            }else{
//                String cardType = card.getCardType();
//                if (cardType.equals("minion")){
//                    if (!Map.checkIfMinionInsertCardInThisCoordination(x, y)){
//                        System.out.println("Invalid target!");
//                    }else {
//                        Game.getInstance().getPlayer1().setNumOfMana(playerMana - card.getMana());
//                        Map.getCells()[x][y].getCellTypes().add(CellType.selfMinion);
//                        card.setX(x);
//                        card.setY(y);
//                        card.setInGame(true);
//                        Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand().remove(card);
//                        Game.getInstance().getMap().getMinions().add(card);
//                    }
//                }else if (cardType.equals("spell")){
//                    Spell.insertSpellInThisCoordination(cardName, x, y);
//                }
//            }
//        }
//    }

    public Card returnCardInHand(String cardName){
        for (Card card : this.getCardsInHand()){
            if (card.getName().equals(cardName)){
                return card;
            }
        }
        return null;
    }

    public void removeCardFromHand(String cardName){
        for (Card card : this.getCardsInHand()){
            if (card.getName().equals(cardName)){
                this.getCardsInHand().remove(card);
            }
        }
    }


}
