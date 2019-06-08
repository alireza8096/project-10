package model.collection;

import model.Player;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Buff {
    public static final String[] negativeBuffs = {"healthPointWeaknessBuff","disarm","poisonBuff","fireCellImpact","poisonCellImpact","stunBuff","attackPowerWeaknessBuff","antiHolyBuff"};
    private int forHowManyTurns;
    private String name;
    private String type;
    private int delay;
    private int howMuchImpact;
    private boolean isUsed;
    private String activationTime;

    public Buff(String forHowManyTurns, String name, String type, String delay, String howMuchImpact,String activationTime) {
        if (!forHowManyTurns.equals("null"))this.forHowManyTurns = Integer.parseInt(forHowManyTurns);
        else this.forHowManyTurns = 0;
        this.name = name;
        this.type = type;
        if(!delay.equals("null")) this.delay = Integer.parseInt(delay);
        else this.delay = 0;
        if(!howMuchImpact.equals("null")) this.howMuchImpact = Integer.parseInt(howMuchImpact);
        else this.howMuchImpact = 0;
        this.activationTime = activationTime;
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

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getForHowManyTurns() {
        return forHowManyTurns;
    }

    public void setForHowManyTurns(int forHowManyTurns) {
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
        int numberOfTurns = buff.getForHowManyTurns();
        if (numberOfTurns > 0){
            return true;
        }else{
            return false;
        }
    }

    //Todo : in the first of the turn, check if adding mana buff in active or not before invoking this method
    public static void applyAddingManaBuff(Player player, Buff buff){
        if (checkIfAddingManaBuffIsActiveYet(buff)){
            int numberOfAddingMana = buff.getHowMuchImpact();
            int currentPlayerMana = player.getNumOfMana();
            player.setNumOfMana(currentPlayerMana + numberOfAddingMana);
            buff.setForHowManyTurns(buff.getForHowManyTurns() - 1);
        }else{
            player.setAddingManaBuffIsActive(false);
        }

    }

    public static boolean checkIfAddingManaBuffIsActiveYet(Buff buff){
        int howManyTurns = buff.getForHowManyTurns();
        if (howManyTurns > 0){
            return true;
        }
        return false;
    }
}

