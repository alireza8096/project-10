package model.collection;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Minion extends Card {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/hamilamailee/Documents/Duelyst Project/model/collection";

    public static ArrayList<String> minionNames = new ArrayList<>();
    private ArrayList<Buff> minionPositiveBuffs=new ArrayList<>();
    private ArrayList<Buff> minionNegativeBuffs=new ArrayList<>();
    private int healthPoint;
    private int attackPower;
    private String attackType;
    private int attackRange;
    private String activationTime;
    private boolean canAttackOrMove;
    private boolean canCounterAttack;
    private boolean hasHolyBuff;

    public Minion(String name, int healthPoint, int attackPower, int attackRange, String attackType, String activationTime){
        this.healthPoint = healthPoint;
        this.setName(name);
        this.attackPower = attackPower;
        this.attackRange = attackRange;
        this.attackType = attackType;
        this.activationTime = activationTime;
    }

    public ArrayList<Buff> getMinionPositiveBuffs() {
        return minionPositiveBuffs;
    }

    public boolean isHasHolyBuff() {
        return hasHolyBuff;
    }

    public void setHasHolyBuff(boolean hasHolyBuff) {
        this.hasHolyBuff = hasHolyBuff;
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

    public void setMinionPositiveBuffs(ArrayList<Buff> minionPositiveBuffs) {
        this.minionPositiveBuffs = minionPositiveBuffs;
    }

    public ArrayList<Buff> getMinionNegativeBuffs() {
        return minionNegativeBuffs;
    }

    public void setMinionNegativeBuffs(ArrayList<Buff> minionNegativeBuffs) {
        this.minionNegativeBuffs = minionNegativeBuffs;
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

    public String getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(String activationTime) {
        this.activationTime = activationTime;
    }

    public static String findMinionNameByID(int id) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Minions");
        File[] listOfFiles = folder.listFiles();
        JSONObject jsonObject;
        String idString;
        int value;
        String fileName;
        for (int i = 0; i < listOfFiles.length; i++) {
            fileName = listOfFiles[i].getName();
            jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Minions/" + fileName);
            idString = jsonObject.get("id").toString();
            value = Integer.parseInt(idString);
            if (value == id){
                return (String) jsonObject.get("name");
            }
        }
        return null;
    }

//    public Minion(String name, int healthPoint, int attackPower, int attackRange, String attackType, String activationTime){
//        this.healthPoint = healthPoint;
//        this.setName(name);
//        this.attackPower = attackPower;
//        this.attackRange = attackRange;
//        this.attackType = attackType;
//        this.activationTime = activationTime;
//    }

    public static Card getMinionByName(String minionName) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Minions");
        File[] listOfFiles = folder.listFiles();
        String fileName;
        JSONObject jsonObject;
        for (int i = 0; i < listOfFiles.length; i++) {
            fileName = listOfFiles[i].getName().split("\\.")[0];
            if (fileName.equals(minionName)){
                jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Minions/" + fileName + ".json");
                String name = jsonObject.get("name").toString();
                String healthPointString = jsonObject.get("healthPoint").toString();
                int healthPoint = Integer.parseInt(healthPointString);
                String attackPowerString = jsonObject.get("attackPower").toString();
                int attackPower = Integer.parseInt(attackPowerString);
                String attackRangeString = jsonObject.get("attackRange").toString();
                int attackRange = Integer.parseInt(attackRangeString);
                String attackType = jsonObject.get("attackType").toString();
                String activationTime = jsonObject.get("activationTime").toString();

                Minion minion = new Minion(name, healthPoint, attackPower, attackRange, attackType, activationTime);
                return minion;
            }
        }
        return null;
    }

    public static boolean thisCardIsMinion(String cardName){
        for (String name : minionNames){
            if (name.equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public void applyBuffsOnMinion(){
        for (Buff buff : this.getMinionPositiveBuffs()){
            this.applyBuffOnMinion(buff);
        }

        for (Buff buff : this.getMinionNegativeBuffs()){
            this.applyBuffOnMinion(buff);
        }
    }
    public void applyBuffOnMinion(Buff buff)
    {
        String buffName = buff.getName();
        if(buffName.equals("holyBuff"))
        {
            this.applyHolyBuff(buff);
        }
        else if(buffName.equals("attackPowerBuff"))
        {
            this.applyAttackPowerBuff(buff);
        }
        else if(buffName.equals("healthPowerBuff"))
        {
            this.applyHealthPowerBuff(buff);
        }
        else if(buffName.equals("poisonBuff"))
        {
            this.applyPoisonBuff(buff);
        }
        else if(buffName.equals("stunBuff"))
        {
            this.applyStunBuff(buff);
        }
        else if(buffName.equals("disarmBuff"))
        {
            this.applyDisarmBuff(buff);
        }
        else if(buffName.equals("healthPointWeaknessBuff"))
        {
            this.applyHealthPointWeaknessBuff(buff);
        }
        else if(buffName.equals("powerAttackWeaknessBuff"))
        {
            this.applyPowerAttackWeaknessBuff(buff);
        }
    }

    public void applyPowerAttackWeaknessBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentAttackPower = this.getAttackPower();
        setAttackPower(currentAttackPower - howMuchImpact);
    }

    public void applyHealthPointWeaknessBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentHealthPoint = this.getHealthPoint();
        setHealthPoint(currentHealthPoint - howMuchImpact);
    }

    public void applyDisarmBuff(Buff buff) {
        this.setCanCounterAttack(false);
    }

    public void applyStunBuff(Buff buff) {
        this.setCanAttackOrMove(false);
    }

    public void applyPoisonBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentHealthPoint = this.getHealthPoint();
        setHealthPoint(currentHealthPoint - howMuchImpact);
    }

    public void applyHealthPowerBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentHealthPoint = this.getHealthPoint();
        setHealthPoint(currentHealthPoint + howMuchImpact);
    }

    public void applyAttackPowerBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentAttackPower = this.getAttackPower();
        setAttackPower(currentAttackPower + howMuchImpact);
    }

    public void applyHolyBuff(Buff buff) {
        setHasHolyBuff(true);
    }

    public void applyBuffs(ArrayList<Buff> buffs,Minion minion)
    {
        for(Buff buff: buffs)
        {
             applyBuff(buff,minion);
        }
    }
}
