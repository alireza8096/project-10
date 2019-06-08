package model.collection;

import model.Player;
import org.json.simple.JSONObject;

import java.util.ArrayList;

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
        if(!howMuchImpact.equals("null")) this.howMuchImpact = Integer.parseInt(howMuchImpact);
        else this.howMuchImpact = 0;
        this.activationTime = activationTime;
        if(isNegative(name)){
            this.type = "negative";
        }
        else{
            this.type = "positive";
        }
    }

    public static void createBuffs(Force force,String action,String buff,String effectValue,String delay,String last){
        Buff tempBuff;
        String[] actions = action.split(",");
        String[] buffs = buff.split(",");
        String[] effectValues = effectValue.split(",");
        String[] delays = delay.split(",");
        String[] lasts = last.split(",");
        for(int i=0; i<action.length(); i++){
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

//    public static boolean checkIfAddingManaBuffIsActiveYet(Buff buff){
//        String num = buff.forHowManyTurns;
//        int howManyTurns = buff.getForHowManyTurns();
//        if (howManyTurns > 0){
//            return true;
//        }
//        return false;
//    }
}

