package model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.collection.Card;
import model.collection.Minion;
import model.collection.Spell;
import org.json.simple.parser.ParseException;
import view.MainView;
import view.MenuView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    private static ImageView[] cards = new ImageView[5];
    private ArrayList<Card> cardsInHand = new ArrayList<>();
    private Card nextCard;

    public static void handleHand(ImageView cardInHand){
        cardInHand.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Image activated = new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/battle/cardActivatedInHand.png"));
                    cardInHand.setImage(activated);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        cardInHand.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Image disabled = new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/battle/card_background_disabled@2x.png"));
                    cardInHand.setImage(disabled);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public static void createHand() throws FileNotFoundException {
        HBox handView = new HBox();
        for(int i=0; i<5; i++){
            cards[i] =  MainView.getPhotoWithThisPath("/Users/bahar/Desktop/DUELYST/view/Photos/battle/card_background_disabled@2x.png");
            handView.getChildren().add(cards[i]);
            handleHand(cards[i]);
        }
        handView.setLayoutX(130);
        handView.setLayoutY(700);
        handView.setSpacing(5);
        handView.setScaleX(0.6);
        handView.setScaleY(0.6);
        AllDatas.currentRoot.getChildren().add(handView);
    }
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
            System.out.println(card.getName() + " : " + card.getCardType() + " " + card.getMana());
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
        return numberOfCardsInHand < 5;
    }

//    public void deleteCardFromHand(Card card){
//        this.getCardsInHand().remove(card);
//    }

    public void addCardToHandFromDeck(){
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
    public void insertCardFromHandInMap(String cardName, int x, int y) throws CloneNotSupportedException {
        if (!checkIfCardIsInHand(cardName)){
            System.out.println("Invalid card name!");
        }else{
            int playerMana = Game.getInstance().getPlayer1().getNumOfMana();
            if (Card.findCardByName(cardName).getMana() > playerMana){
                System.out.println("You don't have enough mana!");
            }else{
                if (Minion.thisCardIsMinion(cardName)){
                    Minion minion = (Minion)returnCardInHand(cardName);
                    if (!Map.checkIfMinionCardCanBeInsertedInThisCoordination(x, y)){
                        System.out.println("Invalid target!");
                    }else {
                        Game.getInstance().getPlayer1().setNumOfMana(playerMana - minion.getMana());
                        Game.getInstance().getMap().getCells()[x][y].setCellType(CellType.selfMinion);
                        minion.setX(x);
                        minion.setY(y);
                        minion.setCanMove(false);
                        minion.setCanAttack(false);
                        Game.getInstance().getPlayer1().getMainDeck().getHand().removeCardFromHand(cardName);
                        Game.getInstance().getMap().getFriendMinions().add(minion);
                    }
                }else if (Spell.thisCardIsSpell(cardName)){
                    Spell spell = (Spell)returnCardInHand(cardName);
                    spell.insertSpellInThisCoordination(x, y);
                    Game.getInstance().getPlayer1().getMainDeck().getHand().removeCardFromHand(cardName);
                }
            }
        }
    }

    public Card returnCardInHand(String cardName){
        for (Card card : this.getCardsInHand()){
            if (card.getName().equals(cardName)){
                return card;
            }
        }
        return null;
    }

    public void removeCardFromHand(String cardName){
        ArrayList<Card> copy = new ArrayList<>(getCardsInHand());
        for (Card card : copy){
            if (card.getName().equals(cardName)){
                this.getCardsInHand().remove(card);
            }
        }
    }


}
