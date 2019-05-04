package model.collection;

import controller.Controller;
import model.AllDatas;
import model.Game;
import model.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
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
    public static Object readPlayerFromFile(String filename) throws Exception{
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }
    public static void writePlayerToFile(String name,String password) throws Exception{
        JSONObject tempPlayer = new JSONObject();
        tempPlayer.put("username",name);
        tempPlayer.put("password",password);
        Files.write(Paths.get(PLAYERS_FOLDER+name+".json"),tempPlayer.toJSONString().getBytes());
    }
    public static boolean checkCorrectPassword(String name,String password) throws Exception{
        JSONObject jsonObject = (JSONObject) readPlayerFromFile(PLAYERS_FOLDER+name+".json");
        if(jsonObject.get("password").toString().matches(password))
            return true;
        return false;
    }
    public static void setPlayer(String name) throws Exception {
        JSONObject jsonObject = (JSONObject) readPlayerFromFile(PLAYERS_FOLDER+name+".json");
        Player player = new Player(
                (String) jsonObject.get("username"),
                (String) jsonObject.get("password"),
                15000
        );
        Game createGame = new Game();
        Game.setCurrentGame(createGame);
        Game.getInstance().setPlayer1(player);
    }
    public static void createAccount(String name, Scanner scanner) throws Exception{
        if(usernameAlreadyExists(name)){
            System.out.println("Username already exists");
        }
        else {
            System.out.println("Please enter password :");
            String password = scanner.nextLine();
            writePlayerToFile(name,password);
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

    public static boolean usernameAlreadyExists(String checkName){
        for (String name: players) {
            if(name.matches(checkName))
                return true;
        }
        return false;
    }
}
