package view;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.collection.Card;
import model.collection.HandleFiles;
import network.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import static javafx.scene.paint.Color.rgb;

public class ServerView extends Application {

    private static Pane currentRoot;
    private static Scene currentScene;
    private static Rectangle2D primaryScreenBounds;

    public static Properties properties = new Properties();

    @Override
    public void start(Stage primaryStage) throws Exception {
//        String configFileName = "/Users/bahar/Desktop/DUELYST/config.txt";
//        InputStream inputStream = new FileInputStream(configFileName);
//        properties.load(inputStream);

//        System.out.println("port : " + properties.getProperty("port"));

        currentRoot = new Pane();
        currentScene = new Scene(currentRoot, MenuView.WINDOW_WIDTH, MenuView.WINDOW_HEIGHT);
        primaryScreenBounds = Screen.getPrimary().getVisualBounds();



        ImageView background = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/server/server_background.jpg")));
        currentRoot.getChildren().add(background);

        ImageView serverButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/server/gray_button.png")));
        serverButton.setFitWidth(400);
        serverButton.setFitHeight(130);
        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 20);
        Label serverLabel = new Label("Start Server");
        serverLabel.setFont(font);
        serverLabel.setTextFill(rgb(227, 252, 255));

        StackPane serverStack = new StackPane(serverButton, serverLabel);
        currentRoot.getChildren().add(serverStack);
        GameView.makeImageGlowWhileMouseEnters(serverStack);

        serverStack.setLayoutX(primaryScreenBounds.getWidth()/2 - 150);
        serverStack.setLayoutY(primaryScreenBounds.getHeight()/2 - 65);

        serverStack.setOnMouseClicked(event -> Platform.runLater(() -> new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    try {
                        Controller.createAllDataFromJSON("server");
                        showCardsInServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                new Server();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start()));

        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void setBackGroundPfServer() throws FileNotFoundException {
        ImageView background = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/server/server_background.jpg")));
        background.fitWidthProperty().bind(currentRoot.widthProperty());
        background.fitHeightProperty().bind(currentRoot.heightProperty());
        ColorAdjust adj = new ColorAdjust(0, 0, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        background.setEffect(adj);

        currentRoot.getChildren().add(background);
    }

    private static void showCardsInServer() throws FileNotFoundException {
        currentRoot.getChildren().clear();
        setScrollBarForServer();

        setBackGroundPfServer();

        VBox generalVBox = new VBox();
        addCardsInServerToVBox(generalVBox, Server.getCards());

        VBox buttonsVBox = new VBox();
        buttonsVBox.setLayoutX(primaryScreenBounds.getWidth()/2 + 300);
        setButtonsForServer(buttonsVBox);
        currentRoot.getChildren().addAll(generalVBox, buttonsVBox);
    }

    private static void setButtonsForServer(VBox vBox) throws FileNotFoundException {
        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 20);

        ImageView showSocketsBtn = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/server/gray_button.png")));
        showSocketsBtn.setFitWidth(400);
        showSocketsBtn.setFitHeight(130);
        Label socketsBtn = new Label("Show Online Clients");
        socketsBtn.setFont(font);
        socketsBtn.setTextFill(rgb(227, 252, 255));

        StackPane showSockets = new StackPane(showSocketsBtn, socketsBtn);
        currentRoot.getChildren().add(showSockets);

        ImageView refreshButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/server/gray_button.png")));
        refreshButton.setFitWidth(400);
        refreshButton.setFitHeight(130);
        Label refreshLabel = new Label("Refresh");
        refreshLabel.setFont(font);
        refreshLabel.setTextFill(rgb(227, 252, 255));
        StackPane refreshStack = new StackPane(refreshButton, refreshLabel);

        GameView.makeImageGlowWhileMouseEnters(showSockets, refreshStack);

        showSockets.setOnMouseClicked(event -> {
            try {
                showClientsWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refreshStack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    showCardsInServer();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        vBox.getChildren().addAll(showSockets, refreshStack);
    }

    private static void addCardsInServerToVBox(VBox generalVBox, ArrayList<Card> cards) throws FileNotFoundException {
        for (int i = 0; i < cards.size(); i++) {
            if (i + 3 < cards.size()) {
                HBox hBox = new HBox();
                hBox.setPadding(new Insets(1, 80, 1, 80));
                setAppearanceOfCardsInServer(hBox, cards.get(i));
                setAppearanceOfCardsInServer(hBox, cards.get(i + 1));
                setAppearanceOfCardsInServer(hBox, cards.get(i + 2));
                setAppearanceOfCardsInServer(hBox, cards.get(i + 3));
                generalVBox.getChildren().add(hBox);
                i += 3;
            } else if (i + 2 < cards.size()) {
                HBox hBox = new HBox();
                hBox.setPadding(new Insets(1, 80, 1, 80));
                setAppearanceOfCardsInServer(hBox, cards.get(i));
                setAppearanceOfCardsInServer(hBox, cards.get(i + 1));
                setAppearanceOfCardsInServer(hBox, cards.get(i + 2));
                generalVBox.getChildren().add(hBox);
                i += 3;
            } else if (i + 1 < cards.size()) {
                HBox hBox = new HBox();
                hBox.setPadding(new Insets(1, 80, 1, 80));
                setAppearanceOfCardsInServer(hBox, cards.get(i));
                setAppearanceOfCardsInServer(hBox, cards.get(i + 1));
                generalVBox.getChildren().add(hBox);
                i += 3;
            } else {
                HBox hBox = new HBox();
                hBox.setPadding(new Insets(1, 80, 1, 80));
                setAppearanceOfCardsInServer(hBox, cards.get(i));
                generalVBox.getChildren().add(hBox);
                i += 3;
            }
        }
    }

    private static void setAppearanceOfCardsInServer(HBox hBox, Card card) throws FileNotFoundException {

        Text cardName = new Text(card.getName());
        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 15);
        cardName.setFont(font);
        cardName.setFill(rgb(191, 248, 255));

        VBox cardVBox = new VBox();

        StackPane cardStack = new StackPane();
        currentRoot.getChildren().add(cardStack);
        MenuView.setImageForCardInShop(card, cardStack);

        cardVBox.getChildren().addAll(cardStack, cardName);
        cardVBox.setAlignment(Pos.CENTER);

        setNumberOfCardsInShop(cardVBox, card);
        MenuView.setPriceForCardInShop(card, cardVBox);

        hBox.getChildren().add(cardVBox);

    }

    private static void setNumberOfCardsInShop(VBox cardVBox, Card card) throws FileNotFoundException {
        ImageView back = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/server/price_back.png")));
        String numOfCard = Integer.toString(card.getNumInShop());

        ImageView icon = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/server/menu_icon_card_backs@2x.png")));
        icon.setFitWidth(70);
        icon.setFitHeight(70);

        Label label = new Label(numOfCard);
        label.setTextFill(rgb(227, 252, 255));
        label.setFont(new Font(20));
        label.textProperty().bind(card.numInShopPropertyProperty().asString());

        StackPane stackPane = new StackPane(back, icon, label);
        StackPane.setAlignment(icon, Pos.CENTER_LEFT);

        cardVBox.getChildren().add(stackPane);

    }

    private static void setScrollBarForServer(){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(currentRoot);
        currentScene.setRoot(scrollPane);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private static void showClientsWindow() throws FileNotFoundException {
        currentRoot.getChildren().clear();
        setBackGroundPfServer();

        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 20);
        VBox vBox = new VBox();
        vBox.setLayoutX(primaryScreenBounds.getWidth()/2 - 400);
        vBox.setLayoutY(200);
        currentRoot.getChildren().add(vBox);
        for (String name : Server.getOnlinePlayers()){
            Text textName = new Text(name);
            textName.setFont(new Font(20));
            textName.setFont(font);
            vBox.getChildren().add(textName);
        }
        StackPane backStack = setBackButton();
        currentRoot.getChildren().add(backStack);
        backStack.setLayoutX(primaryScreenBounds.getWidth()/2 + 100);
        backStack.setLayoutY(primaryScreenBounds.getHeight()/7);
    }

    private static StackPane setBackButton() throws FileNotFoundException {
        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 20);

        ImageView backButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/server/gray_button.png")));
        backButton.setFitWidth(380);
        backButton.setFitHeight(180);
        Label backLabel = new Label("Back");
        backLabel.setTextFill(Color.rgb(230, 239, 255));
        backLabel.setFont(font);

        StackPane stackPane = new StackPane(backButton, backLabel);
        GameView.makeImageGlowWhileMouseEnters(stackPane);
        stackPane.setOnMouseClicked(event -> {
            try {
                showCardsInServer();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        return stackPane;
    }
}
