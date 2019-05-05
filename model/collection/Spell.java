package model.collection;

import model.*;
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
        CellType cellType = Map.getCells()[x][y].getCellType();

        switch (cellType){
            case selfHero:
                insertSpellInCellTypeSelfHero(jsonObject, x, y);
                break;
            case selfMinion:
                insertSpellInCellTypeSelfMinion(jsonObject, x, y);
                break;
            case enemyHero:
                insertSpellInCellTypeEnemyHero(jsonObject, x, y);
                break;
            case enemyMinion:
                insertSpellInCellTypeEnemyMinion(jsonObject, x, y);
                break;
            case empty:
                insertSpellInAnEmptyCell(jsonObject, x, y);
                break;
        }
    }


    public static void insertSpellInAnEmptyCell(JSONObject jsonObject, int x, int y){
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        String square = jsonObject.get("Square").toString();

        switch (numOfTargets){
            case "1":
                System.out.println("Invalid target!");
                break;
            case "all":
                System.out.println("Invalid target!");
                break;
            case "inArea":
                if (square.equals("2")){
                    applySpellOn2x2Square(jsonObject, x, y);
                }else if (square.equals("3")){
                    applySpellOn3x3Square(jsonObject, x, y);
                }
                break;
        }
    }

    public static void insertSpellInCellTypeEnemyMinion(JSONObject jsonObject, int x, int y){
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        String actsOn = jsonObject.get("actsOn").toString();
        String locationOfTarget = jsonObject.get("locationOfTarget").toString();
        String square = jsonObject.get("Square").toString();

        switch (numOfTargets){
            case "1":
                if (actsOn.equals("map") || actsOn.equals("owner")){
                    System.out.println("Invalid target!");
                }else{
                    if (locationOfTarget.equals("random8around")){
                        applySpellOnRandomMinionIn8Round(jsonObject, x, y);
                    }else{
                        applySpellOnMinion(jsonObject, x, y);
                    }
                }
                break;
            case "all":
                if (locationOfTarget.equals("null")){//when target is all enemy forces
                    applySpellOnAllEnemyForces(jsonObject, x, y);
                }else{//when target is all enemy forces in a column
                    applySpellOnAllEnemyForcesInColumn(jsonObject, x, y);
                }
                break;
            case "inArea":
                if (square.equals("2")){
                    applySpellOn2x2Square(jsonObject, x, y);
                }else{
                    applySpellOn3x3Square(jsonObject, x, y);
                }
                break;
        }
    }

    public static void applySpellOnMinion(JSONObject jsonObject, int x, int y){
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] typeOfAction = jsonObject.get("typeOfAction").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");

        for (int i = 0; i < buffNames.length; i++) {
            if (typeOfAction[i].equals("addsBuff")){
                Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                        buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
                if (Buff.getTypeOfBuffByItsName(buffNames[i]).equals("positive")) {
                    Minion.getMinionInThisCoordination(x, y).getMinionPositiveBuffs().add(buff);
                    Minion.getMinionInThisCoordination(x, y).applyBuffOnMinion(buff);
                }else{
                    Minion.getMinionInThisCoordination(x, y).getMinionNegativeBuffs().add(buff);
                    Minion.getMinionInThisCoordination(x, y).applyBuffOnMinion(buff);
                }
            }else if (typeOfAction[i].equals("removesBuff")){
                Minion.getMinionInThisCoordination(x, y).removeBuffFromBuffArrayListOfMinion(buffNames[i]);
            }
        }
    }

    public static void applySpellOnRandomMinionIn8Round(JSONObject jsonObject, int x, int y){
        if (!checkIfThisCoordinationIsAroundSelfMinion(x, y)){
            System.out.println("Invalid target");
        }else{
            applySpellOnEnemyHero(jsonObject, x, y);
        }
    }

    public static boolean checkIfThisCoordinationIsAroundSelfMinion(int x, int y){
        int selfHeroX = Game.getInstance().getHeroOfPlayer1().getX();
        int selfHeroY = Game.getInstance().getHeroOfPlayer1().getY();

        if ( ((x == selfHeroX - 1) && (y == selfHeroY - 1)) ||
                ((x == selfHeroX) && (y == selfHeroY - 1)) ||
                ((x == selfHeroX + 1) && (y == selfHeroY - 1)) ||
                ((x == selfHeroX - 1) && (y == selfHeroY)) ||
                ((x == selfHeroX + 1) && (y == selfHeroY)) ||
                ((x == selfHeroX - 1) && (y == selfHeroY + 1)) ||
                ((x == selfHeroX) && (y == selfHeroY + 1)) ||
                ((x == selfHeroX + 1) && (y == selfHeroY + 1))){
            return true;
        }
        return false;
    }


    public static void insertSpellInCellTypeEnemyHero(JSONObject jsonObject, int x, int y){
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        String actsOn = jsonObject.get("actsOn").toString();
        String locationOfTarget = jsonObject.get("locationOfTarget").toString();
        String square = jsonObject.get("Square").toString();

        switch (numOfTargets){
            case "1":
                if (actsOn.equals("map") || actsOn.equals("owner")){
                    System.out.println("Invalid target!");
                }else{
                    applySpellOnEnemyHero(jsonObject, x, y);
                }
                break;
            case "all":
                if (locationOfTarget.equals("null")){//when target is all enemy forces
                    applySpellOnAllEnemyForces(jsonObject, x, y);
                }else{//when target is all enemy forces in a column
                    applySpellOnAllEnemyForcesInColumn(jsonObject, x, y);
                }
                break;
            case "inArea":
                if (square.equals("2")){
                    applySpellOn2x2Square(jsonObject, x, y);
                }else{
                    applySpellOn3x3Square(jsonObject, x, y);
                }
                break;
        }
    }

    public static void applySpellOnEnemyHero(JSONObject jsonObject, int x, int y){
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] typeOfAction = jsonObject.get("typeOfAction").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");

        for (int i = 0; i < buffNames.length; i++) {
            if (typeOfAction[i].equals("addsBuff")){
                Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                        buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
                if (Buff.getTypeOfBuffByItsName(buffNames[i]).equals("positive")) {
                    Game.getInstance().getHeroOfPlayer2().getPositiveBuffs().add(buff);
                    Game.getInstance().getHeroOfPlayer2().applyBuffsOnHero();
                }else{
                    Game.getInstance().getHeroOfPlayer2().getNegativeBuffs().add(buff);
                    Game.getInstance().getHeroOfPlayer2().applyBuffsOnHero();
                }
            }else if (typeOfAction[i].equals("removesBuff")){
                Game.getInstance().getHeroOfPlayer2().removeBuffFromBuffArrayListOfHero(buffNames[i]);
            }
        }
    }

    public static void applySpellOnAllEnemyForces(JSONObject jsonObject, int x, int y){
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");

        for (int i = 0; i < buffNames.length; i++) {
            Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i])
                    , buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
            for (Card card : Game.getInstance().getPlayer2CardsInField()) {
                if (buff.getType().equals("positive")) {
                    ((Minion) card).getMinionPositiveBuffs().add(buff);
                    ((Minion) card).applyBuffOnMinion(buff);
                } else {
                    ((Minion) card).getMinionNegativeBuffs().add(buff);
                    ((Minion) card).applyBuffOnMinion(buff);
                }
            }
        }
    }


    public static void insertSpellInCellTypeSelfMinion(JSONObject jsonObject, int x, int y){
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        String actsOn = jsonObject.get("actsOn").toString();
        String square = jsonObject.get("Square").toString();

        switch (numOfTargets){
            case "1":
                if (actsOn.equals("map")){
                    System.out.println("Invalid target!");
                }else{
                    applySpellOnMinion(jsonObject, x, y);
                }
                break;
            case "all":
                if (!actsOn.equals("owner")){
                    System.out.println("Invalid target!");
                }else{
                    applySpellOnAllSelfForces(jsonObject, x, y);
                }
                break;
            case "inArea":
                if (square.equals("2")){
                    applySpellOn2x2Square(jsonObject, x, y);
                }else if (square.equals("3")){
                    applySpellOn3x3Square(jsonObject, x, y);
                }


                break;
        }
    }


    public static void insertSpellInCellTypeSelfHero(JSONObject jsonObject, int x, int y){
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        String actsOn = jsonObject.get("actsOn").toString();
        String locationOfTarget = jsonObject.get("locationOfTarget").toString();

        switch (numOfTargets){
            case "1":
                if (actsOn.equals("map") || actsOn.equals("enemy")){
                    System.out.println("Invalid target!");
                }else{
                    applySpellOnSelfHero(jsonObject, x, y);
                }
                break;
            case "all":
                if (!locationOfTarget.equals("null")){//when target is all self forces
                    applySpellOnAllSelfForces(jsonObject, x, y);
                }
                break;
            case "inArea":
                String square = jsonObject.get("Square").toString();
                if (square.equals("2")){
                    applySpellOn2x2Square(jsonObject, x, y);
                }else{
                    applySpellOn3x3Square(jsonObject, x, y);
                }
                break;
        }
    }

    public static void applySpellOnSelfHero(JSONObject jsonObject, int x, int y){
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] typeOfAction = jsonObject.get("typeOfAction").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");

        for (int i = 0; i < buffNames.length; i++) {
            if (typeOfAction[i].equals("addsBuff")){
                Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                        buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
                if (Buff.getTypeOfBuffByItsName(buffNames[i]).equals("positive")) {
                    Game.getInstance().getHeroOfPlayer1().getPositiveBuffs().add(buff);
                    Game.getInstance().getHeroOfPlayer1().applyBuffsOnHero();
                }else{
                    Game.getInstance().getHeroOfPlayer1().getNegativeBuffs().add(buff);
                    Game.getInstance().getHeroOfPlayer1().applyBuffsOnHero();
                }
            }else if (typeOfAction[i].equals("removesBuff")){
                Game.getInstance().getHeroOfPlayer1().removeBuffFromBuffArrayListOfHero(buffNames[i]);
            }
        }
    }

    public static void applySpellOnAllSelfForces(JSONObject jsonObject, int x, int y){
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");

        for (int i = 0; i < buffNames.length; i++) {
            Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i])
                    , buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
            for (Card card : Game.getInstance().getPlayer1CardsInField()) {
                if (buff.getType().equals("positive")) {
                    ((Minion) card).getMinionPositiveBuffs().add(buff);
                    ((Minion) card).applyBuffOnMinion(buff);
                } else {
                    ((Minion) card).getMinionNegativeBuffs().add(buff);
                    ((Minion) card).applyBuffOnMinion(buff);
                }
            }
        }




    }

    public static void applySpellOnAllEnemyForcesInColumn(JSONObject jsonObject, int x, int y){
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");

        for (int i = 0; i < buffNames.length; i++) {
            Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i])
                    , buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
            for (int j = 0; j < 5; j++) {
                if (Map.getCells()[j][y].getCellType() == CellType.enemyMinion){
                    if (buff.getType().equals("positive")){
                        Minion.getMinionInThisCoordination(x, y).getMinionPositiveBuffs().add(buff);
                    }else{
                        Minion.getMinionInThisCoordination(x, y).getMinionNegativeBuffs().add(buff);
                    }
                }else if (Map.getCells()[j][y].getCellType() == CellType.enemyHero){
                    if (buff.getType().equals("positive")){
                        Game.getInstance().getHeroOfPlayer2().getPositiveBuffs().add(buff);
                    }else{
                        Game.getInstance().getHeroOfPlayer2().getNegativeBuffs().add(buff);
                    }
                }
            }
        }





    }

    public static void applySpellOn2x2Square(JSONObject jsonObject, int x, int y){
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");
        String[] actsOn = jsonObject.get("actsOn").toString().split(",");

        for (int i = 0; i < buffNames[i].length(); i++) {
            Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                    buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));

            if (actsOn[i].equals("enemy/owner")){
                applySpellOn2x2SquareOnForces(x, y);
            }else if (actsOn[i].equals("map")){
                applySpellOn2x2SquareOnMap(x, y, buff);
            }


        }
    }

    //method for spell that removes positive buffs from enemy forces and negative buffs from self forces
    public static void applySpellOn2x2SquareOnForces(int x, int y){
        for (int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++) {
                if (Map.getCells()[i][j].getCellType() == CellType.selfHero){
                    Game.getInstance().getHeroOfPlayer1().getNegativeBuffs().clear();
                }else if (Map.getCells()[i][j].getCellType() == CellType.selfMinion){
                    Minion.getMinionInThisCoordination(x, y).getMinionNegativeBuffs().clear();
                }else if (Map.getCells()[i][j].getCellType() == CellType.enemyHero){
                    Game.getInstance().getHeroOfPlayer2().getPositiveBuffs().clear();
                }else if (Map.getCells()[i][j].getCellType() == CellType.enemyMinion){
                    Minion.getMinionInThisCoordination(x, y).getMinionPositiveBuffs().clear();
                }
            }
        }
    }

    public static void applySpellOn2x2SquareOnMap(int x, int y, Buff buff){
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                Map.getCells()[i][j].setCellImpactType(CellImpactType.fire);
            }
        }

        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                if (Map.getCells()[i][j].getCellType() == CellType.selfHero){
                    CellImpactType.applyFireImpactOnCard(Game.getInstance().getHeroOfPlayer1(), buff);
                }else if (Map.getCells()[i][j].getCellType() == CellType.enemyHero){
                    CellImpactType.applyFireImpactOnCard(Game.getInstance().getHeroOfPlayer2(), buff);
                }else if (Map.getCells()[i][j].getCellType() == CellType.enemyMinion ||
                        Map.getCells()[i][j].getCellType() == CellType.selfMinion){
                    CellImpactType.applyFireImpactOnCard(Minion.getMinionInThisCoordination(x, y), buff);
                }
            }
        }
    }

    //method for applying buff on 3x3 square
    public static void applySpellOn3x3Square(JSONObject jsonObject, int x, int y){
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");

        for (int i = 0; i < buffNames.length; i++) {
            Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                    buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));

            applySpellOn3x3SquareOnMap(x, y, buff);
        }
    }

    public static void applySpellOn3x3SquareOnMap(int x, int y, Buff buff){
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                Map.getCells()[i][j].setCellImpactType(CellImpactType.poison);
            }
        }

        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                if (Map.getCells()[i][j].getCellType() == CellType.selfHero){
                    CellImpactType.applyPoisonImpactOnCard(Game.getInstance().getHeroOfPlayer1(), buff);
                }else if (Map.getCells()[i][j].getCellType() == CellType.enemyHero){
                    CellImpactType.applyPoisonImpactOnCard(Game.getInstance().getHeroOfPlayer2(), buff);
                }else if (Map.getCells()[i][j].getCellType() == CellType.enemyMinion ||
                        Map.getCells()[i][j].getCellType() == CellType.selfMinion){
                    CellImpactType.applyPoisonImpactOnCard(Minion.getMinionInThisCoordination(x, y), buff);
                }
            }
        }
    }











}
