package view;

import controller.Controller;
import javafx.application.Application;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.AllDatas;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainView extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws FileNotFoundException, CloneNotSupportedException {
        primaryStage = stage;
        primaryStage.setMaximized(true);

//        Controller.enterLoginMenu();
//        MenuView.showLoginMenu();
        Controller.enterBattle();
        MenuView.showBattle();
        stage.show();
    }
    public static void main(String[] args) throws Exception {
        Controller.createAll();
        launch(args);
        Scanner scanner = new Scanner(System.in);
        while (AllDatas.gameBoolean){
            Controller.handleCommands(scanner);
        }
    }

    public static ImageView getPhotoWithThisPath(String path) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(path));
        return new ImageView(image);

    }
}
