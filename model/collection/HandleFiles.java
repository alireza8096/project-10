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
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Downloads/AP-Project-10/project-10/src/model/collection/";

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

    public static void soutString(ArrayList<String> names,String title) {
        System.out.println(title+":");
        for (String name:
                names) {
            System.out.print(name+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        createStringOfHeroes();
        createStringOfItems();
        createStringOfMinions();
        createStringOfSpells();
//        soutString(Hero.heroeNames,"hero");
//        soutString(Minion.minionNames,"minion");
//        soutString(Item.itemNames,"item");
//        soutString(Spell.spellNames,"spell");
    }

    public static Object readJsonFiles(String fileName) throws IOException, ParseException {
        FileReader fileReader = new FileReader(fileName);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(fileReader);
    }

    public static int findIndexOfUpperCaseLetter(String str){
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i)))
                return i;
        }
        return -1;
    }

    public static String makeFirstLetterUpperCase(String str){
        String strPrime = str.substring(0,1).toUpperCase();
        str = strPrime + str.substring(1);
        return str;
    }

    public static String changeFileNameToProperName(String fileName){
        int upperCaseIndex;
        if (findIndexOfUpperCaseLetter(fileName) != -1) {
            upperCaseIndex = findIndexOfUpperCaseLetter(fileName);
            fileName = fileName.substring(0, upperCaseIndex) + " " + fileName.substring(upperCaseIndex);
        }
        fileName = makeFirstLetterUpperCase(fileName);
        return fileName;
    }
}
