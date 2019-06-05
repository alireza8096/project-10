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
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

    public static String searchTypeAndShow(String name) throws Exception{
        String returnString = "";
        if(Minion.thisCardIsMinion(name))
            returnString = showMinion(name);
        else if(Spell.thisCardIsSpell(name))
            returnString = showSpell(name);
        else if(Item.thisCardIsItem(name))
            returnString = showItem(name);
        else if(Hero.thisCardIsHero(name))
            returnString = showHero(name);
        return returnString;
    }
    public static String showCard(String cardName) throws IOException, ParseException {
        if(Minion.thisCardIsMinion(cardName)){
           return showMinion(cardName);
        }
        else if(Spell.thisCardIsSpell(cardName)){
            return showSpell(cardName);
        }
        return null;
    }
    public static String showItem(String itemName) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES + "JSON-Items/" +itemName+".json");
        String name=jsonObject.get("name").toString();
        String desc=jsonObject.get("desc").toString();
        String sellCost;
        if(jsonObject.get("itemType").toString().matches("usable")) {
            sellCost = jsonObject.get("price").toString();
        }
        else{
            sellCost = "collectible";
        }
        return ("Name : "+name + " - Desc : " + desc + " - Sell Cost : " + sellCost);
    }
    public static String showMinion(String minionName) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES + "JSON-Minions/" + minionName+".json");
        String name = jsonObject.get("name").toString();
        String MP = jsonObject.get("mana").toString();
        String price = jsonObject.get("price").toString();
        String attackType = jsonObject.get("attackType").toString();
        String AP = jsonObject.get("attackPower").toString();
        String HP = jsonObject.get("healthPoint").toString();
        String speacialPower = jsonObject.get("specialPower").toString();
        return ("Type : Minion - Name : " +
                name + " – Class: " + attackType + " - AP : " + AP + " - HP : "
                + HP + " – MP : " + MP + " - Special Power : " +speacialPower +" – Sell Cost : " + price);
    }
    public static String showSpell(String spellName) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES + "JSON-Spells/" + spellName+".json");
        String name = jsonObject.get("name").toString();
        String desc = jsonObject.get("desc").toString();
        String MP = jsonObject.get("mana").toString();
        String price = jsonObject.get("price").toString();
        return ("Type : Spell - Name : " +
                name + " – MP : " + MP + "Desc : " + desc + " – Sell Cost : " + price);
    }

    public static String showHero(String heroName) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Heroes" );
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES + "JSON-Heroes/" + heroName+".json");
        String name = jsonObject.get("name").toString();
        String AP = jsonObject.get("attackPower").toString();
        String HP = jsonObject.get("healthPoint").toString();
        String attackType = jsonObject.get("attackType").toString();
        String specialPower = jsonObject.get("specialPower").toString();
        String sellPrice = jsonObject.get("price").toString();
        return ("Name : " + name + " - AP : " + AP +
                " – HP : " + HP + " – Class : " + attackType +" - Special power : " + specialPower
         +". - Sell Cost : " + sellPrice);
    }
    public static void showDeck(String deckName) throws IOException, ParseException {
        Deck deck = Deck.findDeckByName(deckName);
        ArrayList<String> heroesInDeck = new ArrayList<>();
        ArrayList<String> itemsInDeck = new ArrayList<>();
        ArrayList<String> spellsInDeck = new ArrayList<>();
        ArrayList<String> minionsInDeck = new ArrayList<>();
        for (String cardName : deck.getCardsInDeckNames()){
            if (Hero.thisCardIsHero(cardName)){
                heroesInDeck.add(cardName);
            }else if (Minion.thisCardIsMinion(cardName)){
                minionsInDeck.add(cardName);
            }else if (Spell.thisCardIsSpell(cardName)){
                spellsInDeck.add(cardName);
            }
            else if(Item.thisCardIsItem(cardName)){
                itemsInDeck.add(cardName);
            }
        }
        System.out.println("Heroes :");
        for(int i=1; i<=heroesInDeck.size(); i++){
            System.out.println( i + " : " + showHero(heroesInDeck.get(i-1)));
        }
        System.out.println("Items :");
        for (int i = 1; i <= itemsInDeck.size(); i++) {
            System.out.println(i + " : " + showItem(itemsInDeck.get(i - 1)));
        }
        System.out.println("Cards :");
        for (int i = 1; i <=spellsInDeck.size(); i++) {
            System.out.println(i + " : " + showSpell(spellsInDeck.get(i - 1)));
        }
        for(int i=1; i<=minionsInDeck.size(); i++){
            System.out.println(i + " : " + showMinion(minionsInDeck.get(i-1)));
        }
    }

    public static void showCardsInGraveYard(GraveYard graveYard) throws IOException, ParseException {
        int counter=0;
        System.out.println("Heros :");
        for(String name: graveYard.getCardsDeletedFromHandName())
        {
            System.out.print(counter+" ");
            showMinion(name);
        }
    }

    public static void showNextCard() throws IOException, ParseException {
        String nextCardName = Game.getInstance().getPlayer1().getMainDeck().getHand().getNextCardName();
        if (Minion.thisCardIsMinion(nextCardName)){
            showMinion(nextCardName);
        }else if (Spell.thisCardIsSpell(nextCardName)){
            showSpell(nextCardName);
        }else if (Item.thisCardIsItem(nextCardName)){
            showItem(nextCardName);
        }
    }

    public static void showCardInfoInGraveYard(int cardID) throws Exception {
        String cardName = BattleController.returnNameById(cardID);

        if (GraveYard.thisCardIsInGraveYard(cardName)){
            for (String name : Game.getInstance().getGraveYard().getCardsDeletedFromHandName()){
                if (name.equals(cardName))
                    GameView.showCard(cardName);
            }
        }else{
            System.out.println("Card is not in Grave yard!");
        }
    }

    public static void showCardsInGraveYard() throws IOException, ParseException {
        for (String cardName : Game.getInstance().getGraveYard().getCardsDeletedFromHandName()){
            GameView.showCard(cardName);
        }
    }

    public static void printInvalidCommandWhithThisContent(String content){
        System.out.println(content);
    }
}