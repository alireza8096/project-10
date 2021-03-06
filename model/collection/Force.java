package model.collection;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.CustomCardsEnums.Targets;
import model.Game;
import view.MainView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Force extends Card {

    //buffs that effect on the force itself
    private ArrayList<Buff> positiveBuffsOnItself = new ArrayList<>();
    private ArrayList<Buff> negativeBuffsOnItself = new ArrayList<>();
    private ArrayList<Buff> actionBuffsOnItself = new ArrayList<>();

    //all buffs that force has in its special power
    private ArrayList<Buff> positiveBuffs = new ArrayList<>();
    private ArrayList<Buff> negativeBuffs = new ArrayList<>();
    private ArrayList<Buff> buffActions = new ArrayList<>();

    private ArrayList<String> targets = new ArrayList<>();
    private ArrayList<String> numOfTargets = new ArrayList<>();
    private ArrayList<String> friendOrEnemy = new ArrayList<>();
    private ArrayList<String> locationOfTargets = new ArrayList<>();
    private ArrayList<String> actionTypes = new ArrayList<>();
    private int healthPoint;
    private int attackPower;
    private String attackType;
    private int attackRange;
    private boolean canAttack;
    private boolean canMove;
    private boolean canCounterAttack;
    private boolean hasHolyBuff;

//    private String specialPower;


    //Todo : added recently from here

    private Spell spell;

    private Spell itemSpell;

    public Spell getItemSpell() {
        return itemSpell;
    }

    public void setItemSpell(Spell itemSpell) {
        this.itemSpell = itemSpell;
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    //Todo : to here

    public static ArrayList<String> returnArrayList(String toArray) {
        ArrayList<String> returnString = new ArrayList<>();
        String[] splitter = toArray.split(",");
        if (!splitter[0].equals("null")) Collections.addAll(returnString, splitter);
        return returnString;
    }

    public Force(String mana, String id, String cardType, String name, String price, String targets,
                 String numOfTargets, String friendOrEnemy, String healthPoint,
                 String attackPower, String attackType, String attackRange, String specialPower,
                 String actionTypes, String locationOfTargets, String imagePath, String inField, String numInShop)
            throws FileNotFoundException {
        super(mana, id, cardType, name, price, imagePath, inField,specialPower, numInShop);
        this.targets = returnArrayList(targets);
        this.numOfTargets = returnArrayList(numOfTargets);
        this.friendOrEnemy = returnArrayList(friendOrEnemy);
        this.healthPoint = Integer.parseInt(healthPoint);
        this.attackPower = Integer.parseInt(attackPower);
        this.attackType = attackType;
        if (!attackRange.equals("null")) this.attackRange = Integer.parseInt(attackRange);
        else this.attackRange = 0;
        this.actionTypes = returnArrayList(actionTypes);
        this.locationOfTargets = returnArrayList(locationOfTargets);
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<String> targets) {
        this.targets = targets;
    }

    public ArrayList<String> getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(ArrayList<String> numOfTargets) {
        this.numOfTargets = numOfTargets;
    }

    public ArrayList<String> getFriendOrEnemy() {
        return friendOrEnemy;
    }

    public void setFriendOrEnemy(ArrayList<String> friendOrEnemy) {
        this.friendOrEnemy = friendOrEnemy;
    }

    public ArrayList<String> getLocationOfTargets() {
        return locationOfTargets;
    }

    public void setLocationOfTargets(ArrayList<String> locationOfTargets) {
        this.locationOfTargets = locationOfTargets;
    }

    public ArrayList<String> getActionTypes() {
        return actionTypes;
    }

    public void setActionTypes(ArrayList<String> actionTypes) {
        this.actionTypes = actionTypes;
    }

    public ArrayList<Buff> getPositiveBuffsOnItself() {
        return positiveBuffsOnItself;
    }

    public void setPositiveBuffsOnItself(ArrayList<Buff> positiveBuffsOnItself) {
        this.positiveBuffsOnItself = positiveBuffsOnItself;
    }

    public ArrayList<Buff> getNegativeBuffsOnItself() {
        return negativeBuffsOnItself;
    }

    public void setNegativeBuffsOnItself(ArrayList<Buff> negativeBuffsOnItself) {
        this.negativeBuffsOnItself = negativeBuffsOnItself;
    }

    public ArrayList<Buff> getActionBuffsOnItself() {
        return actionBuffsOnItself;
    }

    public void setActionBuffsOnItself(ArrayList<Buff> actionBuffsOnItself) {
        this.actionBuffsOnItself = actionBuffsOnItself;
    }

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

    public ArrayList<Buff> getBuffActions() {
        return buffActions;
    }

    public void setBuffActions(ArrayList<Buff> buffActions) {
        this.buffActions = buffActions;
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

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isCanCounterAttack() {
        return canCounterAttack;
    }

    public void setCanCounterAttack(boolean canCounterAttack) {
        this.canCounterAttack = canCounterAttack;
    }

    public boolean isHasHolyBuff() {
        return hasHolyBuff;
    }

    public void setHasHolyBuff(boolean hasHolyBuff) {
        this.hasHolyBuff = hasHolyBuff;
    }

    public static Force getRandomForce() {
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        if (randomNumber == 0) {
            return getRandomEnemyForce();
        } else {
            return getRandomFriendForce();
        }
    }

    public static Force getRandomEnemyForce() {
        Random random = new Random();
        int minionOrHero = random.nextInt(2);
        if (minionOrHero == 0) {//return a minion
            int minionNumber = random.nextInt(Game.getInstance().getMap().getEnemyMinions().size());
            return Game.getInstance().getMap().getEnemyMinions().get(minionNumber);
        } else {//return a hero
            return Game.getInstance().getMap().getEnemyHero();
        }
    }

    public static Force getRandomFriendForce() {
        Random random = new Random();
        int minionOrHero = random.nextInt(2);
        if (minionOrHero == 0) {//return a minion
            int minionNumber = random.nextInt(Game.getInstance().getMap().getFriendMinions().size());
            return Game.getInstance().getMap().getFriendMinions().get(minionNumber);
        } else {//return a hero
            return Game.getInstance().getMap().getFriendHero();
        }
    }

    public static Force getRandomFriendMinion() {
        Random random = new Random();
        int minionIndex = random.nextInt(Game.getInstance().getMap().getFriendMinions().size());
        return Game.getInstance().getMap().getFriendMinions().get(minionIndex);
    }

    public static Force getRandomEnemyMinion() {
        Random random = new Random();
        int minionIndex = random.nextInt(Game.getInstance().getMap().getEnemyMinions().size());
        return Game.getInstance().getMap().getEnemyMinions().get(minionIndex);
    }

    public static Force getRandomMinion(){
        Random random = new Random();
        int number = random.nextInt(2);
        if (number == 0){
            return getRandomEnemyMinion();
        }else{
            return getRandomFriendMinion();
        }
    }

    public void dispelPositiveActions() {
        ArrayList<Buff> actionBuffsCopy = actionBuffsOnItself;

        for (Buff buff : actionBuffsCopy) {
            if (buff.getType().equals("positive")) {
                actionBuffsOnItself.remove(buff);
            }
        }
    }

    //TODO : erase this below
    public static boolean forceIsEnemyAndIsInMap(int id) {
        for (Minion minion : Game.getInstance().getMap().getEnemyMinions()) {
            if (minion.id == id)
                return true;
        }
        if (Game.getInstance().getMap().getEnemyHero().id == id) {
            return true;
        }
        return false;
    }

    public static Force returnEnemyCardByIDFromMap(int id) {
        for (Minion minion :
                Game.getInstance().getMap().getEnemyMinions()) {
            if (minion.id == id) {
                return minion;
            }
        }
        if (Game.getInstance().getMap().getEnemyHero().id == id) {
            return Game.getInstance().getMap().getEnemyHero();
        }
        return null;
    }

    public static Force returnCardByIDFromMap(int id) {
        for (Minion minion :
                Game.getInstance().getMap().getFriendMinions()) {
            if (minion.id == id) {
                return minion;
            }
        }
        if (Game.getInstance().getMap().getFriendHero().id == id) {
            return Game.getInstance().getMap().getFriendHero();
        }
        return null;
    }

    public void applyAllBuffsOnForce() {
        for (Buff buff : this.positiveBuffsOnItself) {
            if (!buff.isUsed())
                buff.applyBuffOnForce(this);
        }

        for (Buff buff : this.negativeBuffsOnItself) {
            if (!buff.isUsed())
                buff.applyBuffOnForce(this);
        }

        for (Buff buff : this.actionBuffsOnItself) {
            if (!buff.isUsed())
                buff.applyBuffOnForce(this);
        }
    }

    public void dispelForcePositively() {
        if (thisForceIsFriend()) {
            this.positiveBuffsOnItself.clear();
        } else {
            this.negativeBuffsOnItself.clear();
        }
    }

    public boolean thisForceIsFriend() {
        for (Card card : Game.getInstance().getMap().getFriendMinions()) {
            if (card.getName().equals(this.name))
                return true;
        }
        if (this.name.equals(Game.getInstance().getMap().getFriendHero().name))
            return true;
        return false;
    }

    public boolean thisForceIsEnemy() {
        for (Card card : Game.getInstance().getMap().getEnemyMinions()) {
            if (card.getName().equals(this.name))
                return true;
        }
        if (this.name.equals(Game.getInstance().getMap().getEnemyHero().name))
            return true;
        return false;
    }


}

