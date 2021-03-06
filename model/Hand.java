package model;

import animation.GetImage;
import animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.collection.Card;
import model.collection.HandleFiles;
import model.collection.Minion;
import model.collection.Spell;
import org.json.simple.parser.ParseException;
import org.w3c.dom.css.Rect;
import view.MenuView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    private static ImageView[] scenesBehind = new ImageView[5];
    private static ImageView[] cards = new ImageView[5];
    private static Label[] manas = new Label[5];
    private static SpriteAnimation[] animation = new SpriteAnimation[5];
    private Card[] cardsInHand = new Card[5];
    private Card nextCard;
    private static boolean selectedInHand;
    private static int indexInHand;


    public static boolean isSelectedInHand() {
        return selectedInHand;
    }

    public static void setSelectedInHand(boolean selectedInHand) {
        Hand.selectedInHand = selectedInHand;
    }

    public static int getIndexInHand() {
        return indexInHand;
    }

    public static void setIndexInHand(int indexInHand) {
        Hand.indexInHand = indexInHand;
    }

    public static ImageView[] getCards() {
        return cards;
    }

    public static ImageView[] getScenesBehind() {
        return scenesBehind;
    }

    public static void handController() {
        for (int i = 0; i < 5; i++) {
            handleHand(cards[i], i);
        }
    }

    public static void showAllPossibleEntries(Card card) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                if (Spell.thisCardIsSpell(card.getName())) {
                } else if (Minion.thisCardIsMinion(card.getName())) {
                    if (Map.checkIfMinionCardCanBeInsertedInThisCoordination(j, i)) {
                        try {
                            Map.getCellsView()[i][j].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/tiles_board_hand.png")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void handleHand(ImageView cards, int i) {
        cards.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Hand.indexInHand = i;
            if (Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand()[i] != null) {
                if (Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand()[i].getMana() <= Game.getInstance().getPlayer1().getNumOfMana()) {
                    MenuView.resetMap();
                    showAllPossibleEntries(Game.getInstance().getPlayer1().getMainDeck().getHand().cardsInHand[i]);
                    selectedInHand = true;
                }
            }
        });
        cards.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            if (Game.getInstance().getPlayer1().getMainDeck().getHand().cardsInHand[i] != null) {
                if (Game.getInstance().getPlayer1().getMainDeck().getHand().cardsInHand[i].getMana() <= Game.getInstance().getPlayer1().getNumOfMana()) {
                    cards.setEffect(new Glow(0.7));
                    scenesBehind[i].setEffect(new Glow(0.7));
                }
            }
        });
        cards.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (Game.getInstance().getPlayer1().getMainDeck().getHand().cardsInHand[i] != null) {
                if (Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand()[i] != null) {
                    if (Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand()[i].getMana() <= Game.getInstance().getPlayer1().getNumOfMana()) {
                        cards.setEffect(null);
                        scenesBehind[i].setEffect(null);
                    }
                }
            }
        });
    }

    public static void createHand() {
        HBox handScenes = new HBox();
        HBox cardsView = new HBox();
        HBox manasView = new HBox();
        for (int i = 0; i < 5; i++) {
            cards[i] = new ImageView();
            cards[i].setScaleX(1.3);
            cards[i].setScaleY(1.3);
            scenesBehind[i] = new ImageView();
            manas[i] = new Label();
            manas[i].setTextFill(Color.WHITE);
            animation[i] = new SpriteAnimation(cards[i]);
            handScenes.getChildren().add(scenesBehind[i]);
            cardsView.getChildren().add(cards[i]);
            manasView.getChildren().add(manas[i]);
        }
        handScenes.setLayoutX(130);
        handScenes.setLayoutY(700);
        handScenes.setSpacing(5);
        handScenes.setScaleX(0.6);
        handScenes.setScaleY(0.6);
        AllDatas.currentRoot.getChildren().add(handScenes);
        cardsView.setScaleX(2);
        cardsView.setScaleY(2);
        cardsView.setSpacing(20);
        cardsView.setLayoutX(640);
        cardsView.setLayoutY(780);
        AllDatas.currentRoot.getChildren().add(cardsView);
        manasView.setLayoutX(607);
        manasView.setLayoutY(890);
        manasView.setSpacing(106);
        manasView.setScaleX(1.5);
        manasView.setScaleY(1.5);
        AllDatas.currentRoot.getChildren().add(manasView);
    }

    public Card getNextCard() {
        return nextCard;
    }

    public void setNextCard(Card nextCard) {
        this.nextCard = nextCard;
    }

    public Card[] getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(Card[] cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public static void showHand() {
        for (Card card :
                Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand()) {
//            System.out.println(card.getName() + " : " + card.getCardType() + " " + card.getMana());
        }
    }

    public static void setHand() throws FileNotFoundException {
        Hand hand = new Hand();
        ArrayList<Card> cardsInDeck = Game.getInstance().getPlayer1().getMainDeck().getCardsInDeck();
        Collections.shuffle(cardsInDeck);
        for (int i = 0; i < 5; i++) {
            Card card = cardsInDeck.get(i);
            hand.getCardsInHand()[i] = card;
            if (card.getMana() <= Game.getInstance().getPlayer1().getNumOfMana()) {
                Hand.scenesBehind[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE +
                        "view/Photos/battle/cardActivatedInHand.png")));
            } else {
                Hand.scenesBehind[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE +
                        "view/Photos/battle/card_background_disabled@2x.png")));
            }
            Hand.cards[i].setImage(card.getImageViewOfCard().getImage());
            Rectangle rectangle = new Rectangle(65, 65);
            cards[i].fitHeightProperty().bind(rectangle.heightProperty());
            cards[i].fitWidthProperty().bind(rectangle.widthProperty());
            animation[i].setSpriteAnimation(card, "breathing", Duration.millis(1000));
            animation[i].setCycleCount(Animation.INDEFINITE);
            animation[i].play();
            Hand.manas[i].setText(Integer.toString(card.getMana()));
            Game.getInstance().getPlayer1().getMainDeck().getCardsInDeck().remove(card);
        }
        Game.getInstance().getPlayer1().getMainDeck().setHand(hand);
    }

    public boolean checkIfNumberOfCardsInHandIsValid() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (cardsInHand[i] != null) {
                counter++;
            }
        }
        return counter < 5;
    }

    public void addCardToHandFromDeck() {
        if (this.checkIfNumberOfCardsInHandIsValid()) {
            Deck mainDeck = Game.getInstance().getPlayer1().getMainDeck();
            Collections.shuffle(mainDeck.getCardsInDeck());
            nextCard = mainDeck.getCardsInDeck().get(0);
            mainDeck.getCardsInDeck().remove(nextCard);
            for (int i = 0; i < 5; i++) {
                if (cards[i].getOpacity() == 0) {
                    cards[i].setOpacity(1);
                    manas[i].setOpacity(1);
                    cardsInHand[i] = nextCard;
                    try {
                        if (nextCard.getMana() <= Game.getInstance().getPlayer1().getNumOfMana()) {
                            scenesBehind[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE +
                                    "view/Photos/battle/cardActivatedInHand.png")));
                        } else {
                            scenesBehind[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE +
                                    "view/Photos/battle/card_background_disabled@2x.png")));
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    cards[i].setImage(nextCard.getImageViewOfCard().getImage());
                    Rectangle rectangle = new Rectangle(65, 65);
                    cards[i].fitWidthProperty().bind(rectangle.widthProperty());
                    cards[i].fitHeightProperty().bind(rectangle.heightProperty());
                    animation[i].setSpriteAnimation(nextCard, "breathing", Duration.millis(1000));
                    animation[i].setCycleCount(Animation.INDEFINITE);
                    animation[i].play();
                    manas[i].setText(Integer.toString(nextCard.getMana()));
                    return;
                }
            }
        }
    }

    public boolean checkIfCardIsInHand(String cardName) {
        for (Card card : this.getCardsInHand()) {
            if (card.getName().equals(cardName))
                return true;
        }
        return false;
    }

    public static void setStackForForcesInMap(int x, int y, Minion minion) throws FileNotFoundException {

//        Map.getForcesView()[y][x].setImage(minion.getForceInField());
//        Map.getForcesView()[y][x].setX(Map.getForcesView()[y][x].getX() + 34);
//        Map.getForcesView()[y][x].setY(Map.getForcesView()[y][x].getY() + 34);
        ImageView healthPointIcon = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/hp_icon_in_map.png")));
        healthPointIcon.setFitWidth(25);
        healthPointIcon.setFitHeight(25);
        StackPane.setMargin(healthPointIcon, new Insets(20, 20, 1, 1));
        StackPane.setMargin(Map.getForcesView()[y][x], new Insets(1, 1, 30, 1));

        Map.getForcesStack()[y][x].getChildren().addAll(Map.getForcesView()[y][x], healthPointIcon);
        Map.getForcesStack()[y][x].setLayoutX(Map.getForcesView()[y][x].getX() + 34);
        Map.getForcesStack()[y][x].setLayoutY(Map.getForcesView()[y][x].getY() + 34);


    }

    public void insertCardFromHandInMap(int index, int x, int y) throws IOException, ParseException {
        Card card = cardsInHand[index];
        Game.getInstance().getPlayer1().setNumOfMana(Game.getInstance().getPlayer1().getNumOfMana() - card.getMana());
        for (int i = 0; i < 5; i++) {
            if (cardsInHand[i] != null) {
                if (cardsInHand[i].getMana() > Game.getInstance().getPlayer1().getNumOfMana()) {
                    scenesBehind[i].setImage(new Image(new FileInputStream(
                            HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/card_background_disabled@2x.png")));
                }
            }
        }
        Game.getInstance().getMap().getCells()[x][y].setCellType(CellType.selfMinion);
//        card.setX(x);
//        card.setY(y);
        Map.getForcesView()[y][x].setImage(card.getImageViewOfCard().getImage());
        final Animation animation = new SpriteAnimation(card,"breathing",Map.getForcesView()[y][x],Duration.millis(1000));
        Map.setAnimations(y,x,new SpriteAnimation(card.getImageViewOfCard()));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        card.setX(x);
        card.setY(y);
        card.setHasMovedInThisTurn(true);
        card.setHasAttackedInThisTurn(true);
        Game.getInstance().getPlayer1().getMainDeck().getHand().removeCardFromHand(index);
        for (int i = 0; i < 9; i++) {
            Game.getPlayerMana()[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/icon_mana_inactive.png")));
            if (i < Game.getInstance().getPlayer1().getNumOfMana()) {
                Game.getPlayerMana()[i].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/icon_mana.png")));
            }
        }
        try {
            Hand.cards[Hand.indexInHand].setOpacity(0);
            Hand.manas[Hand.indexInHand].setOpacity(0);
            Hand.scenesBehind[Hand.indexInHand].setImage(new Image(new FileInputStream(
                    HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/card_background_disabled@2x.png")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (card.getCardType().matches("minion")) {
            Game.getInstance().getMap().getFriendMinions().add((Minion) card);
        }
    }

    public void removeCardFromHand(int index) {
        cardsInHand[index] = null;
    }


}
