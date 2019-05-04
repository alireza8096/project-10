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
            Game.getInstance().getPlayer1().getMainDeck().getCardsInDeckNames().remove(cardName);
        }
    }

    public void deleteCardFromHand(Card card){
        this.getCardsInHand().remove(card);
    }

    public boolean checkIfNumberOfCardsInHandIsValid(){
        int numberOfCardsInHand = this.getCardsInHand().size();
        return numberOfCardsInHand <= 5;
    }

    public void addCardToHandFromDeck() throws IOException, ParseException {
        if (this.checkIfNumberOfCardsInHandIsValid()) {
            Deck mainDeck = Game.getInstance().getPlayer1().getMainDeck();
            Collections.shuffle(mainDeck.getCardsInDeckNames());
            String nextCardName = mainDeck.getCardsInDeckNames().get(0);
            mainDeck.getCardsInDeckNames().remove(nextCardName);
            if (Minion.thisCardIsMinion(nextCardName)){
                Minion minion = (Minion) Minion.getMinionByName(nextCardName);
                this.getCardsInHand().add(minion);
            }else if (Spell.thisCardIsSpell(nextCardName)){
                Spell spell = (Spell) Spell.getSpellByName(nextCardName);
                this.getCardsInHand().add(spell);
            }
        }
    }

    public boolean checkIfCardIsInHand(String cardName){
        for (Card card : this.getCardsInHand()) {
            if (card.getName().equals(cardName))
                return true;
        }
        return false;
    }

    public void insertCardFromHandInMap(String cardName, int x, int y) throws IOException, ParseException {
        if (!checkIfCardIsInHand(cardName)){
            System.out.println("Invalid card name!");
        }else{
            Card card = Card.getCardByName(cardName);
            int playerMana = Game.getInstance().getPlayer1().getNumOfMana();
            if (card.getMana() > playerMana){
                System.out.println("You don't have enough mana!");
            }else{
                String cardType = card.getCardType();
                if (cardType.equals("minion")){
                    if (!Map.checkIfMinionInsertCardInThisCoordination(x, y)){
                        System.out.println("Invalid target!");
                    }else {
                        Game.getInstance().getPlayer1().setNumOfMana(playerMana - card.getMana());
                        Map.getCells()[x][y].getCellTypes().add(CellType.selfMinion);
                        card.setX(x);
                        card.setY(y);
                        card.setInGame(true);
                        Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand().remove(card);
                        Game.getInstance().getMap().getMinions().add(card);
                    }
                }else if (cardType.equals("spell")){
                    Spell.insertSpellInThisCoordination(cardName, x, y);
                }
            }
        }
    }


}
