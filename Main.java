import controller.Controller;
import model.*;
import model.collection.Card;
import model.collection.Hero;
import model.collection.Spell;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Controller.createAll();
        Scanner scanner = new Scanner(System.in);
        while (AllDatas.gameBoolean){
            Controller.handleCommands(scanner);
        }
//        Game game = new Game();
//        Game.setCurrentGame(game);
//
//        Player player = new Player("bahar", "baharkh127");
//        Game.getInstance().setPlayer1(player);
//        Map map = new Map();
//
//        Deck deck = new Deck("deck1");
//        player.getDecksOfPlayer().add(deck);
//        Hero.getHeroNames().add("Afsaneh");
//        deck.setHeroInDeckName("Afsaneh");
//
//        Game.getInstance().getPlayer1().setMainDeck(deck);
//
//        Spell spell = new Spell("All Attack", 1500, 4, "spell!");
//        Spell.getSpellNames().add("All Attack");
//        player.getCardsInCollectionNames().add("All Attack");
//
//        Hand hand = new Hand();
//        Game.getInstance().getPlayer1().getMainDeck().setHand(hand);
//        Deck.addCardOrItemToDeck(16, "spell", "deck1");
//
//        hand.addCardToHandFromDeck();
//
//        Game.getInstance().setMap(map);
//
//        Hero.insertHeroInMap();
//
//        Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand().add(spell);
//
//        Game.getInstance().getMap().getCells()[3][4].setCellType(CellType.selfHero);
//
//
//        for (Card card : Game.getInstance().getMap().getHeroes()){
//            Hero hero = (Hero) card;
//            System.out.println("name" + hero.getName());
//            System.out.println("HP : " + ((Hero) card).getHealthPoint());
//        }
//
//        Spell.insertSpellInThisCoordination("All Attack", 3, 4);
//        System.out.println("***");
//
//        for (Card card : Game.getInstance().getMap().getHeroes()){
//            Hero hero = (Hero) card;
//            System.out.println("name" + hero.getName());
//            System.out.println("HP : " + ((Hero) card).getHealthPoint());
//        }






    }
}
