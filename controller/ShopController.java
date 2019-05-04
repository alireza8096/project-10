package controller;

import model.*;
import model.collection.Hero;
import model.collection.Item;
import model.collection.Minion;
import model.collection.Spell;
import model.Game;
import view.GameView;

import java.util.ArrayList;

public interface ShopController {
    static void showCollection(String[] commands) throws Exception{
         if(commands.length == 2 && commands[0].compareToIgnoreCase("show") == 0 &&
        commands[1].compareToIgnoreCase("collection") == 0){
            showHeroesInCollection();
            showItemsInCollection();
            showCardsInCollection();
            AllDatas.hasEnteredShop = true;
        }
    }
    static void searchInShop(String[] commands) throws Exception{
        if(commands.length >= 2 && commands[0].compareToIgnoreCase("search") == 0 && commands[1].compareToIgnoreCase("collection") != 0) {
            String cardName = CollectionController.createName(commands,1);
            if(Hero.thisCardIsHero(cardName)){
                System.out.println(Hero.getHeroIDByName(cardName));
            }
            else if(Item.thisCardIsItem(cardName)){
                System.out.println(Item.getItemIDByName(cardName));
            }
            else if(Minion.thisCardIsMinion(cardName)){
                System.out.println(Minion.getMinionIDByName(cardName));
            }
            else if(Spell.thisCardIsSpell(cardName)){
                System.out.println(Spell.getSpellIDByName(cardName));
            }
            else {
                System.out.println("Card is not available in shop");
            }
            AllDatas.hasEnteredShop = true;
        }
    }
    static void searchInCollection(String[] commands) throws Exception{
        if(commands.length>=3 && commands[0].compareToIgnoreCase("search") == 0
        && commands[1].compareToIgnoreCase("collection") == 0){
            AllDatas.hasEnteredShop = true;
            String cardName = CollectionController.createName(commands,2);
            if(Game.getInstance().getPlayer1().thisCardOrItemIsAvailableInCollection(cardName)){
                if(Minion.thisCardIsMinion(cardName))
                    System.out.println(Minion.getMinionIDByName(cardName));
                else if(Hero.thisCardIsHero(cardName))
                    System.out.println(Hero.getHeroIDByName(cardName));
                else if(Item.thisCardIsItem(cardName))
                    System.out.println(Item.getItemIDByName(cardName));
                else if(Spell.thisCardIsSpell(cardName)){
                    System.out.println(Spell.getSpellIDByName(cardName));
                }
            }
        }
    }
    static void buy(String[] commands) throws Exception{
         if(commands.length>=2 && commands[0].compareToIgnoreCase("buy") == 0){
                AllDatas.hasEnteredShop = true;
                String cardName = CollectionController.createName(commands,1);
                Shop.buy(cardName);
         }
     }
    static void sell(String[] commands) throws Exception{
         if(commands.length == 2 && commands[0].compareToIgnoreCase("sell")==0){
             int id = Integer.parseInt(commands[1]);
             int cardType = id/100;
             int cardId = id%100;
             Shop.sell(cardType,cardId);
             AllDatas.hasEnteredShop = true;
         }
     }
    static void showShop(String[] commands) throws Exception{
        if(commands.length == 1 && commands[0].compareToIgnoreCase("show") == 0) {
            AllDatas.hasEnteredShop = true;
            System.out.println("Heroes :");
            for (int i = 1; i <= Hero.heroNames.size(); i++) {
                System.out.println("        "+i + " : " + GameView.showHero(Hero.heroNames.get(i - 1)));
            }
            System.out.println("Items :");
            for (int i = 1; i <= Item.itemNames.size(); i++) {
                System.out.println("        "+i + " : " + GameView.showItem(Item.itemNames.get(i - 1)));
            }
            System.out.println("Cards :");
            System.out.println("        Spells :");
            for (int i = 1; i <= Spell.spellNames.size(); i++) {
                System.out.println("                " + i + " : " + GameView.showSpell(Spell.spellNames.get(i - 1)));
            }
            System.out.println("        Minions :");
            for (int i = 1; i <= Minion.minionNames.size(); i++) {
                System.out.println("                " + i + " : " + GameView.showMinion(Minion.minionNames.get(i - 1)));
            }
        }
    }
    static void help(String[] commands){
         if(commands.length == 1 && commands[0].compareToIgnoreCase("help") ==0){
             AllDatas.help.setParent(AllDatas.shop);
             AllDatas.help.setNowInThisMenu(true);
             AllDatas.shop.setNowInThisMenu(false);
             for(String commandName : AllDatas.shop.getCommandsForHelp()){
                 System.out.println(commandName);
             }
             AllDatas.hasEnteredShop = true;
         }
    }
    static void showHeroesInCollection() throws Exception{
        ArrayList<String> heroesInCollection = new ArrayList<>(Game.getInstance().getPlayer1().getHeroesInCollectionName());
        System.out.println("Heroes :");
        for(int i=1; i<=heroesInCollection.size(); i++){
            System.out.println(i + " : " + GameView.showHero(heroesInCollection.get(i-1)));
        }
    }
    static void showItemsInCollection() throws Exception{
        ArrayList<String> itemsInCollection = new ArrayList<>(Game.getInstance().getPlayer1().getItemsInCollectionNames());
        System.out.println("Items :");
        for(int i=1; i<=itemsInCollection.size(); i++){
            System.out.println(i + " : " + GameView.showItem(itemsInCollection.get(i-1)));
        }
    }
    static void showCardsInCollection() throws Exception{
        ArrayList<String> cardsInCollection = new ArrayList<>(Game.getInstance().getPlayer1().getCardsInCollectionNames());
        System.out.println("Cards :");
        for(int i=1; i<=cardsInCollection.size(); i++){
            System.out.println(i + " : " + GameView.showCard(cardsInCollection.get(i-1)));
        }
    }
}
