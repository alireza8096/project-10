package network.battle;

import model.Player;

import java.io.PrintStream;
import java.net.Socket;

public class ClientForBattle {
    private Player player;
//    private Socket socket;
    private PrintStream dos;

    public ClientForBattle(Player player, PrintStream dos){
        this.player = player;
//        this.socket = socket;
        this.dos = dos;
    }

}
