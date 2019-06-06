package model.collection;

import model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Spell extends Card {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

    public static ArrayList<String> spellNames = new ArrayList<>();
    private String desc;

    private String target;
    private String numOfTarget;
    private ArrayList<String> actionType;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private ArrayList<Buff> actions = new ArrayList<>();
    private String effectValue;
    private String delay;
    private String lastDuration;
    private String friendOrEnemy;
    private String locationOfTarget;

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

    public void insertSpellInThisCoordination(int x, int y){

//        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
//                + "JSON-Spells/" + spellName + ".json");
        CellType cellType = Game.getInstance().getMap().getCells()[x][y].getCellType();

        switch (cellType){
            case selfHero:
                this.insertSpellInCellTypeSelfHero(x, y);
                break;
            case selfMinion:
                this.insertSpellInCellTypeSelfMinion(x, y);
                break;
            case enemyHero:
                this.insertSpellInCellTypeEnemyHero(x, y);
                break;
            case enemyMinion:
                this.insertSpellInCellTypeEnemyMinion(x, y);
                break;
            case empty:
                this.insertSpellInAnEmptyCell(x, y);
                break;
        }
    }

    public void insertSpellInAnEmptyCell(int x, int y){
        String numOfTargets = this.numOfTarget;
//        String numOfTargets = jsonObject.get("numOfTargets").toString();
//        String square = jsonObject.get("Square").toString();

        switch (numOfTargets){
            case "1":
            case "all":
                GameView.printInvalidCommandWhithThisContent("Invalid Target!");
                break;
            case "square2":
                applySpellOn2x2Square(x, y);
                break;
            case "square3":
                applySpellOn3x3Square(x, y);
                break;
        }
    }

    public void insertSpellInCellTypeEnemyMinion(int x, int y){
//        String numOfTargets = jsonObject.get("numOfTargets").toString();
//        String actsOn = jsonObject.get("actsOn").toString();
//        String locationOfTarget = jsonObject.get("locationOfTarget").toString();
//        String square = jsonObject.get("Square").toString();
        String numOfTargets = this.numOfTarget;
        String actsOn = this.friendOrEnemy;
        String locationOfTarget = this.locationOfTarget;

        //Todo : adding a case for location of target for square2?
        switch (numOfTargets){
            case "1":
                if (actsOn.equals("cell") || actsOn.equals("friend")){
                    GameView.printInvalidCommandWhithThisContent("Invalid Target!");
                }else{
                    if (locationOfTarget.equals("random8")){
                        applySpellOnRandomMinionIn8Round(x, y);
                    }else{
                        applySpellOnMinion(x, y);
                    }
                }
                break;
            case "all":
                if (locationOfTarget.equals("null")){//when target is all enemy forces
                    applySpellOnAllEnemyForces(x, y);
                }else{//when target is all enemy forces in a column
                    applySpellOnAllEnemyForcesInColumn(x, y);
                }
                break;
            case "square2":
                applySpellOn2x2Square(x, y);
                break;
            case "square3":
                applySpellOn3x3Square(x, y);
                break;
        }
    }

    public void applySpellOnMinion(int x, int y){
//        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
//        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
//        String[] typeOfAction = jsonObject.get("typeOfAction").toString().split(",");
//        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");
//
//        int cardID = Integer.parseInt(jsonObject.get("id").toString());
//        String cardName = jsonObject.get("name").toString();
//        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");
        ArrayList<Buff> buffs = this.buffs;
        ArrayList<Buff> actions = this.actions;
        ArrayList<String> typeOfActions = this.actionType;

        for (int i = 0; i < typeOfActions.size(); i++) {
            switch (typeOfActions.get(i)){
                case "dispel":
                    if (Card.getCardByCoordination(x, y).thisCardIsFriend())
                        Card.getCardByCoordination(x, y).dispelThisForce("friend");
                    else
                        Card.getCardByCoordination(x, y).dispelThisForce("enemy");
                    break;
                case "kill":
                    Minion.getMinionInThisCoordination(x, y).killCard();
                    break;
                case "addBuff":
                    for (Buff buff : buffs)
                        Minion.getMinionInThisCoordination(x, y).addBuffToMinion(buff);
                    break;
                case "addAction":
                    break;
            }
        }

    }

    public void applySpellOnRandomMinionIn8Round(int x, int y){

        if (!checkIfThisCoordinationIsAroundSelfMinion(x, y)){
            GameView.printInvalidCommandWhithThisContent("Invalid Target!");
        }else{
            applySpellOnMinion(x, y);
        }
    }

    public boolean checkIfThisCoordinationIsAroundSelfMinion(int x, int y){
        int selfHeroX = Game.getInstance().getHeroOfPlayer1().getX();
        int selfHeroY = Game.getInstance().getHeroOfPlayer1().getY();

        if (Map.thisCellsAreAdjusting(x, y, selfHeroX, selfHeroY)){
            return true;
        }
        return false;
    }

    public void insertSpellInCellTypeEnemyHero(int x, int y){
//        String numOfTargets = jsonObject.get("numOfTargets").toString();
//        String actsOn = jsonObject.get("actsOn").toString();
//        String locationOfTarget = jsonObject.get("locationOfTarget").toString();
//        String square = jsonObject.get("Square").toString();
        String numOfTargets = this.numOfTarget;
        String actsOn = this.friendOrEnemy;
        String locationOfTarget = this.locationOfTarget;

        switch (numOfTargets){
            case "1":
                if (actsOn.equals("cell") || actsOn.equals("friend")){
                    GameView.printInvalidCommandWhithThisContent("Invalid Target!");
                }else{
                    applySpellOnEnemyHero(x, y);
                }
                break;
            case "all":
                if (locationOfTarget.equals("null")){//when target is all enemy forces
                    applySpellOnAllEnemyForces(x, y);
                }else{//when target is all enemy forces in a column
                    applySpellOnAllEnemyForcesInColumn(x, y);
                }
                break;
            case "square2":
                applySpellOn2x2Square(x, y);
                break;
            case "square3":
                applySpellOn3x3Square(x, y);
                break;
        }
    }

    public void applySpellOnEnemyHero(int x, int y){

        ArrayList<Buff> buffs = this.buffs;
        ArrayList<Buff> actions = this.actions;
        ArrayList<String> typeOfActions = this.actionType;

        for (int i = 0; i < typeOfActions.size(); i++) {
            switch (typeOfActions.get(i)){
                case "dispel":
                    Hero.getHeroByCoordination(x, y).dispelThisForce("enemy");
                    break;
                case "kill":
                    Hero.getHeroByCoordination(x, y).killCard();
                    break;
                case "addBuff":
                    for (Buff buff : buffs)
                        Hero.getHeroByCoordination(x, y).addBuffToHero(buff);
                    break;
                case "addAction":
                    for (Buff actionBuff : actions){
                        Hero.getHeroByCoordination(x, y).getActions().add(actionBuff);
                    }
                    break;
            }
        }


    }

    public void applySpellOnAllEnemyForces(int x, int y){
//        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
//        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
//        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");
//
//        int cardID = Integer.parseInt(jsonObject.get("id").toString());
//        String cardName = jsonObject.get("name").toString();
//        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");
        ArrayList<Buff> buffs = this.buffs;
        ArrayList<Buff> actions = this.actions;
        ArrayList<String> typeOfActions = this.actionType;

        for (int i = 0; i < typeOfActions.size(); i++) {
            switch (typeOfActions.get(i)){
                case "dispel":
                    for (Card card : Game.getInstance().getPlayer2CardsInField())
                        card.dispelThisForce("enemy");
                    break;
                case "kill":
                    for (Card card : Game.getInstance().getPlayer2CardsInField())
                        card.killCard();
                    break;
                case "addBuff":
                    for (Buff buff : buffs) {
                        for (Card card : Game.getInstance().getMap().getEnemyMinions())
                            ((Minion) card).addBuffToMinion(buff);
                        for (Card card : Game.getInstance().getMap().getEnemyHeroes())
                            ((Hero) card).addBuffToHero(buff);
                    }
                    break;
                case "addAction":
                    for (Buff actionBuff : actions){
                        for (Card card : Game.getInstance().getMap().getEnemyHeroes())
                            ((Hero) card).getActions().add(actionBuff);
                        for (Card card : Game.getInstance().getMap().getEnemyMinions())
                            ((Minion) card).getActions().add(actionBuff);
                    }

                    break;
            }
        }
    }

    public void insertSpellInCellTypeSelfMinion(int x, int y){
//        String numOfTargets = jsonObject.get("numOfTargets").toString();
//        String actsOn = jsonObject.get("actsOn").toString();
//        String square = jsonObject.get("Square").toString();
        String numOfTargets = this.numOfTarget;
        String actsOn = this.friendOrEnemy;

        switch (numOfTargets){
            case "1":
                if (actsOn.equals("map")){
                    GameView.printInvalidCommandWhithThisContent("Invalid Target!");
                }else{
                    applySpellOnMinion(x, y);
                }
                break;
            case "all":
                if (!actsOn.equals("owner")){
                    GameView.printInvalidCommandWhithThisContent("Invalid Target!");
                }else{
                    applySpellOnAllSelfForces(x, y);
                }
                break;
            case "square2":
                applySpellOn2x2Square(x, y);
                break;
            case "square3":
                applySpellOn3x3Square(x, y);
                break;
        }
    }

    public void insertSpellInCellTypeSelfHero(int x, int y){
//        String numOfTargets = jsonObject.get("numOfTargets").toString();
//        String actsOn = jsonObject.get("actsOn").toString();
//        String locationOfTarget = jsonObject.get("locationOfTarget").toString();
        String numOfTargets = this.numOfTarget;
        String actsOn = this.friendOrEnemy;

        switch (numOfTargets){
            case "1":
                if (actsOn.equals("cell") || actsOn.equals("enemy")){
                    GameView.printInvalidCommandWhithThisContent("Invalid Target!");
                }else{
                    applySpellOnSelfHero(x, y);
                }
                break;
            case "all":
                if (!locationOfTarget.equals("null")){//when target is all self forces
                    applySpellOnAllSelfForces(x, y);
                }
                break;
            case "square2":
                applySpellOn2x2Square(x, y);
                break;
            case "square3":
                applySpellOn3x3Square(x, y);
                break;
        }
    }

    public void applySpellOnSelfHero(int x, int y){
        ArrayList<Buff> buffs = this.buffs;
        ArrayList<Buff> actions = this.actions;
        ArrayList<String> typeOfActions = this.actionType;

        for (int i = 0; i < typeOfActions.size(); i++) {
            switch (typeOfActions.get(i)){
                case "dispel":
                    Hero.getHeroByCoordination(x, y).dispelThisForce("friend");
                    break;
                case "kill":
                    Hero.getHeroByCoordination(x, y).killCard();
                    break;
                case "addBuff":
                    for (Buff buff : buffs)
                        Hero.getHeroByCoordination(x, y).addBuffToHero(buff);
                    break;
                case "addAction":
                    for (Buff actionBuff : actions){
                        Hero.getHeroByCoordination(x, y).getActions().add(actionBuff);
                    }
                    break;
            }
        }
    }

    public void applySpellOnAllSelfForces(int x, int y){

        ArrayList<Buff> buffs = this.buffs;
        ArrayList<Buff> actions = this.actions;
        ArrayList<String> typeOfActions = this.actionType;

        for (int i = 0; i < typeOfActions.size(); i++) {
            switch (typeOfActions.get(i)){
                case "dispel":
                    for (Card card : Game.getInstance().getPlayer1CardsInField())
                        card.dispelThisForce("friend");
                    break;
                case "kill":
                    for (Card card : Game.getInstance().getPlayer1CardsInField())
                        card.killCard();
                    break;
                case "addBuff":
                    for (Buff buff : buffs) {
                        for (Card card : Game.getInstance().getMap().getFriendMinions())
                            ((Minion) card).addBuffToMinion(buff);
                        for (Card card : Game.getInstance().getMap().getFriendHeroes())
                            ((Hero) card).addBuffToHero(buff);
                    }
                    break;
                case "addAction":
                    for (Buff actionBuff : actions){
                        for (Card card : Game.getInstance().getMap().getFriendMinions())
                            ((Hero) card).getActions().add(actionBuff);
                        for (Card card : Game.getInstance().getMap().getFriendHeroes())
                            ((Minion) card).getActions().add(actionBuff);
                    }

                    break;
            }
        }
    }

    public void applySpellOnAllEnemyForcesInColumn(int x, int y){
        //Todo : check if the coordination is correct -> (x, y, i)
        ArrayList<Buff> buffs = this.buffs;
        ArrayList<Buff> actions = this.actions;

        for (Buff buff : buffs){
            for (int i = 0; i < 5; i++) {
                if (Game.getInstance().getMap().getCells()[i][y].getCellType() == CellType.enemyMinion){
                    ((Minion) Card.getCardByCoordination(i, y)).addBuffToMinion(buff);
                }else if (Game.getInstance().getMap().getCells()[i][y].getCellType() == CellType.enemyHero){
                    ((Hero) Card.getCardByCoordination(i, y)).addBuffToHero(buff);
                }
            }
        }

        for (Buff actionBuff : actions){
            for (int i = 0; i < 5; i++) {
                if (Game.getInstance().getMap().getCells()[i][y].getCellType() == CellType.enemyMinion){
                    ((Minion) Card.getCardByCoordination(i, y)).getActions().add(actionBuff);
                }else if (Game.getInstance().getMap().getCells()[i][y].getCellType() == CellType.enemyHero){
                    ((Hero) Card.getCardByCoordination(i, y)).getActions().add(actionBuff);
                }
            }
        }
    }

    public void applySpellOn2x2Square(int x, int y){
//        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
//        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
//        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");
//        String[] actsOn = jsonObject.get("actsOn").toString().split(",");
//
//        int cardID = Integer.parseInt(jsonObject.get("id").toString());
//        String cardName = jsonObject.get("name").toString();
//        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");

        ArrayList<String> typeOfActions = this.actionType;

        for (int i = 0; i < typeOfActions.size(); i++) {
            switch (typeOfActions.get(i)){
                case "dispel":
                    applySpellOn2x2SquareOnForces(x, y);
                    break;
                case "addAction":
                    applySpellOn2x2SquareOnMap(x, y);
                    break;
            }
        }

//        for (int i = 0; i < buffNames[i].length(); i++) {
//            Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
//                    buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
//
//            if (actsOn[i].equals("friend/enemy")){
//                applySpellOn2x2SquareOnForces(jsonObject, x, y);
//            }else if (actsOn[i].equals("cell")){
//                applySpellOn2x2SquareOnMap(jsonObject, x, y, buff);
//            }
//
//
//        }
    }//Todo **** : update cards in arrayList

    //method for spell that removes positive buffs from enemy forces and negative buffs from self forces
    public void applySpellOn2x2SquareOnForces(int x, int y){

        for (int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++) {
                if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfHero ||
                        Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfMinion
                ){
                    Card.getCardByCoordination(x, y).dispelThisForce("friend");
                }else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyHero ||
                        Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyMinion){
                    Card.getCardByCoordination(x, y).dispelThisForce("enemy");
                }
            }
        }
    }

    public void applySpellOn2x2SquareOnMap(int x, int y){
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                Game.getInstance().getMap().getCells()[i][j].setCellImpactType(CellImpactType.fire);
            }
        }

        ArrayList<Buff> buffs = this.buffs;

//        int cardID = Integer.parseInt(jsonObject.get("id").toString());
//        String cardName = jsonObject.get("name").toString();
//        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");

        for (Buff buff : buffs) {
            for (int i = x; i < x + 2; i++) {
                for (int j = y; j < y + 2; j++) {
                    if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfHero) {
                        CellImpactType.applyFireImpactOnCard(Game.getInstance().getHeroOfPlayer1(), buff);
                    } else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyHero) {
                        CellImpactType.applyFireImpactOnCard(Game.getInstance().getHeroOfPlayer2(), buff);
                    } else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyMinion ||
                            Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfMinion) {
                        CellImpactType.applyFireImpactOnCard(Minion.getMinionInThisCoordination(x, y), buff);
                    }
                }
            }
        }
    }

    //method for applying buff on 3x3 square
    public void applySpellOn3x3Square(int x, int y){

        ArrayList<Buff> buffs = this.buffs;

        for (Buff buff : buffs)
            applySpellOn3x3SquareOnMap(x, y, buff);
    }

    public void applySpellOn3x3SquareOnMap(int x, int y, Buff buff){
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                Game.getInstance().getMap().getCells()[i][j].setCellImpactType(CellImpactType.poison);
            }
        }

//        int cardID = Integer.parseInt(jsonObject.get("id").toString());
//        String cardName = jsonObject.get("name").toString();
//        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");

        //Todo : don't know what to do with cell impact
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfHero){
                    CellImpactType.applyPoisonImpactOnCard(Game.getInstance().getHeroOfPlayer1(), buff);
                }else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyHero){
                    CellImpactType.applyPoisonImpactOnCard(Game.getInstance().getHeroOfPlayer2(), buff);
                }else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyMinion ||
                        Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfMinion){
                    CellImpactType.applyPoisonImpactOnCard(Minion.getMinionInThisCoordination(x, y), buff);
                }
            }
        }
    }











}


