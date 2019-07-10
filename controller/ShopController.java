package controller;

import com.google.gson.Gson;
import com.sun.tools.javac.Main;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.*;
import model.collection.*;
import model.Game;
import network.Client;
import network.Message;
import network.Server;
import org.json.simple.parser.ParseException;
import org.w3c.dom.events.EventException;
import view.GameView;
import view.MainView;
import view.MenuView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class ShopController {
    public static void showCollection(String[] commands) throws Exception{
         if(commands.length == 2 && commands[0].compareToIgnoreCase("show") == 0 &&
        commands[1].compareToIgnoreCase("collection") == 0){
            showHeroesInCollection();
            showItemsInCollection();
//            showCardsInCollection();
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

    public static void searchInCollection(String[] commands) throws Exception{
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
    public static void buy(String[] commands) throws Exception{
         if(commands.length>=2 && commands[0].compareToIgnoreCase("buy") == 0){
                AllDatas.hasEnteredShop = true;
                String cardName = CollectionController.createName(commands,1);
                Shop.buy(cardName);
         }
     }
    public static void sell(String[] commands) throws Exception{
         if(commands.length == 2 && commands[0].compareToIgnoreCase("sell")==0){
             int id = Integer.parseInt(commands[1]);
             Shop.sell(id);
             AllDatas.hasEnteredShop = true;
         }
     }
    public static void showShop(String[] commands) throws Exception{
        if(commands.length == 1 && commands[0].compareToIgnoreCase("show") == 0) {
            AllDatas.hasEnteredShop = true;
            System.out.println("Heroes :");
            for (int i = 1; i <= Hero.getHeroes().size(); i++) {
                System.out.println("        "+i + " : " + GameView.showHero(Hero.getHeroes().get(i - 1)));
            }
            System.out.println("Items :");
            for (int i = 1; i <= Item.getItems().size(); i++) {
                System.out.println("        "+i + " : " + GameView.showItem(Item.getItems().get(i - 1)));
            }
            System.out.println("Cards :");
            System.out.println("        Spells :");
            for (int i = 1; i <= Spell.getSpells().size(); i++) {
                System.out.println("                " + i + " : " + GameView.showSpell(Spell.getSpells().get(i - 1)));
            }
            System.out.println("        Minions :");
            for (int i = 1; i <= Minion.getMinions().size(); i++) {
                System.out.println("                " + i + " : " + GameView.showMinion(Minion.getMinions().get(i - 1)));
            }
        }
    }
    public static void help(String[] commands){
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
    public static void showHeroesInCollection(){
        ArrayList<Hero> heroesInCollection = new ArrayList<>(Game.getInstance().getPlayer1().getHeroesInCollection());
        System.out.println("Heroes :");
        for(int i=1; i<=heroesInCollection.size(); i++){
            System.out.println(i + " : " + GameView.showHero(heroesInCollection.get(i-1)));
        }
    }
    public static void showItemsInCollection(){
        ArrayList<Item> itemsInCollection = new ArrayList<>(Game.getInstance().getPlayer1().getItemsInCollection());
        System.out.println("Items :");
        for(int i=1; i<=itemsInCollection.size(); i++){
            System.out.println(i + " : " + GameView.showItem(itemsInCollection.get(i-1)));
        }
    }
 //TODO : omit omitNull :(
    public static<T> void omitNull(ArrayList<T> array){
        ArrayList<T> copy = new ArrayList<>(array);
        for (T object: copy) {
            if(object == null){
                array.remove(object);
            }
        }
    }

    public static void handleEventsOfShop(Hyperlink minions, Hyperlink spells, Hyperlink items, Hyperlink heroes){
        minions.setOnAction(event -> MenuView.showMinionsInShop());
        spells.setOnAction(event -> MenuView.showSpellsInShop());
        items.setOnAction(event -> MenuView.showItemsInShop());
        heroes.setOnAction(event -> MenuView.showHeroesInShop());
    }

    public static void handleEventsOfBuyingCard(VBox generalVBox , ImageView cancelButton, ImageView buyButton, Card card){
        cancelButton.setOnMouseClicked(event -> {
           AllDatas.currentRoot.getChildren().removeAll(generalVBox);
           MenuView.removeBlurEffectOfWindow();
           Shop.setIsShowingSpecificCard(false);
        });

        buyButton.setOnMouseClicked(event -> {
            try {
                if (!Deck.checkIfThisCardOrItemIsInCollection(card.getId())) {
                    if (Shop.checkIfMoneyIsEnough(card.getName())) {
                        Gson gson = new Gson();
                        String cardString = gson.toJson(card, Card.class);
                        Message message = new Message(cardString, "Card", "checkBuy");
                        MainView.getClient().getDos().println(message.messageToString());
                        MainView.getClient().getDos().flush();
                    }else{
                        GameView.printInvalidCommandWithThisContent("You don not have enough money");
                    }
                }else{
                    GameView.printInvalidCommandWithThisContent("This card is already available in collection!");
                }
                AllDatas.currentRoot.getChildren().removeAll(generalVBox);
                MenuView.removeBlurEffectOfWindow();
                Shop.setIsShowingSpecificCard(false);

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void handleEventsOfSearchingInShop(StackPane searchButton, StackPane backButton, TextField searchBox){
        backButton.setOnMouseClicked(event -> {
            try {
                Controller.enterShop();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        searchButton.setOnMouseClicked(event -> {
            String cardName = searchBox.getText();
            if (Shop.checkIfCardWithThisNameIsValid(cardName)){
                try {
                    MenuView.showSearchedCard(cardName);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }else{
                GameView.printInvalidCommandWithThisContent("This card isn't available in shop!");
            }
        });
    }

    public static void handleEventsOfAuctionWindow(StackPane auctionButton, StackPane seeCardsButton, StackPane backButton){
        backButton.setOnMouseClicked(event -> {
            try {
                Controller.enterShop();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        seeCardsButton.setOnMouseClicked(event -> {
            Gson gson = new Gson();
            String jsonString = gson.toJson("seeAuctionedCard", String.class);
            Message message = new Message(jsonString, "String", "seeAuctionedCard");
            MainView.getClient().getDos().println(message.messageToString());
            MainView.getClient().getDos().flush();

//                MenuView.seeCardsInAuction();
        });

        auctionButton.setOnMouseClicked(event -> {
            try {
                MenuView.showCardsForAuction();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    public static void handleEventsOfAddingPriceForAuction(StackPane backButton,
                                                           StackPane addPriceButton, TextField price, Card card){
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    MenuView.seeCardsInAuction();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        addPriceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int offeredPrice = Integer.parseInt(price.getText());
                int currentPrice = card.getHighestAuctionPrice();

//                if (offeredPrice > currentPrice){
//                    card.setHighestPriceUser(MainView.getClient().getDos());
//                    card.setHighestAuctionPrice(offeredPrice);
//                }
//                System.out.println("%%%%%%%%%%%%% card : " + card.getName() + " , price : " + card.getHighestAuctionPrice());
                Gson gson = new Gson();
//                String jsonStr = gson.toJson("string", String.class);
//                Message message1 = new Message(jsonStr, "String", "printCards");
//                MainView.getClient().getDos().println(message1.messageToString());
//                MainView.getClient().getDos().flush();

                card.setAuctionPrice(offeredPrice);
                String jsonString = gson.toJson(card, Card.class);
                Message message = new Message(jsonString, "Card", "offeredPrice");
                MainView.getClient().getDos().println(message.messageToString());
                MainView.getClient().getDos().flush();
            }
        });
    }


    public static void setCardInCollectionAfterReceiving(Card card){
        for (Card card1 : Game.getInstance().getPlayer1().getCardsInCollection()){
            if (card1.getName().equals(card.getName())) {
                card.setTimer(card1.getTimer());
                card1.setHighestAuctionPrice(card.getHighestAuctionPrice());
                card1.setHighestAuctionPriceProperty(card1.getHighestAuctionPrice());
            }
        }
        for (Card card1 : Game.getInstance().getPlayer1().getItemsInCollection()){
            if (card1.getName().equals(card.getName())) {
                card.setTimer(card1.getTimer());
                card1.setHighestAuctionPrice(card.getHighestAuctionPrice());
                card1.setHighestAuctionPriceProperty(card1.getHighestAuctionPrice());
            }
        }
        for (Card card1 : Game.getInstance().getPlayer1().getHeroesInCollection()){
            if (card1.getName().equals(card.getName())){
                card.setTimer(card1.getTimer());
                card1.setHighestAuctionPrice(card.getHighestAuctionPrice());
                card1.setHighestAuctionPriceProperty(card1.getHighestAuctionPrice());
            }
        }
    }

    public static void setCardsInAuction(ArrayList<String> cardNames) throws CloneNotSupportedException {
        Shop.getCardsInAuction().clear();
        for (String cardName : cardNames){
            Card card = Card.findCardByName(cardName);
            System.out.println("_________");
            System.out.println("card name : " + card.getName());
            Shop.getCardsInAuction().add(card);
        }
    }

    public static void sellCardInAuction(Card card){
        System.out.println("selling card " + card.getName());
        int price = card.getHighestAuctionPrice();

        System.out.println("1. highest price : " + price);
        Game.getInstance().getPlayer1().setDaric(Game.getInstance().getPlayer1().getDaric() + price);
//        Game.getInstance().getPlayer1().setDaricProperty(Game.getInstance().getPlayer1().getDaric());

        ArrayList<Card> copyCard = new ArrayList<>(Game.getInstance().getPlayer1().getCardsInCollection());
        for (Card card1 : copyCard){
            if (card1.getName().equals(card.getName())){
                Game.getInstance().getPlayer1().getCardsInCollection().remove(card1);
            }

        }

        copyCard = new ArrayList<>(Game.getInstance().getPlayer1().getItemsInCollection());
        for (Card card1 : copyCard){
            if (card1.getName().equals(card.getName())){
                Game.getInstance().getPlayer1().getItemsInCollection().remove(card1);
            }

        }

        copyCard = new ArrayList<>(Game.getInstance().getPlayer1().getHeroesInCollection());
        for (Card card1 : copyCard){
            if (card1.getName().equals(card.getName())){
                Game.getInstance().getPlayer1().getHeroesInCollection().remove(card1);
            }

        }
    }

    public static void buyCardInAuction(Card card){
        System.out.println("buying card " + card.getName());
        int price = card.getHighestAuctionPrice();

        System.out.println("2. highest price : " + price);
        Game.getInstance().getPlayer1().setDaric(Game.getInstance().getPlayer1().getDaric() - price);
//        Game.getInstance().getPlayer1().setDaricProperty(Game.getInstance().getPlayer1().getDaric());

        switch (card.getCardType()){
            case "minion":
            case "spell":
                Game.getInstance().getPlayer1().getCardsInCollection().add(card);
                break;
            case "hero":
                Game.getInstance().getPlayer1().getHeroesInCollection().add((Hero)card);
                break;
            case "item":
                Game.getInstance().getPlayer1().getItemsInCollection().add((Item)card);
                break;

        }
    }
}
