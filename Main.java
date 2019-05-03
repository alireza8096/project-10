import controller.Controller;
import model.*;
import model.collection.Account;
import model.collection.Buff;
import model.collection.HandleFiles;
import model.collection.Hero;
import model.collection.Minion;
import view.GameView;

import java.util.Scanner;

import static model.collection.HandleFiles.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Controller.createAll();
        while (AllDatas.gameBoolean) {
            Controller.handleCommands(scanner);
        }
    }
}
