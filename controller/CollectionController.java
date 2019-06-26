package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.*;
import model.Game;
import model.collection.Card;
import model.collection.Hero;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import view.GameView;
import view.MenuView;

import javax.swing.text.DefaultEditorKit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static model.collection.Account.PLAYERS_FOLDER;

public class CollectionController {

    public static String createName(String[] commands, int start) {
        String cardName = "";
        for (int i = start; i < commands.length - 1; i++) {
            cardName = cardName + commands[i] + " ";
        }
        return cardName + commands[commands.length - 1];
    }

    public static void show(String[] commands) {
        if (commands.length == 1 && commands[0].compareToIgnoreCase("show") == 0) {
            Player player = Game.getInstance().getPlayer1();
            int num = player.getItemsInCollection().size() + player.getCardsInCollection().size() + player.getHeroesInCollection().size();
            if (num == 0) {
                System.out.println("No card or item is available in collection");
            } else {
                System.out.println("Heroes :");
                for (int i = 1; i <= player.getHeroesInCollection().size(); i++) {
                    System.out.println(i + " : " + GameView.showHero(player.getHeroesInCollection().get(i - 1)));
                }
                System.out.println("Items :");
                for (int i = 1; i <= player.getItemsInCollection().size(); i++) {
                    System.out.println(i + " : " + GameView.showItem(player.getItemsInCollection().get(i - 1)));
                }
                int counter = 1;
                System.out.println("Cards :");
                for (int i = 1; i <= player.getCardsInCollection().size(); i++) {
                    if (GameView.showCard(player.getCardsInCollection().get(i - 1)) != null) {
                        System.out.println(counter + " : " + GameView.showCard(player.getCardsInCollection().get(i - 1)));
                        counter++;
                    }
                }
                AllDatas.hasEnteredCollection = true;
            }
        }
    }

    public static void search(String[] commands) throws Exception {
        if (commands.length >= 2 && commands[0].compareToIgnoreCase("search") == 0) {
            String cardName = createName(commands, 1);
            if (Game.getInstance().getPlayer1().thisCardOrItemIsAvailableInCollection(cardName)) {
                System.out.println(GameView.searchTypeAndShow(cardName));
            }
            AllDatas.hasEnteredCollection = true;
        }
    }

    public static void save(String[] commands) {
        if (commands.length == 1 && commands[0].compareToIgnoreCase("save") == 0) {
            AllDatas.hasEnteredCollection = true;
            System.out.println("saved !");
        }
    }

    public static void createDeck(String[] commands) {
        if (commands.length >= 3 && commands[0].compareToIgnoreCase("create") == 0
                && commands[1].compareToIgnoreCase("deck") == 0) {
            String deckName = createName(commands, 2);
            Deck.createDeck(deckName);
            AllDatas.hasEnteredCollection = true;
        }
    }

    public static void deleteDeck(String[] commands) {
        if (commands.length >= 3 && commands[0].compareToIgnoreCase("delete") == 0
                && commands[1].compareToIgnoreCase("deck") == 0) {
            String deckName = createName(commands, 2);
            Deck.deleteDeck(deckName);
            AllDatas.hasEnteredCollection = true;
        }
    }

    public static void addToDeck(String[] commands, String command) throws Exception {
        if (commands[0].compareToIgnoreCase("add") == 0 && command.contains("to deck") && command.length() >= 5) {
            AllDatas.hasEnteredCollection = true;
            int id = Integer.parseInt(commands[1]);
            String deckName = "";
            d:
            for (int i = 0; i < commands.length; i++) {
                if (commands[i].equals("deck")) {
                    deckName = createName(commands, i + 1);
                    break d;
                }
            }
            Deck.addCardOrItemToDeck(id,deckName);
        }
    }

    public static void remove(String[] commands, String command) throws Exception {
        if (command.contains("remove") && command.contains("from deck") && command.length() >= 5) {
            int id = Integer.parseInt(commands[1]);
            String deckName = "";
            d:
            for (int i = 0; i < commands.length; i++) {
                if (commands[i].equals("deck")) {
                    deckName = createName(commands, i + 1);
                    break d;
                }
            }
            Deck.removeCardOrItemFromDeck(id,deckName);
            AllDatas.hasEnteredCollection = true;
        }
    }

    public static void validateDeck(String[] commands) throws CloneNotSupportedException {
        if (commands.length >= 3 && commands[0].compareToIgnoreCase("validate") == 0
                && commands[1].compareToIgnoreCase("deck") == 0) {
            String deckName = createName(commands, 2);
            if (Deck.validateDeck(deckName))
                System.out.println("This deck is valid");
            else
                System.out.println("This deck is not valid");
            AllDatas.hasEnteredCollection = true;
        }
    }

    public static void selectDeck(String[] commands) throws Exception {
        if (commands.length >= 3 && commands[0].compareToIgnoreCase("select") == 0
                && commands[1].compareToIgnoreCase("deck") == 0) {
            String deckName = createName(commands, 2);
            Deck.selectDeck(deckName);
            Hero hero = Deck.findDeckByName(deckName).getHeroInDeck();
            Game.getInstance().getMap().setFriendHero(hero);
            AllDatas.hasEnteredCollection = true;
        }
    }

    public static void showAllDecks(String[] commands) throws Exception {
        if (commands.length == 3 && commands[0].compareToIgnoreCase("show") == 0
                && commands[1].compareToIgnoreCase("all") == 0 && commands[2].compareToIgnoreCase("decks") == 0) {
            ArrayList<Deck> decks = new ArrayList<>(Game.getInstance().getPlayer1().getDecksOfPlayer());
            if (Game.getInstance().getPlayer1().getMainDeck() == null) {
                for (int i = 1; i <= decks.size(); i++) {
                    System.out.println(i + " : " + decks.get(i - 1).getDeckName() + " :");
                    GameView.showDeck(decks.get(i - 1).getDeckName());
                }
            } else {
                String mainDeck = Game.getInstance().getPlayer1().getMainDeck().getDeckName();
                System.out.println("1 : " + mainDeck + " :");
                GameView.showDeck(mainDeck);
                int counter = 2;
                for (Deck deck :
                        decks) {
                    if (!deck.getDeckName().matches(mainDeck)) {
                        System.out.println(counter + " : " + deck.getDeckName() + " :");
                        GameView.showDeck(deck.getDeckName());
                        counter++;
                    }
                }
            }
            AllDatas.hasEnteredCollection = true;
        }
    }

    public static void showDeckByName(String[] commands) throws Exception {
        if (commands.length >= 3 && commands[0].compareToIgnoreCase("show") == 0
                && commands[1].compareToIgnoreCase("deck") == 0) {
            String deckName = createName(commands, 2);
            GameView.showDeck(deckName);
            AllDatas.hasEnteredCollection = true;
        }
    }

    public static void help(String[] commands) {
        if (commands.length == 1 && commands[0].compareToIgnoreCase("help") == 0) {
            AllDatas.help.setParent(AllDatas.collection);
            AllDatas.help.setNowInThisMenu(true);
            AllDatas.collection.setNowInThisMenu(false);
            for (String commandName : AllDatas.collection.getCommandsForHelp()) {
                System.out.println(commandName);
            }
            AllDatas.hasEnteredCollection = true;
        }
    }

    /* these functions are for custom card making in next phases

    public static void writeHeroCard(String filename, int id, String name, int price, int healthPoint, int attackPower, String attackType, int attackRange, int mana, int coolDown) throws Exception {
        JSONObject hero = new JSONObject();
        hero.put("id", id);
        hero.put("name", name);
        hero.put("price", price);
        hero.put("healthPoint", healthPoint);
        hero.put("attackPower", attackPower);
        hero.put("attackType", attackType);
        hero.put("attackRange", attackRange);
        hero.put("mana", mana);
        hero.put("coolDown", coolDown);
        Files.write(Paths.get(filename), hero.toJSONString().getBytes());
    }
    public static void writeItem(String filename, int id, String name, String itemType, int price) throws Exception {
        JSONObject item = new JSONObject();
        item.put("id", id);
        item.put("name", name);
        item.put("itemType", itemType);
        item.put("price", price);
        Files.write(Paths.get(filename), item.toJSONString().getBytes());
    }
    public static void writeMinionCard(String filename, int id, String name, int price, int mana, int healthPoint, int attackPower, String attackType, int attackRange, String activationTime) throws Exception {
        JSONObject minion = new JSONObject();
        minion.put("id", id);
        minion.put("name", name);
        minion.put("price", price);
        minion.put("mana", mana);
        minion.put("healthPoint", healthPoint);
        minion.put("attackPower", attackPower);
        minion.put("attackType", attackType);
        minion.put("attackRange", attackRange);
        minion.put("activationTime", activationTime);
        Files.write(Paths.get(filename), minion.toJSONString().getBytes());
    }
    public static void writeSpellCard(String filename, int id, String name, int price, int mana) throws Exception {
        JSONObject spell = new JSONObject();
        spell.put("id", id);
        spell.put("name", name);
        spell.put("price", price);
        spell.put("mana", mana);
        Files.write(Paths.get(filename), spell.toJSONString().getBytes());
    }

    */

    public static void handleEventsOfCollectionOptions(Node showCardsButton, Node showDecksButton, Node backButton){
        showCardsButton.setOnMouseClicked(event -> MenuView.showCardsInCollection());
        showDecksButton.setOnMouseClicked(event -> MenuView.showDecksInCollection());
        backButton.setOnMouseClicked(event -> {
            try {
                Controller.enterMainMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public static void handleEventsOfShowCardsButtons(StackPane createDeck, StackPane back){
        createDeck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Todo : create deck of player
            }
        });

        back.setOnMouseClicked(event -> {
            try {
                Controller.enterCollection();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public static void handleEventsOfShowingDeckButtons(StackPane selectDeck, StackPane back){
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    MenuView.showOptionsInCollection();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        selectDeck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
    }

    public static void handleEventsOfSellingCard(HBox cardHBox, HBox buttonsHBox,
                                                 ImageView cancelButton, StackPane sellButton, Card card, VBox cardVBox, HBox inRowHBox){
        cancelButton.setOnMouseClicked(event -> {
            AllDatas.currentRoot.getChildren().removeAll(cardHBox, buttonsHBox);
            MenuView.removeBlurEffectOfWindow();
            Shop.setIsShowingSpecificCard(false);
        });

        sellButton.setOnMouseClicked(event -> {
            inRowHBox.getChildren().remove(cardVBox);
            try {
                Shop.sellCardAndRemoveFromCollection(card.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            AllDatas.currentRoot.getChildren().removeAll(cardHBox, buttonsHBox, cardVBox);
            MenuView.removeBlurEffectOfWindow();
            Shop.setIsShowingSpecificCard(false);

        });
    }
}