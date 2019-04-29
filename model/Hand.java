package model;

import model.collection.Card;
import model.collection.Hero;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Hand {
    private ArrayList<Card> cardsInHand = new ArrayList<>();

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ArrayList<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public void setHand() throws IOException, ParseException {
        Hand hand = new Hand();
        Game.getInstance().getPlayer1().getMainDeck().setHand(hand);
        ArrayList<String> cardNamesInDeck = Game.getInstance().getPlayer1().getMainDeck().getCardsInDeckNames();

        Collections.shuffle(cardNamesInDeck);
        for (int i = 0; i < 5; i++) {
            String cardName = cardNamesInDeck.get(i);
            this.getCardsInHand().add(Card.getCardByName(cardName));
        }
    }

    public void deleteCardFromHand(Card card){
        this.getCardsInHand().remove(card);
    }

    public boolean checkIfNumberOfCardsInHandIsValid(){
        int numberOfCardsInHand = this.getCardsInHand().size();
        if (numberOfCardsInHand > 5)
            return false;
        else
            return true;
    }

    public void addCardToHandFromDeck() throws IOException, ParseException {
        if (this.checkIfNumberOfCardsInHandIsValid()) {
            Deck mainDeck = Game.getInstance().getPlayer1().getMainDeck();
            String nextCardName = mainDeck.getCardsInDeckNames().get(0);
            mainDeck.getCardsInDeckNames().remove(nextCardName);
            Card nextCard = Card.getCardByName(nextCardName);
            this.getCardsInHand().add(nextCard);
        }
    }



}
