package model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.collection.Card;
import model.collection.HandleFiles;
import model.collection.Minion;
import model.collection.Spell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    private static ImageView[] scenesBehind = new ImageView[5];
    private static ImageView[] cards = new ImageView[5];
    private ArrayList<Card> cardsInHand = new ArrayList<>();
    private Card nextCard;
    private static boolean selectedInHand;
    private static int indexInnHand;


    public static boolean isSelectedInHand() {
        return selectedInHand;
    }

    public static void setSelectedInHand(boolean selectedInHand) {
        Hand.selectedInHand = selectedInHand;
    }

    public static int getIndexInHand() {
        return indexInnHand;
    }

    public static void setIndexInnHand(int indexInnHand) {
        Hand.indexInnHand = indexInnHand;
    }

    public static ImageView[] getCards() {
        return cards;
    }

    public static ImageView[] getScenesBehind() {
        return scenesBehind;
    }

    public static void handController(){
        for(int i=0; i<5; i++){
            handleHand(cards[i],i);
        }
    }
    public static void showAllPossibleEntries(){
        for (int i=0; i<9; i++){
            for (int j=0; j<5; j++){
                if(Map.checkIfMinionCardCanBeInsertedInThisCoordination(j,i)){
                    try {
                        Map.getCellsView()[i][j].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/tiles_board_hand.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
    public static void handleHand(ImageView cards,int i){
        cards.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand().get(i)!=null){
                    if (Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand().get(i).getMana() <= Game.getInstance().getPlayer1().getNumOfMana()){
                        selectedInHand = true;
                    }
                }
                showAllPossibleEntries();
            }
        });
    }
    public static void createHand() throws FileNotFoundException {
        HBox handScenes = new HBox();
        HBox cardsView = new HBox();
        for(int i=0; i<5; i++){
            cards[i] = new ImageView();
            scenesBehind[i] =  new ImageView();
            handScenes.getChildren().add(scenesBehind[i]);
            cardsView.getChildren().add(cards[i]);
//            handleHand(scenesBehind[i]);
        }
        handScenes.setLayoutX(130);
        handScenes.setLayoutY(700);
        handScenes.setSpacing(5);
        handScenes.setScaleX(0.6);
        handScenes.setScaleY(0.6);
        AllDatas.currentRoot.getChildren().add(handScenes);
        cardsView.setScaleX(1.3);
        cardsView.setScaleY(1.3);
        cardsView.setSpacing(65);
        cardsView.setLayoutX(540);
        cardsView.setLayoutY(800);
        AllDatas.currentRoot.getChildren().add(cardsView);
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
    public static void setHand() throws FileNotFoundException {
        Hand hand = new Hand();
        ArrayList<Card> cardsInDeck = Game.getInstance().getPlayer1().getMainDeck().getCardsInDeck();
        Collections.shuffle(cardsInDeck);
        for (int i = 0; i < 5; i++) {
            Card card = cardsInDeck.get(i);
            hand.getCardsInHand().add(card);
            if(card.getMana() <= Game.getInstance().getPlayer1().getNumOfMana()){
                Hand.scenesBehind[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE +
                        "view/Photos/battle/cardActivatedInHand.png")));
            }
            else {
                Hand.scenesBehind[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE +
                        "view/Photos/battle/card_background_disabled@2x.png")));
            }
            Hand.cards[i].setImage(card.getForceInField());
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
                        Map.getCellsView()[y][x].setDisable(true);
                        Map.getForcesView()[y][x].setImage(minion.getForceInField());
                        minion.setCanMove(false);
                        minion.setCanAttack(false);
                        Game.getInstance().getPlayer1().getMainDeck().getHand().removeCardFromHand(cardName);
                        Hand.cards[Hand.indexInnHand].setImage(null);
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
