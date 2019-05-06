package model.collection;


import model.Deck;
import model.Player;
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
    private static final String ADDRESS_OF_JSON_FILES = "/Users/hamilamailee/Documents/Duelyst Project/model/collection/";

    public static void createStringOfPlayers() {
        File folder = new File("/Users/hamilamailee/Documents/Duelyst Project/model/players/");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            Account.getPlayers().add(listOfFiles[i].getName().split("\\.")[0]);
        }
    }

    public static void createStringOfHeroes() {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Heroes");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName().split("\\.")[0];
            Hero.heroNames.add(fileName);
        }
    }

    public static void createStringOfItems() {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName().split("\\.")[0];
            Item.itemNames.add(fileName);
        }
    }

    public static void createStringOfMinions() {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Minions");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName().split("\\.")[0];
            Minion.minionNames.add(fileName);
        }
    }

    public static void createStringOfSpells() {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Spells");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName().split("\\.")[0];
            Spell.spellNames.add(fileName);
        }
    }


    public static void writeNewPlayerToFile(String name,String password) throws Exception{
        JSONObject tempPlayer = new JSONObject();
        tempPlayer.put("username",name);
        tempPlayer.put("password",password);
        tempPlayer.put("daric",15000);
        tempPlayer.put("numOfWins",0);
        tempPlayer.put("justCreated","true");
        Files.write(Paths.get(PLAYERS_FOLDER+name+".json"),tempPlayer.toJSONString().getBytes());
    }
    public static String returnStringOfCollection(Player player){
        String list = player.getHeroesInCollectionName().get(0);
        for(int i=1; i<player.getHeroesInCollectionName().size(); i++){
            list = list +","+ player.getHeroesInCollectionName().get(i);
        }
        for (String item:
                player.getItemsInCollectionNames()) {
            list = list + "," + item;
        }
        for (String card:
                player.getCardsInCollectionNames()) {
            list = list + "," + card;
        }
        return list;
    }
    public static void savePlayerWithDeckAndCollection(Player player){
        JSONObject tempPlayer = new JSONObject();
        tempPlayer.put("username",player.getUserName());
        tempPlayer.put("password",player.getPassword());
        tempPlayer.put("daric",player.getDaric());
        tempPlayer.put("numOfWins",player.getNumOfwins());
        tempPlayer.put("numOfDecks",player.getDecksOfPlayer().size());
        for(int i=1; i<=player.getDecksOfPlayer().size(); i++){
            tempPlayer.put("Deck deck_"+i,returnStringOfDeck((player.getDecksOfPlayer().get(i-1))));
        }
        tempPlayer.put("collection",returnStringOfCollection(player));
        tempPlayer.put("mainDeck",returnStringOfDeck(player.getMainDeck()));
    }
    public static void createStringOfUsableItems() throws Exception{
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
        File[] listOfFiles = folder.listFiles();
        for(int i=0; i<listOfFiles.length; i++){
            JSONObject item = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Items/"+listOfFiles[i].getName());
            if(item.get("itemType").toString().matches("usable")){
                String itemName = listOfFiles[i].getName().split("\\.")[0];
                Shop.usableItems.add(itemName);
            }
        }
    }
    public static Object readJsonFiles(String fileName) throws IOException, ParseException {
        FileReader fileReader = new FileReader(fileName);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(fileReader);
    }
}
