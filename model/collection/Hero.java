package model.collection;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Hero extends Card{
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Downloads/AP-Project-10/project-10/src/model/collection/";

    private ArrayList<Buff> positiveBuffs = new ArrayList<>();
    private ArrayList<Buff> negativeBuffs = new ArrayList<>();

    public static ArrayList<String> heroNames = new ArrayList<>();
    private int healthPoint;
    private int attackPower;
    private String attackType;
    private int attackRange;
    private int coolDown;

    public ArrayList<Buff> getPositiveBuffs() {
        return positiveBuffs;
    }

    public void setPositiveBuffs(ArrayList<Buff> positiveBuffs) {
        this.positiveBuffs = positiveBuffs;
    }

    public ArrayList<Buff> getNegativeBuffs() {
        return negativeBuffs;
    }

    public void setNegativeBuffs(ArrayList<Buff> negativeBuffs) {
        this.negativeBuffs = negativeBuffs;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public static ArrayList<String> getHeroNames() {
        return heroNames;
    }

    public static void setHeroNames(ArrayList<String> heroNames) {
        Hero.heroNames = heroNames;
    }

    public static String findHeroNameByID(int id) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Heroes");
        File[] listOfFiles = folder.listFiles();
        JSONObject jsonObject;
        String idString;
        int value;
        String fileName;
        for (int i = 0; i < listOfFiles.length; i++) {
            fileName = listOfFiles[i].getName();
            jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Heroes/" + fileName);
            idString = jsonObject.get("id").toString();
            value = Integer.parseInt(idString);
            if (value == id){
                return (String) jsonObject.get("name");
            }
        }
        return null;
    }

//    public static int findHeroIDByName(int cardName){
//        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Heroes");
//        File[] listOfFiles = folder.listFiles();
//        for (int i = 0; i < listOfFiles.length; i++) {
//          //  if ()
//        }
//
//    }

    public static boolean thisCardIsHero(String cardName){
        for (String name : heroNames){
            if (name.equals(cardName))
                return true;
        }
        return false;
    }

}
