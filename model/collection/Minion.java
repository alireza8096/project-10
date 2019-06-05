package model.collection;

import model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Minion extends Card {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

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
    private String specialPower;

    public String getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(String specialPower) {
        this.specialPower = specialPower;
    }

    public Minion(String name, int healthPoint, int attackPower, int attackRange, String attackType, String activationTime, int mana, int price, String specialPower){
        this.healthPoint = healthPoint;
        this.setName(name);
        this.attackPower = attackPower;
        this.attackRange = attackRange;
        this.attackType = attackType;
        this.activationTime = activationTime;
        this.mana = mana;
        this.price = price;
        this.specialPower = specialPower;
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

                Minion minion = new Minion(name, healthPoint, attackPower, attackRange, attackType, activationTime, mana, price, specialPower);
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
        ArrayList<Buff> positiveBuffsCopy = new ArrayList<>(this.getMinionPositiveBuffs());
        for (Buff buff : positiveBuffsCopy){
            if (Buff.checkIfBuffIsActive(buff))
                this.applyBuffOnMinionForOneTurn(buff);
            else
                this.removeBuffFromMinion(buff);
        }

        ArrayList<Buff> negativeBuffsCopy = new ArrayList<>(this.getMinionNegativeBuffs());
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
        for(Buff buff: this.getMinionNegativeBuffs())
        {
            if(buff.getActivationTime().equals("On Attack"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getMinionPositiveBuffs())
        {
            if(buff.getActivationTime().equals("On Attack"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
    }
    public void applyOnSpawnBuffs()
    {
        for(Buff buff: this.getMinionNegativeBuffs())
        {
            if(buff.getActivationTime().equals("On Spawn"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getMinionPositiveBuffs())
        {
            if(buff.getActivationTime().equals("On Spawn"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
    }
    public void applyOnDeathBuffs()
    {
        for(Buff buff: this.getMinionNegativeBuffs())
        {
            if(buff.getActivationTime().equals("On Death"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getMinionPositiveBuffs())
        {
            if(buff.getActivationTime().equals("On Death"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
    }
    public void applyPassiveBuffs()
    {
        for(Buff buff: this.getMinionNegativeBuffs())
        {
            if(buff.getActivationTime().equals("Passvive"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getMinionPositiveBuffs())
        {
            if(buff.getActivationTime().equals("Passive"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
    }

    public void applyOnDefendBuffs()
    {
        for(Buff buff: this.getMinionNegativeBuffs())
        {
            if(buff.getActivationTime().equals("On Defend"))
            {
                this.applyBuffOnMinionForOneTurn(buff);
            }
        }
        for(Buff buff: this.getMinionPositiveBuffs())
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
                this.getMinionPositiveBuffs().add(buff);
                break;
        }
    }

    public static Minion getMinionInThisCoordination(int x, int y){
        Map map = Game.getInstance().getMap();
        for (Card card : map.getMinions()){
            if (card.getX() == x && card.getY() == y){
                return (Minion) card;
            }
        }
        return null;

    }

    public void addSpecialPowerToBuffs(ArrayList<String> minionNames) throws IOException, ParseException {
        for(String minionName:minionNames)
        {
            JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles
                    (ADDRESS_OF_JSON_FILES + "JSON-Minions/" +minionName+".json");
            int howMuchImpact=Integer.parseInt(jsonObject.get("howMuchChange").toString());
            int forHowManyTurns=Integer.parseInt(jsonObject.get("forHowManyTurns").toString());
            String name=jsonObject.get("whichBuff").toString();
            String activationTime=jsonObject.get("activationTime").toString();
            String type=Buff.getTypeOfBuffByItsName("name");
            if(type.equals("negative"))
            {
                Buff buff=new Buff(howMuchImpact,forHowManyTurns,name,type,activationTime);
                ((Minion) getMinionByName(name)).getMinionNegativeBuffs().add(buff);
            }
            if(type.equals("positive"))
            {
                Buff buff=new Buff(howMuchImpact,forHowManyTurns,name,type,activationTime);
                ((Minion) getMinionByName(name)).getMinionPositiveBuffs().add(buff);
            }
        }
    }

    public void applySpceialPower(String minionName) throws IOException, ParseException {
        JSONObject jsonObject=(JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES+"JSON-Minions/"+minionName+".json");
        String[] buffNames = jsonObject.get("whichBuff").toString().split(",");
        String[] forHowManyTurns = jsonObject.get("forHowManyTurns").toString().split(",");
        String[] typeOfAction = jsonObject.get("typeOfAction").toString().split(",");
        String[] howMuchChange = jsonObject.get("howMuchChange").toString().split(",");

        for (int i = 0; i < buffNames.length; i++) {
            Buff buff = new Buff(Integer.parseInt(howMuchChange[i]), Integer.parseInt(forHowManyTurns[i]),
                    buffNames[i], Buff.getTypeOfBuffByItsName(buffNames[i]));

            if (Buff.getTypeOfBuffByItsName(buffNames[i]).equals("positive")) {
                Minion.getMinionInThisCoordination(x, y).applyBuffOnMinionForOneTurn(buff);
            } else {
                Minion.getMinionInThisCoordination(x, y).applyBuffOnMinionForOneTurn(buff);
            }
        }

        String targetsSpecified=jsonObject.get("targetsSpecified").toString();
        String actsOn=jsonObject.get("actsOn").toString();
//        if(actsOn.equals("hero") && )
    }

    public boolean ifMinionHasComboAttack(String minionName) throws IOException, ParseException {
        JSONObject jsonObject=(JSONObject) HandleFiles.readJsonFiles
                (ADDRESS_OF_JSON_FILES+"JSON-Minions/"+minionName+".json");
        String specialPower=jsonObject.get("specialPower").toString();
        if(specialPower.equals("combo"))
        {
            return true;
        }
        return false;
    }

    public void removeBuffFromBuffArrayListOfMinion(String buffName){
        if (Buff.getTypeOfBuffByItsName(buffName).equals("positive")){
            for (Buff buff : this.getMinionPositiveBuffs())
                if (buff.getName().equals(buffName)){
                    this.getMinionPositiveBuffs().remove(buff);
                    return ;
                }
        }else{
            for (Buff buff : this.getMinionNegativeBuffs())
                if (buff.getName().equals(buffName)){
                    this.getMinionNegativeBuffs().remove(buff);
                    return;
                }
        }
    }

    public void insertSpecialPowerInThisLocation(String minionName) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
                + "JSON-Minions/" + minionName + ".json");
        CellType cellType = Game.getInstance().getMap().getCells()[x][y].getCellType();
        switch(cellType)
        {
            case selfHero:
                insertSpecialPowerInCellTypeSelfHero(jsonObject, x, y);
                break;
            case selfMinion:
                insertSpecialPowerInCellTypeSelfMinion(jsonObject, x, y);
                break;
            case enemyHero:
                insertSpecialPowerInCellTypeEnemyHero(jsonObject, x, y);
                break;
            case enemyMinion:
                insertSpecialPowerInCellTypeEnemyMinion(jsonObject, x, y);
                break;
            case empty:
                insertSpecialPowerInAnEmptyCell(jsonObject, x, y);
                break;
        }
    }

    public void insertSpecialPowerInAnEmptyCell(JSONObject jsonObject,int x,int y)
    {
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        switch (numOfTargets){
            case "1":
                System.out.println("Invalid target!");
                break;
            case "all":
                System.out.println("Invalid target!");
                break;

        }
    }

    public void insertSpecialPowerInCellTypeEnemyMinion(JSONObject jsonObject,int x,int y)
    {
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        String actsOn = jsonObject.get("actsOn").toString();
        String locationOfTarget = jsonObject.get("locationOfTarget").toString();
        switch (numOfTargets){
            case "1":
                if (actsOn.equals("owner")){
                    System.out.println("Invalid target!");
                }else{
                    if (locationOfTarget.equals("random8around")){
                        applySpecialPowerOnMinionsIn8Round(jsonObject, x, y);
                    }else{
                        applySpecialPowerOnMinion(jsonObject, x, y);
                    }
                }
                break;
            case "all":
                if (locationOfTarget.equals("null")){//when target is all enemy forces
                    applySpecialPowerOnAllEnemyForces(jsonObject, x, y);
                }
                break;

        }
    }
    
    public static void applySpecialPowerOnMinion(JSONObject jsonObject, int x, int y)
    {
        Spell.applySpellOnMinion(jsonObject,x,y);
    }

    public static void applySpecialPowerOnMinionsIn8Round(JSONObject jsonObject, int x, int y){

        for(int i=x-1;i<=x+1;i++)
        {
            for(int j=y-1;j<y+1;j++)
            {
                applySpecialPowerOnMinion(jsonObject,i,j);
            }
        }
    }

    public static boolean checkIfThisCoordinationIsAroundSelfMinion(int x, int y){
        int selfHeroX = Game.getInstance().getHeroOfPlayer1().getX();
        int selfHeroY = Game.getInstance().getHeroOfPlayer1().getY();

        if (Map.thisCellsAreAdjusting(x, y, selfHeroX, selfHeroY)){
            return true;
        }
        return false;
    }

    public void insertSpecialPowerInCellTypeEnemyHero(JSONObject jsonObject,int x,int y)
    {
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        String actsOn = jsonObject.get("actsOn").toString();
        String locationOfTarget = jsonObject.get("locationOfTarget").toString();

        switch (numOfTargets) {
            case "1":
                if (actsOn.equals("owner")) {
                    System.out.println("Invalid target!");
                } else {
                    applySpecialPowerOnEnemyHero(jsonObject, x, y);
                }
                break;
            case "all":
                if (locationOfTarget.equals("null")) {
                    applySpecialPowerOnAllEnemyForces(jsonObject, x, y);
                    break;
                }
        }
    }

    public static void applySpecialPowerOnEnemyHero (JSONObject jsonObject,int x,int y){
        Spell.applySpellOnEnemyHero(jsonObject, x, y);
    }

    public static void applySpecialPowerOnAllEnemyForces(JSONObject jsonObject,int x,int y)
    {
        Spell.applySpellOnAllEnemyForces(jsonObject,x,y);
    }

    public static void insertSpecialPowerInCellTypeSelfMinion(JSONObject jsonObject,int x,int y)
    {
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        String actsOn = jsonObject.get("actsOn").toString();

        switch (numOfTargets){
            case "1":
                if (actsOn.equals("map")){
                    System.out.println("Invalid target!");
                }else{
                    applySpecialPowerOnMinion(jsonObject, x, y);
                }
                break;
            case "all":
                if (!actsOn.equals("owner")){
                    System.out.println("Invalid target!");
                }else{
                    applySpecialPowerOnAllSelfForces(jsonObject, x, y);
                }
                break;
        }
    }

    public static void insertSpecialPowerInCellTypeSelfHero(JSONObject jsonObject,int x,int y)
    {
        String numOfTargets = jsonObject.get("numOfTargets").toString();
        String actsOn = jsonObject.get("actsOn").toString();
        String locationOfTarget = jsonObject.get("locationOfTarget").toString();

        switch (numOfTargets){
            case "1":
                if (actsOn.equals("map") || actsOn.equals("enemy")){
                    System.out.println("Invalid target!");
                }else{
                    applySpecialPowerOnSelfHero(jsonObject, x, y);
                }
                break;
            case "all":
                if (!locationOfTarget.equals("null")){//when target is all self forces
                    applySpecialPowerOnAllSelfForces(jsonObject, x, y);
                }
                break;

        }
    }

    public static void applySpecialPowerOnSelfHero(JSONObject jsonObject,int x,int y)
    {
        Spell.applySpellOnSelfHero(jsonObject,x,y);
    }

    public static void applySpecialPowerOnAllSelfForces(JSONObject jsonObject,int x,int y)
    {
        Spell.applySpellOnAllSelfForces(jsonObject,x,y);
    }



}
