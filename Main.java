import model.Deck;
import model.Player;
import model.collection.Account;
import model.Game;
import model.collection.HandleFiles;
import model.collection.Hero;
import model.collection.Minion;
import view.GameView;

import java.util.Scanner;

import static model.collection.HandleFiles.*;

public class Main {
    public static void main(String[] args) throws Exception {

        HandleFiles.createStringOfHeroes();
        HandleFiles.createStringOfItems();
        HandleFiles.createStringOfMinions();
        HandleFiles.createStringOfSpells();

        Player player = new Player("bahar");
        Game game = new Game();
        Game.setCurrentGame(game);
        game.setPlayer1(player);

        String cardName = Minion.findMinionNameByID(1);
        player.getCardsInCollectionNames().add(cardName);
        Deck.createDeck("deck");

        Deck.addCardOrItemToDeck(1, "minion", "deck");
        Deck deck = Deck.findDeckByName("deck");

        GameView.showDeck("deck");
    }
}
