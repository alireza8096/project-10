package model.collection;

import model.Game;

import java.util.ArrayList;
import java.util.Random;

public class Force extends Card{

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
    private boolean canAttackOrMove;
    private boolean canCounterAttack;
    private boolean hasHolyBuff;
    private String specialPower;

    public Force(int healthPoint, int attackPower, String attackType, int attackRange, String specialPower){
        this.healthPoint = healthPoint;
        this.attackPower = attackPower;
        this.attackType = attackType;
        this.attackRange = attackRange;
        this.specialPower = specialPower;
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

    public static Force getRandomForce(){
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        if (randomNumber == 0){
            return getRandomEnemyForce();
        }else{
            return getRandomFriendForce();
        }
    }

    public static Force getRandomEnemyForce(){
        Random random = new Random();
        int minionOrHero = random.nextInt(2);
        if (minionOrHero == 0){//return a minion
            int minionNumber = random.nextInt(Game.getInstance().getMap().getEnemyMinions().size());
            return Game.getInstance().getMap().getEnemyMinions().get(minionNumber);
        }else{//return a hero
            return Game.getInstance().getMap().getEnemyHeroes().get(0);
        }
    }

    public static Force getRandomFriendForce(){
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

    public void dispelPositiveActions() {
        ArrayList<Buff> actionBuffsCopy = actionBuffsOnItself;

        for (Buff buff : actionBuffsCopy) {
            if (buff.getType().equals("positive")) {
                actionBuffsOnItself.remove(buff);
            }
        }
    }

    public void applyAllBuffsOnForce(){
        for (Buff buff : this.positiveBuffsOnItself){

        }

        for (Buff buff : this.negativeBuffsOnItself){

        }

        for (Buff buff : this.actionBuffsOnItself){

        }
    }

    public void applyThisBuffOnForce(Buff buff){
        String buffName = buff.getName();

        switch (buffName){
            case "healthPointWeaknessBuff":
                applyHealthPointWeaknessBuffOnForce(buff);
                break;
            case "attackPowerWeaknessBuff":
                applyAttackPowerWeaknessBuffOnForce(buff);
                break;
            case "attackPowerBuff":
                applyAttackPowerBuff(buff);
                break;
            case "healthPointBuff":
                applyHealthPointBuff(buff);
                break;
            case "holyBuff":
                applyHolyBuff(buff);
                break;
            case "disarm":
                applyDisarmBuff(buff);
                break;
            case "poisonBuff":
                applyPoisonBuff(buff);
                break;
            case "stunBuff":
                applyStunBuff(buff);
                break;
        }
    }

    public void applyHealthPointWeaknessBuffOnForce(Buff buff){
        int forHowManuTurns = buff.getForHowManyTurns();
        int delay = buff.getDelay();

    }


    public void test(){

    }
}

