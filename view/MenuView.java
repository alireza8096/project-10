package view;

import controller.MenusCommandController;
import controller.ShopController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.AllDatas;
import model.LinkedListMenus;
import model.Shop;
import model.collection.Card;
import model.collection.Minion;

import javax.net.ssl.SNIHostName;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuView {

    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;

    public static void showLoginMenu() throws FileNotFoundException {
        MainView.primaryStage.setScene(AllDatas.currentScene);
        MainView.primaryStage.setMaximized(true);

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
        MainView.primaryStage.setMaximized(true);

        setBackgroundOfMainMenu();

        VBox vBox = new VBox();

        Hyperlink shopOption = new Hyperlink("Shop");
        shopOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        shopOption.setTextFill(Color.WHITE);

        Image shopIcon = new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/shop@2x.png"));
        ImageView shopIconImage = new ImageView(shopIcon);
        shopIconImage.setFitWidth(40);
        shopIconImage.setFitHeight(40);
        HBox shopHBox = new HBox(shopIconImage, shopOption);

        Hyperlink collectionOption = new Hyperlink("Collection");
        collectionOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        collectionOption.setTextFill(Color.WHITE);

        Hyperlink battleOption = new Hyperlink("Battle");
        battleOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        battleOption.setTextFill(Color.WHITE);

        Hyperlink exitOption = new Hyperlink("Exit Game");
        exitOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        exitOption.setTextFill(Color.WHITE);

        Hyperlink logoutOption = new Hyperlink("Logout");
        logoutOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        logoutOption.setTextFill(Color.WHITE);

        Hyperlink helpOption = new Hyperlink("Help");
        helpOption.setFont(Font.font(java.awt.Font.SERIF, 25));
        helpOption.setTextFill(Color.WHITE);

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
        Image backgroundImage = new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/login" +
                "/Screen Shot 1398-03-25 at 16.32.26.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        backgroundImageView.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());

        AllDatas.currentRoot.getChildren().addAll(backgroundImageView);


    }

    //Todo : define a style for text and image
    public static void showShop() throws FileNotFoundException {
        MainView.primaryStage.setScene(AllDatas.currentScene);
        MainView.primaryStage.setMaximized(true);

        ImageView background = MainView.getPhotoWithThisPath("/Users/bahar/Desktop/DUELYST/view/Photos/shopBackground.jpg");
        background.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        background.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());
        AllDatas.currentRoot.getChildren().add(background);

        HBox minionsHBox = new HBox();
        ImageView minionIcon = MainView.getPhotoWithThisPath("/Users/bahar/Desktop/DUELYST/view/Photos/minion.png");
      //  minionIcon.getStyleClass().add("shopOptionsImage");
        minionIcon.setFitWidth(40);
        minionIcon.setFitHeight(40);
        Hyperlink minionText = new Hyperlink("Minions");
        minionsHBox.getChildren().addAll(minionIcon, minionText);

        HBox heroesHBox = new HBox();
        ImageView heroIcon = MainView.getPhotoWithThisPath("/Users/bahar/Desktop/DUELYST/view/Photos/Heroes.png");
        heroIcon.setFitWidth(40);
        heroIcon.setFitHeight(40);
        Hyperlink heroText = new Hyperlink("Heroes");
        heroesHBox.getChildren().addAll(heroIcon, heroText);

        HBox itemHBox = new HBox();
        ImageView itemIcon = MainView.getPhotoWithThisPath("/Users/bahar/Desktop/DUELYST/view/Photos/Item.png");
        itemIcon.setFitWidth(50);
        itemIcon.setFitHeight(50);
        Hyperlink itemText = new Hyperlink("Items");
        itemHBox.getChildren().addAll(itemIcon, itemText);

        HBox spellHBox = new HBox();
        ImageView spellIcon = MainView.getPhotoWithThisPath("/Users/bahar/Desktop/DUELYST/view/Photos/spell.png");
        spellIcon.setFitWidth(50);
        spellIcon.setFitHeight(50);
        Hyperlink spellText = new Hyperlink("Spells");
        spellHBox.getChildren().addAll(spellIcon, spellText);

        VBox.setMargin(minionsHBox, new Insets(50,100,10,30));
        VBox.setMargin(spellHBox, new Insets(10,100,10,30));
        VBox.setMargin(itemHBox, new Insets(10,100,10,30));
        VBox.setMargin(heroesHBox, new Insets(10,100,10,30));

        setScrollBar();

        Shop.getLeftVBox().getChildren().addAll(minionsHBox, spellHBox, itemHBox, heroesHBox);

        AllDatas.currentRoot.getChildren().addAll(Shop.getRightVBox(), Shop.getLeftVBox());

        ShopController.handleEventsOfShop(minionText, spellText, itemText, heroText);

    }

    public static void setScrollBar(){
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setLayoutX(AllDatas.currentScene.getWidth()-scrollBar.getWidth());
        scrollBar.setMin(0);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(AllDatas.currentScene.getHeight());
//        scrollBar.setMax(400);

//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setContent(Shop.getRightVBox());
//        scrollPane.setPrefViewportHeight(800);
//        scrollPane.setPrefViewportWidth(1000);


        AllDatas.currentRoot.getChildren().add(scrollBar);

    //   AllDatas.currentRoot.getChildren().add(scrollBar);

        scrollBar.valueProperty().addListener((ov, old_val, new_val) -> Shop.getRightVBox().setLayoutY(-new_val.doubleValue()));
    }

    public static void showBattle(){
        MainView.primaryStage.setScene(AllDatas.currentScene);
        MainView.primaryStage.setMaximized(true);

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
                //showCollection();
                break;
            case "Shop":
                showShop();
                break;
            case "Battle":
                showBattle();
                break;
        }
    }

    public static void showMinionsInShop() throws FileNotFoundException {
        setVBoxForShowingMinionCards();
    }

    public static void showSpellsInShop(){

    }

    public static void showItemsInShop(){

    }

    public static void showHeroesInShop(){

    }

    public static void setCardsOfShop() throws FileNotFoundException {
        setVBoxForShowingMinionCards();
//        setRootForShowingSpellCards();
//        setRootForShowingHeroCards();
//        setRootForShowingItemCards();
    }

    public static void setVBoxForShowingMinionCards() throws FileNotFoundException {

        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hBox4 = new HBox();
        HBox hBox5 = new HBox();
        HBox hBox6 = new HBox();
        HBox hBox7 = new HBox();
        HBox hBox8 = new HBox();
        HBox hBox9 = new HBox();
        HBox hBox10 = new HBox();

        setImagesForMinionCards(hBox1, 1);
        setImagesForMinionCards(hBox2, 2);
        setImagesForMinionCards(hBox3, 3);
        setImagesForMinionCards(hBox4, 4);
        setImagesForMinionCards(hBox5, 5);
        setImagesForMinionCards(hBox6, 6);
        setImagesForMinionCards(hBox7, 7);
        setImagesForMinionCards(hBox8, 8);
        setImagesForMinionCards(hBox9, 9);
        setImagesForMinionCards(hBox10, 10);

    }

    public static void setRootForShowingSpellCards(){

    }

    public static void setRootForShowingItemCards(){

    }

    public static void setRootForShowingHeroCards(){

    }

    public static void setImagesForMinionCards(HBox hBox, int rowNumber) throws FileNotFoundException {
        for (int i = (rowNumber - 1)*4; i < rowNumber*4; i++) {
            Minion minion = Minion.getMinions().get(i);
            StackPane stackPane = new StackPane();

            String text = minion.getSpecialPower();

            Label label = new Label(text);
            label.setTextFill(Color.WHITE);
            label.setMaxWidth(Shop.CARD_IN_SHOP_WIDTH);
            label.setFont(Font.font(7));

            ImageView minionImage = minion.getImageViewOfCard();
         //   ImageView minionImage = new ImageView(new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/minionCardInShop.png")));
            minionImage.setFitWidth(Shop.CARD_IN_SHOP_WIDTH);
            minionImage.setFitHeight(Shop.CARD_IN_SHOP_HEIGHT);
            stackPane.setAccessibleText(Integer.toString(i));

            stackPane.getChildren().addAll(minionImage, label);
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    makeSceneBlur();
                    try {
                        MenuView.showCardForBuying(Minion.getMinions().get(Integer.parseInt(stackPane.getAccessibleText())));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });

            AllDatas.currentScene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    //Todo : handle this event with another event
                    removeBlurEffectOfWindow();
                }
            });

            hBox.getChildren().add(stackPane);
        }
        hBox.setSpacing(20);
        Shop.getRightVBox().getChildren().add(hBox);
    }

    public static void makeSceneBlur(){
        ColorAdjust adj = new ColorAdjust(0, 0, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55); // 55 is just to show edge effect more clearly.
        adj.setInput(blur);
        for (Node node : AllDatas.currentRoot.getChildren())
            node.setEffect(adj);
    }

    public static void removeBlurEffectOfWindow(){
        for (Node node : AllDatas.currentRoot.getChildren()){
            node.setEffect(null);
        }
    }

    public static void showCardForBuying(Card card) throws FileNotFoundException {
        //Todo : set the card minion image itself
//        ImageView cardImage = card.getImageViewOfCard();
        ImageView cardImage = new ImageView(new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/minionCardInShop.png")));

        ImageView cancelButton = new ImageView(new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/view/Photos/" +
                "shop/button_cancel@2x.png")));
        ImageView buyCardButton = new ImageView(new Image(new FileInputStream("/Users/bahar/Desktop/DUELYST/" +
                "view/Photos/shop/button_buy@2x.png")));

        cardImage.setFitWidth(200);
        cardImage.setFitHeight(300);
        cardImage.setX(WINDOW_WIDTH/2 + 80);
        cardImage.setY(WINDOW_HEIGHT/2 - 250);

        buyCardButton.setFitWidth(200);
        buyCardButton.setFitHeight(70);
        cancelButton.setFitWidth(200);
        cancelButton.setFitHeight(70);
        HBox hBox = new HBox(buyCardButton, cancelButton);
        hBox.setLayoutX(480);
        hBox.setLayoutY(500);
        hBox.setSpacing(20);

        AllDatas.currentRoot.getChildren().addAll(cardImage, hBox);

        GameView.makeImageGlowWhileMouseEnters(cancelButton);
        GameView.makeImageGlowWhileMouseEnters(buyCardButton);

        ShopController.handleEventsOfBuyingCard(card, cardImage, cancelButton, buyCardButton, hBox);




    }
}
