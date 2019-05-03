package model.collection;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HandleFiles {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

    public static void createStringOfPlayers() {
        File folder = new File("/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/player/");
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


    public static Object readJsonFiles(String fileName) throws IOException, ParseException {
        FileReader fileReader = new FileReader(fileName);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(fileReader);
    }
}
