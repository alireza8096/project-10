package model;

import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.IOException;
import java.util.ArrayList;

public class GraveYard {
    private ArrayList<String> cardsDeletedFromHandName = new ArrayList<>();
    private boolean graveYardIsBeingUsed;

    public ArrayList<String> getCardsDeletedFromHandName() {
        return cardsDeletedFromHandName;
    }

    public void setCardsDeletedFromHandName(ArrayList<String> cardsDeletedFromHandName) {
        this.cardsDeletedFromHandName = cardsDeletedFromHandName;
    }

    public boolean isGraveYardIsBeingUsed() {
        return graveYardIsBeingUsed;
    }

    public void setGraveYardIsBeingUsed(boolean graveYardIsBeingUsed) {
        this.graveYardIsBeingUsed = graveYardIsBeingUsed;
    }

    public static boolean thisCardIsInGraveYard(String cardName) throws IOException, ParseException {
        if (Game.getInstance().getGraveYard().getCardsDeletedFromHandName().contains(cardName)){
            return true;
        }
        return false;
    }

}
