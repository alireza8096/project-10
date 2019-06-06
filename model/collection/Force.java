package model.collection;

import model.Game;
import model.Map;

import java.util.ArrayList;
import java.util.Random;

public class Force extends Card{

    private ArrayList<Spell> specialPowerBuffs = new ArrayList<>();

    private ArrayList<Buff> positiveBuffs = new ArrayList<>();
    private ArrayList<Buff> negativeBuffs = new ArrayList<>();
    private ArrayList<Buff> actions = new ArrayList<>();

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

    public ArrayList<Spell> getSpecialPowerBuffs() {
        return specialPowerBuffs;
    }

    public void setSpecialPowerBuffs(ArrayList<Spell> specialPowerBuffs) {
        this.specialPowerBuffs = specialPowerBuffs;
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

    public ArrayList<Buff> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Buff> actions) {
        this.actions = actions;
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

