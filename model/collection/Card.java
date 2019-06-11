package model.collection;
import model.AttackType;
import model.Game;
import model.Map;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Card {
//    private static ArrayList<String> cardNames = new ArrayList<>();
    protected int mana;
    protected int id;
    protected String cardType;
    protected String name;
//    protected AttackType targetType;
    protected boolean isActive;
    protected boolean hasAttackedInThisTurn;
    protected boolean hasMovedInThisTurn;
    protected boolean movable;
    protected boolean ableToAttack;
    protected int price;
    protected int counterOfCard;
    protected int x;
    protected int y;
    protected boolean inGame;

    public Card(String mana, String id, String cardType, String name,String price) {
        if (mana.equals("null")) this.mana = 0;
        else this.mana = Integer.parseInt(mana);
        this.id = Integer.parseInt(id);
        this.cardType = cardType;
        this.name = name;
        if (!price.equals("null")) this.price = Integer.parseInt(price);
        else this.price = 0;
    }

    public static Card findCardById(int id){
        switch (id/100){
            case 1:
                return Hero.findHeroByID(id);
            case 3:
                return Minion.findMinionByID(id);
            case 4:
                return Spell.findSpellByID(id);
        }
        return null;
    }
    public static Card findCardByName(String cardName){
        for (Hero hero: Hero.getHeroes()) {
            if(hero.getName().matches(cardName)){
                return hero;
            }
        }
        for(Minion minion : Minion.getMinions()){
            if(minion.getName().matches(cardName)){
                return minion;
            }
        }
        for(Spell spell : Spell.getSpells()){
            if(spell.getName().matches(cardName)){
                return spell;
            }
        }
        return null;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isAbleToAttack() {
        return ableToAttack;
    }

    public void setAbleToAttack(boolean ableToAttack) {
        this.ableToAttack = ableToAttack;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isHasAttackedInThisTurn() {
        return hasAttackedInThisTurn;
    }

    public void setHasAttackedInThisTurn(boolean hasAttackedInThisTurn) {
        this.hasAttackedInThisTurn = hasAttackedInThisTurn;
    }

    public boolean isHasMovedInThisTurn() {
        return hasMovedInThisTurn;
    }

    public void setHasMovedInThisTurn(boolean hasMovedInThisTurn) {
        this.hasMovedInThisTurn = hasMovedInThisTurn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCounterOfCard() {
        return counterOfCard;
    }

    public void setCounterOfCard(int counterOfCard) {
        this.counterOfCard = counterOfCard;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


//    public boolean checkIfCardCanBePlaced(int x, int y){
//
//
//    }
//
//    public boolean checkIfThisCoordinationIsValid(int x, int y){
//
//
//    }
//
//    public boolean checkIfThisCardExistsInHand(String cardName){
//
//    }

    public void increaseCounterOfCards(){

    }

    public int returnCompleteId(String cardName,int id){
        if (Hero.thisCardIsHero(cardName)) {
            return 100+id;
        }
        else if(Item.thisCardIsItem(cardName)){
            return 200+id;
        }
        else if(Minion.thisCardIsMinion(cardName)){
            return 300+id;
        }
        else {
            return 400+id;
        }
    }

    public static boolean thisCardIsMelee(String cardName) throws IOException, ParseException {
        Card card = findCardByName(cardName);
        if (card.getCardType().equals("minion")){
            if (((Minion)card).getAttackType().equals("melee"))
                return true;

        }else if (card.getCardType().equals("hero")){
            if (((Hero)card).getAttackType().equals("melee"))
                return true;
        }
        return false;
    }

    public static boolean thisCardIsRanged(String cardName) throws IOException, ParseException {
        Card card = findCardByName(cardName);
        if (card.getCardType().equals("minion")){
            if (((Minion)card).getAttackType().equals("ranged"))
                return true;
        }else if (card.getCardType().equals("hero")){
            if (((Hero)card).getAttackType().equals("ranged"))
                return true;
        }
        return false;
    }

    public static boolean thisCardIsHybrid(String cardName) throws IOException, ParseException {
        Card card = findCardByName(cardName);
        if (card.getCardType().equals("minion")){
            if (((Minion)card).getAttackType().equals("hybrid"))
                return true;
        }else if (card.getCardType().equals("hero")){
            if (((Hero)card).getAttackType().equals("hybrid"))
                return true;
        }
        return false;
    }

    public static boolean checkIfCardCanAttack(Card card, int targetX, int targetY){
        int cardX = card.getX();
        int cardY = card.getY();

        String attackType = null;
        if (card.getCardType().equals("minion")){
            attackType = ((Minion)card).getAttackType();
        }else if (card.getCardType().equals("hero")){
            attackType = ((Hero)card).getAttackType();
        }

        switch (attackType){
            case "melee":
                if (Map.thisCellsAreAdjusting(cardX, cardY, targetX, targetY))
                    return true;
                else
                    return false;
            case "ranged":
                if (!Map.thisCellsAreAdjusting(cardX, cardY, targetX, targetY))
                    return true;
                else
                    return false;
            case "hybrid":
                return true;
        }
        return true;
    }

    public void dispelThisForce(String enemyOrFriend){
        //Todo : implementing
        if (enemyOrFriend.equals("friend")){

        }else if (enemyOrFriend.equals("enemy")){

        }
    }

    public void killCard(){
        //Todo : implementing
    }

    public static Card getCardByCoordination(int x, int y) {
        Map map = Game.getInstance().getMap();
        for (Card card : map.getFriendMinions()) {
            if (card.getX() == x && card.getY() == y) {
                return card;
            }
        }

        for (Card card : map.getEnemyMinions())
            if (card.getX() == x && card.getY() == y)
                return card;


        if (map.getFriendHero().getX() == x && map.getFriendHero().getY() == y) {
            return map.getFriendHero();
        }

        if (map.getEnemyHero().getX() == x && map.getEnemyHero().getY() == y)
            return map.getEnemyHero();

        return null;
    }

    public boolean thisCardIsFriend(){
        for (Card card : Game.getInstance().getPlayer1CardsInField()){
            if (card.getName().equals(this.name))
                return true;
        }
        return false;
    }

}
