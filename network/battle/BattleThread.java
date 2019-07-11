package network.battle;

import com.google.gson.Gson;
import controller.Controller;
import javafx.application.Platform;
import model.Game;
import model.Map;
import model.Player;
import network.Message;
import network.Server;
import org.ietf.jgss.GSSContext;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class BattleThread {
    //    private static ClientForBattle[] battleThreads = new ClientForBattle[2];
//    private static ArrayList<ClientForBattle> clientsForBattle = new ArrayList<>();
    private static ClientForBattle clientForBattle1;
//    private static ClientForBattle clientForBattle2;

    private ClientForBattle client1;
    private ClientForBattle client2;

    public static ClientForBattle getClientForBattle1() {
        return clientForBattle1;
    }

    public static void setClientForBattle1(ClientForBattle clientForBattle1) {
        BattleThread.clientForBattle1 = clientForBattle1;
    }

    public ClientForBattle getClient1() {
        return client1;
    }

    public void setClient1(ClientForBattle client1) {
        this.client1 = client1;
    }

    public ClientForBattle getClient2() {
        return client2;
    }

    public void setClient2(ClientForBattle client2) {
        this.client2 = client2;
    }

    public BattleThread(ClientForBattle client1, ClientForBattle client2){
        this.client1 = client1;
        this.client2 = client2;
        System.out.println("battle started!!!!1");

        sendBattleInfoForPlayers();


    }

    public void sendBattleInfoForPlayers(){
        Gson gson = new Gson();
        String player1Name = client1.getPlayer().getUserName();
        String player2Name = client2.getPlayer().getUserName();

        String name1JsonString = gson.toJson(player1Name, String.class);
        Message name1Message = new Message(name1JsonString, "String", "opponentName");
        client2.getDos().println(name1Message.messageToString());
        client2.getDos().flush();

        String name2JsonString = gson.toJson(player2Name, String.class);
        Message name2Message = new Message(name2JsonString, "String", "opponentName");
        client1.getDos().println(name2Message.messageToString());
        client1.getDos().flush();

        Map map = new Map();
        String jsonString = gson.toJson(map, Map.class);
        Message message = new Message(jsonString, "Map", "getFirstMap");
        client1.getDos().println(message.messageToString());
        client1.getDos().flush();
        client2.getDos().println(message.messageToString());
        client2.getDos().flush();
    }
}
