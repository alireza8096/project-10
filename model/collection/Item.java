package model.collection;

import model.CellType;
import model.Game;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Item implements Cloneable{
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

    private ArrayList<Buff> positiveBuffs = new ArrayList<>();
    private ArrayList<Buff> negativeBuffs = new ArrayList<>();
    private ArrayList<Buff> actionBuffs = new ArrayList<>();

    private String target;
    private String numOfTarget;
    private ArrayList<String> typeOfActions = new ArrayList<>();
    private String friendOrEnemy;
    private String locationOfTarget;
    private String specification;
    private String user;
    private String desc;

    private static ArrayList<Item> items = new ArrayList<>();
    //    public static ArrayList<String> itemNames = new ArrayList<>();
    private int id;
    private String name;
    private String itemType;
    private int price;
    private int x;
    private int y;

    public Item() {
    }

    public Item(String name, String itemType, int x, int y) {
        this.name = name;
        this.itemType = itemType;
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static Item findItemByID(int id) throws CloneNotSupportedException {
        for (Item item : items) {
            if (item.id == id) {
                return (Item)item.clone();
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
            if (item.name.matches(cardName)) {
                return true;
            }
        }
        return false;
    }

    public static Item findItemByName(String cardName) throws CloneNotSupportedException {
        for (Item item : items) {
            if(item.name.matches(cardName)){
                return (Item)item.clone();
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


//    public static Item returnFlagByRandomCoordination(){
//        int x = Cell.returnRandomNumberForCoordinationInThisRange(0, 4);
//        int y = Cell.returnRandomNumberForCoordinationInThisRange(0, 9);
//
//        Item flag = new Item("flag", "collectible", x, y);
//        return flag;
//    }

    public void applyItem(int x, int y) {
        if (checkIfTargetHasBeenChosenCorrectly(x, y)) {
            if (user.equals("null")) {
                applyItemDirectly(x, y);//when item doesn't have a user
            } else {
                applyItemLikeSpell(x, y);//when item has a user
            }
        } else {
            GameView.printInvalidCommandWhithThisContent("Invalid Target");
        }
    }

    public void applyItemLikeSpell(int x, int y) {
        String user = this.user;
        if (user.equals("hero")) {

        } else {
            String userNumber = user.substring(user.indexOf('(') + 1, user.length() - 1);
            String userType = user.substring(0, user.indexOf('('));

        }
    }

    public void applyItemDirectly(int x, int y) {
        Force force = (Force) Card.getCardByCoordination(x, y);
        for (Buff buff : this.positiveBuffs) {
            force.getPositiveBuffs().add(buff);
        }

        for (Buff buff : this.negativeBuffs) {
            force.getNegativeBuffs().add(buff);
        }

        for (Buff buff : this.actionBuffs) {
            force.getBuffActions().add(buff);
        }

    }

    public boolean checkIfTargetHasBeenChosenCorrectly(int x, int y) {
        CellType cellType = Game.getInstance().getMap().getCells()[x - 1][y - 1].getCellType();

        switch (cellType) {
            case selfHero:
                return checkIfTargetCanBeSelfHero();
            case selfMinion:
                return checkIfTargetCanBeSelfMinion();
            case enemyHero:
                return checkIfTargetCanBeEnemyHero();
            case enemyMinion:
                return checkIfTargetCanEnemyMinion();
            case empty:
                return false;
        }
        return false;
    }

    public boolean checkIfTargetCanBeSelfHero() {
        String numOfTargets = this.numOfTarget;
        String friendOrEnemy = this.friendOrEnemy;
        String target = this.target;

        if (numOfTargets.equals("null")) {
            return false;
        } else {
            if (friendOrEnemy.equals("enemy") || (!target.equals("hero") && !target.equals("minion/hero"))) {
                return false;
            } else
                return true;
        }
    }

    public boolean checkIfTargetCanBeSelfMinion() {
        String numOfTargets = this.numOfTarget;
        String friendOrEnemy = this.friendOrEnemy;
        String target = this.target;

        if (numOfTargets.equals("null")) {
            return false;
        } else {
            if (friendOrEnemy.equals("enemy") || (!target.equals("minion") && !target.equals("minion/hero"))) {
                return false;
            } else
                return true;
        }
    }

    public boolean checkIfTargetCanEnemyMinion() {
        String numOfTargets = this.numOfTarget;
        String friendOrEnemy = this.friendOrEnemy;
        String target = this.target;

        if (numOfTargets.equals("null")) {
            return false;
        } else {
            if (friendOrEnemy.equals("friend") || (!target.equals("minion") && !target.equals("minion/hero"))) {
                return false;
            } else
                return true;
        }
    }

    public boolean checkIfTargetCanBeEnemyHero() {
        String numOfTargets = this.numOfTarget;
        String friendOrEnemy = this.friendOrEnemy;
        String target = this.target;

        if (numOfTargets.equals("null")) {
            return false;
        } else {
            if (friendOrEnemy.equals("friend") || (!target.equals("hero") && !target.equals("minion/hero"))) {
                return false;
            } else
                return true;
        }
    }

    public void applyCollectableItems() {
        String target = this.target;

        switch (target) {
            case "minion":
                applyCollectibleItemOnMinion();
                break;
            case "minion/hero":
                break;
            case "null"://items that adds mana
                for (Buff buff : this.positiveBuffs) {
                    Game.getInstance().getMap().getFriendHero().getPositiveBuffs().add(buff);
                }
                break;
        }
    }

    public void applyCollectibleItemOnMinion() {
        ArrayList<String> actionTypes = this.typeOfActions;

        for (String typeOfAction : actionTypes) {
            switch (typeOfAction) {
                case "addBuff":
                    for (Buff buff : this.positiveBuffs) {
                        Force.getRandomFriendMinion().getPositiveBuffsOnItself().add(buff);
                    }
                    break;
                case "addAction":
                    for (Buff buff : this.actionBuffs) {
                        Force.getRandomFriendMinion().getActionBuffsOnItself().add(buff);
                    }
                    break;
            }
        }


    }

    public void applyCollectableItemOnMinionOrHero() {
        String locationOfTarget = this.locationOfTarget;

        switch (locationOfTarget) {
            case "random":
                applyCollectibleItemOnRandomMinionOrHero();
                break;
            case "null":
                applyCollectibleItemOnMeleeForces();
                break;
            case "closest":
                //Todo : implement "nefrine marg" item
                break;
        }
    }

    public void applyCollectibleItemOnMeleeForces() {
        for (Buff buff : this.actionBuffs) {

            for (Minion minion : Game.getInstance().getMap().getFriendMinions()) {
                minion.getActionBuffsOnItself().add(buff);
            }

            Game.getInstance().getMap().getFriendHero().getActionBuffsOnItself().add(buff);


            Game.getInstance().getMap().getFriendHero().getActionBuffsOnItself().add(buff);
        }
    }

    public void applyCollectibleItemOnRandomMinionOrHero() {

        //all numOfTargets are 1
        boolean forceHasBeenChosenCorrectly = false;
        Force randomForce = null;
        while (!forceHasBeenChosenCorrectly) {
            if (this.friendOrEnemy.equals("friend")) {
                randomForce = Force.getRandomFriendForce();
            } else {
                randomForce = Force.getRandomForce();
            }

            String randomForceSpecification = randomForce.getAttackType();
            String specification = this.specification;
            switch (specification) {
                case "ranged/hybrid":
                    if (randomForceSpecification.equals("ranged") || randomForceSpecification.equals("hybrid"))
                        forceHasBeenChosenCorrectly = true;
                    break;
                case "null":
                    if (randomForceSpecification.equals("null"))
                        forceHasBeenChosenCorrectly = true;
                    break;
            }

        }
        for (String typeOfAction : this.typeOfActions) {
            switch (typeOfAction) {
                case "addBuff":
                    for (Buff buff : this.positiveBuffs) {
                        randomForce.getPositiveBuffsOnItself().add(buff);
                    }
                    break;
                case "addAction":
                    for (Buff buff : this.actionBuffs) {
                        randomForce.getActionBuffsOnItself().add(buff);
                    }
                    break;
            }
        }
    }

    public void applyUsableItems(){
        String target = this.target;

        switch (target){
            case "itself"://it is just for "ghosle tameed"

                break;
            case "minion/hero":
                applyUsableItemOnMinionOrHero();
                break;
            case "hero":
                applyUsableItemOnHero();

                break;
            case "target":
                break;
        }
    }

    public void applyUsableItemOnMinionOrHero(){
        String user = this.user;
        String userNumber = user.substring(user.indexOf('('), user.indexOf(')') - 1);
        switch (userNumber){
            case "random":
                addItemBuffsToRandomForce();
                break;
            case "all":
                addItemBuffsToAllForces();
                break;
        }
    }

    public void addItemBuffsToAllForces(){
        for (Buff buff : this.positiveBuffs) {
            addItemBuffToAllFriendForces(buff);
        }

        for (Buff buff : this.negativeBuffs){
            addItemBuffToAllFriendForces(buff);
        }

        for (Buff buff : this.actionBuffs){
            addItemBuffToAllFriendForces(buff);
        }
    }

    public void addItemBuffsToRandomForce(){
        Force force = Force.getRandomFriendForce();

        for (Buff buff : this.positiveBuffs)
            force.getPowersGivenToForce().add(buff);

        for (Buff buff : this.negativeBuffs)
            force.getPowersGivenToForce().add(buff);

        for (Buff buff : this.actionBuffs)
            force.getPowersGivenToForce().add(buff);
    }

    public void addItemBuffToAllFriendForces(Buff buff){
        for (Minion minion : Game.getInstance().getMap().getFriendMinions())
            minion.getPowersGivenToForce().add(buff);

        Game.getInstance().getMap().getFriendHero().getPowersGivenToForce().add(buff);

    }

}

