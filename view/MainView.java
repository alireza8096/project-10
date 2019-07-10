package view;

import Audio.AudioPlayer;
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
import network.Client;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class MainView extends Application {
    private static Client client;
    public static Stage primaryStage;

    public static Properties properties = new Properties();

    public MainView() throws FileNotFoundException {
    }

    public static void setClient(Client client) {
        MainView.client = client;
    }

    public static Client getClient() {
        return client;
    }

    @Override
    public void start(Stage stage) throws IOException {

        Shop.setRightVBox(new VBox());
        Shop.setLeftVBox(new VBox());
        Shop.getRightVBox().setLayoutX(300);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage = stage;
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());

        AudioPlayer.getMenuAudio().play();
        Controller.enterLoginMenu();
        MenuView.showLoginMenu();
        stage.show();
    }
    public static void main(String[] args) throws Exception {
        Controller.createAll();
        client = new Client();
        launch(args);
    }

    public static ImageView getPhotoWithThisPath(String path) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(path));
        return new ImageView(image);

    }
}
