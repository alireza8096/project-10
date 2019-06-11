package model.collection;

import model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Minion extends Force {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

    private static ArrayList<Minion> minions = new ArrayList<>();

//    public static ArrayList<String> minionNames = new ArrayList<>();

    private ArrayList<String> doesNotGetAttackBuffs = new ArrayList<>();
    private String doesNotGetAttack;
    private String activationTime;
    private boolean isHeadOfCombo;

    public Minion(String mana, String id, String cardType, String name, String price, String targets, String numOfTargets, String friendOrEnemy, String healthPoint, String attackPower, String attackType, String attackRange, String specialPower, String actionTypes, String locationOfTargets, String doesNotGetAttack, String activationTime) {
        super(mana, id, cardType, name, price, targets, numOfTargets, friendOrEnemy, healthPoint, attackPower, attackType, attackRange, specialPower, actionTypes, locationOfTargets);
        if(!doesNotGetAttack.matches("null")) this.doesNotGetAttack = doesNotGetAttack;
        if(!activationTime.matches("null")) this.activationTime = activationTime;
    }

    public static ArrayList<Minion> getMinions() {
        return minions;
    }

    public static void setMinions(ArrayList<Minion> minions) {
        Minion.minions = minions;
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


    public String getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(String activationTime) {
        this.activationTime = activationTime;
    }

    public static Minion findMinionByID(int id){
        for (Minion minion: minions) {
            if(minion.id == id){
                return minion;
            }
        }
        return null;
    }

    public static Minion findMinionByName(String minionName){
        for (Minion minion: minions) {
            if(minion.name.matches(minionName))
                return minion;
        }
        return null;
    }

    public static int getMinionIDByName(String minionName){
        for (Minion minion: minions) {
            if(minion.name.matches(minionName))
                return minion.id;
        }
        return 0;
    }
    public static boolean thisCardIsMinion(String cardName){
        for (Minion minion : minions){
            if (minion.name.equals(cardName)) {
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
//        else if(buffName.equals("stunBuff"))
//        {
//            this.applyStunBuff(buff);
//        }
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
//        else if(buffName.equals("disarmBuff"))
//        {
//            this.removeDisarmBuff(buff);
//        }
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

//    public void applyStunBuff(Buff buff) {
//        this.setCanAttackOrMove(false);
//    }
    public void applyStunBuff(Buff buff) {
  //      this.setCanAttackOrMove(false);
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

//    public void removeDisarmBuff(Buff buff)
//    {
//        this.setCanAttackOrMove(true);
//    }
    public void removeDisarmBuff(Buff buff)
    {
   //     this.setCanAttackOrMove(true);
    }

    public void removeStunBuff(Buff buff)
    {
        this.setCanCounterAttack(true);
    }

    public void removeHolyBuff(Buff buff)
    {
        setHasHolyBuff(false);
    }

//    public void applyCellImpact(Minion minion, Map map)
//    {
//        int x=minion.getX();
//        int y=minion.getY();
//        switch (((map.getCells())[x][y]).getCellImpactType())
//        {
//            case fire:
//                this.setHealthPoint(this.getHealthPoint()-2);
//                break;
//            case holy:
//                this.setHasHolyBuff(true);
//                break;
//            case poison:
//                Buff buff = new Buff(1,3,"poisonBuff","negative");
//                this.getPositiveBuffs().add(buff);
//                break;
//        }
//    }

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
        for (String numOfTarget : this.getNumOfTargets()){
            if (numOfTarget.equals("all")){

                //in this minion location of target is 8 round
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
        ArrayList<String> targets = this.getTargets();

        for (String target : targets){
            switch (target){
                case "itself":
                    applyMinionSpecialPowerOnItself();
                    break;
                case "minion":
                    applyMinionSpecialPowerOnMinionOnAttack(x, y);
                    break;
                case "minion/hero":
                    applyMinionSpecialPowerOnMinionOrHeroOnAttack(x, y);
                    break;
                case "null":
                    applyMinionWithNullTarget();
                    break;
            }
        }
    }

    public void applyMinionSpecialPowerOnMinionOrHeroOnAttack(int x, int y){
        ArrayList<String> actionTypes = this.getActionTypes();

        for (String actionType : actionTypes){
            switch (actionType){
                case "dispelAll":
                    ((Force)Card.getCardByCoordination(x, y)).dispelPositiveActions();
                    break;
                case "addBuff":
                    minionAddsBuffToThisForce(x, y);
                    break;
                case "addAction":
                    minionAddActionToThisForce(x, y);
                    break;
            }
        }
    }

    public void minionAddActionToThisForce(int x, int y){
        for (Buff buff : this.getBuffActions()){
            ((Force) Card.getCardByCoordination(x, y)).getActionBuffsOnItself().add(buff);
        }
    }

    public void minionAddsBuffToThisForce(int x, int y){
        for (Buff buff : this.getPositiveBuffs()){
            ((Force) Card.getCardByCoordination(x, y)).getPositiveBuffsOnItself().add(buff);
        }

        for (Buff buff : this.getNegativeBuffs()){
            ((Force) Card.getCardByCoordination(x, y)).getNegativeBuffsOnItself().add(buff);
        }
    }

    public void applyMinionSpecialPowerOnMinionOnAttack(int x, int y){
        ArrayList<String> numOfTargets = this.getNumOfTargets();

        for (String numOfTarget : numOfTargets){
            if (numOfTarget.equals("1")){
                for (Buff buff : this.getBuffActions()){
                    Minion.getMinionInThisCoordination(x, y).getActionBuffsOnItself().add(buff);
                }
            }
        }


    }

    public void applyMinionWithNullTarget(){
        this.doesNotGetAttackBuffs.add(this.doesNotGetAttack);
    }

    public void applyMinionSpecialPowerOnItself(){

        for (Buff buff : this.getPositiveBuffs()){
            this.getPositiveBuffsOnItself().add(buff);
        }

        for (Buff buff : this.getNegativeBuffs()){
            this.getNegativeBuffsOnItself().add(buff);
        }

        for (Buff buff : this.getBuffActions()){
            this.getActionBuffsOnItself().add(buff);
        }
    }

    public void applyMinionSpecialPowerPassive(){
        ArrayList<String> targets = this.getTargets();

        for (String target : targets){
            switch (target){
                case "itself":
                    applyMinionSpecialPowerOnItself();
                    break;
                case "minion":
                    applyMinionSpecialPowerOnMinionPassive();
                    break;
            }
        }
    }

    public void applyMinionSpecialPowerOnMinionPassive(){

        //Todo : check if coordination are implemented right
        //all have location of target 8around
        int minionX  = this.x;
        int minionY = this.y;

        for (Minion minion : Game.getInstance().getMap().getFriendMinions()){
            if (Map.thisCellsAreAdjusting(minionX, minionY, minion.x, minion.y)){
                for (Buff buff : this.getPositiveBuffs()){
                    minion.getPositiveBuffsOnItself().add(buff);
                }

                for (Buff buff : this.getNegativeBuffs()){
                    minion.getNegativeBuffsOnItself().add(buff);
                }
            }
        }
    }

    public void applyMinionSpecialPowerCombo(int x, int y, int... otherComboCardsID){

        for (Buff buff : this.getNegativeBuffs()){
            Minion.getMinionInThisCoordination(x, y).getNegativeBuffs().add(buff);
        }

        for (int minionID : otherComboCardsID){
            Minion minion = getFriendMinionByID(minionID);
            if (minion.thisMinionIsCombo()){
                for (Buff buff : minion.getNegativeBuffs()){
                    Minion.getMinionInThisCoordination(x, y).getNegativeBuffsOnItself().add(buff);
                }
            }else{
                GameView.printInvalidCommandWhithThisContent("Minion is not combo!");
                return;
            }
        }
    }

    public boolean thisMinionIsCombo(){
        for (Minion minion : Game.getInstance().getMap().getFriendMinions()){
            if (minion.getName().equals(this.name)){
                return minion.getActivationTime().equals("Combo");
            }
        }
        return false;
    }

    public static Minion getFriendMinionByID(int minionID){
        for (Minion minion : Game.getInstance().getMap().getFriendMinions()){
            if (minion.id == minionID){
                return minion;
            }
        }
        return null;
    }

    public void applyMinionSpecialPowerOnDeath(){
        ArrayList<String> targets = this.getTargets();
        for (String target : targets){
            switch (target){
                case "minion":
                    applyMinionSpecialPowerOnMinionOnDeath();
                    break;
                case "hero":
                    applyMinionSpecialPowerOnHeroOnDeath();
                    break;
            }
        }
    }

    public void applyMinionSpecialPowerOnMinionOnDeath(){
        for (Minion enemyMinion : Game.getInstance().getMap().getEnemyMinions()){
            if (Map.thisCellsAreAdjusting(x, y, enemyMinion.x, enemyMinion.y)){
                for (Buff buff : this.getNegativeBuffs()){
                    enemyMinion.getNegativeBuffsOnItself().add(buff);
                }
            }
        }
    }

    public void applyMinionSpecialPowerOnHeroOnDeath(){
        for (Buff buff : this.getNegativeBuffs())
            Game.getInstance().getMap().getEnemyHero().getNegativeBuffsOnItself().add(buff);
    }

    public void applyMinionSpecialPowerOnDefend(){
        this.doesNotGetAttackBuffs.add(this.doesNotGetAttack);
    }

    public void applyMinionSpecialPowerInTurn(){
        for (Buff buff : this.getPositiveBuffs()){
            for (Minion minion : Game.getInstance().getMap().getFriendMinions()){
                minion.getPositiveBuffsOnItself().add(buff);
            }
        }
    }

    public void minionAttacksNormally(int x, int y){
        int currentHealthPoint = Minion.getMinionInThisCoordination(x, y).getHealthPoint();
        int attackPowerOfAttacker = this.getAttackPower();
        Minion.getMinionInThisCoordination(x, y).setHealthPoint(currentHealthPoint - attackPowerOfAttacker);
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

//
//    public void minionComboAttack(){
//        this.isHeadOfCombo = true;
//        for (Minion minion : Game.getInstance().getMap().getFriendMinions()){
//
//        }
//    }



}

