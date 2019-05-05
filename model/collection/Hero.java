package model.collection;

import model.Game;
import model.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Hero extends Card{
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

    private ArrayList<Buff> positiveBuffs = new ArrayList<>();
    private ArrayList<Buff> negativeBuffs = new ArrayList<>();

    public static ArrayList<String> heroNames = new ArrayList<>();
    private int healthPoint;
    private int attackPower;
    private String attackType;
    private int attackRange;
    private int coolDown;
    private String specialPower;
    private boolean canAttackOrMove;
    private boolean canCounterAttack;
    private boolean holyBuffIsActive;

    public Hero(String name, int healthPoint, int attackPower, String attackType, int attackRange, int coolDown,String specialPower){
        this.name = name;
        this.healthPoint = healthPoint;
        this.attackPower = attackPower;
        this.attackType = attackType;
        this.attackRange = attackRange;
        this.coolDown = coolDown;
        this.specialPower = specialPower;
    }

    public static int getHeroIDByName(String heroName) throws Exception{
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
                + "JSON-Heroes/" + heroName + ".json");
        return Integer.parseInt(jsonObject.get("id").toString()) + 100;
    }

    public boolean isHolyBuffIsActive() {
        return holyBuffIsActive;
    }

    public void setHolyBuffIsActive(boolean holyBuffIsActive) {
        this.holyBuffIsActive = holyBuffIsActive;
    }

    public boolean isCanCounterAttack() {
        return canCounterAttack;
    }

    public void setCanCounterAttack(boolean canCounterAttack) {
        this.canCounterAttack = canCounterAttack;
    }

    public boolean isCanAttackOrMove() {
        return canAttackOrMove;
    }

    public void setCanAttackOrMove(boolean canAttackOrMove) {
        this.canAttackOrMove = canAttackOrMove;
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

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public static ArrayList<String> getHeroNames() {
        return heroNames;
    }

    public static void setHeroNames(ArrayList<String> heroNames) {
        Hero.heroNames = heroNames;
    }

    public static String findHeroNameByID(int id) throws IOException, ParseException {
        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Heroes");
        File[] listOfFiles = folder.listFiles();
        JSONObject jsonObject;
        String idString;
        int value;
        String fileName;
        for (int i = 0; i < listOfFiles.length; i++) {
            fileName = listOfFiles[i].getName();
            jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Heroes/" + fileName);
            idString = jsonObject.get("id").toString();
            value = Integer.parseInt(idString);
            if (value == id){
                return (String) jsonObject.get("name");
            }
        }
        return null;
    }

    public static boolean thisCardIsHero(String cardName){
        for (String name : heroNames){
            if (name.equals(cardName))
                return true;
        }
        return false;
    }

    public void applyBuffsOnHero(){
        ArrayList<Buff> positiveBuffsCopy = new ArrayList<>(this.getPositiveBuffs());
        for (Buff buff : positiveBuffsCopy){
            if (Buff.checkIfBuffIsActive(buff))
                this.applyBuffOnHeroForOneTurn(buff);
            else
                this.removeBuffFromHero(buff);
        }

        ArrayList<Buff> negativeBuffsCopy = new ArrayList<>(this.getNegativeBuffs());
        for (Buff buff : negativeBuffsCopy){
            if (Buff.checkIfBuffIsActive(buff))
                this.applyBuffOnHeroForOneTurn(buff);
            else
                this.removeBuffFromHero(buff);
        }
    }



    public void applyBuffOnHeroForOneTurn(Buff buff){
        int currentNumberOfTurns = buff.getForHowManyTurns();
        buff.setForHowManyTurns(currentNumberOfTurns - 1);
        String buffName = buff.getName();
        switch (buffName) {
            case "holyBuff":
                this.setHolyBuffIsActive(true);
                break;
            case "attackPowerBuff":
                this.applyAttackPowerBuff(buff);
                break;
            case "healthPowerBuff":
                this.applyHealthPowerBuff(buff);
                break;
            case "poisonBuff":
                this.applyPoisonBuff(buff);
                break;
            case "healthPointWeaknessBuff":
                this.applyHealthPointWeaknessBuff(buff);
                break;
            case "attackPowerWeaknessBuff":
                this.applyAttackPowerWeaknessBuff(buff);
                break;
            case "stunBuff":
                this.applyStunBuff();
                break;
            case "disarmBuff":
                this.applyDisarmBuff();
                break;
        }
    }

    public void removeBuffFromHero(Buff buff){
        String buffName = buff.getName();
        switch (buffName){
            case "holyBuff":
                this.setHolyBuffIsActive(false);
                break;
            case "attackPowerBuff":
                this.deactivateAttackPowerBuff(buff);
                break;
            case "attackPowerWeaknessBuff":
                this.deactivateAttackPowerWeaknessBuff(buff);
                break;
            case "stunBuff":
                this.deactivateStunBuff();
                break;
            case "disarmBuff":
                this.deactivateDisarmBuff();
                break;
        }
        String buffType = buff.getType();
        if (buffType.equals("negative"))
            this.getNegativeBuffs().remove(buff);
        else
            this.getPositiveBuffs().remove(buff);
    }

    public void applyAttackPowerBuff(Buff buff){
        if (!buff.isUsed()) {
            int howMuchImpact = buff.getHowMuchImpact();
            int currentAttackPower = this.getAttackPower();
            setAttackPower(currentAttackPower + howMuchImpact);
            buff.setUsed(true);
        }
    }

    public void applyHealthPowerBuff(Buff buff){
        int howMuchImpact = buff.getHowMuchImpact();
        int currentHealthPoint = this.getHealthPoint();
        setHealthPoint(currentHealthPoint + howMuchImpact);
    }

    public void applyPoisonBuff(Buff buff){
        int howMuchImpact = buff.getHowMuchImpact();
        int currentHealthPoint = this.getHealthPoint();
        setHealthPoint(currentHealthPoint - howMuchImpact);
    }

    public void applyHealthPointWeaknessBuff(Buff buff){
        int howMuchImpact = buff.getHowMuchImpact();
        int currentHealthPoint = this.getHealthPoint();
        setHealthPoint(currentHealthPoint - howMuchImpact);
    }

    public void applyAttackPowerWeaknessBuff(Buff buff){
        if (!buff.isUsed()) {
            int howMuchImpact = buff.getHowMuchImpact();
            int currentAttackPower = this.getAttackPower();
            setAttackPower(currentAttackPower - howMuchImpact);
            buff.setUsed(true);
        }
    }

    public void applyStunBuff(){
        this.setCanAttackOrMove(false);
    }

    public void applyDisarmBuff(){
        this.setCanCounterAttack(false);
    }

    public void deactivateAttackPowerBuff(Buff buff){
        int howMuchImpact = buff.getHowMuchImpact();
        int currentAttackPower = this.getAttackPower();
        setAttackPower(currentAttackPower - howMuchImpact);
    }

    public void deactivateAttackPowerWeaknessBuff(Buff buff){
        int howMuchImpact = buff.getHowMuchImpact();
        int currentAttackPower = this.getAttackPower();
        setAttackPower(currentAttackPower + howMuchImpact);
    }

    public void deactivateStunBuff(){
        this.setCanAttackOrMove(true);
    }

    public void deactivateDisarmBuff(){
        this.setCanCounterAttack(true);
    }

    public void applyCellImpact(Minion minion, Map map)
    {
        int x=minion.getX();
        int y=minion.getY();
        switch (((map.getCells())[x][y]).getCellSituation())
        {
//            case fire:
//                this.setHealthPoint(this.getHealthPoint()-2);
//                break;
//            case holy:
//                this.setHolyBuffIsActive(true);
//                break;
//            case empty:
//                break;
//            case flag:
//                break;
//            case poison:
//                Buff buff = new Buff(1,3,"poisonBuff","negative");
//                buff.setForHowManyTurns(3);
//                buff.setHowMuchImpact(1);
//                buff.setName("poisonBuff");
//                buff.setType("negative");
//                this.getNegativeBuffs().add(buff);
//                break;
        }
    }

    public static void insertHeroInMap() throws IOException, ParseException {
        String heroName = Game.getInstance().getPlayer1().getMainDeck().getHeroInDeckName();
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES  +
                "JSON-Heroes/" + heroName + ".json");
        int healthPoint = Integer.parseInt(jsonObject.get("healthPoint").toString());
        int attackPower = Integer.parseInt(jsonObject.get("attackPower").toString());
        String attackType = jsonObject.get("attackType").toString();
        int attackRange = Integer.parseInt(jsonObject.get("attackRange").toString());
        int coolDown = Integer.parseInt(jsonObject.get("coolDown").toString());
        String specialPower = jsonObject.get("specialPower").toString();

        Hero hero = new Hero(heroName, healthPoint, attackPower, attackType, attackRange, coolDown,specialPower);
        hero.setX(3);
        hero.setY(4);
        Game.getInstance().getMap().getHeroes().add(hero);
    }

    public static Hero findHeroInMap(int x, int y){
        for (Card card : Game.getInstance().getMap().getHeroes()){
            if (card.getX() == x && card.getY() == y)
                return (Hero) card;
        }
        return null;
    }

}
