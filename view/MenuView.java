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
import static javafx.scene.layout.VBox.setMargin;
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

        Font herculanum = Font.loadFont(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 25);
        VBox vBox = new VBox();

        Hyperlink shopOption = new Hyperlink("Shop");
        shopOption.setFont(herculanum);
        shopOption.setTextFill(Color.WHITE);

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
        saveOption.setFont(herculanum);
        saveOption.setTextFill(Color.WHITE);

        vBox.setSpacing(15);
        setMargin(shopOption, new Insets(40, 10, 10, 100));
        setMargin(collectionOption, new Insets(7, 10, 10, 100));
        setMargin(battleOption, new Insets(7, 10, 10, 100));
        setMargin(helpOption, new Insets(7, 10, 10, 100));
        setMargin(exitOption, new Insets(7, 10, 10, 100));
        setMargin(logoutOption, new Insets(7, 10, 10, 100));
        setMargin(saveOption, new Insets(7, 10, 10, 100));

        vBox.getChildren().addAll(shopOption, collectionOption, battleOption, helpOption, exitOption, logoutOption, saveOption);
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
        Hyperlink minionText = setHBoxForHyperlinksInShop(minionsHBox, "minion");

        HBox heroesHBox = new HBox();
        Hyperlink heroText = setHBoxForHyperlinksInShop(heroesHBox, "hero");

        HBox itemHBox = new HBox();
        Hyperlink itemText = setHBoxForHyperlinksInShop(itemHBox, "item");

        HBox spellHBox = new HBox();
        Hyperlink spellText = setHBoxForHyperlinksInShop(spellHBox, "spell");

        setMargin(minionsHBox, new Insets(50, 100, 10, 30));
        setMargin(spellHBox, new Insets(10, 100, 10, 30));
        setMargin(itemHBox, new Insets(10, 100, 10, 30));
        setMargin(heroesHBox, new Insets(10, 100, 10, 30));

        Shop.getLeftVBox().setPadding(new Insets(50, 10, 10, 10));
        StackPane daricStack = setCurrentDaricView();
        StackPane backStack = setBackButtonForShop();
        StackPane searchButton = returnSearchButtonInShop();
        Shop.getLeftVBox().getChildren().addAll(minionsHBox, spellHBox, itemHBox,
                heroesHBox, searchButton, backStack, daricStack);
        setMargin(backStack, new Insets(15, 1, 1, 1));
        setMargin(searchButton, new Insets(50, 1, 1, 1));

        AllDatas.currentRoot.getChildren().addAll(Shop.getRightVBox(), Shop.getLeftVBox());

        ShopController.handleEventsOfShop(minionText, spellText, itemText, heroText);
    }

    public static StackPane returnSearchButtonInShop() throws FileNotFoundException {
        ImageView imageView = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
        imageView.setFitWidth(100);
        imageView.setFitHeight(60);

        Text searchText = new Text("Search");
        searchText.setFont(Font.font(25));
        searchText.setFill(Color.rgb(204, 249, 255));

        StackPane stackPane = new StackPane(imageView, searchText);
        GameView.makeImageGlowWhileMouseEnters(stackPane);

        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    showSearchWindow();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        return stackPane;
    }

    public static void showSearchWindow() throws FileNotFoundException {
        AllDatas.currentRoot.getChildren().clear();

        setBackGroundOfCollection();

//        VBox generalVBox = new VBox();

        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 20);
        ImageView searchButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/yellow_button.png")));
        Text searchText = new Text("Search");
        searchText.setFont(font);
        searchText.setFill(rgb(247, 250, 210));


        ImageView backButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/yellow_button.png")));
        Text backText = new Text("Back");
        backText.setFont(font);
        backText.setFill(rgb(247, 250, 210));

        StackPane searchStack = new StackPane(searchButton, searchText);
        StackPane backStack = new StackPane(backButton, backText);
        TextField searchingBox = new TextField();

        GameView.makeImageGlowWhileMouseEnters(searchStack, backStack);
        HBox hBox = new HBox(searchingBox, searchStack, backStack);
        hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutX(200);
        hBox.setLayoutY(200);
//        generalVBox.getChildren().add(hBox);
        AllDatas.currentRoot.getChildren().add(hBox);

        ShopController.handleEventsOfSearchingInShop(searchStack, backStack, searchingBox);
    }

    public static Hyperlink setHBoxForHyperlinksInShop(HBox hBox, String type) throws FileNotFoundException {
        String fileName;
        String text;
        if (type.equals("minion")) {
            fileName = HandleFiles.BEFORE_RELATIVE + "view/Photos/minion.png";
            text = "Minions";
        } else if (type.equals("spell")) {
            fileName = HandleFiles.BEFORE_RELATIVE + "view/Photos/spell.png";
            text = "Spells";
        } else if (type.equals("hero")) {
            fileName = HandleFiles.BEFORE_RELATIVE + "view/Photos/Heroes.png";
            text = "Heroes";
        } else {
            fileName = HandleFiles.BEFORE_RELATIVE + "view/Photos/Item.png";
            text = "Items";
        }
        ImageView icon = MainView.getPhotoWithThisPath(fileName);
        icon.setFitWidth(40);
        icon.setFitHeight(40);
        Hyperlink cardText = new Hyperlink(text);
        cardText.setFont(Font.font(null, FontWeight.BOLD, 20));
        hBox.getChildren().addAll(icon, cardText);

        return cardText;
    }

    public static StackPane setCurrentDaricView() throws FileNotFoundException {
        ImageView imageView = new ImageView(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE
                + "view/Photos/shop/gray_button.png")));
        imageView.setFitWidth(180);
        imageView.setFitHeight(100);

        Label currentDaric = new Label(Integer.toString(Game.getInstance().getPlayer1().getDaric()));
        currentDaric.setTextFill(Color.rgb(45, 58, 58));
        currentDaric.setFont(Font.font(null, FontWeight.BOLD, 20));

//        currentDaric.textProperty().bind(Game.getInstance().getPlayer1().daricPropertyProperty().asString());


        return new StackPane(imageView, currentDaric);
    }

    public static StackPane setBackButtonForShop() {
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
        stackPane.setAccessibleText("Back");

        return stackPane;
    }

    public static void setScrollBar() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(AllDatas.currentRoot);
        AllDatas.currentScene.setRoot(scrollPane);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public static void resetMap() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                try {
                    Map.getCellsView()[i][j].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/tiles_board.png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void showBattle() throws IOException, CloneNotSupportedException, ParseException {
        AllDatas.currentRoot.getChildren().clear();
        MainView.primaryStage.setScene(AllDatas.currentScene);

        ImageView background = MainView.getPhotoWithThisPath(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/background@2x.jpg");
        background.fitHeightProperty().bind(AllDatas.currentScene.heightProperty());
        background.fitWidthProperty().bind(AllDatas.currentScene.widthProperty());
        background.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Hand.setSelectedInHand(false);
            Cell.setAForceIsSelected(false);
            resetMap();
        });

        ImageView middleGround = MainView.getPhotoWithThisPath(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/midground@2x.png");
        middleGround.fitHeightProperty().bind(AllDatas.currentScene.heightProperty());
        middleGround.fitWidthProperty().bind(AllDatas.currentScene.widthProperty());
        middleGround.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Hand.setSelectedInHand(false);
                    Cell.setAForceIsSelected(false);
                    resetMap();
                }
        );

        ImageView backButton = setBackButtonInBattle();

        AllDatas.currentRoot.getChildren().addAll(background, middleGround, backButton);

        sampleGame();
        Game.getInstance().setMap(new Map());
        AI.createAIPlayer();
        Hero.insertHeroInMap();
        Rectangle rectangle = new Rectangle(258, 293);
        rectangle.setX(100);
        rectangle.setY(100);
        ImageView yourProfile = new ImageView(Game.getInstance().getMap().getFriendHero().getImageViewOfCard().getImage());
        yourProfile.setX(100);
        yourProfile.setY(30);
        yourProfile.fitWidthProperty().bind(rectangle.widthProperty());
        yourProfile.fitHeightProperty().bind(rectangle.heightProperty());
        AllDatas.currentRoot.getChildren().add(yourProfile);

        AllDatas.currentRoot.getChildren().addAll(Game.getYourHbox(), Game.getEnemyHbox());
        ImageView enemyProfile = new ImageView(Game.getInstance().getMap().getEnemyHero().getImageViewOfCard().getImage());
        enemyProfile.setX(1300);
        enemyProfile.setY(30);
        enemyProfile.fitHeightProperty().bind(rectangle.heightProperty());
        enemyProfile.fitWidthProperty().bind(rectangle.widthProperty());
        AllDatas.currentRoot.getChildren().add(enemyProfile);


        Cell.handleCell();
        Cell.handleForce();
        Hand.createHand();
        Hand.setHand();
        Hand.handController();
        BattleView.setEndTurn(MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/button_end_turn_mine@2x.png"));
        AllDatas.currentRoot.getChildren().add(BattleView.getEndTurn());
        BattleView.handleEndTurn();
    }

    public static ImageView setBackButtonInBattle() throws FileNotFoundException {
        ImageView backButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/battle_back_button.png")));
        GameView.makeImageGlowWhileMouseEnters(backButton);

        backButton.setOnMouseClicked(event -> {
            try {
                Controller.chooseSingleOrMultiPlayerWindow();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        return backButton;
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

            try {
                setImageForCardInShop(card, stackPane);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

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

            Text cardName = new Text(card.getName());
            Font font = null;
            try {
                font = Font.loadFont(new FileInputStream(
                        HandleFiles.BEFORE_RELATIVE + "view/Fonts/averta-extrabold-webfont.ttf"), 15);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            cardName.setFont(font);
            cardName.setFill(rgb(191, 222, 255));

            cardVBox.getChildren().addAll(stackPane, cardName);
            cardVBox.setAlignment(Pos.CENTER);

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

            ImageView priceBack = new ImageView(new Image(new FileInputStream(
                    HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/price_back.png")));
            priceBack.setFitWidth(200);
            priceBack.setFitHeight(50);

            ImageView priceIcon = new ImageView(new Image(new FileInputStream(
                    HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/price_icon.png")));
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

    public static void setImageForCardInShop(Card card, StackPane stackPane) throws FileNotFoundException {
        ImageView cardBack = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/card_background_blue.png")));
        cardBack.setFitWidth(Shop.CARD_IN_SHOP_WIDTH);
        cardBack.setFitHeight(Shop.CARD_IN_SHOP_HEIGHT);

        ImageView image = new ImageView(card.getImageViewOfCard().getImage());
        image.setFitWidth(Shop.CARD_IMAGE_IN_SHOP_WIDTH);
        image.setFitHeight(Shop.CARD_IMAGE_IN_SHOP_HEIGHT);

        Text desc = new Text();
        card.setDescOfCard(desc);

        Label cardType = new Label(card.getCardType());
        cardType.setTextFill(rgb(172, 239, 250));
        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/averta-extrabold-webfont.ttf"), 15);
        cardType.setFont(font);

        stackPane.getChildren().addAll(cardBack, image, cardType, desc);
        stackPane.setAlignment(Pos.CENTER);

        StackPane.setMargin(image, new Insets(1, 1, 60, 1));
        StackPane.setMargin(cardType, new Insets(1, 1, 170, 1));
        StackPane.setMargin(desc, new Insets(150, 1, 1, 1));
    }

    private static void makeSceneBlur() {
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

    public static void showCardForBuying(Card card) throws FileNotFoundException {
        StackPane stackPane = new StackPane();
        setImageForCardInShop(card, stackPane);

        HBox cardHBox = new HBox();
        cardHBox.setPadding(new Insets(20, 50, 50, 20));
        cardHBox.getChildren().addAll(stackPane, addPriceAndManaForShowingCard(card, cardHBox));

        VBox generalVBox = new VBox();

        ImageView cancelButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/button_cancel@2x.png")));
        ImageView buyCardButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/button_buy@2x.png")));

        buyCardButton.setFitWidth(200);
        buyCardButton.setFitHeight(70);
        cancelButton.setFitWidth(200);
        cancelButton.setFitHeight(70);

        HBox hBox = new HBox(buyCardButton, cancelButton);
        hBox.setLayoutX(480);
        hBox.setLayoutY(500);
        hBox.setSpacing(20);

        generalVBox.getChildren().addAll(cardHBox, hBox);

        AllDatas.currentRoot.getChildren().addAll(generalVBox);

        GameView.makeImageGlowWhileMouseEnters(cancelButton);
        GameView.makeImageGlowWhileMouseEnters(buyCardButton);

        generalVBox.setLayoutX(WINDOW_WIDTH / 2 + 30);
        generalVBox.setLayoutY(WINDOW_HEIGHT / 2 - 200);

        ShopController.handleEventsOfBuyingCard(generalVBox, cancelButton, buyCardButton, card);
    }

    public static VBox addPriceAndManaForShowingCard(Card card, HBox cardHBox) throws FileNotFoundException {
        ImageView priceBack = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/price_back.png")));
        ImageView manaBack = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/price_back.png")));
        ImageView priceIcon = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/price_icon.png")));
        ImageView manaIcon = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/icon_mana.png")));

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

    private static void setBackGroundOfCollection() throws FileNotFoundException {
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

        Text cardsText = new Text("Show Cards");
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
        stackPane.setLayoutX(MenuView.WINDOW_WIDTH / 2 - 150);
        stackPane.setLayoutY(MenuView.WINDOW_HEIGHT / 2 - 120);

        GameView.makeImageGlowWhileMouseEnters(cardsStackPane, decksStackPane, backStackPane);

        CollectionController.handleEventsOfCollectionOptions(cardsStackPane, decksStackPane, backStackPane);
    }

    public static void showDecksInCollection() {
        try {
            AllDatas.currentRoot.getChildren().clear();
            setBackGroundOfCollection();

            ArrayList<Deck> decks = Game.getInstance().getPlayer1().getDecksOfPlayer();

            TabPane deckTabPane = new TabPane();
            deckTabPane.setLayoutX(300);
            deckTabPane.setPrefWidth(1000);

            VBox buttonsVBox = new VBox();

            setButtonsVBoxForShowingDecks(buttonsVBox);

            AllDatas.currentRoot.getChildren().addAll(buttonsVBox, deckTabPane);

            showDecks(deckTabPane, decks);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void createNewDeck() throws FileNotFoundException {
        AllDatas.currentRoot.getChildren().clear();

        setBackGroundOfCollection();
        VBox buttonsVBox = new VBox();
        setButtonsVBoxForShowingDecks(buttonsVBox);

        setWindowForCreatingNewDeck();

        VBox generalVBox = new VBox();
        AllDatas.currentRoot.getChildren().add(generalVBox);
        generalVBox.setLayoutX(300);
        generalVBox.setPrefWidth(650);
        showCards(generalVBox);

        VBox createDeckVBox = new VBox();
        createDeckVBox.setLayoutX(1040);
        createDeckVBox.setPrefHeight(AllDatas.currentScene.getHeight());

        TextField deckName = new TextField();

        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 20);

        ImageView cancelButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/deck/button_primary_right.png")));
        cancelButton.setFitWidth(190);
        cancelButton.setFitHeight(50);
        Label cancelLabel = new Label("Cancel");
        cancelLabel.setFont(font);
        cancelLabel.setTextFill(rgb(150, 249, 255));

        StackPane cancelStack = new StackPane(cancelButton, cancelLabel);

        ImageView createButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/deck/button_primary_left_glow@2x.png")));
        createButton.setFitWidth(180);
        createButton.setFitHeight(50);
        Label createLabel = new Label("Create Deck");
        createLabel.setTextFill(rgb(150, 249, 255));
        createLabel.setFont(font);

        StackPane createStack = new StackPane(createButton, createLabel);
        HBox buttonsHBox = new HBox(cancelStack, createStack);

        VBox optionsVBox = new VBox(buttonsHBox, deckName);

        createDeckVBox.getChildren().add(optionsVBox);
        createDeckVBox.setAlignment(Pos.BOTTOM_CENTER);
        setMargin(optionsVBox, new Insets(1, 1, 20, 1));

        AllDatas.currentRoot.getChildren().add(createDeckVBox);

        GameView.makeImageGlowWhileMouseEnters(cancelStack, createStack);

        CollectionController.handleEventsOfCreatingNewDeck(cancelStack, createStack, deckName, createDeckVBox);
    }

    private static void showDecks(TabPane decksTabPane, ArrayList<Deck> decks) {
        for (Deck deck : decks) {
            Tab deckTab = new Tab(deck.getDeckName());

            deckTab.setOnSelectionChanged(event -> Deck.setSelectedDeck(deck));

            decksTabPane.getTabs().add(deckTab);
            VBox eachDeckVBox = new VBox();
            deckTab.setContent(eachDeckVBox);
            ArrayList<Hero> heroInDeck = new ArrayList<>();
            heroInDeck.add(deck.getHeroInDeck());
            if (!deck.getCardsInDeck().contains(null))
                addCardsToVBox(deck.getCardsInDeck(), eachDeckVBox);
            if (!deck.getItemsInDeck().contains(null))
                addCardsToVBox(deck.getItemsInDeck(), eachDeckVBox);
            if (!heroInDeck.contains(null))
                addCardsToVBox(heroInDeck, eachDeckVBox);
        }
    }

    public static void showCards(VBox generalVBox) {
        generalVBox.getChildren().clear();

        VBox cardsVBox = new VBox();
        VBox itemsVBox = new VBox();
        VBox heroesVBox = new VBox();

        generalVBox.getChildren().addAll(cardsVBox, itemsVBox, heroesVBox);

        ArrayList<Card> cardsInCollection = Game.getInstance().getPlayer1().getCardsInCollection();
        ArrayList<Item> itemsInCollection = Game.getInstance().getPlayer1().getItemsInCollection();
        ArrayList<Hero> heroesInCollection = Game.getInstance().getPlayer1().getHeroesInCollection();

        addCardsToVBox(cardsInCollection, cardsVBox);
        addCardsToVBox(itemsInCollection, itemsVBox);
        addCardsToVBox(heroesInCollection, heroesVBox);
    }

    public static void setWindowForCreatingNewDeck() throws FileNotFoundException {
        ImageView newDeckBack = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/deck/gray_window.png")));
        newDeckBack.setX(1030);
        newDeckBack.setOpacity(0.7);
        newDeckBack.setFitWidth(400);
        newDeckBack.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());

        AllDatas.currentRoot.getChildren().add(newDeckBack);
    }

    public static void completeSelectedDeck() throws FileNotFoundException {
        AllDatas.currentRoot.getChildren().clear();

        setBackGroundOfCollection();
        setWindowForCreatingNewDeck();

        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 25);

        ImageView backButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
        Text backText = new Text("Back");
        backText.setFont(font);
        backText.setFill(rgb(130, 238, 255));

        StackPane backStack = new StackPane(backButton, backText);
        GameView.makeImageGlowWhileMouseEnters(backStack);

        ImageView deckNameBack = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/gray_button.png")));
        deckNameBack.setFitWidth(180);
        deckNameBack.setFitHeight(70);

        Text deckName = new Text(Deck.getSelectedDeck().getDeckName());
        deckName.setFont(font);
        deckName.setFill(rgb(130, 238, 255));

        StackPane deckNameStack = new StackPane(deckNameBack, deckName);

        HBox hBox = new HBox(deckNameStack, backStack);

        hBox.setLayoutX(1050);
        hBox.setLayoutY(100);

        VBox generalVBox = new VBox();
        generalVBox.setLayoutX(30);

        showCards(generalVBox);

        AllDatas.currentRoot.getChildren().addAll(generalVBox, hBox);

        CollectionController.handleEventsOfAddingCardToDeck(backStack);

    }

    public static void setButtonsVBoxForShowingDecks(VBox vBox) throws FileNotFoundException {
        StackPane newDeckPane = returnButtonsOfShowingDecks("New Deck");
        StackPane backPane = returnButtonsOfShowingDecks("Back");
        StackPane deleteDeckStack = returnButtonsOfShowingDecks("Delete Deck");
        StackPane completeDeckStack = returnButtonsOfShowingDecks("Add Card");
        StackPane mainDeckStack = returnButtonsOfShowingDecks("Set As Main Deck");
        StackPane importDeckStack = returnButtonsOfShowingDecks("Import Deck");
        StackPane exportDeckStack = returnButtonsOfShowingDecks("Export Deck");

        GameView.makeImageGlowWhileMouseEnters(newDeckPane, backPane, completeDeckStack,
                deleteDeckStack, mainDeckStack, importDeckStack, exportDeckStack);

        vBox.getChildren().addAll(newDeckPane, backPane, deleteDeckStack, completeDeckStack,
                mainDeckStack, importDeckStack, exportDeckStack);

        setMargin(newDeckPane, new Insets(30, 1, 1, 20));
        setMargin(backPane, new Insets(20, 1, 1, 20));
        setMargin(deleteDeckStack, new Insets(20, 1, 1, 20));
        setMargin(completeDeckStack, new Insets(20, 1, 1, 20));
        setMargin(mainDeckStack, new Insets(20, 1, 1, 20));
        setMargin(importDeckStack, new Insets(20, 1, 1, 20));
        setMargin(exportDeckStack, new Insets(20, 1, 1, 20));

        CollectionController.handleEventsOfShowingDeckButtons(
                newDeckPane, backPane, deleteDeckStack, completeDeckStack, mainDeckStack, importDeckStack, exportDeckStack);
    }

    public static StackPane returnButtonsOfShowingDecks(String buttonText) throws FileNotFoundException {
        ImageView button = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
        button.setFitWidth(230);
        button.setFitHeight(80);

        Text text = new Text(buttonText);
        text.setFont(Font.font(20));
        text.setFill(rgb(160, 255, 255));

        StackPane stackPane = new StackPane(button, text);
        stackPane.setAccessibleText(buttonText);

        return stackPane;
    }

    public static void showCardsInCollection() {
        try {
            AllDatas.currentRoot.getChildren().clear();
            setBackGroundOfCollection();

            ImageView backButton = new ImageView(new Image(new FileInputStream(
                    HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
            backButton.setFitWidth(250);
            backButton.setFitHeight(100);

            Text backText = new Text("Back");
            backText.setFill(Color.rgb(130, 255, 255));
            backText.setFont(Font.font(null, FontWeight.BOLD, 20));

            StackPane backStack = new StackPane(backButton, backText);
            backStack.setAlignment(Pos.CENTER);
            GameView.makeImageGlowWhileMouseEnters(backStack);

            VBox buttonsVBox = new VBox(backStack);
            buttonsVBox.setPadding(new Insets(30, 1, 1, 20));

            CollectionController.handleEventsOfShowCardsButtons(backStack);

            VBox generalVBox = new VBox();
            generalVBox.setLayoutX(300);

            AllDatas.currentRoot.getChildren().addAll(buttonsVBox, generalVBox);
            showCards(generalVBox);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Card> void addCardsToVBox(ArrayList<T> cards, VBox vBox) {
        for (int i = 0; i < cards.size(); i++) {
            if (i + 3 < cards.size()) {
                HBox hBox = new HBox();
                setAppearanceOfCardsInCollection(hBox, cards.get(i));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 1));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 2));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 3));
                vBox.getChildren().add(hBox);
                i += 3;
            } else if (i + 2 < cards.size()) {
                HBox hBox = new HBox();
                setAppearanceOfCardsInCollection(hBox, cards.get(i));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 1));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 2));
                vBox.getChildren().add(hBox);
                i += 3;
            } else if (i + 1 < cards.size()) {
                HBox hBox = new HBox();
                setAppearanceOfCardsInCollection(hBox, cards.get(i));
                setAppearanceOfCardsInCollection(hBox, cards.get(i + 1));
                vBox.getChildren().add(hBox);
                i += 3;
            } else {
                HBox hBox = new HBox();
                setAppearanceOfCardsInCollection(hBox, cards.get(i));
                vBox.getChildren().add(hBox);
                i += 3;
            }
        }
    }

//    public static void setAppearanceOfCardsInShop(HBox hBox, int rowNumber, VBox vBox, String cardType, int numOfCardsInEachRow) {
//        for (int i = (rowNumber - 1) * numOfCardsInEachRow; i < rowNumber * numOfCardsInEachRow; i++) {
//            VBox cardVBox = new VBox();
//
//            Card card = Card.returnNthCard(cardType, i);
//
//            StackPane stackPane = new StackPane();
//            stackPane.setAccessibleText(Integer.toString(i));
//            stackPane.setPadding(new Insets(13));
//
//            try {
//                setImageForCardInShop(card, stackPane);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            stackPane.setAlignment(Pos.CENTER);
//            stackPane.setOnMouseClicked(event -> {
//                if (stackPane.getEffect() == null) {
//                    if (!Shop.isIsShowingSpecificCard()) {
//                        try {
//                            makeSceneBlur();
//                            Shop.setIsShowingSpecificCard(true);
//                            MenuView.showCardForBuying(card);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//
//            Text cardName = new Text(card.getName());
//            Font font = null;
//            try {
//                font = Font.loadFont(new FileInputStream(
//                        HandleFiles.BEFORE_RELATIVE + "view/Fonts/averta-extrabold-webfont.ttf"), 15);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            cardName.setFont(font);
//            cardName.setFill(rgb(191, 222, 255));
//
//            cardVBox.getChildren().addAll(stackPane, cardName);
//            cardVBox.setAlignment(Pos.CENTER);
//
//            setPriceForCardInShop(card, cardVBox);
//            //   setManaForCardInShop(card, stackPane, onCardVBox);
//
//            hBox.getChildren().add(cardVBox);
//        }
//        hBox.setSpacing(20);
//        vBox.getChildren().add(hBox);
//    }

    public static void setAppearanceOfCardsInCollection(HBox hBox, Card card) {
        Font font = null;
        try {
            font = Font.loadFont(new FileInputStream(
                    HandleFiles.BEFORE_RELATIVE + "view/Fonts/averta-extrabold-webfont.ttf"), 17);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Text manaText = new Text(Integer.toString(card.getMana()));
        ImageView manaIcon = null;
        manaText.setFont(font);
        manaText.setFill(Color.rgb(204, 249, 255));

        try {
            manaIcon = new ImageView(new Image(new FileInputStream(
                    HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/icon_mana.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StackPane manaPane = new StackPane(manaIcon, manaText);

        StackPane cardStack = new StackPane();
        try {
            setImageForCardInShop(card, cardStack);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        Text cardName = new Text(card.getName());
        cardName.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        cardName.setEffect(ds);

        VBox vBox = new VBox(cardStack, cardName, manaPane);
        vBox.setAlignment(Pos.CENTER);
        vBox.setAccessibleText(Integer.toString(card.getId()));

        //is showing cards for selling or completing a deck
        System.out.println("***selling cards");
        vBox.setOnMouseClicked(event -> {
            if (!CollectionController.isIsChoosingForCreatingNewDeck()) {
                try {
                    if (Controller.getPressedButton().getAccessibleText() != null) {
                        System.out.println("HERE");
                        if (Controller.getPressedButton().getAccessibleText().equals("Add Card")) {
                            Deck.getSelectedDeck().addCardToDeck(card);
                            GameView.printInfoMessageWithThisContent(card.getName() +
                                    " was added to " + Deck.getSelectedDeck().getDeckName());
                        }
                    } else if (!Shop.isIsShowingSpecificCard()) {
                        System.out.println("THERE");
                        makeSceneBlur();
                        Shop.setIsShowingSpecificCard(true);
                        showCardForSelling(card, vBox, hBox);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("***createing new deck");
                if (Controller.getPressedButton().getAccessibleText().equals("New Deck")) {
                    try {
                        CollectionController.getDeckIsBeingCreated().addCardToDeck(card);
                    } catch (ParseException | CloneNotSupportedException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });//is showing cards for creating new deck


        cardName.setFill(rgb(210, 250, 247));
        hBox.setPadding(new Insets(30));
        hBox.getChildren().add(vBox);

    }

    public static void showCardForSelling(Card card, VBox cardVBox,
                                          HBox inRowCardsHBox) throws FileNotFoundException {
        HBox cardHBox = new HBox();

        StackPane cardStack = new StackPane();
        setImageForCardInShop(card, cardStack);

//        ImageView cardImage = new ImageView(card.getImageViewOfCard().getImage());

        ImageView cancelButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/button_cancel@2x.png")));

        ImageView sellButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/green_button.png")));
        Label sellLabel = new Label("Sell");
        sellLabel.setTextFill(rgb(190, 237, 173));
        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 30);

        StackPane sellStack = new StackPane(sellButton, sellLabel);
        sellStack.setAlignment(Pos.CENTER);
        sellLabel.setFont(font);

//        cardImage.setFitWidth(200);
//        cardImage.setFitHeight(300);
//        cardImage.setX(WINDOW_WIDTH / 2 + 80);
//        cardImage.setY(WINDOW_HEIGHT / 2 - 250);

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

        cardHBox.setLayoutX(WINDOW_WIDTH / 2 + 50);
        cardHBox.setLayoutY(WINDOW_HEIGHT / 2 - 250);

        cardHBox.getChildren().addAll(cardStack, addPriceAndManaForShowingCard(card, cardHBox));

        CollectionController.handleEventsOfSellingCard(cardHBox, hBox, cancelButton, sellStack, card, cardVBox, inRowCardsHBox);

    }

    public static void showSearchedCard(String cardName) throws CloneNotSupportedException {
        Card card = Card.findCardByName(cardName);

        StackPane stackPane = new StackPane();

        try {
            assert card != null;
            setImageForCardInShop(card, stackPane);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HBox hBox = new HBox();
        VBox priceAndManaVBox = new VBox();

        try {
            priceAndManaVBox = addPriceAndManaForShowingCard(card, hBox);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        hBox.getChildren().addAll(stackPane, priceAndManaVBox);
        hBox.setLayoutX(500);
        hBox.setLayoutY(400);

        AllDatas.currentRoot.getChildren().add(hBox);
    }

    public static void showImportDeckWindow() throws FileNotFoundException {
        setBackGroundOfCollection();

        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 22);

        ImageView importButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
        importButton.setFitWidth(280);
        importButton.setFitHeight(90);
        Text importText = new Text("Import");
        importText.setFont(font);
        importText.setFill(rgb(210, 250, 247));
        StackPane importStack = new StackPane(importButton, importText);

        ImageView backButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/collection/blueButton.png")));
        backButton.setFitWidth(280);
        backButton.setFitHeight(90);
        Text backText = new Text("Back");
        backText.setFont(font);
        backText.setFill(rgb(210, 250, 247));
        StackPane backStack = new StackPane(backButton, backText);

        GameView.makeImageGlowWhileMouseEnters(backStack, importStack);

        TextField textField = new TextField();
        textField.setPrefWidth(280);
        textField.setPrefHeight(80);
        textField.setFont(Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/averta-extrabold-webfont.ttf"), 18));

        HBox hBox = new HBox(textField, importStack, backStack);
        hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutX(300);
        hBox.setLayoutY(300);

        AllDatas.currentRoot.getChildren().add(hBox);

        CollectionController.handleEventsOfImportingDeck(textField, backStack, importStack);
    }


}
