import controller.Controller;
import model.AllDatas;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Controller.createAll();
        Scanner scanner = new Scanner(System.in);
        while (AllDatas.gameBoolean){
            Controller.handleCommands(scanner);
        }
    }
}
