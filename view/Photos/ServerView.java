package view.Photos;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.collection.Card;
import model.collection.HandleFiles;
import network.Server;
import view.GameView;
import view.MenuView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.paint.Color.rgb;

public class ServerView extends Application {

    private static Pane currentRoot;
    private static Scene currentScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        currentRoot = new Pane();
        currentScene = new Scene(currentRoot, MenuView.WINDOW_WIDTH, MenuView.WINDOW_HEIGHT);

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
        serverStack.setLayoutX(500);
        serverStack.setLayoutY(350);
        currentRoot.getChildren().add(serverStack);
        GameView.makeImageGlowWhileMouseEnters(serverStack);

        serverStack.setOnMouseClicked(event -> Platform.runLater(() -> {
            new Thread(() -> {
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
            }).start();
        }));

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    public static void showCardsInServer() throws FileNotFoundException {
        currentRoot.getChildren().clear();
        setScrollBarForServer();

        ImageView background = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/server/server_background.jpg")));
        background.fitWidthProperty().bind(currentRoot.widthProperty());
        background.fitHeightProperty().bind(currentRoot.heightProperty());
        ColorAdjust adj = new ColorAdjust(0, 0, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        background.setEffect(adj);

        currentRoot.getChildren().add(background);

        VBox generalVBox = new VBox();
        addCardsInServerToVBox(generalVBox, Server.getCards());

        currentRoot.getChildren().add(generalVBox);
    }

    public static void addCardsInServerToVBox(VBox generalVBox, ArrayList<Card> cards) throws FileNotFoundException {
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

    public static void setAppearanceOfCardsInServer(HBox hBox, Card card) throws FileNotFoundException {

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

    public static void setNumberOfCardsInShop(VBox cardVBox, Card card) throws FileNotFoundException {
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

    public static void setScrollBarForServer(){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(currentRoot);
        currentScene.setRoot(scrollPane);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
