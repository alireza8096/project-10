package controller;

import animation.SpriteAnimation;
import controller.BattleController;
import controller.CollectionController;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.*;
import model.collection.*;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;

import static model.Map.*;

public class AI {

    public static void setAllAICardsMovable() {
        for (Force force : Game.getInstance().getMap().getEnemyMinions()) {
            force.setCanMove(true);
        }
        Game.getInstance().getMap().getEnemyHero().setCanMove(true);
    }

    public static void createDeckOfAI(Player player) throws CloneNotSupportedException {
        Random random = new Random();
        int heroId = random.nextInt(10) + 1;
        int[] idsOfItems = new int[3];
        int[] idsOfMinions = new int[12];
        int[] idsOfSpells = new int[5];
        setRandomIdsItems(idsOfItems);
        setRandomIdsSpells(idsOfSpells);
        setRandomIdsMinions(idsOfMinions);
        Deck deckOfAI = new Deck("AIDeck");
        deckOfAI.setHeroInDeck(Hero.findHeroByID(heroId + 100));
        for (int i = 0; i < idsOfItems.length; i++) {
            deckOfAI.getItemsInDeck().add(Item.findItemByID(idsOfItems[i]));
        }
        for (int i = 0; i < idsOfSpells.length; i++) {
            deckOfAI.getCardsInDeck().add(Spell.findSpellByID(idsOfSpells[i]));
        }
        for (int i = 0; i < idsOfMinions.length; i++) {
            deckOfAI.getCardsInDeck().add(Minion.findMinionByID(idsOfMinions[i]));
        }
//        System.out.println("********hero : " + deckOfAI.getHeroInDeck().getName());
        player.setMainDeck(deckOfAI);
//        for (Card card : Game.getInstance().getPlayer2().getMainDeck().getCardsInDeck()) {
//            System.out.println("* " + card.getName());
//        }

//        for (Item item : Game.getInstance().getPlayer2().getMainDeck().getItemsInDeck())
//            System.out.println(":-* " + item.getName());
    }

    public static void createAIPlayer() throws CloneNotSupportedException {
        System.out.println("createAI player!!!!!!");
        Player AIPlayer = new Player("AI", "AI");
        AIPlayer.setNumOfMana(2);
        Game.getInstance().setPlayer2(AIPlayer);
        createDeckOfAI(AIPlayer);
        AIPlayer.getMainDeck().getHeroInDeck().setX(2);
        AIPlayer.getMainDeck().getHeroInDeck().setY(8);
        Game.getInstance().getMap().getCells()[2][8].setCellType(CellType.enemyHero);
        Game.getInstance().getMap().setEnemyHero(AIPlayer.getMainDeck().getHeroInDeck());
        Map.getForcesView()[8][2].setImage(AIPlayer.getMainDeck().getHeroInDeck().getImageViewOfCard().getImage());
        final Animation animation = new SpriteAnimation(AIPlayer.getMainDeck().getHeroInDeck(),"breathing"
        ,Map.getForcesView()[8][2],Duration.millis(1000));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        Map.getForcesView()[8][2].setY(Map.getForcesView()[8][2].getY() - 25);
        AI.setAllAICardsMovable();
    }

    public static boolean cardCanBeMovedToThisCellAI(Card card, int x, int y) {
        if (distance(card.getX(), card.getY(), x, y) > 2) {
            System.out.println("1");
            return false;
        }
        if (!thisCellIsEmpty(x, y)) {
            System.out.println("2");
            return false;
        }
        if (friendInWay(card.getX(), card.getY(), x, y)) {
            System.out.println("3");
            return false;
        }
        System.out.println("4");
        return true;
    }

    public static boolean friendIsAvailableBetweenThis2Cells(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            int maxY = Math.max(y1, y2);
            int minY = Math.min(y1, y2);
            for (int i = minY; i <= maxY; i++) {
                if (cellIsFriend(x1, i)) {
                    return true;
                }
            }
        } else if (y1 == y2) {
            int maxX = Math.max(x1, x2);
            int minX = Math.max(x2, x2);
            for (int i = minX; i <= maxX; i++) {
                if (cellIsFriend(i, y1))
                    return true;
            }
        }
        return false;
    }

    public static boolean checkAllWaysToReachAI(int x1, int y1, int x2, int y2) {
        if (x1 > x2 && y1 > y2) {
            if (!cellIsFriend(x1 - 1, y1) || !cellIsFriend(x1, y1 - 1))
                return true;
            return false;
        } else if (x1 < x2 && y1 > y2) {
            if (!cellIsFriend(x1, y1 - 1) || !cellIsFriend(x1 + 1, y1))
                return true;
            return false;
        } else if (x1 > x2 && y1 < y2) {
            if (!cellIsFriend(x1 - 1, y1) || !cellIsFriend(x1, y1 + 1))
                return true;
            return false;
        } else {
            if (!cellIsFriend(x1, y1 + 1) || !cellIsFriend(x1 + 1, y1))
                return true;
            return false;
        }
    }

    public static boolean friendInWay(int x1, int y1, int x2, int y2) {
        if (distance(x1, y1, x2, y2) == 1)
            return false;
        else {
            if (x1 == x2 || y1 == y2) {
                if (friendIsAvailableBetweenThis2Cells(x1, y1, x2, y2)) {
                    return true;
                }
                return false;
            } else {
                if (checkAllWaysToReachAI(x1, y1, x2, y2)) {
                    return false;
                }
                return true;
            }
        }
    }

    public static void applyCellTypeAI(Force force, int x, int y) {
        if (force.getCardType().matches("hero")) {
            Game.getInstance().getMap().getCells()[x][y].setCellType(CellType.enemyHero);
        } else
            Game.getInstance().getMap().getCells()[x][y].setCellType(CellType.enemyMinion);
    }

    public static void moveAI(Force force, int x, int y) {
        System.out.println(force.getName() + "cardcanbemovedtothiscellAI");
        System.out.println(force.isCanMove());
        System.out.println(force.isHasMovedInThisTurn());
        if (force.isCanMove() && !force.isHasMovedInThisTurn()) {
            if (cardCanBeMovedToThisCellAI(force, x, y)) {
                Cell.getCellByCoordination(force.getX(), force.getY()).setCellType(CellType.empty);
                Map.getForcesView()[force.getY()][force.getX()].setImage(null);
//                if (force.getCardType().matches("hero")) {
                    Map.getForcesView()[force.getY()][force.getX()].setY(Map.getForcesView()[force.getY()][force.getX()].getY() + 25);
//                } else {
//                    Map.getForcesView()[force.getY()][force.getX()].setX(Map.getForcesView()[force.getY()][force.getX()].getX() - 34);
//                    Map.getForcesView()[force.getY()][force.getX()].setY(Map.getForcesView()[force.getY()][force.getX()].getY() - 34);
//                }
                force.setX(x);
                force.setY(y);
                Map.getForcesView()[y][x].setImage(force.getImageViewOfCard().getImage());
                final Animation animation = new SpriteAnimation(force,"breathing",Map.getForcesView()[y][x]
                ,Duration.millis(1000));
                animation.setCycleCount(Animation.INDEFINITE);
                animation.play();
//                if (force.getCardType().matches("hero")) {
                    Map.getForcesView()[y][x].setY(Map.getForcesView()[y][x].getY() - 25);
//                } else {
//                    Map.getForcesView()[y][x].setX(Map.getForcesView()[y][x].getX() + 34);
//                    Map.getForcesView()[y][x].setY(Map.getForcesView()[y][x].getY() + 34);
//                }
                force.setHasMovedInThisTurn(true);
                applyCellTypeAI(force, x, y);
                System.out.println("moooooooveeeeeee");
                Map.show();
            }
        }
    }

    public static void moveTillPossible() {
        Random random = new Random();
        int x;
        int y;
        while (!Game.getInstance().getMap().getEnemyHero().isHasMovedInThisTurn()) {
            x = random.nextInt(5);
            y = random.nextInt(9);
            moveAI(Game.getInstance().getMap().getEnemyHero(), x, y);
        }
        int minionX;
        int minionY;
        for (Minion minion : Game.getInstance().getMap().getEnemyMinions()) {
            if (!minion.isHasMovedInThisTurn()) {
                minionX = random.nextInt(5);
                minionY = random.nextInt(9);
                moveAI(minion, minionX, minionY);
            }
        }
    }

    public static void attackHeroAI(){
        BattleController.checkAllConditionsToAttack(Game.getInstance().getMap().getEnemyHero(), Game.getInstance().getMap().getFriendHero());
        for (Minion attacker : Game.getInstance().getMap().getEnemyMinions()) {
            BattleController.checkAllConditionsToAttack(attacker, Game.getInstance().getMap().getFriendHero());
        }
        try {
            BattleController.endTurn();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void attackTillPossible() {
        try {
            ArrayList<Minion> friendMinionCopy = Game.getInstance().getMap().getFriendMinions();
            for (Minion minion : friendMinionCopy) {
                BattleController.checkAllConditionsToAttack(Game.getInstance().getMap().getEnemyHero(), minion);
                System.out.println("1");
            }
            BattleController.checkAllConditionsToAttack(Game.getInstance().getMap().getEnemyHero(), Game.getInstance().getMap().getFriendHero());
            System.out.println("2");
            for (Minion attacker : Game.getInstance().getMap().getEnemyMinions()) {
                BattleController.checkAllConditionsToAttack(attacker, Game.getInstance().getMap().getFriendHero());
                Random random = new Random();
                int index = random.nextInt(Game.getInstance().getMap().getFriendMinions().size());
                Minion randomTarget = Game.getInstance().getMap().getFriendMinions().get(index);
                BattleController.checkAllConditionsToAttack(attacker, randomTarget);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        try {
            BattleController.endTurn();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void setRandomIdsItems(int[] array) {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int randomNumber = random.nextInt(20) + 1;
            array[i] = 200 + randomNumber;
            i++;
        }
    }

    public static void setRandomIdsMinions(int[] array) {
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            int randomNumber = random.nextInt(40) + 1;
            array[i] = 300 + randomNumber;
        }
    }

    public static void setRandomIdsSpells(int[] array) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomNumber = random.nextInt(20) + 1;
            array[i] = 400 + randomNumber;
        }
    }

    public static void insertCardTillPossible() throws CloneNotSupportedException {
        System.out.println(Game.getInstance().getPlayer2().getMainDeck().getCardsInDeck().size());
        ArrayList<Card> copyCards = new ArrayList<>(Game.getInstance().getPlayer2().getMainDeck().getCardsInDeck());
        for (Card card : copyCards) {
            if (card.getMana() <= Game.getInstance().getPlayer2().getNumOfMana()) {
                if (card.getCardType().matches("minion")) {
                    insertAIMinionInMap(card.getName());
                    System.out.println(card.getName() + " ENTERDDDDDDDDD");
                    Map.show();
                }
            }
        }
    }

    public static void insertAIMinionInMap(String cardName) throws CloneNotSupportedException {
        Minion minion = Minion.findMinionByName(cardName);
        Random random = new Random();
        int x = random.nextInt(5);
        int y = random.nextInt(9);
        while (!Map.checkIfMinionCardCanBeInsertedInThisCoordinationAI(x, y)) {
            x = random.nextInt(5);
            y = random.nextInt(9);
        }
        Game.getInstance().getPlayer2().setNumOfMana(Game.getInstance().getPlayer2().getNumOfMana() - minion.getMana());
        Game.getInstance().getMap().getCells()[x][y].setCellType(CellType.enemyMinion);
        Map.getForcesView()[y][x].setImage(minion.getImageViewOfCard().getImage());
        final Animation animation = new SpriteAnimation(minion,"breathing",Map.getForcesView()[y][x],
                Duration.millis(1000));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
//        Map.getForcesView()[y][x].setX(Map.getForcesView()[y][x].getX() + 34);
//        Map.getForcesView()[y][x].setY(Map.getForcesView()[y][x].getY() + 34);
        Map.getForcesView()[y][x].setY(Map.getForcesView()[y][x].getY() + 25);
        minion.setX(x);
        minion.setY(y);
        minion.setHasMovedInThisTurn(true);
        minion.setHasAttackedInThisTurn(true);
        minion.setCanMove(true);
        minion.setCanAttack(true);
        Shop.removeProcess(Game.getInstance().getPlayer2().getMainDeck().getCardsInDeck(), minion);
        Game.getInstance().getMap().getEnemyMinions().add(minion);
    }
}

