package view;

import controller.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Window;
import model.*;
import model.Cell;
import model.collection.Card;
import model.collection.HandleFiles;
import model.collection.Hero;
import model.collection.Item;

import javax.management.ObjectName;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

import static javafx.scene.paint.Color.rgb;

import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.File;
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
        accountRectangle.setFill(rgb(129, 135, 145, 0.5));
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

        Image shopIcon = new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/shop@2x.png"));
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
        Image backgroundImage = new Image(new FileInputStream("view/Photos/login/Screen Shot 1398-03-25 at 16.32.26.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        backgroundImageView.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());

        AllDatas.currentRoot.getChildren().add(backgroundImageView);


    }

    //Todo : define a style for text and image
    public static void showShop() throws FileNotFoundException {
        AllDatas.currentRoot.getChildren().clear();
        MainView.primaryStage.setScene(AllDatas.currentScene);
        MainView.primaryStage.setMaximized(true);

        setScrollBar();

        ImageView background = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/blurBackground.jpg");
        background.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        background.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());
        AllDatas.currentRoot.getChildren().add(background);

        HBox minionsHBox = new HBox();
        ImageView minionIcon = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/minion.png");
        //  minionIcon.getStyleClass().add("shopOptionsImage");
        minionIcon.setFitWidth(40);
        minionIcon.setFitHeight(40);
        Hyperlink minionText = new Hyperlink("Minions");
        minionsHBox.getChildren().addAll(minionIcon, minionText);

        HBox heroesHBox = new HBox();
        ImageView heroIcon = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/Heroes.png");
        heroIcon.setFitWidth(40);
        heroIcon.setFitHeight(40);
        Hyperlink heroText = new Hyperlink("Heroes");
        heroesHBox.getChildren().addAll(heroIcon, heroText);

        HBox itemHBox = new HBox();
        ImageView itemIcon = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/Item.png");
        itemIcon.setFitWidth(50);
        itemIcon.setFitHeight(50);
        Hyperlink itemText = new Hyperlink("Items");
        itemHBox.getChildren().addAll(itemIcon, itemText);

        HBox spellHBox = new HBox();
        ImageView spellIcon = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/spell.png");
        spellIcon.setFitWidth(50);
        spellIcon.setFitHeight(50);
        Hyperlink spellText = new Hyperlink("Spells");
        spellHBox.getChildren().addAll(spellIcon, spellText);

        VBox.setMargin(minionsHBox, new Insets(50, 100, 10, 30));
        VBox.setMargin(spellHBox, new Insets(10, 100, 10, 30));
        VBox.setMargin(itemHBox, new Insets(10, 100, 10, 30));
        VBox.setMargin(heroesHBox, new Insets(10, 100, 10, 30));

        Shop.getLeftVBox().getChildren().addAll(minionsHBox, spellHBox, itemHBox, heroesHBox);

        AllDatas.currentRoot.getChildren().addAll(Shop.getRightVBox(), Shop.getLeftVBox());

        ShopController.handleEventsOfShop(minionText, spellText, itemText, heroText);

    }

    public static void setScrollBar() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(AllDatas.currentRoot);
        AllDatas.currentScene.setRoot(scrollPane);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public static void showBattle() throws FileNotFoundException, CloneNotSupportedException {
        MainView.primaryStage.setScene(AllDatas.currentScene);
        MainView.primaryStage.setMaximized(true);

        ImageView background = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE +"view/Photos/battle/background@2x.jpg");
        background.fitHeightProperty().bind(AllDatas.currentScene.heightProperty());
        background.fitWidthProperty().bind(AllDatas.currentScene.widthProperty());

        ImageView middleGround = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE +"view/Photos/battle/midground@2x.png");
        middleGround.fitHeightProperty().bind(AllDatas.currentScene.heightProperty());
        middleGround.fitWidthProperty().bind(AllDatas.currentScene.widthProperty());

        AllDatas.currentRoot.getChildren().addAll(background, middleGround);
        Cell.createCellsAndView();
        Cell.createForceView();
        AI.createAIPlayer();
        Hero.insertHeroInMap();
        Hand.createHand();
        Hand.setHand();
        BattleView.setEndTurn(MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE+"view/Photos/battle/button_end_turn_mine@2x.png"));
        AllDatas.currentRoot.getChildren().add(BattleView.getEndTurn());
        BattleView.handleEndTurn();
    }


    public static void showThisMenu(LinkedListMenus menu) throws FileNotFoundException, CloneNotSupportedException {
        String menuName = menu.getMenuName();
        switch (menuName) {
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

//    public static void showMinionsInShop() {
//        setVBoxForShowingMinionCards();
//    }
//
//    public static void showSpellsInShop(){
//        setVBoxForShowingSpellCards();
//    }
//
//    public static void showItemsInShop(){
//        setVBoxForShowingItemCards();
//    }
//
//    public static void showHeroesInShop(){
//        setVBoxForShowingHeroCards();
//    }

    public static void showMinionsInShop() {

        VBox minionVBox = new VBox();

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

        setAppearanceOfCardsInShop(hBox1, 1, minionVBox, "minion", 4);
        setAppearanceOfCardsInShop(hBox2, 2, minionVBox, "minion", 4);
        setAppearanceOfCardsInShop(hBox3, 3, minionVBox, "minion", 4);
        setAppearanceOfCardsInShop(hBox4, 4, minionVBox, "minion", 4);
        setAppearanceOfCardsInShop(hBox5, 5, minionVBox, "minion", 4);
        setAppearanceOfCardsInShop(hBox6, 6, minionVBox, "minion", 4);
        setAppearanceOfCardsInShop(hBox7, 7, minionVBox, "minion", 4);
        setAppearanceOfCardsInShop(hBox8, 8, minionVBox, "minion", 4);
        setAppearanceOfCardsInShop(hBox9, 9, minionVBox, "minion", 4);
        setAppearanceOfCardsInShop(hBox10, 10, minionVBox, "minion", 4);

        AllDatas.currentRoot.getChildren().remove(Shop.getRightVBox());
        Shop.setRightVBox(minionVBox);
        Shop.getRightVBox().setLayoutX(300);
        AllDatas.currentRoot.getChildren().add(Shop.getRightVBox());

    }

    public static void showSpellsInShop() {
        VBox spellVBox = new VBox();

        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hBox4 = new HBox();
        HBox hBox5 = new HBox();

        setAppearanceOfCardsInShop(hBox1, 1, spellVBox, "spell", 4);
        setAppearanceOfCardsInShop(hBox2, 2, spellVBox, "spell", 4);
        setAppearanceOfCardsInShop(hBox3, 3, spellVBox, "spell", 4);
        setAppearanceOfCardsInShop(hBox4, 4, spellVBox, "spell", 4);
        setAppearanceOfCardsInShop(hBox5, 5, spellVBox, "spell", 4);

        AllDatas.currentRoot.getChildren().remove(Shop.getRightVBox());
        Shop.setRightVBox(spellVBox);
        Shop.getRightVBox().setLayoutX(300);
        AllDatas.currentRoot.getChildren().add(Shop.getRightVBox());
    }

    public static void showItemsInShop() {
        VBox itemsVBox = new VBox();

        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hBox4 = new HBox();
        HBox hBox5 = new HBox();

        setAppearanceOfCardsInShop(hBox1, 1, itemsVBox, "item", 4);
        setAppearanceOfCardsInShop(hBox2, 2, itemsVBox, "item", 4);
        setAppearanceOfCardsInShop(hBox3, 3, itemsVBox, "item", 4);
        setAppearanceOfCardsInShop(hBox4, 4, itemsVBox, "item", 4);
        setAppearanceOfCardsInShop(hBox5, 5, itemsVBox, "item", 4);

        AllDatas.currentRoot.getChildren().remove(Shop.getRightVBox());
        Shop.setRightVBox(itemsVBox);
        Shop.getRightVBox().setLayoutX(300);
        AllDatas.currentRoot.getChildren().add(Shop.getRightVBox());
    }

    public static void showHeroesInShop() {
        VBox heroVBox = new VBox();

        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();

        setAppearanceOfCardsInShop(hBox1, 1, heroVBox, "hero", 5);
        setAppearanceOfCardsInShop(hBox2, 2, heroVBox, "hero", 5);

        AllDatas.currentRoot.getChildren().remove(Shop.getRightVBox());
        Shop.setRightVBox(heroVBox);
        Shop.getRightVBox().setLayoutX(300);
        AllDatas.currentRoot.getChildren().add(Shop.getRightVBox());
    }

    public static void setAppearanceOfCardsInShop(HBox hBox, int rowNumber, VBox vBox, String cardType, int numOfCardsInEachRow) {
        for (int i = (rowNumber - 1) * numOfCardsInEachRow; i < rowNumber * numOfCardsInEachRow; i++) {
            VBox cardVBox = new VBox();


            Card card = Card.returnNthCard(cardType, i);

            StackPane stackPane = new StackPane();
            stackPane.setAccessibleText(Integer.toString(i));
            stackPane.setPadding(new Insets(13));

            setImageForCardInShop(card, stackPane);

            stackPane.setAlignment(Pos.CENTER);
            stackPane.setOnMouseClicked(event -> {
                if (stackPane.getEffect() == null) {
                    if (!Shop.isIsShowingSpecificCard()) {
                        try {
                            makeSceneBlur();
                            Shop.setIsShowingSpecificCard(true);
                            MenuView.showCardForBuying(card);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            cardVBox.getChildren().add(stackPane);

            setPriceForCardInShop(card, cardVBox);
            //   setManaForCardInShop(card, stackPane, onCardVBox);

            hBox.getChildren().add(cardVBox);
        }
        hBox.setSpacing(20);
        vBox.getChildren().add(hBox);
    }

    public static void setPriceForCardInShop(Card card, VBox vBox) {

        StackPane stackPane = new StackPane();

        int price = card.getPrice();
        Text priceText = new Text(Integer.toString(price));
        priceText.setFont(Font.font(20));
        priceText.setFill(rgb(192, 200, 214));

        try {

            ImageView priceBack = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/price_back.png")));
            priceBack.setFitWidth(200);
            priceBack.setFitHeight(50);

            ImageView priceIcon = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/price_icon.png")));
            priceIcon.setFitWidth(100);
            priceIcon.setFitHeight(110);

            stackPane.getChildren().addAll(priceBack, priceIcon, priceText);
            StackPane.setAlignment(priceBack, Pos.CENTER);
            StackPane.setAlignment(priceIcon, Pos.CENTER_LEFT);
            StackPane.setAlignment(priceText, Pos.CENTER);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        vBox.getChildren().add(stackPane);
    }

    public static void setManaForCardInShop(Card card, StackPane stackPane, VBox vBox) {

    }

    public static void setImageForCardInShop(Card card, StackPane stackPane) {
        ImageView image = card.getImageViewOfCard();
        image.setFitWidth(Shop.CARD_IN_SHOP_WIDTH);
        image.setFitHeight(Shop.CARD_IN_SHOP_HEIGHT);

        stackPane.getChildren().add(image);
    }

    public static void setDescForCardInShop(Card card, StackPane stackPane, VBox vBox) {
        Text desc = card.getTextForCard();
        desc.setWrappingWidth(Shop.CARD_IN_SHOP_WIDTH);
        desc.setFont(Font.font(10));
        desc.setFill(Color.YELLOW);

        stackPane.getChildren().add(desc);

    }

    public static void makeSceneBlur() {
        ColorAdjust adj = new ColorAdjust(0, 0, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55); // 55 is just to show edge effect more clearly.
        adj.setInput(blur);
        for (Node node : AllDatas.currentRoot.getChildren())
            node.setEffect(adj);
    }

    public static void removeBlurEffectOfWindow() {
        for (Node node : AllDatas.currentRoot.getChildren()) {
            node.setEffect(null);
        }
    }

    public static void showCardForBuying (Card card) throws FileNotFoundException{

        HBox cardHBox = new HBox();
        //Todo : set the card minion image itself
        ImageView cardImage = new ImageView(card.getImageViewOfCard().getImage());

        ImageView cancelButton = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/button_cancel@2x.png")));
        ImageView buyCardButton = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/button_buy@2x.png")));

        cardImage.setFitWidth(200);
        cardImage.setFitHeight(300);
        cardImage.setX(WINDOW_WIDTH / 2 + 80);
        cardImage.setY(WINDOW_HEIGHT / 2 - 250);

        buyCardButton.setFitWidth(200);
        buyCardButton.setFitHeight(70);
        cancelButton.setFitWidth(200);
        cancelButton.setFitHeight(70);
        HBox hBox = new HBox(buyCardButton, cancelButton);
        hBox.setLayoutX(480);
        hBox.setLayoutY(500);
        hBox.setSpacing(20);

        AllDatas.currentRoot.getChildren().addAll(cardHBox, hBox);

        GameView.makeImageGlowWhileMouseEnters(cancelButton);
        GameView.makeImageGlowWhileMouseEnters(buyCardButton);

        cardHBox.setLayoutX(WINDOW_WIDTH/2 + 50);
        cardHBox.setLayoutY(WINDOW_HEIGHT/2 - 250);

        cardHBox.getChildren().addAll(cardImage, addPriceAndManaForShowingCard(card, cardHBox));

//        ShopController.handleEventsOfBuyingCard(card, cardImage, cancelButton, buyCardButton, hBox);
        ShopController.handleEventsOfBuyingCard(cardHBox, hBox, cancelButton, buyCardButton, card);
    }

    public static VBox addPriceAndManaForShowingCard(Card card, HBox cardHBox) throws FileNotFoundException {
        ImageView priceBack = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE+"view/Photos/shop/price_back.png")));
        ImageView manaBack = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE+"view/Photos/shop/price_back.png")));
        ImageView priceIcon = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE+"view/Photos/shop/price_icon.png")));
        ImageView manaIcon = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE+"view/Photos/shop/icon_mana.png")));

        priceBack.setFitWidth(200);
        priceBack.setFitHeight(70);
        manaBack.setFitWidth(200);
        manaBack.setFitHeight(70);
        priceIcon.setFitWidth(100);
        priceIcon.setFitHeight(120);
        manaIcon.setFitWidth(60);
        manaIcon.setFitHeight(70);

        int price = card.getPrice();
        int mana = card.getMana();

        Text priceText = new Text(Integer.toString(price));
        Text manaText = new Text(Integer.toString(mana));

        priceText.setFont(Font.font(20));
        manaText.setFont(Font.font(20));
        priceText.setFill(Color.rgb(193, 244, 240));
        manaText.setFill(Color.rgb(193, 244, 240));

        VBox vBox = new VBox();

        StackPane priceStackPane = new StackPane();
        StackPane manaStackPane = new StackPane();

        priceStackPane.getChildren().addAll(priceBack, priceIcon, priceText);
        StackPane.setAlignment(priceBack, Pos.CENTER);
        StackPane.setAlignment(priceText, Pos.CENTER);
        StackPane.setAlignment(priceIcon, Pos.CENTER_LEFT);

        manaStackPane.getChildren().addAll(manaBack, manaIcon, manaText);
        StackPane.setAlignment(manaBack, Pos.CENTER);
        StackPane.setAlignment(manaText, Pos.CENTER);
        StackPane.setAlignment(manaIcon, Pos.CENTER_LEFT);

        vBox.getChildren().addAll(priceStackPane, manaStackPane);

        return vBox;
    }

    public static void showCollection() throws FileNotFoundException {
        AllDatas.currentRoot.getChildren().clear();
        setBackGroundOfCollection();

        StackPane stackPane = new StackPane();
        AllDatas.currentRoot.getChildren().add(stackPane);
        stackPane.setAlignment(Pos.CENTER);

        MainView.primaryStage.setScene(AllDatas.currentScene);
        MainView.primaryStage.setMaximized(true);

        showOptionsInCollection(stackPane);
    }

    public static void setBackGroundOfCollection() throws FileNotFoundException {
        ImageView backGround = new ImageView(new Image(new FileInputStream(
                "/Users/bahar/Desktop/DUELYST/view/Photos/blurBackground.jpg")));
        backGround.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        backGround.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());

        AllDatas.currentRoot.getChildren().add(backGround);
    }

    public static void showOptionsInCollection(StackPane stackPane) throws FileNotFoundException {
        ImageView showCardsButton = new ImageView(new Image(new FileInputStream(
                "/Users/bahar/Desktop/DUELYST/view/Photos/collection/blueButton.png")));
        showCardsButton.setFitWidth(300);
        showCardsButton.setFitHeight(120);
        ImageView showDecksButton = new ImageView(new Image(new FileInputStream(
                "/Users/bahar/Desktop/DUELYST/view/Photos/collection/blueButton.png")));
        showDecksButton.setFitWidth(300);
        showDecksButton.setFitHeight(120);

        GameView.makeImageGlowWhileMouseEnters(showCardsButton);
        GameView.makeImageGlowWhileMouseEnters(showDecksButton);

        Text cardsText= new Text("Show Cards");
        cardsText.setFont(Font.font(25));
        cardsText.setFill(Color.rgb(178, 247, 255));
        Text decksText = new Text("Show Decks");
        decksText.setFont(Font.font(25));
        decksText.setFill(Color.rgb(178, 247, 255));

        StackPane cardsStackPane = new StackPane(showCardsButton, cardsText);
        cardsStackPane.setAlignment(Pos.CENTER);
        StackPane itemsStackPane = new StackPane(showDecksButton, decksText);
        itemsStackPane.setAlignment(Pos.CENTER);

        HBox optionsHBox = new HBox(cardsStackPane, itemsStackPane);
        optionsHBox.setPadding(new Insets(50));

        stackPane.getChildren().add(optionsHBox);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setLayoutX(MenuView.WINDOW_WIDTH/2 - 150);
        stackPane.setLayoutY(MenuView.WINDOW_HEIGHT/2 - 120);

        CollectionController.handleEventsOfCollectionOptions(showCardsButton, showDecksButton);
    }

    public static void showCardsInCollection(){
        AllDatas.currentRoot.getChildren().clear();
        try {
            setBackGroundOfCollection();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Card> cardsInCollection = Game.getInstance().getPlayer1().getCardsInCollection();
        ArrayList<Item> itemsInCollection = Game.getInstance().getPlayer1().getItemsInCollection();

        VBox cardsVBox = new VBox();
        VBox itemsVBox = new VBox();

        ArrayList<Card> cards = new ArrayList<>();

        for (int i = 0; i < cardsInCollection.size(); i += 4) {

        }



    }

    public static void setAppearanceOfCardsInCollection(){

    }

    public static void showDecksInCollection(){

    }
}
