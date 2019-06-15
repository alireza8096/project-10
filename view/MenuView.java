package view;

import controller.Controller;
import controller.MenusCommandController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.AllDatas;
import model.LinkedListMenus;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuView {

    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;

    public static void showLoginMenu() throws FileNotFoundException {
        MainView.primaryStage.setScene(AllDatas.currentScene);

        setBackgroundOfMainMenu();

        Rectangle accountRectangle = new Rectangle(400, 300);
        accountRectangle.setX(300);
        accountRectangle.setY(300);
        accountRectangle.setFill(Color.rgb(129, 135, 145, 0.5));
        AllDatas.currentRoot.getChildren().add(accountRectangle);

        TextField email = new TextField("username");
        email.relocate(420, 350);
        email.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                email.clear();
            }
        });
        TextField password = new TextField("password");
        password.relocate(420, 400);
        password.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                password.clear();
            }
        });

        AllDatas.currentRoot.getChildren().addAll(email, password);

        Button loginButton = new Button("login");
        Button createAccountButton = new Button("create account");
        loginButton.setLayoutX(470);
        loginButton.setLayoutY(450);
        createAccountButton.setLayoutX(440);
        createAccountButton.setLayoutY(500);

        AllDatas.currentRoot.getChildren().addAll(loginButton, createAccountButton);

        MenusCommandController.loginMenuEventHandler(loginButton, createAccountButton, email, password);

    }

    public static void showMainMenu() throws FileNotFoundException {
        MainView.primaryStage.setScene(AllDatas.currentScene);

        setBackgroundOfMainMenu();

        VBox vBox = new VBox();

        Hyperlink shopOption = new Hyperlink("Shop");
        shopOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        shopOption.setTextFill(Color.WHITE);
        shopOption.setOnAction(event -> Controller.enterShop());

        Image shopIcon = new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/shop@2x.png"));
        ImageView shopIconImage = new ImageView(shopIcon);
        shopIconImage.setFitWidth(40);
        shopIconImage.setFitHeight(40);
        HBox shopHBox = new HBox(shopIconImage, shopOption);

        Hyperlink collectionOption = new Hyperlink("Collection");
        collectionOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        collectionOption.setTextFill(Color.WHITE);
        collectionOption.setOnAction(event -> Controller.enterColllection());

        Hyperlink battleOption = new Hyperlink("Battle");
        battleOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        battleOption.setTextFill(Color.WHITE);
        battleOption.setOnAction(event -> Controller.enterBattle());

        Hyperlink exitOption = new Hyperlink("Exit");
        exitOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        exitOption.setTextFill(Color.WHITE);
        exitOption.setOnAction(event -> {
            //Todo : handle exit from main menu
        });

        Hyperlink logoutOption = new Hyperlink("Logout");
        logoutOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        logoutOption.setTextFill(Color.WHITE);
        logoutOption.setOnAction(event -> {
            //Todo : handle logging out from current account
        });

        Hyperlink helpOption = new Hyperlink("Help");
        helpOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        helpOption.setTextFill(Color.WHITE);
        helpOption.setOnAction(event -> {
            //Todo : handle help in each menu
        });



        vBox.setSpacing(15);
        vBox.setMargin(shopHBox, new Insets(40, 10, 10, 100));
        vBox.setMargin(collectionOption, new Insets(7, 10, 10, 100));
        vBox.setMargin(battleOption, new Insets(7, 10, 10, 100));
        vBox.setMargin(helpOption, new Insets(7, 10, 10, 100));
        vBox.setMargin(exitOption, new Insets(7, 10, 10, 100));
        vBox.setMargin(logoutOption, new Insets(7, 10, 10, 100));

        vBox.getChildren().addAll(shopHBox, collectionOption, battleOption, helpOption, exitOption, logoutOption);
        AllDatas.currentRoot.getChildren().addAll(vBox);

        MenusCommandController.handleEventsOfMainMenu(shopOption, collectionOption, battleOption, helpOption, exitOption, logoutOption);

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

    public static void showThisMenu(LinkedListMenus menu) throws FileNotFoundException {
        String menuName = menu.getMenuName();
        switch (menuName){
            case "Account":
                showLoginMenu();
                break;
            case "Leaderboard":
                //Todo : show leaderboard menu
                break;
            case "Command Line":
                showMainMenu();
                break;
            case "Collection":
                showCollection();
                break;
            case "Shop":
                showShop();
                break;
            case "Battle":
                showBattle();
                break;
        }
    }
}
