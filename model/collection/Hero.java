package model.collection;

import model.Game;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Hero extends Force{
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";
    public static ArrayList<Hero> heroes = new ArrayList<>();
//    public static ArrayList<String> heroNames = new ArrayList<>();

    public static int turnCounterForPlayer1Hero;
    public static int turnCounterForPlayer2Hero;


    public static ArrayList<String> heroNames = new ArrayList<>();
    private int coolDown;

    public static void setHeroes(ArrayList<Hero> heroes) {
        Hero.heroes = heroes;
    }

    public static ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public Hero(String mana, String id, String cardType, String name, String price, String targets, String numOfTargets, String friendOrEnemy, String healthPoint, String attackPower, String attackType, String attackRange, String specialPower, String actionTypes, String locationOfTargets, String coolDown) {
        super(mana, id, cardType, name, price, targets, numOfTargets, friendOrEnemy, healthPoint, attackPower, attackType, attackRange, specialPower, actionTypes, locationOfTargets);
        if(!coolDown.equals("null")) this.coolDown = Integer.parseInt(coolDown);
        else this.coolDown =0;
    }
    public static int getHeroIDByName(String heroName) throws Exception{
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES
                + "JSON-Heroes/" + heroName + ".json");
        return Integer.parseInt(jsonObject.get("id").toString()) + 100;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
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
        for(Hero hero : heroes){
            if(hero.name.matches(cardName))
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
                this.setHasHolyBuff(true);
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
                this.setHasHolyBuff(false);
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
//
//    public void applyFireBuff(Buff buff){
//        int howMuchImpact = buff.getHowMuchImpact();
//        int currentHP = this.getHealthPoint();
//        this.setHealthPoint(currentHP - 2);
//    }

    public void applyStunBuff(){
        this.setAbleToAttack(false);
        this.setMovable(false);
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
        this.setMovable(true);
        this.setAbleToAttack(true);
    }

    public void deactivateDisarmBuff(){
        this.setCanCounterAttack(true);
    }

//    public void applyCellImpact(Minion minion, Map map)
//    {
//        int x=minion.getX();
//        int y=minion.getY();
//        switch (((map.getCells())[x][y]).getCellSituation())
//        {
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
//        }
//    }

    public static void insertHeroInMap() throws IOException, ParseException {
        Hero hero = Game.getInstance().getPlayer1().getMainDeck().getHeroInDeck();
        hero.setX(3);
        hero.setY(4);
        Game.getInstance().getMap().setFriendHero(hero);
    }

//    public static Hero findHeroInMap(int x, int y){
//        for (Card card : Game.getInstance().getMap().getHeroes()){
//            if (card.getX() == x && card.getY() == y)
//                return (Hero) card;
//        }
//        return null;
//    }

    public static Hero getHeroByCoordination(int x, int y){
        if (Game.getInstance().getHeroOfPlayer1().getX() == x && Game.getInstance().getHeroOfPlayer1().getY() == y){
            return Game.getInstance().getHeroOfPlayer1();
        }else if (Game.getInstance().getHeroOfPlayer2().getX() == x && Game.getInstance().getHeroOfPlayer2().getY() == y){
            return Game.getInstance().getHeroOfPlayer2();
        }
        return null;
    }

    public void removeBuffFromBuffArrayListOfHero(String buffName){
        if (Buff.getTypeOfBuffByItsName(buffName).equals("positive")){
            for (Buff buff : this.getPositiveBuffs())
                if (buff.getName().equals(buffName)){
                    this.getPositiveBuffs().remove(buff);
                    return ;
                }
        }else{
            for (Buff buff : this.getNegativeBuffs())
                if (buff.getName().equals(buffName)){
                    this.getNegativeBuffs().remove(buff);
                    return;
                }
        }
    }

    public static Hero getHeroByName(String heroName){
        for (Hero check:heroes) {
            if(check.name.matches(heroName)){
                return check;
            }
        }
        return null;
    }

    public void addBuffToHero(Buff buff) {
        if (buff.getType().equals("positive"))
            getPositiveBuffs().add(buff);
        else
            getNegativeBuffs().add(buff);
    }


}

