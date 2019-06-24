package view;

import controller.Controller;
import javafx.application.Application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.AllDatas;
import model.Shop;

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
        Shop.setRightVBox(new VBox());
        Shop.setLeftVBox(new VBox());
        Shop.getRightVBox().setLayoutX(300);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage = stage;
//        primaryStage.setMaximized(true);
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());

        Controller.enterLoginMenu();
        MenuView.showLoginMenu();
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
