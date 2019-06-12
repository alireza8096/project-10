package model.collection;

import controller.AI;
import javafx.scene.layout.CornerRadii;
import model.AllDatas;
import model.Deck;
import model.Game;
import model.Map;
import model.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.print.DocFlavor;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Inet4Address;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
//testing
public class Account {
    public static final String PLAYERS_FOLDER = "/Users/hamilamailee/Documents/project-10/model/collection/players/";
    private static ArrayList<String> players = new ArrayList<>();
    public static ArrayList<String> getPlayers() {
        return players;
    }

    public static void createAccount(String name, Scanner scanner) throws Exception{
        if(usernameAlreadyExists(name)){
            System.out.println("Username already exists");
        }
        else {
            System.out.println("Please enter password :");
            String password = scanner.nextLine();
            writeJustCreatedPlayerToFile(name,password,"true");
            players.add(name);
        }
    }
    public static void login(String name,Scanner scanner) throws Exception{
        if(!usernameAlreadyExists(name)){
            System.out.println("Invalid username");
        }
        else{
            System.out.println("Please enter password :");
            String password = scanner.nextLine();
            if(checkCorrectPassword(name,password)){
                setPlayer(name);
                AllDatas.account.setNowInThisMenu(false);
                AllDatas.commandLine.setNowInThisMenu(true);
            }
            else
                System.out.println("Password is not correct");
        }
    }
    public static Object readPlayerFromFile(String filename) throws Exception{
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }
    /*****/

    public static void writeJustCreatedPlayerToFile(String name,String password,String justCreated) throws IOException {
        JSONObject tempPlayer = new JSONObject();
        tempPlayer.put("username",name);
        tempPlayer.put("password",password);
        tempPlayer.put("daric",150000000);
        tempPlayer.put("numOfWins",0);
        tempPlayer.put("justCreated",justCreated);
        Files.write(Paths.get(PLAYERS_FOLDER+name+".json"),tempPlayer.toJSONString().getBytes());
    }
    public static boolean usernameAlreadyExists(String checkName){
        for (String name: players) {
            if(name.matches(checkName))
                return true;
        }
        return false;
    }


    public static void writePlayerThatHasPlayedBefore(Player player) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",player.getUserName());
        jsonObject.put("password",player.getPassword());
        jsonObject.put("daric",player.getDaric());
        jsonObject.put("numOfWins",player.getNumOfWins());
        jsonObject.put("numOfWins",player.getNumOfWins());
        jsonObject.put("justCreated","false");
        jsonObject.put("numOfAllDecks",player.getDecksOfPlayer().size());
        for(int i=0; i<player.getDecksOfPlayer().size(); i++){
            jsonObject.put("deck"+i,returnStringOfDeck(player.getDecksOfPlayer().get(i)));
        }
        jsonObject.put("mainDeck",returnStringOfDeck(player.getMainDeck()));
        jsonObject.put("collection",returnStringOfCollection(player));
        Files.write(Paths.get(PLAYERS_FOLDER + player.getUserName()+".json"),jsonObject.toJSONString().getBytes());
    }
    public static boolean checkCorrectPassword(String name,String password) throws Exception{
        JSONObject jsonObject = (JSONObject) readPlayerFromFile(PLAYERS_FOLDER+name+".json");
        return jsonObject.get("password").toString().matches(password);
    }
    public static String returnStringOfDeck(Deck deck){
        String list = "";
        if(deck!=null) {
            list = deck.getDeckName();
            list = "," + deck.getHeroInDeck().name;
            for (Item item :
                    deck.getItemsInDeck()) {
                list = "," + item.getName();
            }
            for (Card card : deck.getCardsInDeck()) {
                list = "," + card.name;
            }
        }
        return list;
    }
    public static Deck createDeckFromString(String deckString){
        if(!deckString.matches("")) {
            String[] parts = deckString.split(",");
            Deck deck = new Deck(parts[0]);
            deck.setHeroInDeck(Hero.findHeroByName(parts[1]));
            for (int i = 2; i < parts.length; i++) {
                if (Item.thisCardIsItem(parts[i])) {
                    deck.getItemsInDeck().add(Item.findItemByName(parts[i]));
                } else {
                    deck.getCardsInDeck().add(Card.findCardByName(parts[i]));
                }
            }
            return deck;
        }
        return null;
    }
    public static void createCollectionFromString(Player player,String collection){
        System.out.println(collection);
        if(!collection.matches("")) {
            String[] parts = collection.split(",");
            for (int i = 0; i < parts.length; i++) {
                if (Hero.thisCardIsHero(parts[i])) {
                    player.getHeroesInCollection().add(Hero.findHeroByName(parts[i]));
                } else if (Item.thisCardIsItem(parts[i])) {
                    player.getItemsInCollection().add(Item.findItemByName(parts[i]));
                } else {
                    player.getCardsInCollection().add(Card.findCardByName(parts[i]));
                }
            }
        }
    }

    public static String returnStringOfCollection(Player player){
        String list = "";
        if(player.getHeroesInCollection().size()!=0) {
            list = player.getHeroesInCollection().get(0).name;
            for (int i = 1; i < player.getHeroesInCollection().size(); i++) {
                list = list + "," + player.getHeroesInCollection().get(i).name;
            }
        }
        for (Item item:
                player.getItemsInCollection()) {
            list = list + "," + item.getName();
        }
        for (Card card:
                player.getCardsInCollection()) {
            list = list + "," + card.name;
        }
        return list;
    }
    public static void setPlayer(String name) throws Exception {
        setJustCreatedPlayer(name);
        setPlayerThatHasPlayedBefore(name);
    }
    public static void setJustCreatedPlayer(String name) throws Exception {
        JSONObject jsonObject = (JSONObject) readPlayerFromFile(PLAYERS_FOLDER + name + ".json");
        Player player;
        if(jsonObject.get("justCreated").toString().matches("true")) {
            player = new Player(
                    (String) jsonObject.get("username"),
                    (String) jsonObject.get("password")
            );
            player.setDaric(Integer.parseInt(jsonObject.get("daric").toString()));
            player.setNumOfMana(2);
            player.setNumOfWins(Integer.parseInt(jsonObject.get("numOfWins").toString()));
            writePlayerThatHasPlayedBefore(player);
            Game createGame = new Game();
            Game.setCurrentGame(createGame);
//            model.Map map = new Map();
//            Game.getInstance().setMap(map);
            Game.getInstance().setPlayer1(player);
            Game.getInstance().setPlayer1Turn(true);
        }
    }
    public static void setPlayerThatHasPlayedBefore(String name) throws Exception {
        JSONObject jsonObject = (JSONObject) readPlayerFromFile(PLAYERS_FOLDER + name + ".json");
        Player player;
        if(jsonObject.get("justCreated").toString().matches("false")){
            player = new Player(
                    (String) jsonObject.get("username"),
                    (String) jsonObject.get("password"));
            player.setDaric(Integer.parseInt(jsonObject.get("daric").toString()));
            player.setNumOfWins(Integer.parseInt(jsonObject.get("numOfWins").toString()));
            int numOfDecks = Integer.parseInt(jsonObject.get("numOfAllDecks").toString());
            for(int i=0; i<numOfDecks; i++){
                Deck addDeck = createDeckFromString(jsonObject.get("deck").toString()+i);
                if(addDeck != null) {
                    player.getDecksOfPlayer().add(addDeck);
                }
            }
            if(jsonObject.get("mainDeck")!=null) {
                player.setMainDeck(createDeckFromString((String) jsonObject.get("mainDeck")));
            }
            player.setNumOfMana(2);
            createCollectionFromString(player,(String)jsonObject.get("collection"));
            Game createGame = new Game();
            Game.setCurrentGame(createGame);
            model.Map map = new Map();
            Game.getInstance().setMap(map);
            Game.getInstance().setPlayer1(player);
            Game.getInstance().setPlayer1Turn(true);
        }
    }
}
