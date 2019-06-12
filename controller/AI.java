package controller;

import controller.BattleController;
import controller.CollectionController;
import model.*;
import model.collection.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class AI {
    private static boolean canAttack = true;

    public static void createDeckOfAI(Player player) throws Exception {
        Deck deck = new Deck("player2");
        int heroId =(int) Math.random()%10 + 1;
        deck.setHeroInDeck(Hero.findHeroByID(heroId));
        int[] idsOfItems = new int[3];
        int[] idsOfMinions = new int[12];
        int[] idsOfSpells = new int[5];
        setRandomIdsItems(idsOfItems);
        setRandomIdsSpells(idsOfSpells);
        setRandomIdsMinions(idsOfMinions);
        Game.getInstance().getMap().setEnemyHero(Hero.findHeroByName(BattleController.returnNameById(heroId+100)));
        Deck deckOfAI = new Deck("AIDeck");
        for (int i = 0; i < idsOfItems.length; i++) {
            deckOfAI.getItemsInDeck().add(Item.findItemByID(idsOfItems[i]));
        }
        for (int i = 0; i < idsOfSpells.length; i++) {
            deckOfAI.getCardsInDeck().add(Spell.findSpellByID(idsOfSpells[i]));
        }
        for (int i = 0; i < idsOfMinions.length; i++) {
            deckOfAI.getCardsInDeck().add(Minion.findMinionByID(idsOfMinions[i]));
        }
        player.setMainDeck(deckOfAI);
    }
    public static void createAIPlayer() throws Exception {
        Player AIPlayer = new Player("AI", "AI");
        createDeckOfAI(AIPlayer);
        AIPlayer.setNumOfMana(2);
        Game.getInstance().setPlayer2(AIPlayer);
    }
    public static void moveTillPossible() {
        while (!Game.getInstance().getMap().getFriendHero().isHasMovedInThisTurn()) {
            int x = (int) Math.random() % 5;
            int y = (int) Math.random() % 9;
            BattleController.move(Game.getInstance().getMap().getFriendHero(), x, y);
        }
        for (Card card : Game.getInstance().getMap().getFriendMinions()) {
            while (!card.isHasMovedInThisTurn()) {
                int x = (int) Math.random() % 5;
                int y = (int) Math.random() % 9;
                BattleController.move((Force)card, x, y);
            }
        }
    }
    public static void attckTillPossible() throws Exception {
        String cardName = Game.getInstance().getMap().getFriendHero().getName();
        while(!Game.getInstance().getMap().getFriendHero().isHasAttackedInThisTurn()){
            int idToAttack = (int) Math.random()%500;
            if(Shop.checkValidId(idToAttack)) {
                String[] parts = {"attack",Integer.toString(idToAttack)};
//                BattleController.attack(parts,cardName);
            }
        }
        for (Card card:
             Game.getInstance().getMap().getFriendMinions()) {
            while(!card.isHasAttackedInThisTurn()){
                int idToAttack = (int) Math.random()%500;
                if(Shop.checkValidId(idToAttack)) {
                    String[] parts = {"attack",Integer.toString(idToAttack)};
//                    BattleController.attack(parts,cardName);
                }
            }
        }
        canAttack = false;
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
    public static boolean idAlreadyExists(int id,int[] array){
        for (int i=0; i<array.length; i++){
            if(id == array[i])
                return true;
        }
        return false;
    }

}
