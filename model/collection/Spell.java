package model.collection;

import model.CellType;
import model.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Spell extends Card {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/hamilamailee/Documents/Duelyst Project/model/collection/";

    public static ArrayList<String> spellNames = new ArrayList<>();
    private String desc;

    public Spell(String name, int price, int mana, String desc){
        this.name = name;
        this.price = price;
        this.mana = mana;
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
                jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
                        + "JSON-Spells/" + fileName + ".json");
                String name = jsonObject.get("name").toString();
                String priceString = jsonObject.get("price").toString();
                int price = Integer.parseInt(priceString);
                String manaString = jsonObject.get("mana").toString();
                int mana = Integer.parseInt(manaString);
                String desc = jsonObject.get("desc").toString();

//                String numOfTargets = jsonObject.get("numOfTargets").toString();
//                String square = jsonObject.get("Square").toString();
//                String targetsSpecified = jsonObject.get("targetsSpecified").toString();
//                String actsOn = jsonObject.get("actsOn").toString();
//                String forHowManyTurns = jsonObject.get("forHowManyTurns").toString();
//                String typeOfAction = jsonObject.get("typeOfAction").toString();
//                String whichBuff = jsonObject.get("whichBuff").toString();
//                String howMuchChange = jsonObject.get("howMuchChange").toString();
//                String cellImpact = jsonObject.get("cellImpact").toString();
//                String locationOfTarget = jsonObject.get("locationOfTarget").toString();

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

    public static int getSpellIDByName(String spellName) throws Exception{
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES +
                 "JSON-Spells/" + spellName + ".json");
        return Integer.parseInt(jsonObject.get("id").toString())+400;
    }
    public static void applySpell(String spellName) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
                + "JSON-Spells/" + spellName + ".json");
        String[] buffsThatSpellHas = jsonObject.get("whichBuff").toString().split(",");

        for (String buffName : buffsThatSpellHas){

        }
    }

    public static ArrayList<String> getSpellNames() {
        return spellNames;
    }

    public static void setSpellNames(ArrayList<String> spellNames) {
        Spell.spellNames = spellNames;
    }

    public static void insertSpellInThisCoordination(String spellName, int x, int y) throws IOException, ParseException {

        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
                + "JSON-Spells/" + spellName + ".json");
        CellType cellType = Map.getCells()[x][y].getCellSituation();
        String targetsSpecified = jsonObject.get("targetsSpecified").toString();
        String actsOn = jsonObject.get("actsOn").toString();
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] typeOfAction = jsonObject.get("typeOfAction").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");

        switch (cellType){
            case selfHero:
                if ((targetsSpecified.equals("hero") || targetsSpecified.equals("minion/hero")) && actsOn.equals("owner")){
                    for (int i = 0; i < buffNames.length; i++) {
                        Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                                buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
                        if (typeOfAction[i].equals("addsBuff")){
                            if (Buff.getTypeOfBuffByItsName(buffNames[i]).equals("negative")){
                                Hero.findHeroInMap(x, y).getNegativeBuffs().add(buff);
                                Hero.findHeroInMap(x, y).applyBuffsOnHero();
                            }else{
                                Hero.findHeroInMap(x, y).getPositiveBuffs().add(buff);
                                Hero.findHeroInMap(x, y).applyBuffsOnHero();
                            }
                        }else{
                            Hero.findHeroInMap(x, y).removeBuffFromHero(buff);
                        }
                    }
                }else{
                    System.out.println("Invalid target!");
                }
                break;
            case selfMinion:
                if ((targetsSpecified.equals("minion") || targetsSpecified.equals("minion/hero")) && actsOn.equals("owner")){

                    for (int i = 0; i < buffNames.length; i++) {
                        Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                                buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
                        if (typeOfAction[i].equals("addsBuff")){
                            if (Buff.getTypeOfBuffByItsName(buffNames[i]).equals("negative")){
                                Minion.getMinionInThisCoordination(x, y).getMinionNegativeBuffs().add(buff);
                                Minion.getMinionInThisCoordination(x, y).applyBuffsOnMinion();
                            }else{
                                Minion.getMinionInThisCoordination(x, y).getMinionPositiveBuffs().add(buff);
                                Minion.getMinionInThisCoordination(x, y).applyBuffsOnMinion();
                            }
                        }else{
                            Minion.getMinionInThisCoordination(x, y).removeBuffFromMinion(buff);
                        }
                    }
                }else{
                    System.out.println("Invalid target!");
                }
                break;
            case enemyHero:
                if ((targetsSpecified.equals("hero") || targetsSpecified.equals("minion/hero")) && actsOn.equals("enemy")){
                    for (int i = 0; i < buffNames.length; i++) {
                        Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                                buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
                        if (typeOfAction[i].equals("addsBuff")){
                            if (Buff.getTypeOfBuffByItsName(buffNames[i]).equals("negative")){
                                Hero.findHeroInMap(x, y).getNegativeBuffs().add(buff);
                                Hero.findHeroInMap(x, y).applyBuffsOnHero();
                            }else{
                                Hero.findHeroInMap(x, y).getPositiveBuffs().add(buff);
                                Hero.findHeroInMap(x, y).applyBuffsOnHero();
                            }
                        }else{
                            Hero.findHeroInMap(x, y).removeBuffFromHero(buff);
                        }
                    }
                }else{
                    System.out.println("Invalid target!");
                }
                break;
            case enemyMinion:
                if ((targetsSpecified.equals("minion") || targetsSpecified.equals("minion/hero")) && actsOn.equals("enemy")){
                    for (int i = 0; i < buffNames.length; i++) {
                        Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                                buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
                        if (typeOfAction[i].equals("addsBuff")){
                            if (Buff.getTypeOfBuffByItsName(buffNames[i]).equals("negative")){
                                Minion.getMinionInThisCoordination(x, y).getMinionNegativeBuffs().add(buff);
                                Minion.getMinionInThisCoordination(x, y).applyBuffsOnMinion();
                            }else{
                                Minion.getMinionInThisCoordination(x, y).getMinionPositiveBuffs().add(buff);
                                Minion.getMinionInThisCoordination(x, y).applyBuffsOnMinion();
                            }
                        }else{
                            Minion.getMinionInThisCoordination(x, y).removeBuffFromMinion(buff);
                        }
                    }
                }else{
                    System.out.println("Invalid target!");
                }
                break;
            case empty:
               // if (targetsSpecified.equals())
                break;
        }
    }

//    public static void applySpellTo2x2Square(Spell spell, int x, int y) throws IOException, ParseException {
//        String spellName = spell.getName();
//        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
//                + "JSON-Spells/" + spellName + ".json");
//        String typeOfAction = jsonObject.get("typeOfAction").toString().split(",")[0];
//        switch (typeOfAction){
//            case "addsBuff":
//
//                break;
//            case "removesBuff":
//
//                break;
//        }
//
//        String[] buffsThatSpellHas = jsonObject.get("whichBuff").toString().split(",");
//    }






}
