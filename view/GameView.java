package view;

import controller.BattleController;
import model.Deck;
import model.Game;
import model.collection.*;
import model.GraveYard;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class GameView {

    public static String searchTypeAndShow(String name) throws CloneNotSupportedException {
        String returnString = "";
        if(Minion.thisCardIsMinion(name))
            returnString = showMinion(Minion.findMinionByName(name));
        else if(Spell.thisCardIsSpell(name))
            returnString = showSpell(Spell.findSpellByName(name));
        else if(Item.thisCardIsItem(name))
            returnString = showItem(Item.findItemByName(name));
        else if(Hero.thisCardIsHero(name))
            returnString = showHero(Hero.findHeroByName(name));
        return returnString;
    }
    public static String showCard(Card card) {
        if(Minion.thisCardIsMinion(card.getName())){
           return showMinion((Minion)card);
        }
        else if(Spell.thisCardIsSpell(card.getName())){
            return showSpell((Spell)card);
        }
        return null;
    }
    public static String showItem(Item item){
        String sellCost;
        if(item.getItemType().matches("usable")){
            sellCost = Integer.toString(item.getPrice());
        }
        else{
            sellCost = "collectible";
        }
        return ("Name : "+item.getName() + " - Desc : " + item.getDesc() + " - Sell Cost : " + sellCost);
    }
    public static String showMinion(Minion minion){
        return ("Type : Minion - Name : " +
                minion.getName() + " – Class: " + minion.getAttackType() + " - AP : " + minion.getAttackPower() + " - HP : "
                + minion.getHealthPoint() + " – MP : " + minion.getMana() + " - Special Power : " +minion.getSpecialPower() +" – Sell Cost : " + minion.getPrice());
    }
    public static String showSpell(Spell spell){
        return ("Type : Spell - Name : " +
                spell.getName() + " – MP : " + spell.getMana() + "Desc : " + spell.getDesc() + " – Sell Cost : " + spell.getPrice());
    }

    public static String showHero(Hero hero){
        return ("Name : " + hero.getName() + " - AP : " + hero.getAttackPower() +
                " – HP : " + hero.getHealthPoint() + " – Class : " + hero.getAttackType() +" - Special power : " + hero.getSpecialPower()
         +". - Sell Cost : " + hero.getPrice());
    }
    public static void showDeck(String deckName) throws CloneNotSupportedException {
            Deck deck = Deck.findDeckByName(deckName);
            System.out.println("Heroes :");
            try{
                System.out.println("1 : " + showHero(deck.getHeroInDeck()));
            }
            catch (NullPointerException e){
                System.out.println();
            }
            System.out.println("Items :");
            for (int i = 1; i <= deck.getItemsInDeck().size(); i++) {
                System.out.println(i + " : " + showItem(deck.getItemsInDeck().get(i - 1)));
            }
            System.out.println("Cards :");
            for (int i = 1; i <= deck.getCardsInDeck().size(); i++) {
                System.out.println(i + " : " + showCard(deck.getCardsInDeck().get(i - 1)));
            }
    }

//    public static void showCardsInGraveYard(GraveYard graveYard) throws IOException, ParseException {
//        int counter=0;
//        System.out.println("Heros :");
//        for(String name: graveYard.getCardsDeletedFromHandName())
//        {
//            System.out.print(counter+" ");
//            showMinion(Minion.findMinionByName(name));
//        }
//    }

    public static void showNextCard(){
        Card nextCard = Game.getInstance().getPlayer1().getMainDeck().getHand().getNextCard();
        if (Minion.thisCardIsMinion(nextCard.getName())){
            showMinion((Minion)nextCard);
        }else if (Spell.thisCardIsSpell(nextCard.getName())){
            showSpell((Spell)nextCard);
        }
    }

//    public static void showCardInfoInGraveYard(int cardID) throws Exception {
//        String cardName = BattleController.returnNameById(cardID);
//
//        if (GraveYard.thisCardIsInGraveYard(cardName)){
//            for (String name : Game.getInstance().getGraveYard().getCardsDeletedFromHandName()){
//                if (name.equals(cardName))
//                    GameView.showCard(cardName);
//            }
//        }else{
//            System.out.println("Card is not in Grave yard!");
//        }
//    }

//    public static void showCardsInGraveYard(){
//        for (String cardName : Game.getInstance().getGraveYard().getCardsDeletedFromHandName()){
//            GameView.showCard(cardName);
//        }
//    }

    public static void printInvalidCommandWhithThisContent(String content){
        System.out.println(content);
    }
}