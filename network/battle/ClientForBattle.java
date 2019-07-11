package network.battle;

import model.Player;

import java.io.PrintStream;
import java.net.Socket;

public class ClientForBattle {
    private Player player;
    private Socket socket;
    private PrintStream dos;

    public ClientForBattle(Player player, Socket socket, PrintStream dos){
        this.player = player;
        this.socket = socket;
        this.dos = dos;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintStream getDos() {
        return dos;
    }

    public void setDos(PrintStream dos) {
        this.dos = dos;
    }
}
