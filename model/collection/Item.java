package model.collection;

import model.CellType;
import model.Game;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Objects;

public class Item extends Card implements Cloneable {

    private static ArrayList<Item> items = new ArrayList<>();
    private Spell spell;
    private String itemType;
    private String desc;
    private String specification;
    private String user;
    private String activationTime;

    public Item(Spell spell, String itemType, String price, String name, String id,
                String desc, String specification, String user, String activationTime, String imagePath, String inField) throws FileNotFoundException {
        super("0", id, "item", name, price, imagePath, inField);
        this.spell = spell;
        this.itemType = itemType;
        this.desc = desc;
        this.specification = specification;
        this.user = user;
        this.activationTime = activationTime;
        this.cardType = "item";
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(String activationTime) {
        this.activationTime = activationTime;
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public static void setItems(ArrayList<Item> items) {
        Item.items = items;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public static Item findItemByID(int id) throws CloneNotSupportedException {
        for (Item item : items) {
            if (item.id == id) {
                return (Item) item.clone();
            }
        }
        return null;
    }

    public static int getItemIDByName(String itemName) {
        for (Item item :
                items) {
            if (item.name.matches(itemName)) {
                return item.id;
            }
        }
        return 0;
    }

    public static boolean thisCardIsItem(String cardName) {
        for (Item item : items) {
            if (item.name.compareToIgnoreCase(cardName) == 0) {
                return true;
            }
        }
        return false;
    }

    public static Item findItemByName(String cardName) throws CloneNotSupportedException {
        for (Item item : items) {
            if (item.name.matches(cardName)) {
                return (Item) item.clone();
            }
        }
        return null;
    }

    public static String getItemTypeByName(String itemName) {
        for (Item item :
                items) {
            if (item.name.matches(itemName)) {
                return item.itemType;
            }
        }
        return "";
    }

    public void insertCollectibleItemInThisCoordination(int x, int y){
        if (checkIfTargetIsValid(x, y)){
            applyCollectibleItem(x, y);
        }else{
            GameView.printInvalidCommandWithThisContent("Target is not valid!");
        }
    }

    public void applyCollectibleItem(int x, int y){
        Target itemTarget = this.spell.getSpellTarget();
        String numOfTargets = itemTarget.getNumOfTargets();

        if (numOfTargets.equals("1")){
            applyCollectibleItemOnOneForce(x, y);
        }else{
            applyCollectibleItemOnAllForces(x, y);
        }
    }

    public void applyCollectibleItemOnOneForce(int x, int y){
        //Todo : don't know if it works properly or not!?
        this.spell.getSpellTarget().setTargetOfSpell(this.spell, x, y);
        this.spell.applySpellOnItsTargets();
    }

    public void applyCollectibleItemOnAllForces(int x, int y){
        //just for "shamshire chini"
        for (Minion minion : Game.getInstance().getMap().getFriendMinions()){
            if (minion.getAttackType().equals("melee")){
                this.spell.getForceTargets().add(minion);
            }
        }
        if (Game.getInstance().getMap().getFriendHero().getAttackType().equals("melee"))
            this.spell.getForceTargets().add(Game.getInstance().getMap().getFriendHero());
    }

    public boolean checkIfTargetIsValid(int x, int y){
        Target itemTarget = this.spell.getSpellTarget();
        String target = itemTarget.getTarget();

        switch (target){
            case "minion":
                if (Game.getInstance().getMap().getCells()[x][y].minionIsOnCell() &&
                        checkIfTargetSpecificationIsRight(x, y))
                    return true;
                break;
            case "minion/hero":
                if ((Game.getInstance().getMap().getCells()[x][y].minionIsOnCell() ||
                Game.getInstance().getMap().getCells()[x][y].heroIsOnCell()) &&
                        checkIfTargetSpecificationIsRight(x, y))
                    return true;
                break;
            case "null":
                return false;
        }
        return false;
    }

    public boolean checkIfTargetSpecificationIsRight(int x, int y){
        String specification = this.specification;

        switch (specification){
            case "ranged/hybrid":
                if (((Force) Objects.requireNonNull(Card.getCardByCoordination(x, y))).getAttackType().equals("ranged") ||
                        ((Force) Objects.requireNonNull(Card.getCardByCoordination(x, y))).getAttackType().equals("hybrid")){
                    return true;
                }
                break;
            case "melee":
                if (((Force) Objects.requireNonNull(Card.getCardByCoordination(x, y))).getAttackType().equals("melee"))
                    return true;
                break;
            case "null":
                return true;
        }
        return false;
    }

    public void applyUsableItem(int x, int y){
        String user = this.user;
        if (user.equals("null")){//item works like spell
            applyItemWithNoUser();
        }else{//item work like minion
            applyItemWithUser(x, y);
        }
    }

    public void applyItemWithNoUser(){//it is used jist for 3 items
        Spell spell = this.spell;
        for (Buff buff : spell.getBuffs()){
            Game.getInstance().getMap().getFriendHero().getPositiveBuffs().add(buff);
        }
    }

    public void applyItemWithUser(int x, int y){
        if (checkIfTargetOfUsableItemIsCorrect(x, y)) {
            String user = this.user;
            String userForce = user.substring(0, user.indexOf('('));
            String numOfUsers = user.substring(user.indexOf('(') + 1, user.indexOf(')'));

            switch (userForce) {
                case "minion":

                    break;
                case "hero":

                    break;
                case "minion/hero":

                    break;
            }
        }else{
            GameView.printInvalidCommandWithThisContent("Target is not valid!");
        }
    }

    public boolean checkIfTargetOfUsableItemIsCorrect(int x, int y){
        String user = this.user;
        String userForce = user.substring(0, user.indexOf('('));

        switch (userForce){
            case "minion":
                if (Game.getInstance().getMap().getCells()[x][y].getCellType() == CellType.selfMinion)
                    return true;
                break;
            case "hero":
                if (Game.getInstance().getMap().getCells()[x][y].getCellType() == CellType.selfHero)
                    return true;
                break;
            case "minion/hero":
                if (Card.thisCardIsYours(x, y))
                    return true;
                break;
        }
        return false;
    }


//    public static Item returnFlagByRandomCoordination(){
//        int x = Cell.returnRandomNumberForCoordinationInThisRange(0, 4);
//        int y = Cell.returnRandomNumberForCoordinationInThisRange(0, 9);
//
//        Item flag = new Item("flag", "collectible", x, y);
//        return flag;
//    }

//    public void applyItem(int x, int y) {
//        if (checkIfTargetHasBeenChosenCorrectly(x, y)) {
//            if (user.equals("null")) {
//                applyItemDirectly(x, y);//when item doesn't have a user
//            } else {
//                applyItemLikeSpell(x, y);//when item has a user
//            }
//        } else {
//            GameView.printInvalidCommandWhithThisContent("Invalid Target");
//        }
//    }

//    public void applyItemLikeSpell(int x, int y) {
//        String user = this.user;
//        if (user.equals("hero")) {
//
//        } else {
//            String userNumber = user.substring(user.indexOf('(') + 1, user.length() - 1);
//            String userType = user.substring(0, user.indexOf('('));
//
//        }
//    }

//    public void applyItemDirectly(int x, int y) {
//        Force force = (Force) Card.getCardByCoordination(x, y);
//        for (Buff buff : this.positiveBuffs) {
//            force.getPositiveBuffs().add(buff);
//        }
//
//        for (Buff buff : this.negativeBuffs) {
//            force.getNegativeBuffs().add(buff);
//        }
//
//        for (Buff buff : this.actionBuffs) {
//            force.getBuffActions().add(buff);
//        }
//
//    }

//    public boolean checkIfTargetHasBeenChosenCorrectly(int x, int y) {
//        CellType cellType = Game.getInstance().getMap().getCells()[x - 1][y - 1].getCellType();
//
//        switch (cellType) {
//            case selfHero:
//                return checkIfTargetCanBeSelfHero();
//            case selfMinion:
//                return checkIfTargetCanBeSelfMinion();
//            case enemyHero:
//                return checkIfTargetCanBeEnemyHero();
//            case enemyMinion:
//                return checkIfTargetCanEnemyMinion();
//            case empty:
//                return false;
//        }
//        return false;
//    }

//    public boolean checkIfTargetCanBeSelfHero() {
//        String numOfTargets = this.numOfTarget;
//        String friendOrEnemy = this.friendOrEnemy;
//        String target = this.target;
//
//        if (numOfTargets.equals("null")) {
//            return false;
//        } else {
//            if (friendOrEnemy.equals("enemy") || (!target.equals("hero") && !target.equals("minion/hero"))) {
//                return false;
//            } else
//                return true;
//        }
//    }

//    public boolean checkIfTargetCanBeSelfMinion() {
//        String numOfTargets = this.numOfTarget;
//        String friendOrEnemy = this.friendOrEnemy;
//        String target = this.target;
//
//        if (numOfTargets.equals("null")) {
//            return false;
//        } else {
//            if (friendOrEnemy.equals("enemy") || (!target.equals("minion") && !target.equals("minion/hero"))) {
//                return false;
//            } else
//                return true;
//        }
//    }

//    public boolean checkIfTargetCanEnemyMinion() {
//        String numOfTargets = this.numOfTarget;
//        String friendOrEnemy = this.friendOrEnemy;
//        String target = this.target;
//
//        if (numOfTargets.equals("null")) {
//            return false;
//        } else {
//            if (friendOrEnemy.equals("friend") || (!target.equals("minion") && !target.equals("minion/hero"))) {
//                return false;
//            } else
//                return true;
//        }
//    }

//    public boolean checkIfTargetCanBeEnemyHero() {
//        String numOfTargets = this.numOfTarget;
//        String friendOrEnemy = this.friendOrEnemy;
//        String target = this.target;
//
//        if (numOfTargets.equals("null")) {
//            return false;
//        } else {
//            if (friendOrEnemy.equals("friend") || (!target.equals("hero") && !target.equals("minion/hero"))) {
//                return false;
//            } else
//                return true;
//        }
//    }

//    public void applyCollectableItems() {
//        String target = this.target;
//
//        switch (target) {
//            case "minion":
//                applyCollectibleItemOnMinion();
//                break;
//            case "minion/hero":
//                break;
//            case "null"://items that adds mana
//                for (Buff buff : this.positiveBuffs) {
//                    Game.getInstance().getMap().getFriendHero().getPositiveBuffs().add(buff);
//                }
//                break;
//        }
//    }

//    public void applyCollectibleItemOnMinion() {
//        ArrayList<String> actionTypes = this.typeOfActions;
//
//        for (String typeOfAction : actionTypes) {
//            switch (typeOfAction) {
//                case "addBuff":
//                    for (Buff buff : this.positiveBuffs) {
//                        Force.getRandomFriendMinion().getPositiveBuffsOnItself().add(buff);
//                    }
//                    break;
//                case "addAction":
//                    for (Buff buff : this.actionBuffs) {
//                        Force.getRandomFriendMinion().getActionBuffsOnItself().add(buff);
//                    }
//                    break;
//            }
//        }
//
//
//    }

//    public void applyCollectableItemOnMinionOrHero() {
//        String locationOfTarget = this.locationOfTarget;
//
//        switch (locationOfTarget) {
//            case "random":
//                applyCollectibleItemOnRandomMinionOrHero();
//                break;
//            case "null":
//                applyCollectibleItemOnMeleeForces();
//                break;
//            case "closest":
//                //Todo : implement "nefrine marg" item
//                break;
//        }
//    }

//    public void applyCollectibleItemOnMeleeForces() {
//        for (Buff buff : this.actionBuffs) {
//
//            for (Minion minion : Game.getInstance().getMap().getFriendMinions()) {
//                minion.getActionBuffsOnItself().add(buff);
//            }
//
//            Game.getInstance().getMap().getFriendHero().getActionBuffsOnItself().add(buff);
//
//
//            Game.getInstance().getMap().getFriendHero().getActionBuffsOnItself().add(buff);
//        }
//    }
//
//    public void applyCollectibleItemOnRandomMinionOrHero() {
//
//        //all numOfTargets are 1
//        boolean forceHasBeenChosenCorrectly = false;
//        Force randomForce = null;
//        while (!forceHasBeenChosenCorrectly) {
//            if (this.friendOrEnemy.equals("friend")) {
//                randomForce = Force.getRandomFriendForce();
//            } else {
//                randomForce = Force.getRandomForce();
//            }
//
//            String randomForceSpecification = randomForce.getAttackType();
//            String specification = this.specification;
//            switch (specification) {
//                case "ranged/hybrid":
//                    if (randomForceSpecification.equals("ranged") || randomForceSpecification.equals("hybrid"))
//                        forceHasBeenChosenCorrectly = true;
//                    break;
//                case "null":
//                    if (randomForceSpecification.equals("null"))
//                        forceHasBeenChosenCorrectly = true;
//                    break;
//            }
//
//        }
//        for (String typeOfAction : this.typeOfActions) {
//            switch (typeOfAction) {
//                case "addBuff":
//                    for (Buff buff : this.positiveBuffs) {
//                        randomForce.getPositiveBuffsOnItself().add(buff);
//                    }
//                    break;
//                case "addAction":
//                    for (Buff buff : this.actionBuffs) {
//                        randomForce.getActionBuffsOnItself().add(buff);
//                    }
//                    break;
//            }
//        }
//    }

//    public void applyUsableItems(){
//        String target = this.target;
//
//        switch (target){
//            case "itself"://it is just for "ghosle tameed"
//
//                break;
//            case "minion/hero":
//
//                break;
//            case "hero":
//                break;
//            case "target":
//                break;
//        }
//    }

}

