package view;

import controller.BattleController;
import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.AllDatas;
import model.Game;
import model.Hand;
import model.Shop;
import model.collection.*;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;

public class BattleView {
    private static ImageView endTurn;
    public static ImageView getEndTurn() {
        return endTurn;
    }

    public static void setEndTurn(ImageView endTurn) {
        BattleView.endTurn = endTurn;
        endTurn.setX(1150);
        endTurn.setY(745);
        endTurn.setScaleX(0.5);
        endTurn.setScaleY(0.5);
    }

    //not completed
    public static void handleEndTurn(){
        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("handleee");
                try {
                    BattleController.endTurn();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (CloneNotSupportedException e) {
                    e.getMessage();
                }
            }
        });
        endTurn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Glow glow = new Glow(0.5);
                endTurn.setEffect(glow);
            }
        });
        endTurn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Glow glow = new Glow(0);
                endTurn.setEffect(glow);
            }
        });
    }
    public static void showEndTurn() throws FileNotFoundException {
        Image setImage;
        if (Game.getInstance().isPlayer1Turn()) {
            System.out.println("1");
            setImage = new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE+ "view/Photos/battle/button_end_turn_mine@2x.png"));
        } else {
            System.out.println("2");
            setImage = new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE +"view/Photos/battle/button_end_turn_enemy@2x.png"));
        }
        endTurn.setImage(setImage);

    }

    public static void showGameInfo(String[] commands) {
        if (commands.length == 2 && commands[0].compareToIgnoreCase("game") == 0
                && commands[1].compareToIgnoreCase("info") == 0) {
            System.out.println("Game info :");
            int ownMana = Game.getInstance().getPlayer1().getNumOfMana();
            int enemyMana = Game.getInstance().getPlayer2().getNumOfMana();
            System.out.println("Your mana point : " + ownMana);
            System.out.println("Enemy's mana point : " + enemyMana);
            switch (Game.getInstance().getGameMode()) {
                case killingHeroOfEnemy:
//                    BattleView.showGameForFirstMode();
                    break;
                case collectingAndKeepingFlags:
                    break;
                case collectingHalfOfTheFlags:
                    break;
            }
            AllDatas.hasEnteredBattle = true;
        }
    }

    public static void showMyMinions(String[] commands) {
        if (commands.length == 3 && commands[0].compareToIgnoreCase("show") == 0
                && commands[1].compareToIgnoreCase("my") == 0 && commands[2].compareToIgnoreCase("minions") == 0) {
//            Hero hero = Game.getInstance().getHeroOfPlayer1();
//            System.out.println((300 + hero.getId()) + " : " + hero.getName() + ", health : " + hero.getHealthPoint() + ", location : " + "(" + hero.getX() + ", " + hero.getY() + "), power : " + hero.getAttackPower());
            for (Card card : Game.getInstance().getMap().getFriendMinions()) {
                if (Minion.thisCardIsMinion(card.getName())) {
                    Minion minion = (Minion) card;
                    System.out.println((300 + minion.getId()) + " : " + minion.getName() + ", health : " + minion.getHealthPoint() + ", location : " + "(" + minion.getX() + ", " + minion.getY() + "), power : " + minion.getAttackPower());
                }
            }
            AllDatas.hasEnteredBattle = true;
        }
    }

    public static void showOpponentMinions(String[] commmands) {
        if (commmands.length == 3 && commmands[0].compareToIgnoreCase("show") == 0
                && commmands[1].compareToIgnoreCase("opponent") == 0 && commmands[2].compareToIgnoreCase("minions") == 0) {
//            Hero hero = Game.getInstance().getHeroOfPlayer2();
//            System.out.println((300 + hero.getId()) + " : " + hero.getName() + ", health : " + hero.getHealthPoint() + ", location : " + "(" + hero.getX() + ", " + hero.getY() + "), power : " + hero.getAttackPower());
            for (Card card : Game.getInstance().getMap().getEnemyMinions()) {
                if (Minion.thisCardIsMinion(card.getName())) {
                    Minion minion = (Minion) card;
                    System.out.println((300 + minion.getId()) + " : " + minion.getName() + ", health : " + minion.getHealthPoint() + ", location : " + "(" + minion.getX() + ", " + minion.getY() + "), power : " + minion.getAttackPower());
                }
            }
            AllDatas.hasEnteredBattle = true;
        }
    }

    public static void showCardInfo(String[] commands) throws Exception {
        if (commands.length == 4 && commands[0].compareToIgnoreCase("show") == 0 && commands[1].compareToIgnoreCase("card") == 0
                && commands[2].compareToIgnoreCase("info") == 0 && commands[3].matches("[\\d]+")) {
            int cardId = Integer.parseInt(commands[3]);
            if (Shop.checkValidId(cardId)) {
                String name = BattleController.returnNameById(cardId);
                if (BattleController.cardIsInGame(name)) {
//                    showHeroInGame(name);
                    showMinionInGame(name);
//                    showSpellInGame(name);
                } else {
                    System.out.println("Card with this name is not in game");
                }
            } else System.out.println("The ID is not valid");
            AllDatas.hasEnteredBattle = true;
        }
    }

    //    public static void showGameForFirstMode(){
//        int ownHP = Game.getInstance().getHeroOfPlayer1().getHealthPoint();
//        int enemyHP = Game.getInstance().getHeroOfPlayer2().getHealthPoint();
//        System.out.println("Your health point : " + ownHP);
//        System.out.println("Enemy's health point : " + enemyHP);
//    }
//    public static void showHeroInGame(String cardName){
//        Hero hero;
//        if(cardName.equals(Game.getInstance().getHeroOfPlayer1().getName())){
//            hero = Game.getInstance().getHeroOfPlayer1();
//        }
//        else hero = Game.getInstance().getHeroOfPlayer2();
//        System.out.println("Hero :");
//        System.out.println("Name : " +hero.getName());
//        System.out.println("Cost : " +hero.getPrice());
//        System.out.println("Desc : "+hero.getSpecialPower());
//    }
    public static void showMinionInGame(String cardName) {
        Minion minion = null;
        for (Card card :
                Game.getInstance().getMap().getFriendMinions()) {
            if (card.getName().equals(cardName)) {
                minion = (Minion) card;
            }
        }
        for (Card card :
                Game.getInstance().getMap().getEnemyMinions()) {
            if (card.getName().equals(cardName)) {
                minion = (Minion) card;
            }
        }
        System.out.println("Minion :");
        System.out.println("Name : " + minion.getName());
        System.out.println("HP : " + minion.getHealthPoint() + "    AP : " + minion.getAttackPower() + "    MP : " + minion.getMana());
        System.out.println("Range : " + minion.getAttackRange());
        System.out.println("Combo-ability : ");
        System.out.println("Cost : " + minion.getPrice());
        System.out.println("Desc : " + minion.getDesc());
    }

    //    public static void showSpellInGame(String cardName){
//        Spell spell = null;
//        for (Card card:
//                Game.getInstance().getMap().getFriendMinions()) {
//            if(card.getName().equals(cardName)){
//                spell = (Spell) card;
//            }
//        }
//        for (Card card:
//                Game.getInstance().getMap().) {
//            if(card.getName().equals(cardName)){
//                spell = (Spell) card;
//            }
//        }
//        System.out.println("Spell: ");
//        System.out.println("Name : " + spell.getName());
//        System.out.println("MP : " + spell.getMana());
//        System.out.println("Cost : " + spell.getPrice());
//        System.out.println("Desc : " + spell.getDesc());
//    }
    public static void showHand(String[] commands) throws Exception {
        if (commands.length == 2 && commands[0].compareToIgnoreCase("show") == 0
                && commands[1].compareToIgnoreCase("hand") == 0) {
            Hand hand = Game.getInstance().getPlayer1().getMainDeck().getHand();
            Card nextCard = hand.getNextCard();
            System.out.println("Next card information : ");
            GameView.searchTypeAndShow(nextCard.getName());
            System.out.println("Cards in hand : ");
            for (Card card :
                    hand.getCardsInHand()) {
                GameView.searchTypeAndShow(card.getName());
            }
            AllDatas.hasEnteredBattle = true;
        }
    }
}
