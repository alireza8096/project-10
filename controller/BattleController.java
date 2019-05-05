package controller;

import model.*;
import model.collection.*;

import java.util.Scanner;

public interface BattleController {


    public static void removeCardFromGameToApplyChanges(Card card){

    }
    public static String returnNameById(int cardId)throws Exception{
        int idType = cardId/100;
        int id = cardId%100;
        String name = "";
        switch (idType){
            case 1:
                name = Hero.findHeroNameByID(id);
                break;
            case 2:
                name = Item.findItemNameByID(id);
                break;
            case 3:
                name = Minion.findMinionNameByID(id);
                break;
            case 4:
                name = Spell.findSpellNameByID(id);
                break;
        }
        return name;
    }
    public static Card returnCardInGameByName(String cardName){
        for (Card card:
                Game.getInstance().getPlayer1CardsInField()) {
            if(card.getName().equals(cardName))
                return card;
        }
        for(Card card : Game.getInstance().getPlayer2CardsInField()){
            if(card.getName().equals(cardName))
                return card;
        }
        if(Game.getInstance().getHeroOfPlayer1().getName().equals(cardName)) return Game.getInstance().getHeroOfPlayer1();
//        if(Game.getInstance().getHeroOfPlayer2().getName().equals(checkCard)) return true;
//        return false;
        return null;
    }
    public static boolean thisCardIsYours(String cardName){
        for (Card card:
                Game.getInstance().getPlayer1CardsInField()) {
            if(card.getName().equals(cardName))
                return true;
        }
        if(Game.getInstance().getHeroOfPlayer1().getName().equals(cardName)) return true;
        return false;
    }
    public static boolean cardIsInGame(String checkCard){
        for (Card card:
             Game.getInstance().getPlayer1CardsInField()) {
            if(card.getName().equals(checkCard))
                return true;
        }
        for(Card card : Game.getInstance().getPlayer2CardsInField()){
            if(card.getName().equals(checkCard))
                return true;
        }
        if(Game.getInstance().getHeroOfPlayer1().getName().equals(checkCard)) return true;
        if(Game.getInstance().getHeroOfPlayer2().getName().equals(checkCard)) return true;
        return false;
    }
    public static void selectCardById(int id, Scanner scanner) throws Exception{
        if(Shop.checkValidId(id)){
            String cardName = returnNameById(id);
            if(cardIsInGame(cardName)){
                if(thisCardIsYours(cardName)) {
                    doActionOnCard(cardName, scanner);
                }
                else {
                    System.out.println("This card does not belong to you");
                }
            }
        }
        else {
            System.out.println("Invalid card id");
        }
    }
    public static void doActionOnCard(String cardName,Scanner scanner){
        String command = scanner.nextLine();
        String[] commands = command.split(" ");
        moveCardToCheckConditions(commands,cardName);
    }
    public static void move(Card card,int x,int y){
        Cell.getCellByCoordination(card.getX(),card.getY()).getCellTypes()
        card.setX(x);
        card.setY(y);

    }
    public static void moveCardToCheckConditions(String[] commands,String cardName){
        //enter everything right
        Card card=null;
        for (Card check : Game.getInstance().getPlayer1CardsInField()) {
            if(check.getName().equals(cardName))
                card = check;
        }
        if(Game.getInstance().getHeroOfPlayer1().getName().equals(cardName)){
            card = Game.getInstance().getHeroOfPlayer1();
        }
        int x;
        int y;
        if(card.isMovable()) {
            if (!Map.cardCanBeMovedToThisCell(card,x,y)) {
                System.out.println("Invalid target");
            } else {
                move(card,x,y);
            }
        }
        else {
            System.out.println("This card is not movable");
        }
//        Game.getInstance().getPlayer1().

    }


}
