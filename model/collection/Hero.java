package model.collection;

import model.CellType;
import model.Game;
import model.Map;
import model.Shop;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Hero extends Force implements Cloneable{
    private static ArrayList<Hero> heroes = new ArrayList<>();
    public static int turnCounterForPlayer1Hero;
    public static int turnCounterForPlayer2Hero;
    private int coolDown;

    public static void setHeroes(ArrayList<Hero> heroes) {
        Hero.heroes = heroes;
    }

    public static ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public Hero(String mana, String id, String cardType, String name, String price, String targets, String numOfTargets, String friendOrEnemy, String healthPoint, String attackPower, String attackType, String attackRange, String specialPower, String actionTypes, String locationOfTargets, String coolDown,String imagePath,String forceInField) throws FileNotFoundException {
        super(mana, id, cardType, name, price, targets, numOfTargets, friendOrEnemy, healthPoint, attackPower, attackType, attackRange, specialPower, actionTypes, locationOfTargets,imagePath,forceInField);
        if (!coolDown.equals("null")) this.coolDown = Integer.parseInt(coolDown);
        else this.coolDown = 0;
    }

    public static int getHeroIDByName(String heroName) {
        for (Hero hero :
                heroes) {
            if (hero.name.matches(heroName)) {
                return hero.id;
            }
        }
        return 0;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public static Hero findHeroByID(int id) throws CloneNotSupportedException {
        for (Hero hero : heroes) {
            if (hero.id == id) {
                return (Hero)hero.clone();
            }
        }
        return null;
    }

    public static boolean thisCardIsHero(String cardName) {
        for (Hero hero : heroes) {
            if (hero.name.matches(cardName))
                return true;
        }
        return false;
    }

//    public void applyBuffsOnHero(){
//        ArrayList<Buff> positiveBuffsCopy = new ArrayList<>(this.getPositiveBuffs());
//        for (Buff buff : positiveBuffsCopy){
//            if (Buff.checkIfBuffIsActive(buff))
//                this.applyBuffOnHeroForOneTurn(buff);
//            else
//                this.removeBuffFromHero(buff);
//        }
//
//        ArrayList<Buff> negativeBuffsCopy = new ArrayList<>(this.getNegativeBuffs());
//        for (Buff buff : negativeBuffsCopy){
//            if (Buff.checkIfBuffIsActive(buff))
//                this.applyBuffOnHeroForOneTurn(buff);
//            else
//                this.removeBuffFromHero(buff);
//        }
//    }

//    public void applyBuffOnHeroForOneTurn(Buff buff){
//        int currentNumberOfTurns = buff.getForHowManyTurns();
//        buff.setForHowManyTurns(currentNumberOfTurns - 1);
//        String buffName = buff.getName();
//        switch (buffName) {
//            case "holyBuff":
//                this.setHasHolyBuff(true);
//                break;
//            case "attackPowerBuff":
//                this.applyAttackPowerBuff(buff);
//                break;
//            case "healthPowerBuff":
//                this.applyHealthPowerBuff(buff);
//                break;
//            case "poisonBuff":
//                this.applyPoisonBuff(buff);
//                break;
//            case "healthPointWeaknessBuff":
//                this.applyHealthPointWeaknessBuff(buff);
//                break;
//            case "attackPowerWeaknessBuff":
//                this.applyAttackPowerWeaknessBuff(buff);
//                break;
//            case "stunBuff":
//                this.applyStunBuff();
//                break;
//            case "disarmBuff":
//                this.applyDisarmBuff();
//                break;
//        }
//    }

    public void removeBuffFromHero(Buff buff) {
        String buffName = buff.getName();
        switch (buffName) {
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

    public void applyAttackPowerBuff(Buff buff) {
        if (!buff.isUsed()) {
            int howMuchImpact = buff.getHowMuchImpact();
            int currentAttackPower = this.getAttackPower();
            setAttackPower(currentAttackPower + howMuchImpact);
            buff.setUsed(true);
        }
    }

    public void applyHealthPowerBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentHealthPoint = this.getHealthPoint();
        setHealthPoint(currentHealthPoint + howMuchImpact);
    }

    public void applyPoisonBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentHealthPoint = this.getHealthPoint();
        setHealthPoint(currentHealthPoint - howMuchImpact);
    }

    public void applyHealthPointWeaknessBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentHealthPoint = this.getHealthPoint();
        setHealthPoint(currentHealthPoint - howMuchImpact);
    }

    public void applyAttackPowerWeaknessBuff(Buff buff) {
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

    public void applyStunBuff() {
        this.setAbleToAttack(false);
        this.setCanMove(false);
    }

    public void applyDisarmBuff() {
        this.setCanCounterAttack(false);
    }

    public void deactivateAttackPowerBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentAttackPower = this.getAttackPower();
        setAttackPower(currentAttackPower - howMuchImpact);
    }

    public void deactivateAttackPowerWeaknessBuff(Buff buff) {
        int howMuchImpact = buff.getHowMuchImpact();
        int currentAttackPower = this.getAttackPower();
        setAttackPower(currentAttackPower + howMuchImpact);
    }

    public void deactivateStunBuff() {
        this.setCanMove(true);
        this.setAbleToAttack(true);
    }

    public void deactivateDisarmBuff() {
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

    public static void insertHeroInMap() {
        Hero hero = Game.getInstance().getPlayer1().getMainDeck().getHeroInDeck();
        hero.setX(2);
        hero.setY(0);
        Game.getInstance().getMap().getCells()[2][0].setCellType(CellType.selfHero);
        Map.getForcesView()[0][2].setImage(hero.getForceInField());
        Map.getForcesView()[0][2].setY(Map.getForcesView()[0][2].getY()-25);
        Map.getCellsView()[0][2].setDisable(true);
        Game.getInstance().getMap().setFriendHero(hero);
    }

    public static Hero getHeroByCoordination(int x, int y) {
        if (Game.getInstance().getMap().getFriendHero().getX() == x && Game.getInstance().getMap().getFriendHero().getY() == y) {
            return Game.getInstance().getMap().getFriendHero();
        } else if (Game.getInstance().getMap().getEnemyHero().getX() == x && Game.getInstance().getMap().getEnemyHero().getY() == y) {
            return Game.getInstance().getMap().getEnemyHero();
        }
        return null;
    }

    public void removeBuffFromBuffArrayListOfHero(String buffName) {
        if (Buff.getTypeOfBuffByItsName(buffName).equals("positive")) {
            for (Buff buff : this.getPositiveBuffs())
                if (buff.getName().equals(buffName)) {
                    this.getPositiveBuffs().remove(buff);
                    return;
                }
        } else {
            for (Buff buff : this.getNegativeBuffs())
                if (buff.getName().equals(buffName)) {
                    this.getNegativeBuffs().remove(buff);
                    return;
                }
        }
    }

    public static Hero findHeroByName(String heroName) {
        for (Hero check : heroes) {
            if (check.name.matches(heroName)) {
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

