package model;

import model.Deck;
import model.Game;
import model.Map;
import model.Shop;
import model.collection.Card;
import model.collection.Hero;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class AI {
    private boolean canMove = true;
    private boolean canAttack = true;

    public static void createDeckOfAIPlayer() throws IOException, ParseException {
        Deck deck = new Deck("player2");
        int heroId =(int) Math.random()%10;
        deck.setHeroInDeckName(Hero.findHeroNameByID(heroId));
        int[] idsOfItems = new int[3];
        int[] idsOfMinions = new int[12];
        int[] idsOfSpells = new int[5];
        setRandomIdsItems(idsOfItems);
        setRandomIdsSpells(idsOfSpells);
        setRandomIdsMinions(idsOfMinions);



    }
    public static void setAIAsSecondPlayer(){

    }
    public static void randomMove(){

    }
    public static void randomAttack(){

    }
    public static void insertCardToDeck(int[] array,Deck deck){
        for(int i=0; i<array.length; i++){
//            deck.getCardsInDeckNames().add(Card.getCardByName());
        }
    }
    public static void insertItemToDeck(int[] array,Deck deck){

    }
    public static boolean idAlreadyExists(int id,int[] array){
        for (int i=0; i<array.length; i++){
            if(id == array[i])
                return true;
        }
        return false;
    }
    public static void setRandomIdsItems(int[] array){
        int i=0;
        while(i<3){
            int random = (int) Math.random()%20 + 1;
            if(!idAlreadyExists(random,array)){
                array[i] = 200 + random;
                i++;
            }
        }
    }
    public static void setRandomIdsMinions(int[] array){
        int i=0;
        while(i<12){
            int random = (int) Math.random()%40 + 1;
            if(!idAlreadyExists(random,array)){
                array[i] = 300 + random;
                i++;
            }
        }
    }
    public static void setRandomIdsSpells(int[] array){
        int i=0;
        while(i<5){
            int random = (int) Math.random()%20 + 1;
            if(!idAlreadyExists(random,array)){
                array[i] = 400 + random;
                i++;
            }
        }
    }

}
