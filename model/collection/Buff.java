package model.collection;

import model.Player;
import org.json.simple.JSONObject;

public class Buff {
    private int forHowManyTurns;
    private String name;
    private String type;
    private int delay;
    private int howMuchImpact;
    private boolean isUsed;
    private String activationTime;


    public Buff(String name, int howMuchImpact, int delay, int forHowManyTurns){
        this.name = name;
        this.howMuchImpact = howMuchImpact;
        this.delay = delay;
        this.forHowManyTurns = forHowManyTurns;
    }

    public Buff(String name, int howMuchImpact, int forHowManyTurns){
        this.name = name;
        this.howMuchImpact = howMuchImpact;
        this.forHowManyTurns = forHowManyTurns;
    }

    public Buff(int howMuchImpact, int forHowManyTurns, String name, String type){
        this.howMuchImpact = howMuchImpact;
        this.forHowManyTurns = forHowManyTurns;
        this.name = name;
        this.type = type;
    }
    public Buff(int howMuchImpact,int forHowManyTurns,String name,String type,String activationTime)
    {
        this.howMuchImpact=howMuchImpact;
        this.forHowManyTurns=forHowManyTurns;
        this.name=name;
        this.type=type;
        this.activationTime=activationTime;
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

