package controller;

import model.AllDatas;
import model.Game;
import model.Hand;
import model.Shop;
import model.collection.Hero;
import model.collection.Item;
import model.collection.Minion;
import model.collection.Spell;
import view.GameView;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public interface ShopController {
    public static void showCollection(String[] commands) throws Exception{
        if(commands.length == 2 && commands[0].compareToIgnoreCase("show") == 0 &&
        commands[1].compareToIgnoreCase("collection") == 0){
            ArrayList<String> heroesInCollection = new ArrayList<>(Game.getInstance().getPlayer1().getHeroesInCollectionName());
            ArrayList<String> itemsInCollection = new ArrayList<>(Game.getInstance().getPlayer1().getItemsInCollectionNames());
            ArrayList<String> cardsInCollection = new ArrayList<>(Game.getInstance().getPlayer1().getCardsInCollectionNames());
            System.out.println("Heroes :");
            for(int i=1; i<=heroesInCollection.size(); i++){
                System.out.println(i + " : " + GameView.showHero(heroesInCollection.get(i-1)));
            }
            System.out.println("Items :");
            for(int i=1; i<=itemsInCollection.size(); i++){
                System.out.println(i + " : " + GameView.showItem(itemsInCollection.get(i-1)));
            }
            System.out.println("Cards :");
            for(int i=1; i<=cardsInCollection.size(); i++){
                System.out.println(i + " : " + GameView.showCard(cardsInCollection.get(i-1)));
            }
            AllDatas.hasEnteredShop = true;
        }
    }
    public static void searchInShop(String[] commands) throws Exception{
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
}
