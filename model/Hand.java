package model;

import model.collection.Card;
import model.collection.Hero;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    private ArrayList<Card> cardsInHand = new ArrayList<>();

//    private ArrayList<String> cardsInHandNames = new ArrayList<>();

//    public ArrayList<String> getCardsInHandNames() {
//        return cardsInHandNames;
//    }
//
//    public void setCardsInHandNames(ArrayList<String> cardsInHandNames) {
//        this.cardsInHandNames = cardsInHandNames;
//    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ArrayList<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public void setHand() throws IOException, ParseException {
        ArrayList<String> cardNamesInDeck = Game.getInstance().getPlayer1().getMainDeck().getCardsInDeckNames();

        Collections.shuffle(cardNamesInDeck);
        for (int i = 0; i < 5; i++) {
            String cardName = cardNamesInDeck.get(i);
            this.getCardsInHand().add(Card.getCardByName(cardName));
        }
    }

}
