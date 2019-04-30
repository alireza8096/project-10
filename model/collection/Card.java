package model.collection;
import model.AttackType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Card {
    private static ArrayList<String> cardNames = new ArrayList<>();
    private int mana;
    private int id;
    private String cardType;
    private String name;
    private AttackType targetType;
    private boolean isActive;
    private boolean hasAttackedInThisTurn;
    private boolean hasMovedInThisTurn;
    private int price;
    private int counterOfCard;
    private int x;
    private int y;

    public AttackType getTargetType() {
        return targetType;
    }

    public void setTargetType(AttackType targetType) {
        this.targetType = targetType;
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

    public static ArrayList<String> getCardNames() {
        return cardNames;
    }

    public static void setCardNames(ArrayList<String> cardNames) {
        Card.cardNames = cardNames;
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

    public void showCardInfo(){

    }

    public static Card getCardByName(String cardName) throws IOException, ParseException {
        if (Minion.thisCardIsMinion(cardName)){
            return Minion.getCardByName(cardName);
        }else if(Spell.thisCardIsSpell(cardName)){
            return Spell.getSpellByName(cardName);
        }
        return null;
    }
}
