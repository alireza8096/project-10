package view;

import controller.Controller;
import javafx.application.Application;

import javafx.stage.Stage;
import model.AllDatas;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainView extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        primaryStage = stage;

        Controller.enterLoginMenu();
        MenuView.showLoginMenu();
        stage.show();
    }
    public static void main(String[] args) throws Exception {
        Controller.createAll();
//        launch(args);
        Scanner scanner = new Scanner(System.in);
        while (AllDatas.gameBoolean){
            Controller.handleCommands(scanner);
        }
    }
}
