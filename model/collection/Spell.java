package model.collection;

import model.*;
import model.CustomCardsEnums.ActivationTime;
import model.CustomCardsEnums.Targets;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Spell extends Card implements Cloneable{
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

//    public static ArrayList<String> spellNames = new ArrayList<>();
    private static ArrayList<Spell> spells = new ArrayList<>();
    private String desc;
//    private String target;
//    private String numOfTarget;
    private ArrayList<String> actionTypes;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private ArrayList<Buff> actions = new ArrayList<>();
//    private String friendOrEnemy;
//    private String locationOfTarget;

    //Todo : checking start
    private ActivationTime activationTime;//it is for the spells that items give to minions


    private String specificationOfTargets;

    public String getSpecificationOfTargets() {
        return specificationOfTargets;
    }

    public void setSpecificationOfTargets(String specificationOfTargets) {
        this.specificationOfTargets = specificationOfTargets;
    }

    private ArrayList<Force> forceTargets = new ArrayList<>();
    private ArrayList<Cell> cellTargets = new ArrayList<>();
    private Target spellTarget;

    public ArrayList<String> getActionTypes() {
        return actionTypes;
    }

    public void setActionTypes(ArrayList<String> actionTypes) {
        this.actionTypes = actionTypes;
    }

    public void setActions(ArrayList<Buff> actions) {
        this.actions = actions;
    }

    public ArrayList<Force> getForceTargets() {
        return forceTargets;
    }

    public void setForceTargets(ArrayList<Force> forceTargets) {
        this.forceTargets = forceTargets;
    }

    public ArrayList<Cell> getCellTargets() {
        return cellTargets;
    }

    public void setCellTargets(ArrayList<Cell> cellTargets) {
        this.cellTargets = cellTargets;
    }

    //Todo : checking finish


    public ActivationTime getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(ActivationTime activationTime) {
        this.activationTime = activationTime;
    }

    public static ArrayList<Spell> getSpells() {
        return spells;
    }

    public Target getSpellTarget() {
        return spellTarget;
    }

    public void setSpellTarget(Target spellTarget) {
        this.spellTarget = spellTarget;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<Buff> buffs) {
        this.buffs = buffs;
    }

    public ArrayList<Buff> getActions() {
        return actions;
    }


    public Spell(String mana, String id, String cardType, String name, String price, String desc,
                 String target, String numOfTarget, String actionType, String friendOrEnemy,
                 String locationOfTarget,String imagePath,String inField) throws FileNotFoundException {
        super(mana, id, cardType, name, price, imagePath,inField);
        this.desc = desc;
//        this.target = target;
//        this.numOfTarget = numOfTarget;
        this.actionTypes = Force.returnArrayList(actionType);
//        this.friendOrEnemy = friendOrEnemy;
//        this.locationOfTarget = locationOfTarget;
        this.spellTarget = new Target(target, numOfTarget, locationOfTarget, friendOrEnemy);
    }
    public Spell(String target,String numOfTarget,String actionType,String friendOrEnemy,String locationOfTarget){
//        this.target = target;
//        this.numOfTarget = numOfTarget;
        this.actionTypes = Force.returnArrayList(actionType);
//        this.friendOrEnemy = friendOrEnemy;
//        this.locationOfTarget = locationOfTarget;
        this.spellTarget = new Target(target, numOfTarget, locationOfTarget, friendOrEnemy);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Spell findSpellByID(int id) throws CloneNotSupportedException {
        for (Spell spell: spells) {
            if(spell.id == id){
                return (Spell)spell.clone();
            }
        }
        return null;
    }

    public static Spell findSpellByName(String spellName) throws CloneNotSupportedException {
        for (Spell spell: spells) {
            if (spell.name.matches(spellName)) {
                return (Spell)spell.clone();
            }
        }
        return null;
    }

    public static boolean thisCardIsSpell(String cardName){
        for (Spell spell:spells) {
            if(spell.name.compareToIgnoreCase(cardName) == 0){
                return true;
            }
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

        for (String buffName : buffsThatSpellHas) {

        }
    }

    public void insertSpellInThisCoordination(int x, int y){
       if ( this.spellTarget.checkIfTargetHasBeenChosenCorrectly(this, x, y)){
           this.spellTarget.setTargetOfSpell(this, x, y);
           applySpellOnItsTargets();
       }else {
           GameView.printInvalidCommandWithThisContent("Target is not valid!");
       }
    }

    public void applySpellOnItsTargets(){
        String target = this.spellTarget.getTarget();

        if (target.equals("cell")){
            applySpellOnItsCellTargets();
        }else{
            applySpellOnItsForceTargets();
        }
    }

    public void applySpellOnItsCellTargets(){
        for (int i = 0; i < this.actionTypes.size(); i++) {
            String actionType = this.actionTypes.get(i);

            if (actionType.equals("addBuff")){
                for (Buff buff : this.getBuffs()){
                    addThisBuffToCellTargets(buff);
                }
            }else if (actionType.equals("addAction")){
                for (Buff buff : this.getActions())
                    addThisBuffToCellTargets(buff);
            }

        }
    }

    public void addThisBuffToCellTargets(Buff buff){
        for (Cell cell : this.cellTargets){
            cell.getCellBuffs().add(buff);
        }
    }

    public void applySpellOnItsForceTargets(){
        for (int i = 0; i < this.actionTypes.size(); i++) {
            String actionType = this.actionTypes.get(i);

            switch (actionType){
                case "addBuff":
                    addThisBuffToForceTargets(this.buffs.get(i), "addBuff");
                    break;
                case "addAction":
                    addThisBuffToForceTargets(this.buffs.get(i), "addAction");
                    break;
                case "kill":
                    killForceTargets();
                    break;
                case "dispel":
                    dispelForceTargetsPositively();
                    break;
            }
        }
    }

    public void dispelForceTargetsPositively(){
        for (Force force : this.forceTargets)
            force.dispelForcePositively();
    }

    public void killForceTargets(){
        for (Force force : this.forceTargets)
            force.killCard();
    }

    public void addThisBuffToForceTargets(Buff buff, String actionType){
        for (Force force : this.forceTargets){
            if (actionType.equals("addBuff")){
                String buffType = buff.getType();
                if (buffType.equals("positive")){
                    force.getPositiveBuffsOnItself().add(buff);
                }else{
                    force.getNegativeBuffsOnItself().add(buff);
                }

            }else if (actionType.equals("addAction")){
                force.getActionBuffsOnItself().add(buff);
            }
        }
    }



//    public void insertSpellInThisCoordination(int x, int y){
//
////        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
////                + "JSON-Spells/" + spellName + ".json");
//        CellType cellType = Game.getInstance().getMap().getCells()[x][y].getCellType();
//
//        switch (cellType){
//            case selfHero:
//                this.insertSpellInCellTypeSelfHero(x, y);
//                break;
//            case selfMinion:
//                this.insertSpellInCellTypeSelfMinion(x, y);
//                break;
//            case enemyHero:
//                this.insertSpellInCellTypeEnemyHero(x, y);
//                break;
//            case enemyMinion:
//                this.insertSpellInCellTypeEnemyMinion(x, y);
//                break;
//            case empty:
//                this.insertSpellInAnEmptyCell(x, y);
//                break;
//        }
//    }
//
//    public void insertSpellInAnEmptyCell(int x, int y){
//        String numOfTargets = this.numOfTarget;
////        String numOfTargets = jsonObject.get("numOfTargets").toString();
////        String square = jsonObject.get("Square").toString();
//
//        switch (numOfTargets){
//            case "1":
//            case "all":
//                GameView.printInvalidCommandWithThisContent("Invalid Target!");
//                break;
//            case "square2":
//                applySpellOn2x2Square(x, y);
//                break;
//            case "square3":
//                applySpellOn3x3Square(x, y);
//                break;
//        }
//    }
//
//    public void insertSpellInCellTypeEnemyMinion(int x, int y){
////        String numOfTargets = jsonObject.get("numOfTargets").toString();
////        String actsOn = jsonObject.get("actsOn").toString();
////        String locationOfTarget = jsonObject.get("locationOfTarget").toString();
////        String square = jsonObject.get("Square").toString();
//        String numOfTargets = this.numOfTarget;
//        String actsOn = this.friendOrEnemy;
//        String locationOfTarget = this.locationOfTarget;
//
//        //Todo : adding a case for location of target for square2?
//        switch (numOfTargets){
//            case "1":
//                if (actsOn.equals("cell") || actsOn.equals("friend")){
//                    GameView.printInvalidCommandWithThisContent("Invalid Target!");
//                }else{
//                    if (locationOfTarget.equals("random8")){
//                        applySpellOnRandomMinionIn8Round(x, y);
//                    }else{
//                        applySpellOnMinion(x, y);
//                    }
//                }
//                break;
//            case "all":
//                if (locationOfTarget.equals("null")){//when target is all enemy forces
//                    applySpellOnAllEnemyForces(x, y);
//                }else{//when target is all enemy forces in a column
//                    applySpellOnAllEnemyForcesInColumn(x, y);
//                }
//                break;
//            case "square2":
//                applySpellOn2x2Square(x, y);
//                break;
//            case "square3":
//                applySpellOn3x3Square(x, y);
//                break;
//        }
//    }
//
//    public void applySpellOnMinion(int x, int y){
////        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
////        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
////        String[] typeOfAction = jsonObject.get("typeOfAction").toString().split(",");
////        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");
////
////        int cardID = Integer.parseInt(jsonObject.get("id").toString());
////        String cardName = jsonObject.get("name").toString();
////        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");
//        ArrayList<Buff> buffs = this.buffs;
//        ArrayList<Buff> actions = this.actions;
//        ArrayList<String> typeOfActions = this.actionType;
//
//        for (int i = 0; i < typeOfActions.size(); i++) {
//            switch (typeOfActions.get(i)){
//                case "dispel":
//                    if (Card.getCardByCoordination(x, y).thisCardIsFriend())
//                        Card.getCardByCoordination(x, y).dispelThisForce("friend");
//                    else
//                        Card.getCardByCoordination(x, y).dispelThisForce("enemy");
//                    break;
//                case "kill":
//                    Minion.getMinionInThisCoordination(x, y).killCard();
//                    break;
//                case "addBuff":
//                    for (Buff buff : buffs)
//                        Minion.getMinionInThisCoordination(x, y).addBuffToMinion(buff);
//                    break;
//                case "addAction":
//                    break;
//            }
//        }
//
//    }
//
//    public void applySpellOnRandomMinionIn8Round(int x, int y){
//
//        if (!checkIfThisCoordinationIsAroundSelfMinion(x, y)){
//            GameView.printInvalidCommandWithThisContent("Invalid Target!");
//        }else{
//            applySpellOnMinion(x, y);
//        }
//    }
//
//    public boolean checkIfThisCoordinationIsAroundSelfMinion(int x, int y){
//        int selfHeroX = Game.getInstance().getMap().getFriendHero().getX();
//        int selfHeroY = Game.getInstance().getMap().getFriendHero().getY();
//
//        if (Map.thisCellsAreAdjusting(x, y, selfHeroX, selfHeroY)){
//            return true;
//        }
//        return false;
//    }
//
//    public void insertSpellInCellTypeEnemyHero(int x, int y){
////        String numOfTargets = jsonObject.get("numOfTargets").toString();
////        String actsOn = jsonObject.get("actsOn").toString();
////        String locationOfTarget = jsonObject.get("locationOfTarget").toString();
////        String square = jsonObject.get("Square").toString();
//        String numOfTargets = this.numOfTarget;
//        String actsOn = this.friendOrEnemy;
//        String locationOfTarget = this.locationOfTarget;
//
//        switch (numOfTargets){
//            case "1":
//                if (actsOn.equals("cell") || actsOn.equals("friend")){
//                    GameView.printInvalidCommandWithThisContent("Invalid Target!");
//                }else{
//                    applySpellOnEnemyHero(x, y);
//                }
//                break;
//            case "all":
//                if (locationOfTarget.equals("null")){//when target is all enemy forces
//                    applySpellOnAllEnemyForces(x, y);
//                }else{//when target is all enemy forces in a column
//                    applySpellOnAllEnemyForcesInColumn(x, y);
//                }
//                break;
//            case "square2":
//                applySpellOn2x2Square(x, y);
//                break;
//            case "square3":
//                applySpellOn3x3Square(x, y);
//                break;
//        }
//    }
//
//    public void applySpellOnEnemyHero(int x, int y){
//
//        ArrayList<Buff> buffs = this.buffs;
//        ArrayList<Buff> actions = this.actions;
//        ArrayList<String> typeOfActions = this.actionType;
//
//        for (int i = 0; i < typeOfActions.size(); i++) {
//            switch (typeOfActions.get(i)){
//                case "dispel":
//                    Hero.getHeroByCoordination(x, y).dispelThisForce("enemy");
//                    break;
//                case "kill":
//                    Hero.getHeroByCoordination(x, y).killCard();
//                    break;
//                case "addBuff":
//                    for (Buff buff : buffs)
//                        Hero.getHeroByCoordination(x, y).addBuffToHero(buff);
//                    break;
//                case "addAction":
//                    for (Buff actionBuff : actions){
//                        Hero.getHeroByCoordination(x, y).getBuffActions().add(actionBuff);
//                    }
//                    break;
//            }
//        }
//
//
//    }
//    public static ArrayList<Card> friendCardsInMap(){
//        ArrayList<Card> cards = new ArrayList<>(Game.getInstance().getMap().getFriendMinions());
//        cards.add(Game.getInstance().getMap().getFriendHero());
//        return cards;
//    }
//    public static ArrayList<Card> enemyCardsInMap(){
//        ArrayList<Card> cards = new ArrayList<>(Game.getInstance().getMap().getEnemyMinions());
//        cards.add(Game.getInstance().getMap().getEnemyHero());
//        return cards;
//    }
//
//    public void applySpellOnAllEnemyForces(int x, int y){
////        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
////        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
////        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");
////
////        int cardID = Integer.parseInt(jsonObject.get("id").toString());
////        String cardName = jsonObject.get("name").toString();
////        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");
//        ArrayList<Buff> buffs = this.buffs;
//        ArrayList<Buff> actions = this.actions;
//        ArrayList<String> typeOfActions = this.actionType;
//
//        for (int i = 0; i < typeOfActions.size(); i++) {
//            switch (typeOfActions.get(i)){
//                case "dispel":
//                    for (Card card : friendCardsInMap())
//                        card.dispelThisForce("enemy");
//                    break;
//                case "kill":
//                    for (Card card : enemyCardsInMap())
//                        card.killCard();
//                    break;
//                case "addBuff":
//                    for (Buff buff : buffs) {
//                        for (Card card : Game.getInstance().getMap().getEnemyMinions())
//                            ((Minion) card).addBuffToMinion(buff);
//                        Game.getInstance().getMap().getEnemyHero().addBuffToHero(buff);
//                    }
//                    break;
//                case "addAction":
//                    for (Buff actionBuff : actions){
//                        Game.getInstance().getMap().getEnemyHero().getBuffActions().add(actionBuff);
//                        for (Card card : Game.getInstance().getMap().getEnemyMinions())
//                            ((Minion) card).getBuffActions().add(actionBuff);
//                    }
//
//                    break;
//            }
//        }
//    }
//
//    public void insertSpellInCellTypeSelfMinion(int x, int y){
////        String numOfTargets = jsonObject.get("numOfTargets").toString();
////        String actsOn = jsonObject.get("actsOn").toString();
////        String square = jsonObject.get("Square").toString();
//        String numOfTargets = this.numOfTarget;
//        String actsOn = this.friendOrEnemy;
//
//        switch (numOfTargets){
//            case "1":
//                if (actsOn.equals("map")){
//                    GameView.printInvalidCommandWithThisContent("Invalid Target!");
//                }else{
//                    applySpellOnMinion(x, y);
//                }
//                break;
//            case "all":
//                if (!actsOn.equals("owner")){
//                    GameView.printInvalidCommandWithThisContent("Invalid Target!");
//                }else{
//                    applySpellOnAllSelfForces(x, y);
//                }
//                break;
//            case "square2":
//                applySpellOn2x2Square(x, y);
//                break;
//            case "square3":
//                applySpellOn3x3Square(x, y);
//                break;
//        }
//    }
//
//    public void insertSpellInCellTypeSelfHero(int x, int y){
////        String numOfTargets = jsonObject.get("numOfTargets").toString();
////        String actsOn = jsonObject.get("actsOn").toString();
////        String locationOfTarget = jsonObject.get("locationOfTarget").toString();
//        String numOfTargets = this.numOfTarget;
//        String actsOn = this.friendOrEnemy;
//
//        switch (numOfTargets){
//            case "1":
//                if (actsOn.equals("cell") || actsOn.equals("enemy")){
//                    GameView.printInvalidCommandWithThisContent("Invalid Target!");
//                }else{
//                    applySpellOnSelfHero(x, y);
//                }
//                break;
//            case "all":
//                if (!locationOfTarget.equals("null")){//when target is all self forces
//                    applySpellOnAllSelfForces(x, y);
//                }
//                break;
//            case "square2":
//                applySpellOn2x2Square(x, y);
//                break;
//            case "square3":
//                applySpellOn3x3Square(x, y);
//                break;
//        }
//    }
//
//    public void applySpellOnSelfHero(int x, int y){
//        ArrayList<Buff> buffs = this.buffs;
//        ArrayList<Buff> actions = this.actions;
//        ArrayList<String> typeOfActions = this.actionType;
//
//        for (int i = 0; i < typeOfActions.size(); i++) {
//            switch (typeOfActions.get(i)){
//                case "dispel":
//                    Hero.getHeroByCoordination(x, y).dispelThisForce("friend");
//                    break;
//                case "kill":
//                    Hero.getHeroByCoordination(x, y).killCard();
//                    break;
//                case "addBuff":
//                    for (Buff buff : buffs)
//                        Hero.getHeroByCoordination(x, y).addBuffToHero(buff);
//                    break;
//                case "addAction":
//                    for (Buff actionBuff : actions){
//                        Hero.getHeroByCoordination(x, y).getBuffActions().add(actionBuff);
//                    }
//                    break;
//            }
//        }
//    }
//
//    public void applySpellOnAllSelfForces(int x, int y){
//
//        ArrayList<Buff> buffs = this.buffs;
//        ArrayList<Buff> actions = this.actions;
//        ArrayList<String> typeOfActions = this.actionType;
//
//        for (int i = 0; i < typeOfActions.size(); i++) {
//            switch (typeOfActions.get(i)){
//                case "dispel":
//                    for (Card card : friendCardsInMap())
//                        card.dispelThisForce("friend");
//                    break;
//                case "kill":
//                    for (Card card : friendCardsInMap())
//                        card.killCard();
//                    break;
//                case "addBuff":
//                    for (Buff buff : buffs) {
//                        for (Card card : Game.getInstance().getMap().getFriendMinions())
//                            ((Minion) card).addBuffToMinion(buff);
//                        Game.getInstance().getMap().getFriendHero().getBuffActions().add(buff);
//                    }
//                    break;
//                case "addAction":
//                    for (Buff actionBuff : actions){
//                        for (Card card : Game.getInstance().getMap().getFriendMinions())
//                            ((Minion) card).getBuffActions().add(actionBuff);
//                        Game.getInstance().getMap().getFriendHero().getBuffActions().add(actionBuff);
//                    }
//
//                    break;
//            }
//        }
//    }
//
//    public void applySpellOnAllEnemyForcesInColumn(int x, int y){
//        //Todo : check if the coordination is correct -> (x, y, i)
//        ArrayList<Buff> buffs = this.buffs;
//        ArrayList<Buff> actions = this.actions;
//
//        for (Buff buff : buffs){
//            for (int i = 0; i < 5; i++) {
//                if (Game.getInstance().getMap().getCells()[i][y].getCellType() == CellType.enemyMinion){
//                    ((Minion) Card.getCardByCoordination(i, y)).addBuffToMinion(buff);
//                }else if (Game.getInstance().getMap().getCells()[i][y].getCellType() == CellType.enemyHero){
//                    ((Hero) Card.getCardByCoordination(i, y)).addBuffToHero(buff);
//                }
//            }
//        }
//
//        for (Buff actionBuff : actions){
//            for (int i = 0; i < 5; i++) {
//                if (Game.getInstance().getMap().getCells()[i][y].getCellType() == CellType.enemyMinion){
//                    ((Minion) Card.getCardByCoordination(i, y)).getBuffActions().add(actionBuff);
//                }else if (Game.getInstance().getMap().getCells()[i][y].getCellType() == CellType.enemyHero){
//                    ((Hero) Card.getCardByCoordination(i, y)).getBuffActions().add(actionBuff);
//                }
//            }
//        }
//    }
//
//    public void applySpellOn2x2Square(int x, int y){
////        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
////        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
////        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");
////        String[] actsOn = jsonObject.get("actsOn").toString().split(",");
////
////        int cardID = Integer.parseInt(jsonObject.get("id").toString());
////        String cardName = jsonObject.get("name").toString();
////        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");
//
//        ArrayList<String> typeOfActions = this.actionType;
//
//        for (int i = 0; i < typeOfActions.size(); i++) {
//            switch (typeOfActions.get(i)){
//                case "dispel":
//                    applySpellOn2x2SquareOnForces(x, y);
//                    break;
//                case "addAction":
//                    applySpellOn2x2SquareOnMap(x, y);
//                    break;
//            }
//        }
//
////        for (int i = 0; i < buffNames[i].length(); i++) {
////            Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
////                    buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));
////
////            if (actsOn[i].equals("friend/enemy")){
////                applySpellOn2x2SquareOnForces(jsonObject, x, y);
////            }else if (actsOn[i].equals("cell")){
////                applySpellOn2x2SquareOnMap(jsonObject, x, y, buff);
////            }
////
////
////        }
//    }//Todo **** : update cards in arrayList
//
//    //method for spell that removes positive buffs from enemy forces and negative buffs from self forces
//    public void applySpellOn2x2SquareOnForces(int x, int y){
//
//        for (int i = x; i < x + 3; i++) {
//            for (int j = y; j < y + 3; j++) {
//                if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfHero ||
//                        Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfMinion
//                ){
//                    Card.getCardByCoordination(x, y).dispelThisForce("friend");
//                }else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyHero ||
//                        Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyMinion){
//                    Card.getCardByCoordination(x, y).dispelThisForce("enemy");
//                }
//            }
//        }
//    }
//
//    public void applySpellOn2x2SquareOnMap(int x, int y){
//        for (int i = x; i < x + 2; i++) {
//            for (int j = y; j < y + 2; j++) {
////                Game.getInstance().getMap().getCells()[i][j].setCellImpactType(CellImpactType.fire);
//            }
//        }
//
//        ArrayList<Buff> buffs = this.buffs;
//
////        int cardID = Integer.parseInt(jsonObject.get("id").toString());
////        String cardName = jsonObject.get("name").toString();
////        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");
//
//        for (Buff buff : buffs) {
//            for (int i = x; i < x + 2; i++) {
//                for (int j = y; j < y + 2; j++) {
//                    if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfHero) {
//                        CellImpactType.applyFireImpactOnCard(Game.getInstance().getMap().getFriendHero(), buff);
//                    } else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyHero) {
//                        CellImpactType.applyFireImpactOnCard(Game.getInstance().getMap().getEnemyHero(), buff);
//                    } else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyMinion ||
//                            Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfMinion) {
//                        CellImpactType.applyFireImpactOnCard(Minion.getMinionInThisCoordination(x, y), buff);
//                    }
//                }
//            }
//        }
//    }
//
//    //method for applying buff on 3x3 square
//    public void applySpellOn3x3Square(int x, int y){
//
//        ArrayList<Buff> buffs = this.buffs;
//
//        for (Buff buff : buffs)
//            applySpellOn3x3SquareOnMap(x, y, buff);
//    }
//
//    public void applySpellOn3x3SquareOnMap(int x, int y, Buff buff){
//        for (int i = x; i < x + 2; i++) {
//            for (int j = y; j < y + 2; j++) {
////                Game.getInstance().getMap().getCells()[i][j].setCellImpactType(CellImpactType.poison);
//            }
//        }
//
////        int cardID = Integer.parseInt(jsonObject.get("id").toString());
////        String cardName = jsonObject.get("name").toString();
////        System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");
//
//        //Todo : don't know what to do with cell impact
//        for (int i = x; i < x + 2; i++) {
//            for (int j = y; j < y + 2; j++) {
//                if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfHero){
//                    CellImpactType.applyPoisonImpactOnCard(Game.getInstance().getMap().getFriendHero(), buff);
//                }else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyHero){
//                    CellImpactType.applyPoisonImpactOnCard(Game.getInstance().getMap().getEnemyHero(), buff);
//                }else if (Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.enemyMinion ||
//                        Game.getInstance().getMap().getCells()[i][j].getCellType() == CellType.selfMinion){
//                    CellImpactType.applyPoisonImpactOnCard(Minion.getMinionInThisCoordination(x, y), buff);
//                }
//            }
//        }
//    }
}


