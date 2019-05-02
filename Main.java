import controller.Controller;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Controller.createAll();
        while (true){
            Controller.handleCommands(scanner);
        }
    }
}
