package view;

import model.Deck;
import model.collection.Card;
import model.collection.Item;
import model.GraveYard;
import model.collection.HandleFiles;
import model.collection.Hero;
import model.collection.Minion;
import model.collection.Spell;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameView {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Downloads/AP-Project-10/project-10/src/model/collection/";

    private static GameView view = new GameView();

    public static GameView getInstance(){
        return view;
    }
    Scanner scanner = new Scanner(System.in);

    public static void showCard(String cardName,String type) throws IOException, ParseException {
        if(type.equals("Minion"))
        {
            showMinion(cardName);
        }
        if(type.equals("Spell"))
        {
            showSpell(cardName);
        }
        if(type.equals("Hero"))
        {
            showHero(cardName);
        }
    }

    public static void showItem(String itemName) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES + "JSON-Item/" +itemName+".json");
        String name=jsonObject.get("name").toString();
        String desc=jsonObject.get("desc").toString();
        System.out.println("Name : "+name);
    }

    public static void showMinion(String minionName) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Minions" );
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES + "JSON-Spell/" + minionName+".json");
        String name = jsonObject.get("name").toString();
        String MP = jsonObject.get("mana").toString();
        String price = jsonObject.get("price").toString();
        String attackType = jsonObject.get("attackType").toString();
        String AP = jsonObject.get("attackPower").toString();
        String HP = jsonObject.get("healthPoint").toString();
        System.out.println("Type : Minion - Name : " +
                name + " – Class: " + attackType + " - AP : " + AP + " - HP : " + HP + " – MP : " + MP + " – Sell Cost : " + price);
    }

    public static void showSpell(String spellName) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Spells" );
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES + "JSON-Spell/" + spellName+".json");
        String name = jsonObject.get("name").toString();
        String desc = jsonObject.get("desc").toString();
        String MP = jsonObject.get("MP").toString();
        String price = jsonObject.get("price").toString();
        System.out.println("Type : Spell - Name : " +
                name + " – MP : " + MP + "Desc : " + desc + " – Sell Cost : " + price);
    }

    public static void showHero(String heroName) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Heroes" );
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES + "JSON-Heroes/" + heroName+".json");
        String name = jsonObject.get("name").toString();
        String AP = jsonObject.get("attackPower").toString();
        String HP = jsonObject.get("healthPoint").toString();
        String attackType = jsonObject.get("attackType").toString();
        System.out.println("Name : " + name + " - AP : " + AP +
                " – HP : " + HP + " – Class : " + attackType);
    }

    public static void showDeck(String deckName) throws IOException, ParseException {
        Deck deck = Deck.findDeckByName(deckName);
        ArrayList<String> heroesInDeck = new ArrayList<>();
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
        }

        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Heroes");
        File[] listOfFiles = folder.listFiles();

        System.out.println("Heros :");
        int counter = 1;

        for (String heroInDeck : heroesInDeck){
            for (int i = 0; i < listOfFiles.length; i++) {
                String fileName = listOfFiles[i].getName().split("\\.")[0];
                fileName = HandleFiles.changeFileNameToProperName(fileName);
                if (fileName.equals(heroInDeck)){
              /*      JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                            (ADDRESS_OF_JSON_FILES + "JSON-Heroes/" + listOfFiles[i].getName());
                    String name = jsonObject.get("name").toString();
                    String AP = jsonObject.get("attackPower").toString();
                    String HP = jsonObject.get("healthPoint").toString();
                    String attackType = jsonObject.get("attackType").toString();

                    System.out.println("    " + counter + " : Name : " + name + " - AP : " + AP +
                            " – HP : " + HP + " – Class : " + attackType); */
                    counter++;
                    break;
                }
            }
        }

        folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
        listOfFiles = folder.listFiles();
        System.out.println("Items :");
        counter = 1;
        for (String itemInDeck : deck.getItemsInDeckNames()){
            for (int i = 0; i < listOfFiles.length; i++) {
                String fileName = listOfFiles[i].getName().split("\\.")[0];
                fileName = HandleFiles.changeFileNameToProperName(fileName);
                if (fileName.equals(itemInDeck)){
                   /* JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                            (ADDRESS_OF_JSON_FILES + "JSON-Spells/" + listOfFiles[i].getName());
                    String name = jsonObject.get("name").toString();
                    String desc = jsonObject.get("desc").toString();
                    String MP = jsonObject.get("MP").toString();
                    String price = jsonObject.get("price").toString();

                    System.out.println("    " + counter + " : Type : Spell - Name : " +
                            name + " – MP : " + MP + "Desc : " + desc + " – Sell Cost : " + price); */
                    counter++;
                    break;
                }
            }
        }

        folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Spells");
        listOfFiles = folder.listFiles();
        System.out.println("Cards :");
        counter = 1;
        for (String spellInDeck : spellsInDeck){
            for (int i = 0; i < listOfFiles.length; i++) {
                String fileName = listOfFiles[i].getName().split("\\.")[0];
                fileName = HandleFiles.changeFileNameToProperName(fileName);
                if (fileName.equals(spellInDeck)){
                /*    JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                            (ADDRESS_OF_JSON_FILES + "JSON-Spells/" + listOfFiles[i].getName());
                    String name = jsonObject.get("name").toString();
                    String desc = jsonObject.get("desc").toString();
                    String MP = jsonObject.get("MP").toString();
                    String price = jsonObject.get("price").toString();

                    System.out.println("    " + counter + " : Type : Spell - Name : " +
                            name + " – MP : " + MP + "Desc : " + desc + " – Sell Cost : " + price); */
                    counter++;
                    break;
                }
            }
        }

        folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Minions");
        listOfFiles = folder.listFiles();
        counter = 1;
        for (String minionInDeck : minionsInDeck){
            for (int i = 0; i < listOfFiles.length; i++) {
                String fileName = listOfFiles[i].getName().split("\\.")[0];
                fileName = HandleFiles.changeFileNameToProperName(fileName);
                if (fileName.equals(minionInDeck)){
                /*    JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                            (ADDRESS_OF_JSON_FILES + "JSON-Minions/" + listOfFiles[i].getName());
                    String name = jsonObject.get("name").toString();
                    String MP = jsonObject.get("mana").toString();
                    String price = jsonObject.get("price").toString();
                    String attackType = jsonObject.get("attackType").toString();
                    String AP = jsonObject.get("attackPower").toString();
                    String HP = jsonObject.get("healthPoint").toString();

                    System.out.println("    " + counter + " : Type : Minion - Name : " +
                            name + " – Class: " + attackType + " - AP : " + AP + " - HP : " + HP + " – MP : " + MP + " – Sell Cost : " + price);*/
                    counter++;
                    break;
                }
            }
        }

    }

    public static void showCardsInGraveYard(GraveYard graveYard) throws IOException, ParseException {
        int counter=0;
        System.out.println("Heros :");
        for(String name: graveYard.getCardsDeletedFromHandName())
        {
            System.out.print(counter+" ");
            showCard(name,"minion");
        }
    }
}