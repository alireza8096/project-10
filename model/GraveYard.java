package model;

import model.collection.Card;
import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.IOException;
import java.util.ArrayList;

public class GraveYard {
    private ArrayList<Card> cardsDeletedFromHand = new ArrayList<>();
    private boolean graveYardIsBeingUsed;

    public ArrayList<Card> getCardsDeletedFromHand() {
        return cardsDeletedFromHand;
    }

    public void setCardsDeletedFromHand(ArrayList<Card> cardsDeletedFromHandName) {
        this.cardsDeletedFromHand = cardsDeletedFromHandName;
    }

    public boolean isGraveYardIsBeingUsed() {
        return graveYardIsBeingUsed;
    }

    public void setGraveYardIsBeingUsed(boolean graveYardIsBeingUsed) {
        this.graveYardIsBeingUsed = graveYardIsBeingUsed;
    }

    public static boolean thisCardIsInGraveYard(Card card){
        if (Game.getInstance().getGraveYard().getCardsDeletedFromHand().contains(card)){
            return true;
        }
        return false;
    }

}
