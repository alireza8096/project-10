package network;

import view.MainView;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

//    private MainView mainView;
    private Socket socket;
    private Scanner dis;
    private PrintStream dos;

//    public MainView getMainView() {
//        return mainView;
//    }

    public PrintStream getDos() {
        return dos;
    }

    public Scanner getDis() {
        return dis;
    }

    public Socket getSocket() {
        return socket;
    }

    public Client() throws IOException {
        this.socket = new Socket("localhost",7766);
//        mainView = new MainView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dis = new Scanner(socket.getInputStream());
                    dos = new PrintStream(socket.getOutputStream());
                    while (true){
                        if (dis.hasNext()){
                            Message.stringToMessage(dis.nextLine()).handleMessageReceivedByClient();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
