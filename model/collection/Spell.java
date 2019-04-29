package model.collection;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Spell extends Card {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Downloads/AP-Project-10/project-10/src/model/collection/";

    public static ArrayList<String> spellNames = new ArrayList<>();
    private String desc;

    public Spell(String name, int price, int mana, String desc){
        this.setName(name);
        this.setPrice(price);
        this.setMana(mana);
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String findSpellNameByID(int id) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Spells");
        File[] listOfFiles = folder.listFiles();
        JSONObject jsonObject;
        String idString;
        int value;
        String fileName;
        for (int i = 0; i < listOfFiles.length; i++) {
            fileName = listOfFiles[i].getName();
            jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Spells/" + fileName);
            idString = jsonObject.get("id").toString();
            value = Integer.parseInt(idString);
            if (value == id){
                return (String) jsonObject.get("name");
            }
        }
        return null;
    }

    public static Card getSpellByName(String spellName) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Spells");
        File[] listOfFiles = folder.listFiles();
        String fileName;
        JSONObject jsonObject;
        for (int i = 0; i < listOfFiles.length; i++) {
            fileName = listOfFiles[i].getName().split("\\.")[0];
            if (fileName.equals(spellName)){
                jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Spells/" + fileName + ".json");
                String name = jsonObject.get("name").toString();
                String priceString = jsonObject.get("price").toString();
                int price = Integer.parseInt(priceString);
                String manaString = jsonObject.get("mana").toString();
                int mana = Integer.parseInt(manaString);
                String desc = jsonObject.get("desc").toString();

                Spell spell = new Spell(name, price, mana, desc);
                return spell;
            }
        }
        return null;
    }

    public static boolean thisCardIsSpell(String cardName){
        for (String name : spellNames){
            if (name.equals(cardName))
                return true;
        }
        return false;
    }
}
