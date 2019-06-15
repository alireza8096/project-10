package model.collection;


import model.Deck;
import model.Player;
import model.Hand;
import model.Shop;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static model.collection.Account.PLAYERS_FOLDER;

public class HandleFiles {
    private static final String ADDRESS_HERO = "/Users/bahar/Desktop/DUELYST/model/collection/JSON-Heroes";
    private static final String ADDRESS_MINION = "/Users/bahar/Desktop/DUELYST/model/collection/JSON-Minions";
    private static final String ADDRESS_SPELL = "/Users/bahar/Desktop/DUELYST/model/collection/JSON-Spells";
    private static final String ADDRESS_ITEM = "/Users/bahar/Desktop/DUELYST/model/collection/JSON-Items";

        public static void createStringOfPlayers() {
        File folder = new File("/Users/bahar/Desktop/DUELYST/model/collection/players");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            Account.getPlayers().add(listOfFiles[i].getName().split("\\.")[0]);
        }
    }
    public static void createSpells() throws IOException, ParseException {
        File folder = new File(ADDRESS_SPELL);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_SPELL + "/" + listOfFiles[i].getName());
                String mana = jsonObject.get("mana").toString();
                String price = jsonObject.get("price").toString();
                String name = jsonObject.get("name").toString();
                String id = jsonObject.get("id").toString();
                String desc = jsonObject.get("desc").toString();
                String target = jsonObject.get("target").toString();
                String numOfTarget = jsonObject.get("numOfTarget").toString();
                String action = jsonObject.get("action").toString();
                String buffs = jsonObject.get("buffs").toString();
                String effectValue = jsonObject.get("effectValue").toString();
                String delay = jsonObject.get("delay").toString();
                String last = jsonObject.get("last").toString();
                String friendOrEnemy = jsonObject.get("friendOrEnemy").toString();
                String locationOfTarget = jsonObject.get("locationOfTarget").toString();
                Spell spell = new Spell(mana, id, "spell", name, price, desc, target, numOfTarget, action, friendOrEnemy, locationOfTarget);
                Buff.createBuffsForSpell(spell, action, buffs, effectValue, delay, last);
                Spell.getSpells().add(spell);
            }
        }
    }

    public static void createItems() throws IOException, ParseException {
        File folder = new File(ADDRESS_ITEM);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_ITEM+"/"+listOfFiles[i].getName());
                String itemType = jsonObject.get("itemType").toString();
                String price = jsonObject.get("price").toString();
                String name = jsonObject.get("name").toString();
                String id = jsonObject.get("id").toString();
                String type = jsonObject.get("type").toString();
                String desc = jsonObject.get("desc").toString();
                String target = jsonObject.get("target").toString();
                String numOfTarget = jsonObject.get("numOfTarget").toString();
                String action = jsonObject.get("action").toString();
                String buffs = jsonObject.get("buffs").toString();
                String effectValue = jsonObject.get("effectValue").toString();
                String delay = jsonObject.get("delay").toString();
                String last = jsonObject.get("last").toString();
                String friendOrEnemy = jsonObject.get("friendOrEnemy").toString();
                String locationOfTarget = jsonObject.get("locationOfTarget").toString();
                String specification = jsonObject.get("specification").toString();
                String user = jsonObject.get("user").toString();
                String activationTime = jsonObject.get("activationTime").toString();
                Spell spell = new Spell(target, numOfTarget, action, friendOrEnemy, locationOfTarget);
                Buff.createBuffsForSpell(spell, action, buffs, effectValue, delay, last);
//                Item item = new Item(spell,itemType,price,name,id,desc,specification,user,activationTime);
//                Item.getItems().add(item);
            }
        }
    }
    public static void createMinions() throws IOException, ParseException {
        File folder = new File(ADDRESS_MINION);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_MINION + "/" + listOfFiles[i].getName());
                String healthPoint = jsonObject.get("healthPoint").toString();
                String attackRange = jsonObject.get("attackRange").toString();
                String attackPower = jsonObject.get("attackPower").toString();
                String mana = jsonObject.get("mana").toString();
                String attackType = jsonObject.get("attackType").toString();
                String price = jsonObject.get("price").toString();
                String name = jsonObject.get("name").toString();
                String id = jsonObject.get("id").toString();
                String activationTime = jsonObject.get("activationTime").toString();
                String specialPower = jsonObject.get("specialPower").toString();
                String target = jsonObject.get("target").toString();
                String numOfTarget = jsonObject.get("numOfTarget").toString();
                String action = jsonObject.get("action").toString();
                String buffs = jsonObject.get("buffs").toString();
                String effectValue = jsonObject.get("effectValue").toString();
                String delay = jsonObject.get("delay").toString();
                String last = jsonObject.get("last").toString();
                String friendOrEnemy = jsonObject.get("friendOrEnemy").toString();
                String locationOfTarget = jsonObject.get("locationOfTarget").toString();
                String doesNotGetAttack = jsonObject.get("doesNotGetAttack").toString();
                Minion minion = new Minion(mana, id, "minion", name, price, target, numOfTarget, friendOrEnemy, healthPoint, attackPower, attackType
                        , attackRange, specialPower, action, locationOfTarget, doesNotGetAttack, activationTime);
                Buff.createBuffsForMinion(minion, action, buffs, effectValue, delay, last, activationTime);
                Minion.getMinions().add(minion);
            }
        }
    }

    public static void createHeroes() throws IOException, ParseException {
        File folder = new File(ADDRESS_HERO);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_HERO + "/" + listOfFiles[i].getName());
                String healthPoint = jsonObject.get("healthPoint").toString();
                String attackRange = jsonObject.get("attackRange").toString();
                String attackPower = jsonObject.get("attackPower").toString();
                String attackType = jsonObject.get("attackType").toString();
                String mana = jsonObject.get("mana").toString();
                String price = jsonObject.get("price").toString();
                String name = jsonObject.get("name").toString();
                String id = jsonObject.get("id").toString();
                String coolDown = jsonObject.get("coolDown").toString();
                String specialPower = jsonObject.get("specialPower").toString();
                String target = jsonObject.get("target").toString();
                String numOfTarget = jsonObject.get("numOfTarget").toString();
                String action = jsonObject.get("action").toString();
                String buffs = jsonObject.get("buffs").toString();
                String effectValue = jsonObject.get("effectValue").toString();
                String delay = jsonObject.get("delay").toString();
                String last = jsonObject.get("last").toString();
                String friendOrEnemy = jsonObject.get("friendOrEnemy").toString();
                String locationOfTarget = jsonObject.get("healthPoint").toString();
                Hero hero = new Hero(mana, id, "hero", name, price, target, numOfTarget, friendOrEnemy, healthPoint, attackPower, attackType, attackRange,
                        specialPower, action, locationOfTarget, coolDown);
                Buff.createBuffsForHero(hero, action, buffs, effectValue, delay, last);
                Hero.getHeroes().add(hero);
            }
        }
    }

    //    public static void createStringOfItems() {
//        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
//        File[] listOfFiles = folder.listFiles();
//        for (int i = 0; i < listOfFiles.length; i++) {
//            String fileName = listOfFiles[i].getName().split("\\.")[0];
//            Item.itemNames.add(fileName);
//        }
//    }
//    public static void createStringOfUsableItems() throws Exception{
//        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
//        File[] listOfFiles = folder.listFiles();
//        for(int i=0; i<listOfFiles.length; i++){
//            JSONObject item = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Items/"+listOfFiles[i].getName());
//            if(item.get("itemType").toString().matches("usable")){
//                String itemName = listOfFiles[i].getName().split("\\.")[0];
//                Shop.usableItems.add(itemName);
//            }
//        }
//    }
    public static Object readJsonFiles(String fileName) throws IOException, ParseException {
        FileReader fileReader = new FileReader(fileName);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(fileReader);
    }

//    public static JSONObject returnJsonObjectByItsAddress(String fileName) throws IOException, ParseException {
//        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
//        File[] listOfFiles = folder.listFiles();
//        for (int i = 0; i < listOfFiles[i].length(); i++) {
//            return (JSONObject) readJsonFiles(fileName);
//        }
//        return null;
//    }
}
