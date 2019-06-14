package view;

import controller.Controller;
import controller.MenusCommandController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.AllDatas;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuView {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public static void showLoginMenu() throws FileNotFoundException {
        MainView.primaryStage.setScene(AllDatas.currentScene);

        setBackgroundOfMainMenu();

        Rectangle accountRectangle = new Rectangle(400, 300);
        accountRectangle.setX(200);
        accountRectangle.setY(200);
        accountRectangle.setFill(Color.rgb(129, 135, 145, 0.5));
        AllDatas.currentRoot.getChildren().add(accountRectangle);

        TextField email = new TextField("username");
        email.relocate(300, 250);
        TextField password = new TextField("password");
        password.relocate(300, 300);

        AllDatas.currentRoot.getChildren().addAll(email, password);

        Button loginButton = new Button("login");
        Button createAccountButton = new Button("create account");
        loginButton.setLayoutX(350);
        loginButton.setLayoutY(350);
        createAccountButton.setLayoutX(320);
        createAccountButton.setLayoutY(400);

        AllDatas.currentRoot.getChildren().addAll(loginButton, createAccountButton);

        MenusCommandController.loginMenuEventHandler(loginButton, createAccountButton, email, password);

    }

    public static void showMainMenu() throws FileNotFoundException {
        MainView.primaryStage.setScene(AllDatas.currentScene);

        setBackgroundOfMainMenu();

        VBox vBox = new VBox();

        //collection, shop, battle, help, exit, logout
        Button shopButton = new Button("Shop");
        Button collectionButton = new Button("Collection");
        Button battleButton = new Button("Battle");
        Button helpButton = new Button("Help");
        Button exitButton = new Button("Exit");
        Button logoutButton = new Button("Logout");

        vBox.setSpacing(15);
        vBox.setMargin(shopButton, new Insets(10, 10, 10, 300));
        vBox.setMargin(collectionButton, new Insets(10, 10, 10, 300));
        vBox.setMargin(battleButton, new Insets(10, 10, 10, 300));
        vBox.setMargin(helpButton, new Insets(10, 10, 10, 300));
        vBox.setMargin(exitButton, new Insets(10, 10, 10, 300));
        vBox.setMargin(logoutButton, new Insets(10, 10, 10, 300));

        vBox.getChildren().addAll(shopButton, collectionButton, battleButton, helpButton, exitButton, logoutButton);
        AllDatas.currentRoot.getChildren().addAll(vBox);

    }

    public static void setBackgroundOfMainMenu() throws FileNotFoundException {
        Image background = new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/background@2x.jpg"));
        ImageView backgroundImageView = new ImageView(background);
        backgroundImageView.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        backgroundImageView.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());

        Image midground = new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/pillars_near@2x.png"));
        ImageView midgroundImageView = new ImageView(midground);
        midgroundImageView.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        midgroundImageView.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());


        Image foreground = new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/foreground@2x.png"));
        ImageView foregroundImageView = new ImageView(foreground);
        foregroundImageView.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        foregroundImageView.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());

        AllDatas.currentRoot.getChildren().addAll(backgroundImageView, midgroundImageView, foregroundImageView);


    }

    public static void showShop(){

    }

    public static void showCollection(){

    }

    public static void showBattle(){

    }
}
