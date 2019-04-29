package model;

import model.Player;
import model.collection.Card;
import model.collection.Hero;
import model.collection.Item;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    private Game game;
    private Player player;
    private ArrayList<String> cardsAvailableInShop = new ArrayList<>();
    private ArrayList<String> itemsAvailableInShop = new ArrayList<>();
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Downloads/AP-Project-10/project-10/src/model/collection/";
    public static Object readCardOrItemFromFile(String filename) throws Exception{
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    public ArrayList<String> getCardsAvailableInShop() {
        return cardsAvailableInShop;
    }

    public void setCardsAvailableInShop(ArrayList<String> cardsAvailableInShop) {
        this.cardsAvailableInShop = cardsAvailableInShop;
    }

    public ArrayList<String> getItemsAvailableInShop() {
        return itemsAvailableInShop;
    }

    public void setItemsAvailableInShop(ArrayList<String> itemsAvailableInShop) {
        this.itemsAvailableInShop = itemsAvailableInShop;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean checkIfThisIDIsAvailableInShop(int id, String type) throws Exception {
       if(type.equals("Item"))
       {
            for(String name: cardsAvailableInShop)
            {
                if(getNameById(id,type).equals(name))
                {
                    return true;
                }
            }
       }
       else if(type.equals("Minion") || type.equals("Spell") || type.equals("Hero"))
       {
           for(String name: itemsAvailableInShop)
           {
               if(getNameById(id,type).equals(name))
               {
                   return true;
               }
           }
       }
        return false;
    }

    public String getNameById(int id,String type) throws Exception {
        if(type.equals("Minion"))
        {
            File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Minions");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {

            }
        }
        if(type.equals("Spell"))
        {
            File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Spells");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {

            }
        }
        if(type.equals("Hero"))
        {
            File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Heroes");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {

            }
        }
        if(type.equals("Item"))
        {
            File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {

            }
        }
    }

    public boolean checkIfThisCardIsAvailableInCards(int id,String type) throws Exception {
        JSONObject jsonObject = (JSONObject) readCardOrItemFromFile(getNameById(id,type));
        for(String name: player.getCardsInCollectionNames())
        {
            if(name.equals((String) jsonObject.get("name")))
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfThisIDCanBeBought(int id,String type) throws Exception {
        if(checkIfThisIDIsAvailableInShop(id,type))
        {
            if(type.equals("Item"))
            {
                if(!checkIfThisItemIsAvailableInItems(id,type))
                {
                    return true;
                }
            }
            else
            {
                if(!checkIfThisCardIsAvailableInCards(id,type))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfThisItemIsAvailableInItems(int id,String type) throws Exception {
        JSONObject jsonObject = (JSONObject) readCardOrItemFromFile(getNameById(id,type));
        for(String name: player.getItemsInCollectionNames())
        {
            if(name.equals((String) jsonObject.get("name")))
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfMoneyIsEnough(int id,String type,Player player) throws Exception {
        JSONObject jsonObject = (JSONObject) readCardOrItemFromFile(getNameById(id,type));
        int price=(int) jsonObject.get("price");
        if(player.getDaric()>=price)
        {
            return true;
        }
        return false;
    }

    public void buy(int id,String type) throws Exception
    {
        if(type.equals("Item"))
        {
            JSONObject jsonObject = (JSONObject) readCardOrItemFromFile(getNameById(id,type));
            player.getCardsInCollectionNames().add((String) jsonObject.get("name"));
            int daric=player.getDaric()- (int) jsonObject.get("price");
            player.setDaric(daric);
        }
        else
        {
            JSONObject jsonObject = (JSONObject) readCardOrItemFromFile(getNameById(id,type));
            player.getItemsInCollectionNames().add((String) jsonObject.get("name"));
            int daric=player.getDaric()- (int) jsonObject.get("price");
            player.setDaric(daric);
        }
    }

    public void sell(int id, String type) throws Exception {
        if(type.equals("Item"))
        {
            JSONObject jsonObject = (JSONObject) readCardOrItemFromFile(getNameById(id,type));
            player.getCardsInCollectionNames().add((String) jsonObject.get("name"));
            int daric=player.getDaric()+ (int) jsonObject.get("price");
            player.setDaric(daric);
        }
        else
        {
            JSONObject jsonObject = (JSONObject) readCardOrItemFromFile(getNameById(id,type));
            player.getItemsInCollectionNames().add((String) jsonObject.get("name"));
            int daric=player.getDaric()+ (int) jsonObject.get("price");
            player.setDaric(daric);
        }
    }
}

