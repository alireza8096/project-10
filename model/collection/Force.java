package model.collection;

import model.Game;

import java.util.ArrayList;
import java.util.Random;

public class Force extends Card{

    //buffs that effect on the force itself
    private ArrayList<Spell> specialPowerSpells = new ArrayList<>();
    private ArrayList<Buff> positiveBuffsOnItself = new ArrayList<>();
    private ArrayList<Buff> negativeBuffsOnItself = new ArrayList<>();
    private ArrayList<Buff> actionBuffsOnItself = new ArrayList<>();

    //all buffs that force has in its special power
    private ArrayList<Buff> positiveBuffs = new ArrayList<>();
    private ArrayList<Buff> negativeBuffs = new ArrayList<>();
    private ArrayList<Buff> buffActions = new ArrayList<>();

    private int healthPoint;
    private int attackPower;
    private String attackType;
    private int attackRange;
    private boolean canAttackOrMove;
    private boolean canCounterAttack;
    private boolean hasHolyBuff;
    private String specialPower;
    private ArrayList<String> actionTypes = new ArrayList<>();

    public Force(int healthPoint, int attackPower, String attackType, int attackRange, String specialPower){
        this.healthPoint = healthPoint;
        this.attackPower = attackPower;
        this.attackType = attackType;
        this.attackRange = attackRange;
        this.specialPower = specialPower;
    }

    public ArrayList<String> getActionTypes() {
        return actionTypes;
    }

    public void setActionTypes(ArrayList<String> actionTypes) {
        this.actionTypes = actionTypes;
    }

    public ArrayList<Spell> getSpecialPowerSpells() {
        return specialPowerSpells;
    }

    public void setSpecialPowerSpells(ArrayList<Spell> specialPowerSpells) {
        this.specialPowerSpells = specialPowerSpells;
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

    public boolean isCanAttackOrMove() {
        return canAttackOrMove;
    }

    public void setCanAttackOrMove(boolean canAttackOrMove) {
        this.canAttackOrMove = canAttackOrMove;
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

    public String getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(String specialPower) {
        this.specialPower = specialPower;
    }

    public Force getRandomEnemyForce(){
        Random random = new Random();
        int minionOrHero = random.nextInt(2);
        if (minionOrHero == 0){//return a minion
            int minionNumber = random.nextInt(Game.getInstance().getMap().getEnemyMinions().size());
            return Game.getInstance().getMap().getEnemyMinions().get(minionNumber);
        }else{//return a hero
            return Game.getInstance().getMap().getEnemyHeroes().get(0);
        }
    }

    public Force getRandomFriendForce(){
        Random random = new Random();
        int minionOrHero = random.nextInt(2);
        if (minionOrHero == 0){//return a minion
            int minionNumber = random.nextInt(Game.getInstance().getMap().getFriendMinions().size());
            return Game.getInstance().getMap().getFriendMinions().get(minionNumber);
        }else{//return a hero
            return Game.getInstance().getMap().getFriendHeroes().get(0);
        }
    }

    public static Force getRandomFriendMinion(){
        Random random = new Random();
        int minionIndex = random.nextInt(Game.getInstance().getMap().getFriendMinions().size());
        return Game.getInstance().getMap().getFriendMinions().get(minionIndex);
    }

    public static Force getRandomEnemyMinion(){
        Random random = new Random();
        int minionIndex = random.nextInt(Game.getInstance().getMap().getEnemyMinions().size());
        return Game.getInstance().getMap().getEnemyMinions().get(minionIndex);
    }
}

