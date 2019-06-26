package view;

import controller.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.*;
import model.Cell;
import model.collection.Card;
import model.collection.HandleFiles;
import model.collection.Hero;
import model.collection.Item;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static controller.Controller.sampleGame;
import static javafx.scene.paint.Color.*;

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
        AllDatas.currentRoot.getChildren().clear();
        MainView.primaryStage.setScene(AllDatas.currentScene);
        MainView.primaryStage.setMaximized(true);

        setBackgroundOfMainMenu();

        Font herculanum = Font.loadFont(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"),25);
        VBox vBox = new VBox();

        Hyperlink shopOption = new Hyperlink("Shop");
        shopOption.setFont(herculanum);
        shopOption.setTextFill(Color.WHITE);

        Image shopIcon = new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/shop@2x.png"));
        ImageView shopIconImage = new ImageView(shopIcon);
        shopIconImage.setFitWidth(40);
        shopIconImage.setFitHeight(40);
        HBox shopHBox = new HBox(shopIconImage, shopOption);

        Hyperlink collectionOption = new Hyperlink("Collection");
        collectionOption.setFont(herculanum);
        collectionOption.setTextFill(Color.WHITE);

        Hyperlink battleOption = new Hyperlink("Battle");
        battleOption.setFont(herculanum);
        battleOption.setTextFill(Color.WHITE);

        Hyperlink exitOption = new Hyperlink("Exit Game");
        exitOption.setFont(herculanum);
        exitOption.setTextFill(Color.WHITE);

        Hyperlink logoutOption = new Hyperlink("Logout");
        logoutOption.setFont(herculanum);
        logoutOption.setTextFill(Color.WHITE);

        Hyperlink helpOption = new Hyperlink("Help");
        helpOption.setFont(herculanum);
        helpOption.setTextFill(Color.WHITE);
        Hyperlink saveOption = new Hyperlink("Save");
        helpOption.setFont(herculanum);
        helpOption.setTextFill(Color.WHITE);

        vBox.setSpacing(15);
        vBox.setMargin(shopHBox, new Insets(40, 10, 10, 100));
        vBox.setMargin(collectionOption, new Insets(7, 10, 10, 100));
        vBox.setMargin(battleOption, new Insets(7, 10, 10, 100));
        vBox.setMargin(helpOption, new Insets(7, 10, 10, 100));
        vBox.setMargin(exitOption, new Insets(7, 10, 10, 100));
        vBox.setMargin(logoutOption, new Insets(7, 10, 10, 100));
        vBox.setMargin(saveOption, new Insets(7, 10, 10, 100));

        vBox.getChildren().addAll(shopHBox, collectionOption, battleOption, helpOption, exitOption, logoutOption,saveOption);
        AllDatas.currentRoot.getChildren().addAll(vBox);

        MenusCommandController.handleEventsOfMainMenu(shopOption, collectionOption, battleOption, helpOption, exitOption, logoutOption, saveOption);

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
        Shop.setRightVBox(new VBox());
        Shop.setLeftVBox(new VBox());

        MainView.primaryStage.setScene(AllDatas.currentScene);

        setScrollBar();

        ImageView background = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/blurBackground.jpg");
        background.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        background.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());
        AllDatas.currentRoot.getChildren().add(background);

        HBox minionsHBox = new HBox();
        ImageView minionIcon = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/minion.png");
        minionIcon.setFitWidth(40);
        minionIcon.setFitHeight(40);
        Hyperlink minionText = new Hyperlink("Minions");
        minionText.setFont(Font.font(null, FontWeight.BOLD, 20));
        minionsHBox.getChildren().addAll(minionIcon, minionText);

        HBox heroesHBox = new HBox();
        ImageView heroIcon = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/Heroes.png");
        heroIcon.setFitWidth(40);
        heroIcon.setFitHeight(40);
        Hyperlink heroText = new Hyperlink("Heroes");
        heroText.setFont(Font.font(null, FontWeight.BOLD, 20));
        heroesHBox.getChildren().addAll(heroIcon, heroText);

        HBox itemHBox = new HBox();
        ImageView itemIcon = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/Item.png");
        itemIcon.setFitWidth(50);
        itemIcon.setFitHeight(50);
        Hyperlink itemText = new Hyperlink("Items");
        itemText.setFont(Font.font(null, FontWeight.BOLD, 20));
        itemHBox.getChildren().addAll(itemIcon, itemText);

        HBox spellHBox = new HBox();
        ImageView spellIcon = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/spell.png");
        spellIcon.setFitWidth(50);
        spellIcon.setFitHeight(50);
        Hyperlink spellText = new Hyperlink("Spells");
        spellText.setFont(Font.font(null, FontWeight.BOLD, 20));
        spellHBox.getChildren().addAll(spellIcon, spellText);

        VBox.setMargin(minionsHBox, new Insets(50, 100, 10, 30));
        VBox.setMargin(spellHBox, new Insets(10, 100, 10, 30));
        VBox.setMargin(itemHBox, new Insets(10, 100, 10, 30));
        VBox.setMargin(heroesHBox, new Insets(10, 100, 10, 30));

        Shop.getLeftVBox().setPadding(new Insets(50, 10, 10, 10));
        StackPane daricStack = setCurrentDaricView();
        StackPane backStack = setBackButtonForShop();
        Shop.getLeftVBox().getChildren().addAll(minionsHBox, spellHBox, itemHBox, heroesHBox, backStack, daricStack);
        VBox.setMargin(backStack, new Insets(70, 1, 1, 1));

        AllDatas.currentRoot.getChildren().addAll(Shop.getRightVBox(), Shop.getLeftVBox());

        ShopController.handleEventsOfShop(minionText, spellText, itemText, heroText);

    }

    public static StackPane setCurrentDaricView() throws FileNotFoundException {
        ImageView imageView = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE
                + "view/Photos/shop/gray_button.png")));
        imageView.setFitWidth(180);
        imageView.setFitHeight(100);

        Label currentDaric = new Label(Integer.toString(Game.getInstance().getPlayer1().getDaric()));
        currentDaric.setTextFill(Color.rgb(45, 58, 58));
        currentDaric.setFont(Font.font(null, FontWeight.BOLD, 20));

        currentDaric.textProperty().bind(Game.getInstance().getPlayer1().daricPropertyProperty().asString());


        return new StackPane(imageView, currentDaric);
    }

    public static StackPane setBackButtonForShop(){
        ImageView backButton = null;
        try {
            backButton = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE
                    + "view/Photos/collection/blueButton.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert backButton != null;
        backButton.setFitWidth(100);
        backButton.setFitHeight(60);

        Text text = new Text("Back");
        text.setFont(Font.font(25));
        text.setFill(Color.rgb(204, 249, 255));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backButton, text);
        stackPane.setAlignment(Pos.CENTER);

        GameView.makeImageGlowWhileMouseEnters(stackPane);

        stackPane.setOnMouseClicked(event -> {
            try {
                Controller.enterMainMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return stackPane;
    }

    public static void setScrollBar() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(AllDatas.currentRoot);
        AllDatas.currentScene.setRoot(scrollPane);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public static void showBattle() throws IOException, CloneNotSupportedException, ParseException {
        AllDatas.currentRoot.getChildren().clear();
        MainView.primaryStage.setScene(AllDatas.currentScene);
//        MainView.primaryStage.setMaximized(true);

        ImageView background = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE +"view/Photos/battle/background@2x.jpg");
        background.fitHeightProperty().bind(AllDatas.currentScene.heightProperty());
        background.fitWidthProperty().bind(AllDatas.currentScene.widthProperty());

        ImageView middleGround = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE +"view/Photos/battle/midground@2x.png");
        middleGround.fitHeightProperty().bind(AllDatas.currentScene.heightProperty());
        middleGround.fitWidthProperty().bind(AllDatas.currentScene.widthProperty());

        AllDatas.currentRoot.getChildren().addAll(background, middleGround);
        sampleGame();
        Game.getInstance().setMap(new Map());
        AI.createAIPlayer();
        Hero.insertHeroInMap();
        Cell.handleCell();
        Cell.handleForce();
        Hand.createHand();
        Hand.setHand();
        BattleView.setEndTurn(MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE+"view/Photos/battle/button_end_turn_mine@2x.png"));
        AllDatas.currentRoot.getChildren().add(BattleView.getEndTurn());
        BattleView.handleEndTurn();
    }


    public static void showThisMenu(LinkedListMenus menu) throws IOException, CloneNotSupportedException, ParseException {
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
//                showCollection();
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

    public static void showCardForBuying (Card card) throws FileNotFoundException {

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
        showOptionsInCollection();
    }

    public static void setBackGroundOfCollection() throws FileNotFoundException {
        ImageView backGround = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/blurBackground.jpg")));
        backGround.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        backGround.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());

        AllDatas.currentRoot.getChildren().add(backGround);
    }

    public static void showOptionsInCollection() throws FileNotFoundException {
        MainView.primaryStage.setScene(AllDatas.currentScene);
        AllDatas.currentRoot.getChildren().clear();
        setScrollBar();
        setBackGroundOfCollection();

        StackPane stackPane = new StackPane();
        AllDatas.currentRoot.getChildren().add(stackPane);
        stackPane.setAlignment(Pos.CENTER);

        ImageView showCardsButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
        showCardsButton.setFitWidth(300);
        showCardsButton.setFitHeight(120);
        ImageView showDecksButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
        showDecksButton.setFitWidth(300);
        showDecksButton.setFitHeight(120);
        ImageView backButton = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE +
                "view/Photos/collection/blueButton.png")));
        backButton.setFitWidth(300);
        backButton.setFitHeight(120);

        Text cardsText= new Text("Show Cards");
        cardsText.setFont(Font.font(25));
        cardsText.setFill(Color.rgb(178, 247, 255));
        Text decksText = new Text("Show Decks");
        decksText.setFont(Font.font(25));
        decksText.setFill(Color.rgb(178, 247, 255));
        Text backText = new Text("Back");
        backText.setFont(Font.font(25));
        backText.setFill(Color.rgb(178, 247, 255));

        StackPane cardsStackPane = new StackPane(showCardsButton, cardsText);
        cardsStackPane.setAlignment(Pos.CENTER);
        StackPane decksStackPane = new StackPane(showDecksButton, decksText);
        decksStackPane.setAlignment(Pos.CENTER);
        StackPane backStackPane = new StackPane(backButton, backText);
        backStackPane.setAlignment(Pos.CENTER);

        HBox optionsHBox = new HBox(cardsStackPane, decksStackPane);
        optionsHBox.setPadding(new Insets(50));

        VBox buttonsVBox = new VBox(optionsHBox, backStackPane);

        stackPane.getChildren().add(buttonsVBox);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setLayoutX(MenuView.WINDOW_WIDTH/2 - 150);
        stackPane.setLayoutY(MenuView.WINDOW_HEIGHT/2 - 120);

        GameView.makeImageGlowWhileMouseEnters(cardsStackPane, decksStackPane, backStackPane);

        CollectionController.handleEventsOfCollectionOptions(cardsStackPane, decksStackPane, backStackPane);
    }

    public static void showDecksInCollection(){
        try{
            AllDatas.currentRoot.getChildren().clear();
            setBackGroundOfCollection();

            ArrayList<Deck> decks = Game.getInstance().getPlayer1().getDecksOfPlayer();

            VBox generalVBox = new VBox();
            generalVBox.setLayoutX(300);

            VBox buttonsVBox = new VBox();

            setButtonsVBoxForShowingDecks(buttonsVBox);

            AllDatas.currentRoot.getChildren().addAll(buttonsVBox, generalVBox);

            for (Deck deck : decks){
                showThisDeck(generalVBox, deck);
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    public static void showThisDeck(VBox generalVBox, Deck deck) throws FileNotFoundException {
        VBox deckVBox = new VBox();
        AllDatas.currentRoot.getChildren().add(deckVBox);

        Text constantText = new Text("Deck Name : ");
        constantText.setFont(Font.font(null, FontWeight.BOLD, 25));
        constantText.setFill(Color.rgb(33, 247, 255));
        constantText.setStrokeWidth(1);
        constantText.setStroke(Color.rgb(25, 112, 122));

        ImageView leftCollapse = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/battlelog_button_collapse@2x.png")));
        ImageView rightCollapse = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/battlelog_button_expand@2x.png")));

        Text deckName = new Text(deck.getDeckName());
        deckName.setFont(Font.font(null, FontWeight.BOLD, 25));
        deckName.setFill(Color.rgb(33, 247, 255));
        deckName.setStrokeWidth(1);
        deckName.setStroke(Color.rgb(25, 112, 122));

        HBox nameHBox = new HBox(constantText, leftCollapse, deckName, rightCollapse);
        HBox.setMargin(deckName, new Insets(20, 1, 1, 1));
        HBox.setMargin(constantText, new Insets(20, 1, 1, 1));
        deckVBox.getChildren().add(nameHBox);

        ArrayList<Card> cardsInDeck = deck.getCardsInDeck();
        ArrayList<Item> itemsInDeck = deck.getItemsInDeck();
        Hero hero = deck.getHeroInDeck();
        ArrayList<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

//        addCardsOfCollectionToVBox(cardsInDeck, deckVBox);
//        addCardsOfCollectionToVBox(itemsInDeck, deckVBox);
        addCardsOfCollectionToVBox(heroes, deckVBox);
        generalVBox.getChildren().add(deckVBox);

    }



    public static void setButtonsVBoxForShowingDecks(VBox vBox) throws FileNotFoundException {
        ImageView selectDeckButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
        selectDeckButton.setFitWidth(200);
        selectDeckButton.setFitHeight(80);

        Text selectDeckText = new Text("Select Deck");
        selectDeckText.setFont(Font.font(20));
        selectDeckText.setFill(rgb(160, 255, 255));

        StackPane selectDeckPane = new StackPane(selectDeckButton, selectDeckText);

        ImageView backButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
        backButton.setFitWidth(200);
        backButton.setFitHeight(80);

        Text backText = new Text("Back");
        backText.setFont(Font.font(20));
        backText.setFill(rgb(160, 255, 255));

        StackPane backPane = new StackPane(backButton, backText);

        GameView.makeImageGlowWhileMouseEnters(selectDeckPane, backPane);

        vBox.getChildren().addAll(selectDeckPane, backPane);

        VBox.setMargin(selectDeckPane, new Insets(30, 1, 1, 20));
        VBox.setMargin(backPane, new Insets(20, 1, 1, 20));

        CollectionController.handleEventsOfShowingDeckButtons(selectDeckPane, backPane);
    }

    public static void showCardsInCollection(){
        try {
            AllDatas.currentRoot.getChildren().clear();
            setBackGroundOfCollection();

            ArrayList<Card> cardsInCollection = Game.getInstance().getPlayer1().getCardsInCollection();
            ArrayList<Item> itemsInCollection = Game.getInstance().getPlayer1().getItemsInCollection();
            ArrayList<Hero> heroesInCollection = Game.getInstance().getPlayer1().getHeroesInCollection();

            VBox cardsVBox = new VBox();
            VBox itemsVBox = new VBox();
            VBox heroesVBox = new VBox();

            ImageView createDeckButton = new ImageView(new Image(new FileInputStream(
                        HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
            createDeckButton.setFitWidth(250);
            createDeckButton.setFitHeight(100);
            ImageView backButton = new ImageView(new Image(new FileInputStream(
                    HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
            backButton.setFitWidth(250);
            backButton.setFitHeight(100);

            Text createDeckText = new Text("Create New Deck");
            Text backText = new Text("Back");
            createDeckText.setFill(Color.rgb(130, 255, 255));
            backText.setFill(Color.rgb(130, 255, 255));
            createDeckText.setFont(Font.font(null, FontWeight.BOLD, 20));
            backText.setFont(Font.font(null, FontWeight.BOLD, 20));

            StackPane createDeckStack = new StackPane(createDeckButton, createDeckText);
            createDeckStack.setAlignment(Pos.CENTER);
            StackPane backStack = new StackPane(backButton, backText);
            backStack.setAlignment(Pos.CENTER);
            GameView.makeImageGlowWhileMouseEnters(createDeckStack, backStack);

            VBox buttonsVBox = new VBox(createDeckStack, backStack);
            buttonsVBox.setPadding(new Insets(30, 1, 1, 20));

            CollectionController.handleEventsOfShowCardsButtons(createDeckStack, backStack);

            VBox generalVBox = new VBox(cardsVBox, itemsVBox, heroesVBox);
            generalVBox.setLayoutX(300);

            AllDatas.currentRoot.getChildren().addAll(buttonsVBox, generalVBox);

//        addCardsOfCollectionToVBox(cardsInCollection, cardsVBox);
        addCardsOfCollectionToVBox(itemsInCollection, itemsVBox);
        addCardsOfCollectionToVBox(heroesInCollection, heroesVBox);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static <T extends Card> void addCardsOfCollectionToVBox(ArrayList<T> cards, VBox vBox){
//        vBox.getChildren().clear();
        for (int i = 0; i < cards.size(); i++) {
            if (i + 3 < cards.size()){
                HBox hBox = new HBox();
                setAppearanceOfCardsInCollection(hBox, cards.get(i));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 1));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 2));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 3));
                vBox.getChildren().add(hBox);
                i += 3;
            }else if (i + 2 < cards.size()){
                HBox hBox = new HBox();
                setAppearanceOfCardsInCollection(hBox, cards.get(i));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 1));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 2));
                vBox.getChildren().add(hBox);
                i += 3;
            }else if (i + 1 < cards.size()){
                HBox hBox = new HBox();
                setAppearanceOfCardsInCollection(hBox, cards.get(i));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 1));
                vBox.getChildren().add(hBox);
                i += 3;
            }else {
                HBox hBox = new HBox();
                setAppearanceOfCardsInCollection(hBox, cards.get(i));
                vBox.getChildren().add(hBox);
                i += 3;
            }
        }
    }

    public static void setAppearanceOfCardsInCollection(HBox hBox, Card card){
        Text manaText = new Text(Integer.toString(card.getMana()));
        ImageView manaIcon = null;
        manaText.setFont(Font.font("verdana", 20));
        manaText.setFill(Color.rgb(204, 249, 255));

        try {
            manaIcon = new ImageView(new Image(new FileInputStream(
                    HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/icon_mana.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StackPane manaPane = new StackPane(manaIcon, manaText);

        ImageView cardImage = card.getImageViewOfCard();
        cardImage.setFitWidth(200);
        cardImage.setFitHeight(200);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        Text cardName = new Text(card.getName());
        cardName.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        cardName.setEffect(ds);
        VBox vBox = new VBox(cardImage, cardName, manaPane);
        vBox.setAccessibleText(Integer.toString(card.getId()));

        vBox.setOnMouseClicked(event -> {
            try {
                if (!Shop.isIsShowingSpecificCard()) {
                    makeSceneBlur();
                    Shop.setIsShowingSpecificCard(true);
                    showCardForSelling(card, vBox, hBox);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        VBox.setMargin(cardName, new Insets(1,1,1,60));
        cardName.setFill(Color.rgb(255, 225, 58));

        hBox.getChildren().add(vBox);
    }

    public static void showCardForSelling(Card card, VBox cardVBox, HBox inRowCardsHBox) throws FileNotFoundException {
        HBox cardHBox = new HBox();


        ImageView cardImage = new ImageView(card.getImageViewOfCard().getImage());

        ImageView cancelButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/button_cancel@2x.png")));
        //Todo : set sell text for sellButton
        ImageView sellButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/green_button.png")));
        Label sellLabel = new Label("Sell");
        sellLabel.setTextFill(rgb(190, 237, 173));
        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 30);

        StackPane sellStack = new StackPane(sellButton, sellLabel);
        sellStack.setAlignment(Pos.CENTER);
        sellLabel.setFont(font);

        cardImage.setFitWidth(200);
        cardImage.setFitHeight(300);
        cardImage.setX(WINDOW_WIDTH / 2 + 80);
        cardImage.setY(WINDOW_HEIGHT / 2 - 250);

        sellButton.setFitWidth(200);
        sellButton.setFitHeight(70);
        cancelButton.setFitWidth(200);
        cancelButton.setFitHeight(70);

        HBox hBox = new HBox(sellStack, cancelButton);
        hBox.setLayoutX(480);
        hBox.setLayoutY(500);
        hBox.setSpacing(20);

        AllDatas.currentRoot.getChildren().addAll(cardHBox, hBox);

        GameView.makeImageGlowWhileMouseEnters(cancelButton, sellStack);

        cardHBox.setLayoutX(WINDOW_WIDTH/2 + 50);
        cardHBox.setLayoutY(WINDOW_HEIGHT/2 - 250);

        cardHBox.getChildren().addAll(cardImage, addPriceAndManaForShowingCard(card, cardHBox));

        CollectionController.handleEventsOfSellingCard(cardHBox, hBox, cancelButton, sellStack, card, cardVBox, inRowCardsHBox);

    }

}
