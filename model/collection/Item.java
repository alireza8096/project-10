package model.collection;

import model.Cell;
import model.Game;
import model.Hand;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Item {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/hamilamailee/Documents/Duelyst Project/model/collection";

    public static ArrayList<String> itemNames = new ArrayList<>();
    private int id;
    private String name;
    private String itemType;
    private int price;
    private int x;
    private int y;

    public Item(){ }

    public Item(String name, String itemType, int x, int y){
        this.name = name;
        this.itemType = itemType;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static String findItemNameByID(int id) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
        File[] listOfFiles = folder.listFiles();
        JSONObject jsonObject;
        String idString;
        int value;
        String fileName;
        for (int i = 0; i < listOfFiles.length; i++) {
            fileName = listOfFiles[i].getName();
            jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Items/" + fileName);
            idString = jsonObject.get("id").toString();
            value = Integer.parseInt(idString);
            if (value == id){
                return (String) jsonObject.get("name");
            }
        }
        return null;
    }

    public static int getItemIDByName(String itemName) throws Exception{
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Items/" + itemName+".json");
        return Integer.parseInt(jsonObject.get("id").toString())+200;
    }
    public static boolean thisCardIsItem(String cardName){
        for (String name : itemNames){
            if (name.equals(cardName))
                return true;
        }
        return false;
    }

    public static String getItemTypeByName(String itemName) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
        File[] listOfFiles = folder.listFiles();
        String fileName;
        for (int i = 0; i < listOfFiles[i].length(); i++) {
            fileName = listOfFiles[i].getName().split("\\.")[0];
            if (fileName.equals(itemName)){
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(fileName);
                return jsonObject.get("itemType").toString();
            }
        }
        return null;
    }

    public static void applyItem(String itemName) throws IOException, ParseException {
  //      String itemType = getItemTypeByName(itemName);

        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Items" + itemName + ".json");
        String typeOfAction = jsonObject.get("typeOfAction").toString();

        switch (typeOfAction){
            case "addMana":
                applyAddingManaByItem(jsonObject);
                break;
            case "addBuff":
             //   applyAddingBuffbyItem();
                break;
        }


    }

    public static void applyAddingManaByItem(JSONObject jsonObject){
        int howManyMana = Integer.parseInt(jsonObject.get("howManyMana").toString());
        int howManyTurns = Integer.parseInt(jsonObject.get("howManyTurns").toString());

        Buff buff = new Buff("addingManaBuff", howManyMana, howManyTurns);
        Buff.applyAddingManaBuff(Game.getInstance().getPlayer1(), buff);

    }

//    public static Item returnFlagByRandomCoordination(){
//        int x = Cell.returnRandomNumberForCoordinationInThisRange(0, 4);
//        int y = Cell.returnRandomNumberForCoordinationInThisRange(0, 9);
//
//        Item flag = new Item("flag", "collectible", x, y);
//        return flag;
//    }

    

    
}
