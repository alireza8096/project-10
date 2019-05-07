package model;

import model.collection.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Shop {
    private Game game;
    private Player player;
    public static ArrayList<String> usableItems = new ArrayList<>();
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";

    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public static int heroSellCost(String cardName)throws Exception{
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES+"JSON-Heroes/"+cardName+".json");
        return Integer.parseInt(jsonObject.get("price").toString());
    }
    public static int itemSellCost(String cardName) throws Exception{
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES+"JSON-Items/"+cardName+".json");
        if(jsonObject.get("itemType").toString().matches("usable")) {
            return Integer.parseInt(jsonObject.get("price").toString());
        }
        else {
            System.out.println("Item is not usable");
            return 0;
        }
    }
    public static int minionSellCost(String cardName) throws Exception{
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES+"JSON-Minions/"+cardName+".json");
        return Integer.parseInt(jsonObject.get("price").toString());
    }
    public static int spellSellCost(String cardName) throws Exception{
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES+"JSON-Spells/"+cardName+".json");
        return Integer.parseInt(jsonObject.get("price").toString());
    }
    public static boolean checkIfMoneyIsEnough(String cardName) throws Exception {
        int playersDaric = Game.getInstance().getPlayer1().getDaric();
        int price=0;
        switch (returnCardType(cardName)){
            case "hero" :
                price+=heroSellCost(cardName);
                break;
            case "item" :
                price+=itemSellCost(cardName);
                break;
            case "minion" :
                price+=minionSellCost(cardName);
                break;
            case "spell" :
                price+=spellSellCost(cardName);
                break;
        }
        return playersDaric >= price;
    }
    public static boolean checkItemBuyingConditions(String cardName){
        return Game.getInstance().getPlayer1().getItemsInCollectionNames().size() != 3;
    }
    public static void buyCardAndAddToCollection(String cardName)throws Exception{
        int daric = Game.getInstance().getPlayer1().getDaric();
        switch (returnCardType(cardName)){
            case "hero" :
                daric-=heroSellCost(cardName);
                Game.getInstance().getPlayer1().getHeroesInCollectionName().add(cardName);
                break;
            case "item" :
                daric-=itemSellCost(cardName);
                Game.getInstance().getPlayer1().getItemsInCollectionNames().add(cardName);
                break;
            case "minion" :
                daric-=minionSellCost(cardName);
                Game.getInstance().getPlayer1().getCardsInCollectionNames().add(cardName);
                break;
            case "spell" :
                daric-=spellSellCost(cardName);
                Game.getInstance().getPlayer1().getCardsInCollectionNames().add(cardName);
                break;
        }
        Game.getInstance().getPlayer1().setDaric(daric);
        System.out.println(cardName + " was added to your collection successfully");
        System.out.println("Remained daric : " +Game.getInstance().getPlayer1().getDaric());
    }
    public static boolean checkIfCardWithThisNameIsValid(String cardName){
        if(Minion.thisCardIsMinion(cardName))
            return true;
        else if(Hero.thisCardIsHero(cardName))
            return true;
        else if(Spell.thisCardIsSpell(cardName))
            return true;
        else return Item.thisCardIsItem(cardName);
    }
    public static boolean itemAndUsable(String cardName){
        for (String name:
             usableItems) {
            if(name.matches(cardName))
                return true;
        }
        return false;
    }
    public static String returnCardType(String cardName){
        String type="";
        if(Minion.thisCardIsMinion(cardName))
            type =  "minion";
        else if(Spell.thisCardIsSpell(cardName))
            type = "spell";
        else if(Hero.thisCardIsHero(cardName))
            type = "hero";
        else if(Item.thisCardIsItem(cardName))
            type = "item";
        return type;
    }
    public static void buy(String cardName) throws Exception{
        if(checkIfCardWithThisNameIsValid(cardName)){
            if(Item.thisCardIsItem(cardName) && !itemAndUsable(cardName)){
                System.out.println("This item is not usable");
            }
            else{
                if(checkIfMoneyIsEnough(cardName)){
                    if(Item.thisCardIsItem(cardName)){
                        if(checkItemBuyingConditions(cardName)){
                            buyCardAndAddToCollection(cardName);
                        }
                        else{
                            System.out.println("You have reached the limit of items in collection");
                        }
                    }
                    else{
                        buyCardAndAddToCollection(cardName);
                    }
                }
                else{
                    System.out.println("Your money is not enough");
                }
            }
        }
        else
            System.out.println("This card is not available in shop");
    }
    public static void removeProcess(ArrayList<String> names,String cardName){
        ArrayList<String> copyOfName = new ArrayList<>(names);
        for (String nameToFind:
             copyOfName) {
            if(nameToFind.matches(cardName)){
                names.remove(cardName);
            }
        }
    }
    public static void sellCardAndRemoveFromCollection(int cardID,String cardType) throws Exception{
        int daric = Game.getInstance().getPlayer1().getDaric();
        String name = "";
        switch (cardType){
            case "hero" :
                name = Hero.findHeroNameByID(cardID);
                removeProcess(Game.getInstance().getPlayer1().getHeroesInCollectionName(),name);
                daric += heroSellCost(name);
                break;
            case "item" :
                name = Item.findItemNameByID(cardID);
                if(itemAndUsable(name)) {
                    removeProcess(Game.getInstance().getPlayer1().getItemsInCollectionNames(), name);
                    daric += itemSellCost(name);
                }
                else
                    System.out.println("This item can not be sold");
                break;
            case "minion" :
                name = Minion.findMinionNameByID(cardID);
                removeProcess(Game.getInstance().getPlayer1().getCardsInCollectionNames(),name);
                daric +=minionSellCost(name);
                break;
            case "spell" :
                name = Spell.findSpellNameByID(cardID);
                removeProcess(Game.getInstance().getPlayer1().getCardsInCollectionNames(),name);
                daric+=spellSellCost(name);
                break;
        }
        Game.getInstance().getPlayer1().setDaric(daric);
        System.out.println(name +" was sold successfully");
        System.out.println("Your daric now : " + daric);
    }
    public static boolean checkValidId(int id){
        return (id >= 101 && id <= 110)
                || (id >= 201 && id <= 220)
                || (id >= 301 && id <= 340)
                || (id >= 401 && id <= 420);
    }
    public static void sell(int cardType,int cardId) throws Exception{
        if(!checkValidId(cardType*100+cardId)){
            System.out.println("This ID is not valid");
        }
        else {
            switch (cardType) {
                case 1:
                    if (Deck.checkIfThisCardOrItemIsInCollection(cardId, "hero")) {
                        sellCardAndRemoveFromCollection(cardId,"hero");
                    }
                    else
                        System.out.println("This hero is not in collection");
                    break;
                case 2 :
                    if(Deck.checkIfThisCardOrItemIsInCollection(cardId,"item")){
                        sellCardAndRemoveFromCollection(cardId,"item");
                    }
                    else
                        System.out.println("This item is not in collection");
                    break;
                case 3 :
                    if(Deck.checkIfThisCardOrItemIsInCollection(cardId,"minion")){
                        sellCardAndRemoveFromCollection(cardId,"minion");
                    }
                    else
                        System.out.println("This minion is not in collection");
                    break;
                case 4 :
                    if (Deck.checkIfThisCardOrItemIsInCollection(cardId,"spell")){
                        sellCardAndRemoveFromCollection(cardId,"spell");
                    }
                    else
                        System.out.println("This spell is not in collection");
                    break;
            }
        }
    }
}

