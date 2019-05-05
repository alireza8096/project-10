package controller;

import model.*;
import model.Game;
import model.collection.Hero;
import org.json.simple.parser.ParseException;
import view.GameView;

import java.io.IOException;
import java.util.ArrayList;

public interface CollectionController {

    static String createName(String[] commands, int start) {
        String cardName = "";
        for (int i = start; i < commands.length - 1; i++) {
            cardName = cardName + commands[i] + " ";
        }
        return cardName + commands[commands.length - 1];
    }

    static void show(String[] commands) throws Exception {
        if (commands.length == 1 && commands[0].compareToIgnoreCase("show") == 0) {
            Player player = Game.getInstance().getPlayer1();
            int num = player.getItemsInCollectionNames().size() + player.getCardsInCollectionNames().size() + player.getHeroesInCollectionName().size();
            if (num == 0) {
                System.out.println("No card or item is available in collection");
            } else {
                System.out.println("Heroes :");
                for (int i = 1; i <= player.getHeroesInCollectionName().size(); i++) {
                    System.out.println(i + " : " + GameView.showHero(player.getHeroesInCollectionName().get(i - 1)));
                }
                System.out.println("Items :");
                for (int i = 1; i <= player.getItemsInCollectionNames().size(); i++) {
                    System.out.println(i + " : " + GameView.showItem(player.getItemsInCollectionNames().get(i - 1)));
                }
                System.out.println("Cards :");
                for (int i = 1; i <= player.getCardsInCollectionNames().size(); i++) {
                    System.out.println(i + " : " + GameView.showCard(player.getCardsInCollectionNames().get(i - 1)));
                }
                AllDatas.hasEnteredCollection = true;
            }
        }
    }

    static void search(String[] commands) throws Exception {
        if (commands.length >= 2 && commands[0].compareToIgnoreCase("search") == 0) {
            String cardName = createName(commands, 1);
            if (Game.getInstance().getPlayer1().thisCardOrItemIsAvailableInCollection(cardName)) {
                System.out.println(GameView.searchTypeAndShow(cardName));
            }
            AllDatas.hasEnteredCollection = true;
        }
    }

    static void save(String[] commands) {
        if (commands.length == 1 && commands[0].compareToIgnoreCase("save") == 0) {
            AllDatas.hasEnteredCollection = true;
            System.out.println("saved !");
        }
    }

    static void createDeck(String[] commands) {
        if (commands.length >= 3 && commands[0].compareToIgnoreCase("create") == 0
                && commands[1].compareToIgnoreCase("deck") == 0) {
            String deckName = createName(commands, 2);
            Deck.createDeck(deckName);
            AllDatas.hasEnteredCollection = true;
        }
    }

    static void deleteDeck(String[] commands) {
        if (commands.length >= 3 && commands[0].compareToIgnoreCase("delete") == 0
                && commands[1].compareToIgnoreCase("deck") == 0) {
            String deckName = createName(commands, 2);
            Deck.deleteDeck(deckName);
            AllDatas.hasEnteredCollection = true;
        }
    }

    static void addToDeck(String[] commands, String command) throws Exception {
        if (command.contains("add") && command.contains("to deck") && command.length() >= 5) {
            AllDatas.hasEnteredCollection = true;
            int id = Integer.parseInt(commands[1])%100;
            int type = Integer.parseInt(commands[1])/100;
            String deckName = "";
            d:
            for (int i = 0; i < commands.length; i++) {
                if (commands[i].equals("deck")) {
                    deckName = createName(commands,i+1);
                    break d;
                }
            }
            switch (type) {
                case 1:
                    Deck.addCardOrItemToDeck(id, "hero", deckName);
                    break;
                case 2:
                    Deck.addCardOrItemToDeck(id, "item", deckName);
                    break;
                case 3:
                    Deck.addCardOrItemToDeck(id, "minion", deckName);
                    break;
                case 4:
                    Deck.addCardOrItemToDeck(id, "spell", deckName);
                    break;
                default:
                    System.out.println("Type is not valid");
                    break;
            }
        }
    }

    static void remove(String[] commands, String command) throws Exception {
        if (command.contains("remove") && command.contains("from deck") && command.length() >= 5) {
            int id = Integer.parseInt(commands[1])%100;
            int type = Integer.parseInt(commands[1])/100;
            String deckName = "";
            d:
            for (int i = 0; i < commands.length; i++) {
                if (commands[i].equals("deck")) {
                    deckName = createName(commands, i + 1);
                    break d;
                }
            }
            switch (type) {
                case 1:
                    Deck.removeCardOrItemFromDeck(id, "hero", deckName);
                    break;
                case 2:
                    Deck.removeCardOrItemFromDeck(id, "item", deckName);
                    break;
                case 3:
                    Deck.removeCardOrItemFromDeck(id, "minion", deckName);
                    break;
                case 4:
                    Deck.removeCardOrItemFromDeck(id, "spell", deckName);
                    break;
                default:
                    System.out.println("Type is not valid");
            }
            AllDatas.hasEnteredCollection = true;
        }
    }

    static void validateDeck(String[] commands) {
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

    static void selectDeck(String[] commands) throws IOException, ParseException {
        if (commands.length >= 3 && commands[0].compareToIgnoreCase("select") == 0
                && commands[1].compareToIgnoreCase("deck") == 0) {
            String deckName = createName(commands, 2);
            Deck.selectDeck(deckName);
            Hero hero =(Hero) Hero.getCardByName(Deck.findDeckByName(deckName).getHeroInDeckName());
            Game.getInstance().setHeroOfPlayer1(hero);
            AllDatas.hasEnteredCollection = true;
        }
    }

    static void showAllDecks(String[] commands) throws Exception {
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

    static void showDeckByName(String[] commands) throws Exception {
        if (commands.length >= 3 && commands[0].compareToIgnoreCase("show") == 0
                && commands[1].compareToIgnoreCase("deck") == 0) {
            String deckName = createName(commands, 2);
            GameView.showDeck(deckName);
            AllDatas.hasEnteredCollection = true;
        }
    }

    static void help(String[] commands) {
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
}