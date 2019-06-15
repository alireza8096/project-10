package model.collection;

import model.Player;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Buff {
    public static final String[] negativeBuffs = {"healthPointWeaknessBuff","disarm","poisonBuff","fireCellImpact","poisonCellImpact","stunBuff","attackPowerWeaknessBuff","antiHolyBuff"};
    private String forHowManyTurns;
    private String name;
    private String type;
    private int delay;
    private int howMuchImpact;
    private boolean isUsed;
    private String activationTime;

    public Buff(String forHowManyTurns, String name, String delay, String howMuchImpact,String activationTime) {
        this.forHowManyTurns = forHowManyTurns;
        this.name = name;
        if(!delay.equals("null")) this.delay = Integer.parseInt(delay);
        else this.delay = 0;
        if(!howMuchImpact.equals("null") && !howMuchImpact.equals("numOfAttacksBefore")) this.howMuchImpact = Integer.parseInt(howMuchImpact);
        else this.howMuchImpact = 0;
        this.activationTime = activationTime;
        if(isNegative(name)){
            this.type = "negative";
        }
        else{
            this.type = "positive";
        }
    }
    public static void createBuffsForSpell(Spell spell,String action,String buff,String effectValue,String delay,String last){
        Buff tempBuff;
        String[] actions = action.split(",");
        String[] buffs = buff.split(",");
        String[] effectValues = effectValue.split(",");
        String[] delays = delay.split(",");
        String[] lasts = last.split(",");
        for(int i=0; i<actions.length; i++){
            if(actions[i].matches("addAction")){
                tempBuff = new Buff(lasts[i],buffs[i],delays[i],effectValues[i],"null");
                spell.getActions().add(tempBuff);
            }
            else if(actions[i].matches("addBuff")){
                tempBuff = new Buff(lasts[i],buffs[i],delays[i],effectValues[i],"null");
                spell.getBuffs().add(tempBuff);
            }
        }
    }
    public static void createBuffsForMinion(Force force,String action,String buff,String effectValue,String delay,String last,String activationTime){
        Buff tempBuff;
        String[] actions = action.split(",");
        String[] buffs = buff.split(",");
        String[] effectValues = effectValue.split(",");
        String[] delays = delay.split(",");
        String[] lasts = last.split(",");
        for(int i=0; i<actions.length; i++){
            if(actions[i].matches("addAction")){
                tempBuff = new Buff(lasts[i],buffs[i],delays[i],effectValues[i],activationTime);
                force.getBuffActions().add(tempBuff);
            }
            else if(actions[i].matches("addBuff")){
                tempBuff = new Buff(lasts[i],buffs[i],delays[i],effectValues[i],activationTime);
                addBuff(force,tempBuff);
            }
        }
    }
    public static void createBuffsForHero(Force force,String action,String buff,String effectValue,String delay,String last){
        Buff tempBuff;
        String[] actions = action.split(",");
        String[] buffs = buff.split(",");
        String[] effectValues = effectValue.split(",");
        String[] delays = delay.split(",");
        String[] lasts = last.split(",");
        for(int i=0; i<actions.length; i++){
            if(actions[i].matches("addAction")){
                tempBuff = new Buff(lasts[i],buffs[i],delays[i],effectValues[i],"null");
                force.getBuffActions().add(tempBuff);
            }
            else if(actions[i].matches("addBuff")){
                tempBuff = new Buff(lasts[i],buffs[i],delays[i],effectValues[i],"null");
                addBuff(force,tempBuff);
            }
        }
    }

    public static void addBuff(Force force,Buff buff){
        if(isNegative(buff.name)){
            force.getNegativeBuffs().add(buff);
        }
        else{
            force.getPositiveBuffs().add(buff);
        }
    }
    public static boolean isNegative(String name){
        for (String buff: negativeBuffs) {
            if(name.equals(buff)){
                return true;
            }
        }
        return false;
    }
    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(String activationTime) {
        this.activationTime = activationTime;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public String getForHowManyTurns() {
        return forHowManyTurns;
    }

    public void setForHowManyTurns(String forHowManyTurns) {
        this.forHowManyTurns = forHowManyTurns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHowMuchImpact() {
        return howMuchImpact;
    }

    public void setHowMuchImpact(int howMuchImpact) {
        this.howMuchImpact = howMuchImpact;
    }

    public static String getTypeOfBuffByItsName(String buffName){
        if (buffName.equals("healthPointWeaknessBuff") || buffName.equals("disarm") || buffName.equals("poisonBuff")
                || buffName.equals("stunBuff") || buffName.equals("attackPowerWeaknessBuff")){
            return "negative";
        }else
            return "positive";
    }

    public static boolean checkIfBuffIsActive(Buff buff){
        String num = buff.getForHowManyTurns();
        if(!num.matches("null")){
            if(num.matches("[\\d]+")){
                if(Integer.parseInt(num) <=0 ){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    //Todo : in the first of the turn, check if adding mana buff in active or not before invoking this method
    public static void applyAddingManaBuff(Player player, Buff buff){
        if (checkIfBuffIsActive(buff)){
            int numberOfAddingMana = buff.getHowMuchImpact();
            int currentPlayerMana = player.getNumOfMana();
            player.setNumOfMana(currentPlayerMana + numberOfAddingMana);
            if(buff.forHowManyTurns.matches("[\\d]+")) {
                int num = Integer.parseInt(buff.forHowManyTurns);
                buff.setForHowManyTurns(Integer.toString(num-1));
            }
        }else{
            player.setAddingManaBuffIsActive(false);
        }

    }

    public void applyBuffOnForce(Force force){
        String buffName = this.getName();

        switch (buffName){
            case "healthPointWeaknessBuff":
                applyHealthPointWeaknessBuffOnForce(force);
                break;
            case "attackPowerWeaknessBuff":
                applyAttackPowerWeaknessBuffOnForce(force);
                break;
            case "attackPowerBuff":
                applyAttackPowerBuff(force);
                break;
            case "healthPointBuff":
                applyHealthPointBuff(force);
                break;
            case "holyBuff":
                applyHolyBuff(force);
                break;
            case "disarm":
                applyDisarmBuff(force);
                break;
            case "poisonBuff":
                applyPoisonBuff(force);
                break;
            case "stunBuff":
                applyStunBuff(force);
                break;
        }
    }

    public void applyHealthPointWeaknessBuffOnForce(Force force){
        int howMuchImpact = this.howMuchImpact;
        int currentHealthPoint = force.getHealthPoint();
        force.setHealthPoint(currentHealthPoint - howMuchImpact);
        this.isUsed = true;
    }

    public void applyAttackPowerWeaknessBuffOnForce(Force force){
        int howMuchImpact = this.howMuchImpact;
        int currentAttackPower = force.getAttackPower();
        force.setAttackPower(currentAttackPower - howMuchImpact);
        this.isUsed = true;
    }

    public void applyAttackPowerBuff(Force force){
        int howMuchImpact = this.howMuchImpact;
        int currentAttackPower = force.getAttackPower();
        force.setAttackPower(currentAttackPower + howMuchImpact);
        this.isUsed = true;
    }

    public void applyHealthPointBuff(Force force){
        int howMuchImpact = this.howMuchImpact;
        int currentHealthPoint = force.getHealthPoint();
        force.setHealthPoint(currentHealthPoint - howMuchImpact);
        this.isUsed = true;
    }

    public void applyHolyBuff(Force force){
        force.setHasHolyBuff(true);
    }

    public void applyDisarmBuff(Force force){
        force.setCanCounterAttack(false);
    }

    public void applyPoisonBuff(Force force){
        int howMuchImpact = this.howMuchImpact;
        int currentHealthPoint = force.getHealthPoint();
        force.setHealthPoint(currentHealthPoint - howMuchImpact);
        this.isUsed = true;
    }

    public void applyStunBuff(Force force){
        force.setCanAttack(false);
        force.setCanMove(false);
    }

}

