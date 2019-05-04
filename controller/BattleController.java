package controller;

import model.Game;
import model.Shop;
import model.collection.*;
import view.BattleView;

import javax.sound.midi.Soundbank;
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
        if(Game.getInstance().getHeroOfPlayer2().getName().equals(checkCard)) return true;
        return false;
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
                doActionOnCard(cardName,scanner);
            }
        }
        else {
            System.out.println("Invalid card id");
        }
    }
    public static void doActionOnCard(String cardName,Scanner scanner){
        String command = scanner.nextLine();
        String[] commands = command.split(" ");
    }
    public static void moveCardTo(String cardName,int x, int y){

    }

}
