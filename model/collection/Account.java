package model.collection;

import model.AllDatas;
import model.Deck;
import model.Game;
import model.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.print.DocFlavor;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Account {
    public static final String PLAYERS_FOLDER = "/Users/hamilamailee/Documents/Duelyst Project/model/players/";
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
        tempPlayer.put("daric",15000);
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
    /////////////////////

    public static void writePlayerThatHasPlayedBefore(Player player) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",player.getUserName());
        jsonObject.put("password",player.getPassword());
        jsonObject.put("daric",player.getDaric());
        jsonObject.put("numOfWins",player.getNumOfwins());
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
        String list = deck.getDeckName();
        list = "," + deck.getHeroInDeckName();
        for (String item:
                deck.getItemsInDeckNames()) {
            list = "," + item;
        }
        for(String card : deck.getCardsInDeckNames()){
            list = "," + card;
        }
        return list;
    }
    public static Deck createDeckFromString(String deckString){
        String[] parts = deckString.split(",");
        Deck deck = new Deck(parts[0]);
        deck.setHeroInDeckName(parts[1]);
        for(int i=2; i<parts.length; i++){
            if(Item.thisCardIsItem(parts[i])){
                deck.getItemsInDeckNames().add(parts[i]);
            }
            else{
                deck.getCardsInDeckNames().add(parts[i]);
            }
        }
        return deck;
    }
    public static void createCollectionFromString(Player player,String collection){
        String[] parts = collection.split(",");
        for(int i=0; i<parts.length; i++){
            if(Hero.thisCardIsHero(parts[i])){
                player.getHeroesInCollectionName().add(parts[i]);
            }
            else if(Item.thisCardIsItem(parts[i])){
                player.getItemsInCollectionNames().add(parts[i]);
            }
            else{
                player.getCardsInCollectionNames().add(parts[i]);
            }
        }
    }

    public static String returnStringOfCollection(Player player){
        String list = player.getHeroesInCollectionName().get(0);
        for(int i=1; i<player.getHeroesInCollectionName().size(); i++){
            list = list +","+ player.getHeroesInCollectionName().get(i);
        }
        for (String item:
                player.getItemsInCollectionNames()) {
            list = list + "," + item;
        }
        for (String card:
                player.getCardsInCollectionNames()) {
            list = list + "," + card;
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
                    (String) jsonObject.get("password"),
                    Integer.parseInt(jsonObject.get("daric").toString())
            );
            player.setNumOfwins(Integer.parseInt(jsonObject.get("numOfWins").toString()));
            writeJustCreatedPlayerToFile((String) jsonObject.get("username"), (String) jsonObject.get("password"), "false");
            Game createGame = new Game();
            Game.setCurrentGame(createGame);
            Game.getInstance().setPlayer1(player);
        }
    }
    public static void setPlayerThatHasPlayedBefore(String name) throws Exception {
        JSONObject jsonObject = (JSONObject) readPlayerFromFile(PLAYERS_FOLDER + name + ".json");
        Player player;
        if(jsonObject.get("justCreated").toString().matches("false")){
            player = new Player(
                    (String) jsonObject.get("username"),
                    (String) jsonObject.get("password"),
                    Integer.parseInt((String)jsonObject.get("daric")));
            player.setNumOfwins(Integer.parseInt((String)jsonObject.get("numOfWins")));
            int numOfDecks = Integer.parseInt((String)jsonObject.get("numOfAllDecks"));
            for(int i=0; i<numOfDecks; i++){
                Deck addDeck = createDeckFromString((String)jsonObject.get("deck")+i);
                player.getDecksOfPlayer().add(addDeck);
            }
            player.setMainDeck(createDeckFromString((String)jsonObject.get("mainDeck")));
            createCollectionFromString(player,(String)jsonObject.get("collection"));
            Game createGame = new Game();
            Game.setCurrentGame(createGame);
            Game.getInstance().setPlayer1(player);
        }
    }
}
