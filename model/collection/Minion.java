package model.collection;

import model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Minion extends Force {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

    public static ArrayList<Minion> minions = new ArrayList<>();

    public static ArrayList<String> minionNames = new ArrayList<>();

    private ArrayList<String> doesNotGetAttackBuffs = new ArrayList<>();

    private ArrayList<String> targets = new ArrayList<>();
    private ArrayList<String> numOfTargets = new ArrayList<>();
    private ArrayList<String> friendOrEnemy = new ArrayList<>();
    private ArrayList<String> locationOfTargets = new ArrayList<>();
    private String doesNotGetAttack = new String();
    private String activationTime;
    private boolean isHeadOfCombo;

    public Minion(String name, int healthPoint, int attackPower, String attackType,
                  int attackRange, String activationTime, int mana, int price, String specialPower){
        super(healthPoint, attackPower, attackType, attackRange, specialPower);

        this.setName(name);
        this.activationTime = activationTime;
        this.mana = mana;
        this.price = price;
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

    public ArrayList<String> getDoesNotGetAttackBuffs() {
        return doesNotGetAttackBuffs;
    }

    public void setDoesNotGetAttackBuffs(ArrayList<String> doesNotGetAttackBuffs) {
        this.doesNotGetAttackBuffs = doesNotGetAttackBuffs;
    }

    public String getDoesNotGetAttack() {
        return doesNotGetAttack;
    }

    public void setDoesNotGetAttack(String doesNotGetAttack) {
        this.doesNotGetAttack = doesNotGetAttack;
    }

    public static ArrayList<String> getMinionNames() {
        return minionNames;
    }

    public static void setMinionNames(ArrayList<String> minionNames) {
        Minion.minionNames = minionNames;
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
                String manaString = jsonObject.get("price").toString();
                int mana = Integer.parseInt(manaString);
                String priceString = jsonObject.get("price").toString();
                int price = Integer.parseInt(priceString);

                String specialPower = jsonObject.get("specialPower").toString();

                Minion minion = new Minion(name, healthPoint, attackPower, attackType, attackRange, activationTime, mana, price, specialPower);
                return minion;
            }
        }
        return null;
    }

    public static int getMinionIDByName(String minionName) throws Exception{
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
                + "JSON-Minions/" + minionName + ".json");
        return Integer.parseInt(jsonObject.get("id").toString())+300;
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
        ArrayList<Buff> positiveBuffsCopy = new ArrayList<>(this.getPositiveBuffs());
        for (Buff buff : positiveBuffsCopy){
            if (Buff.checkIfBuffIsActive(buff))
                this.applyBuffOnMinionForOneTurn(buff);
            else
                this.removeBuffFromMinion(buff);
        }

        ArrayList<Buff> negativeBuffsCopy = new ArrayList<>(this.getNegativeBuffs());
        for (Buff buff : negativeBuffsCopy){
            if (Buff.checkIfBuffIsActive(buff))
                this.applyBuffOnMinionForOneTurn(buff);
            else
                this.removeBuffFromMinion(buff);
        }
    }

    public void applyBuffOnMinionForOneTurn(Buff buff)
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

    public void applyOnAttackBuffs()
    {
        for(Buff buff: this.getNegativeBuffs())
        {
            if(buff.getActivationTime().equals("On Attack"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getPositiveBuffs())
        {
            if(buff.getActivationTime().equals("On Attack"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
    }
    public void applyOnSpawnBuffs()
    {
        for(Buff buff: this.getNegativeBuffs())
        {
            if(buff.getActivationTime().equals("On Spawn"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getPositiveBuffs())
        {
            if(buff.getActivationTime().equals("On Spawn"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
    }
    public void applyOnDeathBuffs()
    {
        for(Buff buff: this.getNegativeBuffs())
        {
            if(buff.getActivationTime().equals("On Death"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getPositiveBuffs())
        {
            if(buff.getActivationTime().equals("On Death"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
    }
    public void applyPassiveBuffs()
    {
        for(Buff buff: this.getNegativeBuffs())
        {
            if(buff.getActivationTime().equals("Passvive"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getPositiveBuffs())
        {
            if(buff.getActivationTime().equals("Passive"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
    }

    public void applyOnDefendBuffs()
    {
        for(Buff buff: this.getNegativeBuffs())
        {
            if(buff.getActivationTime().equals("On Defend"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getPositiveBuffs())
        {
            if(buff.getActivationTime().equals("On Defend"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
    }
    public void removeBuffFromMinion(Buff buff){
        String buffName = buff.getName();
        if(buffName.equals("attackPowerWeaknessBuff"))
        {
            this.removeAttackPowerWeaknessBuff(buff);
        }
        else if(buffName.equals("disarmBuff"))
        {
            this.removeDisarmBuff(buff);
        }
        else if(buffName.equals("stunBuff"))
        {
            this.removeStunBuff(buff);
        }
        else if(buffName.equals("attackPowerBuff"))
        {
            this.removeAttackPowerBuff(buff);
        }
        else if(buffName.equals("holyBuff"))
        {
            this.removeHolyBuff(buff);
        }
    }
    public void applyPowerAttackWeaknessBuff(Buff buff) {
        setAttackPower(this.getAttackPower() - buff.getHowMuchImpact());
    }

    public void applyHealthPointWeaknessBuff(Buff buff) {
        setHealthPoint(this.getHealthPoint() - buff.getHowMuchImpact());
    }

    public void applyDisarmBuff(Buff buff) {
        this.setCanCounterAttack(false);
    }

    public void applyStunBuff(Buff buff) {
        this.setCanAttackOrMove(false);
    }

    public void applyPoisonBuff(Buff buff) {
        setHealthPoint(this.getHealthPoint() - buff.getHowMuchImpact());
    }

    public void applyHealthPowerBuff(Buff buff) {
        setHealthPoint(this.getHealthPoint() + buff.getHowMuchImpact());
    }

    public void applyAttackPowerBuff(Buff buff) {
        setAttackPower(this.getAttackPower() + buff.getHowMuchImpact());
    }

    public void applyHolyBuff(Buff buff) {
        setHasHolyBuff(true);
    }

    public void removeAttackPowerBuff(Buff buff)
    {
        setAttackPower(this.getAttackPower() - buff.getHowMuchImpact());
    }

    public void removeAttackPowerWeaknessBuff(Buff buff)
    {
        setAttackPower(this.getAttackPower() + buff.getHowMuchImpact());
    }

    public void removeDisarmBuff(Buff buff)
    {
        this.setCanAttackOrMove(true);
    }

    public void removeStunBuff(Buff buff)
    {
        this.setCanCounterAttack(true);
    }

    public void removeHolyBuff(Buff buff)
    {
        setHasHolyBuff(false);
    }

    public void applyCellImpact(Minion minion, Map map)
    {
        int x=minion.getX();
        int y=minion.getY();
        switch (((map.getCells())[x][y]).getCellImpactType())
        {
            case fire:
                this.setHealthPoint(this.getHealthPoint()-2);
                break;
            case holy:
                this.setHasHolyBuff(true);
                break;
            case poison:
                Buff buff = new Buff(1,3,"poisonBuff","negative");
                this.getPositiveBuffs().add(buff);
                break;
        }
    }

    public static Minion getMinionInThisCoordination(int x, int y){
        Map map = Game.getInstance().getMap();
        for (Card card : map.getFriendMinions()){
            if (card.getX() == x && card.getY() == y){
                return (Minion) card;
            }
        }
        for (Card card : map.getEnemyMinions()){
            if (card.getX() == x && card.getY() == y)
                return (Minion) card;
        }
        return null;

    }

    public void addBuffToMinion(Buff buff){
        if (buff.getType().equals("positive")){
            getPositiveBuffs().add(buff);
        }else{
            getNegativeBuffs().add(buff);
        }
    }

    public void applyMinionSpecialPowerOnSpawn(){
        //target : minion
        for (String numOfTarget : this.numOfTargets){
            if (numOfTarget.equals("all")){

                //in this minion location of target is 8 round;
                applyMinionOnAllMinionsIn8RoundCells();
            }else{
                String locationOfTarget = this.getLocationOfTargets().get(0);
                if (locationOfTarget.equals("distance2")){
                    applyMinionOnMinionsWithMax2CellDistance();
                }else if (locationOfTarget.equals("random")){
                    applyMinionOnARandomMinion();
                }
            }
        }

    }

    public void applyMinionOnARandomMinion(){
        Buff buff = this.getBuffActions().get(0);

        Force.getRandomEnemyMinion().getActionBuffsOnItself().add(buff);
    }

    public void applyMinionOnMinionsWithMax2CellDistance(){
        Buff antiHolyBuff = this.getBuffActions().get(0);

        for (Minion enemyMinion : Game.getInstance().getMap().getEnemyMinions()){
            //Todo : check if contains works correctly here!
            if (Map.distance(this.x, this.y, enemyMinion.x, enemyMinion.y) <= 2){
                if (!enemyMinion.getActionBuffsOnItself().contains(antiHolyBuff)){
                    enemyMinion.getActionBuffsOnItself().add(antiHolyBuff);
                }
            }
        }
    }

    public void applyMinionOnAllMinionsIn8RoundCells(){
        //Todo : check if coordination are implemented right
        int minionX  = this.x;
        int minionY = this.y;

        for (Buff buff : this.getNegativeBuffs()) {
            if (Game.getInstance().getMap().getCells()[minionX - 1][minionY - 1].getCellType().equals(CellType.enemyMinion)) {
                Minion.getMinionInThisCoordination(minionX - 1, minionY - 1).getNegativeBuffsOnItself().add(buff);
            } else if (Game.getInstance().getMap().getCells()[minionX - 1][minionY].getCellType().equals(CellType.enemyMinion)) {
                Minion.getMinionInThisCoordination(minionX - 1, minionY).getNegativeBuffsOnItself().add(buff);
            } else if (Game.getInstance().getMap().getCells()[minionX - 1][minionY + 1].getCellType().equals(CellType.enemyMinion)) {
                Minion.getMinionInThisCoordination(minionX - 1, minionY + 1).getNegativeBuffsOnItself().add(buff);
            } else if (Game.getInstance().getMap().getCells()[minionX][minionY - 1].getCellType().equals(CellType.enemyMinion)) {
                Minion.getMinionInThisCoordination(minionX, minionY - 1).getNegativeBuffsOnItself().add(buff);
            } else if (Game.getInstance().getMap().getCells()[minionX][minionY + 1].getCellType().equals(CellType.enemyMinion)) {
                Minion.getMinionInThisCoordination(minionX, minionY + 1).getNegativeBuffsOnItself().add(buff);
            } else if (Game.getInstance().getMap().getCells()[minionX + 1][minionY - 1].getCellType().equals(CellType.enemyMinion)) {
                Minion.getMinionInThisCoordination(minionX +  1, minionY - 1).getNegativeBuffsOnItself().add(buff);
            } else if (Game.getInstance().getMap().getCells()[minionX + 1][minionY].getCellType().equals(CellType.enemyMinion)) {
                Minion.getMinionInThisCoordination(minionX + 1, minionY).getNegativeBuffsOnItself().add(buff);
            } else if (Game.getInstance().getMap().getCells()[minionX + 1][minionY + 1].getCellType().equals(CellType.enemyMinion)) {
                Minion.getMinionInThisCoordination(minionX + 1, minionY + 1).getNegativeBuffsOnItself().add(buff);
            }
        }
    }

    public void applyMinionSpecialPowerOnAttack(int x, int y){
        ArrayList<String> targets = this.targets;

        for (String target : targets){
            switch (target){
                case "itself":
                    break;
                case "minion":
                    break;
                case "minion/hero":
                    break;
            }
        }
    }

//    public void applyMinionSpecialPower(){
//        ArrayList<String> targets = this.targets;
//
//        for (String target : targets){
//            switch (target){
//                case "null":
//                    applyMinionWithNullTarget();
//                    break;
//                case "itself":
//                    applyMinionOnItself();
//                    break;
//
//            }
//        }
//    }
//
//    public void applyMinionSpecialPowerOnItself(){
//
//        for (Buff buff : this.getPositiveBuffs()){
//            this.getPositiveBuffsOnItself().add(buff);
//        }
//
//        for (Buff buff : this.getNegativeBuffs()){
//            this.getNegativeBuffsOnItself().add(buff);
//        }
//
//        for (Buff buff : this.getBuffActions()){
//            this.getActionBuffsOnItself().add(buff);
//        }
//    }
//
//    public void applyMinionWithNullTarget(){
//        this.doesNotGetAttackBuffs.add(this.doesNotGetAttack);
//    }
//
//    public void minionComboAttack(){
//        this.isHeadOfCombo = true;
//        for (Minion minion : Game.getInstance().getMap().getFriendMinions()){
//
//        }
//    }



}

