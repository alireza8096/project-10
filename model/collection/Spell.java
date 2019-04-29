package model.collection;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Spell extends Card {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Downloads/AP-Project-10/project-10/src/model/collection/";

    public static ArrayList<String> spellNames = new ArrayList<>();

    public Spell(int id,String name,int price,int mana){
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setMana(mana);
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

    public static boolean thisCardIsSpell(String cardName){
        for (String name : spellNames){
            if (name.equals(cardName))
                return true;
        }
        return false;
    }
}
