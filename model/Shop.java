package model;

import model.collection.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Shop<T> {
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public static boolean checkIfMoneyIsEnough(String cardName) {
        int playersDaric = Game.getInstance().getPlayer1().getDaric();
        int price = 0;
        switch (returnCardTypeByName(cardName)) {
            case "hero":
                price += Hero.findHeroByName(cardName).getPrice();
                break;
            case "item":
                price += Item.findItemByName(cardName).getPrice();
                break;
            case "minion":
                price += Minion.findMinionByName(cardName).getPrice();
                break;
            case "spell":
                price += Spell.findCardByName(cardName).getPrice();
                break;
        }
        return playersDaric >= price;
    }

    public static boolean checkItemBuyingConditions() {
        return Game.getInstance().getPlayer1().getItemsInCollection().size() != 3;
    }

    public static void buyCardAndAddToCollection(String cardName) {
        int daric = Game.getInstance().getPlayer1().getDaric();
        switch (returnCardTypeByName(cardName)) {
            case "hero":
                daric -= Hero.findHeroByName(cardName).getPrice();
                Game.getInstance().getPlayer1().getHeroesInCollection().add(Hero.findHeroByName(cardName));
                break;
            case "item":
                daric -= Item.findItemByName(cardName).getPrice();
                Game.getInstance().getPlayer1().getItemsInCollection().add(Item.findItemByName(cardName));
                break;
            case "minion":
                daric -= Minion.findMinionByName(cardName).getPrice();
                Game.getInstance().getPlayer1().getCardsInCollection().add(Card.findCardByName(cardName));
                break;
            case "spell":
                daric -= Spell.findCardByName(cardName).getPrice();
                Game.getInstance().getPlayer1().getCardsInCollection().add(Card.findCardByName(cardName));
                break;
        }
        Game.getInstance().getPlayer1().setDaric(daric);
        System.out.println(cardName + " was added to your collection successfully");
        System.out.println("Remained daric : " + Game.getInstance().getPlayer1().getDaric());
    }

    public static boolean checkIfCardWithThisNameIsValid(String cardName) {
        if (Minion.thisCardIsMinion(cardName))
            return true;
        else if (Hero.thisCardIsHero(cardName))
            return true;
        else if (Spell.thisCardIsSpell(cardName))
            return true;
        else return Item.thisCardIsItem(cardName);
    }

    public static boolean itemAndUsable(String cardName) {
        for (Item item :
                Item.getItems()) {
            if (item.getName().matches(cardName)) {
                if (item.getItemType().matches("usable")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String returnCardTypeByName(String cardName) {
        if (Hero.thisCardIsHero(cardName))
            return "hero";
        else if (Item.thisCardIsItem(cardName))
            return "item";
        else if (Minion.thisCardIsMinion(cardName))
            return "minion";
        else if (Spell.thisCardIsSpell(cardName))
            return "spell";
        return "";
    }

    public static String returnCardTypeById(int id) {
        switch (id / 100) {
            case 1:
                return "hero";
            case 2:
                return "item";
            case 3:
                return "minion";
            case 4:
                return "spell";
        }
        return "";
    }

    public static void buy(String cardName) throws Exception {
        if (checkIfCardWithThisNameIsValid(cardName)) {
            if (Item.thisCardIsItem(cardName) && !itemAndUsable(cardName)) {
                System.out.println("This item is not usable");
            } else {
                if (checkIfMoneyIsEnough(cardName)) {
                    if (Item.thisCardIsItem(cardName)) {
                        if (checkItemBuyingConditions()) {
                            buyCardAndAddToCollection(cardName);
                        } else {
                            System.out.println("You have reached the limit of items in collection");
                        }
                    } else {
                        buyCardAndAddToCollection(cardName);
                    }
                } else {
                    System.out.println("Your money is not enough");
                }
            }
        } else
            System.out.println("This card is not available in shop");
    }

    public static <T> void removeProcess(ArrayList<T> cards, T card) {
        ArrayList<T> copyOfCards = new ArrayList<>(cards);
        for (T cardToFind : copyOfCards) {
            if (cardToFind.equals(card)) {
                cards.remove(card);
            }
        }
    }

    public static void sellCardAndRemoveFromCollection(int cardID) throws Exception {
        int daric = Game.getInstance().getPlayer1().getDaric();
        switch (cardID / 100) {
            case 1:
                Hero hero = Hero.findHeroByID(cardID);
                removeProcess(Game.getInstance().getPlayer1().getHeroesInCollection(), hero);
                daric += hero.getPrice();
                System.out.println(hero.getName() + " was sold successfully");
                break;
            case 2:
                Item item = Item.findItemByID(cardID);
                if (item.getItemType().matches("usable")) {
                    removeProcess(Game.getInstance().getPlayer1().getItemsInCollection(), item);
                    daric += item.getPrice();
                    System.out.println(item.getName() + " was sold successfully");
                } else
                    System.out.println("This item can not be sold");
                break;
            case 3:
                Minion minion = Minion.findMinionByID(cardID);
                removeProcess(Game.getInstance().getPlayer1().getCardsInCollection(), minion);
                daric += minion.getPrice();
                System.out.println(minion.getName() + " was sold successfully");
                break;
            case 4:
                Spell spell = Spell.findSpellByID(cardID);
                removeProcess(Game.getInstance().getPlayer1().getCardsInCollection(), spell);
                daric += spell.getPrice();
                System.out.println(spell.getName() + " was sold successfully");
                break;
        }
        Game.getInstance().getPlayer1().setDaric(daric);
        System.out.println("Your daric now : " + daric);
    }

    public static boolean checkValidId(int id) {
        return (id >= 101 && id <= 110)
                || (id >= 201 && id <= 220)
                || (id >= 301 && id <= 340)
                || (id >= 401 && id <= 420);
    }

    public static void sell(int cardId) throws Exception {
        if (!checkValidId(cardId)) {
            System.out.println("This ID is not valid");
        } else {
            switch (cardId / 100) {
                case 1:
                    if (Deck.checkIfThisCardOrItemIsInCollection(cardId)) {
                        sellCardAndRemoveFromCollection(cardId);
                    } else
                        System.out.println("This hero is not in collection");
                    break;
                case 2:
                    if (Deck.checkIfThisCardOrItemIsInCollection(cardId)) {
                        sellCardAndRemoveFromCollection(cardId);
                    } else
                        System.out.println("This item is not in collection");
                    break;
                case 3:
                    if (Deck.checkIfThisCardOrItemIsInCollection(cardId)) {
                        sellCardAndRemoveFromCollection(cardId);
                    } else
                        System.out.println("This minion is not in collection");
                    break;
                case 4:
                    if (Deck.checkIfThisCardOrItemIsInCollection(cardId)) {
                        sellCardAndRemoveFromCollection(cardId);
                    } else
                        System.out.println("This spell is not in collection");
                    break;
            }
        }
    }
}

