package controller;

import Audio.AudioPlayer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.*;
import model.collection.HandleFiles;
import network.Message;
import org.json.simple.parser.ParseException;
import view.GameView;
import view.MainView;
import view.MenuView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    private static StackPane pressedButton;

    public static StackPane getPressedButton() {
        return pressedButton;
    }

    public static void setPressedButton(StackPane pressedButton) {
        Controller.pressedButton = pressedButton;
    }

    public static void createAll() throws Exception{
        createAllMenus();
        createAllDataFromJSON("client");
    }
    public static void createAllDataFromJSON(String thread) throws Exception{
        HandleFiles.createHeroes(thread);
        HandleFiles.createItems(thread);
        HandleFiles.createMinions(thread);
        HandleFiles.createSpells(thread);
    }


    public static void createAllMenus() {
        AllDatas.account= new LinkedListMenus("Account",true);
        AllDatas.leaderboard = new LinkedListMenus("Leaderboard",false);
        AllDatas.commandLine = new LinkedListMenus("Command Line",false);
        AllDatas.collection = new LinkedListMenus("Collection",false);
        AllDatas.shop = new LinkedListMenus("Shop",false);
        AllDatas.battle = new LinkedListMenus("Battle",false);
        AllDatas.help = new LinkedListMenus("Help",false);
        AllDatas.chatroom = new LinkedListMenus("Chatroom",false);
        AllDatas.account.getChilds().add(AllDatas.commandLine);
        AllDatas.commandLine.getChilds().add(AllDatas.collection);
        AllDatas.commandLine.getChilds().add(AllDatas.shop);
        AllDatas.commandLine.getChilds().add(AllDatas.battle);
        AllDatas.commandLine.getChilds().add(AllDatas.chatroom);
        AllDatas.chatroom.setParent(AllDatas.commandLine);
        AllDatas.collection.setParent(AllDatas.commandLine);
        AllDatas.leaderboard.setParent(AllDatas.account);
        AllDatas.commandLine.setParent(AllDatas.account);
        AllDatas.shop.setParent(AllDatas.commandLine);
        AllDatas.battle.setParent(AllDatas.commandLine);

//        AllDatas.cardsInCollection = new LinkedListMenus("cardsInCollection", false);
//        AllDatas.decksInCollection = new LinkedListMenus("decksInCollection", false);
//        AllDatas.cardsInCollection.setParent(AllDatas.collection);
//        AllDatas.decksInCollection.setParent(AllDatas.collection);

   //     Controller.enterLoginMenu();
//        AllDatas.account.setCommandsForHelp("create account [user name]","login [user name]","show leaderboard","save");
//        AllDatas.leaderboard.setCommandsForHelp("exit");
//        AllDatas.commandLine.setCommandsForHelp("Collection","Shop","Battle","exit","Help","logout");
//        AllDatas.collection.setCommandsForHelp("exit","show","search [card name | item name]","save","create deck[deck name]"
//        ,"delete deck [deck name]","add [card id | card id | hero id] to deck [deck name]",
//                "remove [card id | card id| hero id] from deck [deck name]","validate deck [deck name]","select deck [deck name]"
//        ,"show all decks","show deck [deck name]","Help");
//        AllDatas.shop.setCommandsForHelp("exit","show collection","search [item name | card name]","search collection [item name | card name]",
//                "buy [card name | item name]","sell [card id | card id]","show","Help");
        LinkedListMenus.allMenus.add(AllDatas.account);
        LinkedListMenus.allMenus.add(AllDatas.leaderboard);
        LinkedListMenus.allMenus.add(AllDatas.commandLine);
        LinkedListMenus.allMenus.add(AllDatas.collection);
        LinkedListMenus.allMenus.add(AllDatas.shop);
        LinkedListMenus.allMenus.add(AllDatas.battle);
        LinkedListMenus.allMenus.add(AllDatas.help);
        LinkedListMenus.allMenus.add(AllDatas.chatroom);
//        LinkedListMenus.allMenus.add(AllDatas.cardsInCollection);
//        LinkedListMenus.allMenus.add(AllDatas.decksInCollection);

    }
    public static void handleCommands(Scanner scanner) throws Exception{
        assert LinkedListMenus.whichMenuNow() != null;
        switch (LinkedListMenus.whichMenuNow().getMenuName()) {
            case "Account":
                System.out.println("account");
                MenusCommandController.accountController(scanner);
                break;
            case "Leaderboard":
                System.out.println("leaderboard");
                MenusCommandController.leaderboardController(scanner);
                break;
            case "Command Line":
                System.out.println("command line");
                MenusCommandController.commandLineController(scanner);
                break;
            case "Collection":
                System.out.println("collection");
                MenusCommandController.collectionController(scanner);
                break;
            case "Shop":
                System.out.println("shop");
                MenusCommandController.shopController(scanner);
                break;
            case "Battle":
                System.out.println("battle");
                if (Game.getInstance().isPlayer1Turn()) {
                    MenusCommandController.battleController(scanner);
                    System.out.println(Game.getInstance().getPlayer1().getNumOfMana());
                }else{
                    System.out.println(Game.getInstance().getPlayer2().getNumOfMana());
                    AI.insertCardTillPossible();
                    AI.moveTillPossible();
                    System.out.println(Game.getInstance().getPlayer2().getNumOfMana());
                }
                Map.show();
                Hand.showHand();


                break;
            case "Help":
                System.out.println("help");
                MenusCommandController.helpController(scanner);
                break;
        }
    }

    public static void enterLoginMenu() throws IOException{
        AllDatas.currentRoot = AllDatas.account.getRoot();
        AllDatas.currentScene = AllDatas.account.getScene();
        AllDatas.account.setNowInThisMenu(true);

        MenuView.showLoginMenu();
    }

    public static void enterMainMenu() throws FileNotFoundException {
        AllDatas.currentRoot = AllDatas.commandLine.getRoot();
        AllDatas.currentScene = AllDatas.commandLine.getScene();
        AllDatas.commandLine.setNowInThisMenu(true);
        AllDatas.account.setNowInThisMenu(false);

        MenuView.showMainMenu();
    }

    public static void enterShop() throws FileNotFoundException {
        AllDatas.currentRoot = AllDatas.shop.getRoot();
        AllDatas.currentScene = AllDatas.shop.getScene();
        AllDatas.shop.setNowInThisMenu(true);
        AllDatas.commandLine.setNowInThisMenu(false);

        MenuView.showShop();
    }

    public static void enterCollection() throws FileNotFoundException {
        AllDatas.currentRoot = AllDatas.collection.getRoot();
        AllDatas.currentScene = AllDatas.collection.getScene();

        AllDatas.collection.setNowInThisMenu(true);
        AllDatas.commandLine.setNowInThisMenu(false);

        MenuView.showCollection();
    }


    public static void enterChatroom(){
        AllDatas.currentRoot = AllDatas.chatroom.getRoot();
        AllDatas.currentScene = AllDatas.chatroom.getScene();

        AllDatas.chatroom.setNowInThisMenu(true);
        AllDatas.commandLine.setNowInThisMenu(false);


        try {
            MenuView.showChatroom();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void enterBattle() throws IOException, CloneNotSupportedException, ParseException {
        AudioPlayer.getBattleAudio().play();
        try {
            AudioPlayer.getMenuAudio().stop();
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }

        AllDatas.currentRoot = AllDatas.battle.getRoot();
        AllDatas.currentScene = AllDatas.battle.getScene();
        AllDatas.battle.setNowInThisMenu(true);
        AllDatas.commandLine.setNowInThisMenu(false);
        MenuView.showBattle();
    }

    public static void enterLeaderBoard(){
        AllDatas.currentRoot = AllDatas.leaderboard.getRoot();
        AllDatas.currentScene = AllDatas.leaderboard.getScene();
        AllDatas.leaderboard.setNowInThisMenu(true);
        AllDatas.commandLine.setNowInThisMenu(false);

        AllDatas.currentRoot.getChildren().clear();
        MainView.primaryStage.setScene(AllDatas.currentScene);


        try {
            ImageView leaderBoardBack = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/leaderboard_background.jpg");
            leaderBoardBack.setFitHeight(MenuView.getPrimaryScreenBounds().getHeight());
            leaderBoardBack.setFitWidth(MenuView.getPrimaryScreenBounds().getWidth());
            AllDatas.currentRoot.getChildren().add(leaderBoardBack);

            ImageView back = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/chatroom/button_back_corner@2x.png");
            back.setFitWidth(100);
            back.setFitHeight(100);
            GameView.makeImageGlowWhileMouseEnters(back);
            back.setOnMouseClicked(event -> {
                try {
                    enterMainMenu();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            AllDatas.currentRoot.getChildren().add(back);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String showLeaderBoardRequest = "showLeaderBoard";
        MainView.getClient().getDos().println(new Message(showLeaderBoardRequest,"String","showLeaderBoard").messageToString());
        MainView.getClient().getDos().flush();
    }


    public static void sampleGame() throws CloneNotSupportedException, IOException, ParseException {
        Shop.buyCardAndAddToCollection("Arash");
        Shop.buyCardAndAddToCollection("Total Disarm");
        Shop.buyCardAndAddToCollection("Lighting Bolt");
        Shop.buyCardAndAddToCollection("All Disarm");
        Shop.buyCardAndAddToCollection("All Poison");
        Shop.buyCardAndAddToCollection("Dispel");
        Shop.buyCardAndAddToCollection("Sacrifice");
        Shop.buyCardAndAddToCollection("Shock");
        Shop.buyCardAndAddToCollection("Kamandar Fars");
        Shop.buyCardAndAddToCollection("Neyzedar Toorani");
        Shop.buyCardAndAddToCollection("Gorzdar Toorani");
        Shop.buyCardAndAddToCollection("Gorzdar Toorani");
        Shop.buyCardAndAddToCollection("Div Siah");
        Shop.buyCardAndAddToCollection("Ghool Takcheshm");
        Shop.buyCardAndAddToCollection("Mar Sammi");
        Shop.buyCardAndAddToCollection("Mar Ghoolpeikar");
        Shop.buyCardAndAddToCollection("Gorg Sefid");
        Shop.buyCardAndAddToCollection("Jadoogar Aazam");
        Shop.buyCardAndAddToCollection("Siavash");
        Shop.buyCardAndAddToCollection("Nane Sarma");
        Shop.buyCardAndAddToCollection("Arjhang Div");

//        Deck.createDeck("bahar");
//        Deck.addCardOrItemToDeck(107,"bahar");
//        Deck.addCardOrItemToDeck(401,"bahar");
//        Deck.addCardOrItemToDeck(407,"bahar");
//        Deck.addCardOrItemToDeck(410,"bahar");
//        Deck.addCardOrItemToDeck(411,"bahar");
//        Deck.addCardOrItemToDeck(412,"bahar");
//        Deck.addCardOrItemToDeck(418,"bahar");
//        Deck.addCardOrItemToDeck(420,"bahar");
//        Deck.addCardOrItemToDeck(301,"bahar");
//        Deck.addCardOrItemToDeck(309,"bahar");
//        Deck.addCardOrItemToDeck(311,"bahar");
//        Deck.addCardOrItemToDeck(311,"bahar");
//        Deck.addCardOrItemToDeck(313,"bahar");
//        Deck.addCardOrItemToDeck(317,"bahar");
//        Deck.addCardOrItemToDeck(318,"bahar");
//        Deck.addCardOrItemToDeck(321,"bahar");
//        Deck.addCardOrItemToDeck(322,"bahar");
//        Deck.addCardOrItemToDeck(326,"bahar");
//        Deck.addCardOrItemToDeck(338,"bahar");
//        Deck.addCardOrItemToDeck(336,"bahar");
//        Deck.addCardOrItemToDeck(340,"bahar");
//        Deck.selectDeck("bahar");
    }

    public static void chooseSingleOrMultiPlayerWindow() throws FileNotFoundException {
        AllDatas.currentRoot.getChildren().clear();

        MenuView.setBackgroundOfMainMenu();

        VBox generalVBox = new VBox();

        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/Herculanum.ttf"), 23);


        ImageView singleButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/button_primary_middle_glow@2x.png")));
        Label singleLabel = new Label("Single Player");
        singleLabel.setFont(font);
        singleLabel.setTextFill(Color.rgb(158, 250, 255));

        StackPane singleStack = new StackPane(singleButton, singleLabel);

        ImageView multiButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/button_primary_middle_glow@2x.png")));
        Label multiLabel = new Label("Multi Player");
        multiLabel.setFont(font);
        multiLabel.setTextFill(Color.rgb(158, 250, 255));

        StackPane multiStack = new StackPane(multiButton, multiLabel);

        ImageView backButton = new ImageView(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/button_primary_middle_glow@2x.png")));
        Label backLabel = new Label("Back");
        backLabel.setFont(font);
        backLabel.setTextFill(Color.rgb(158, 250, 255));

        StackPane backStack = new StackPane(backButton, backLabel);

        GameView.makeImageGlowWhileMouseEnters(singleStack, multiStack, backStack);

        HBox hBox = new HBox(singleStack, multiStack);
        generalVBox.setLayoutX(MenuView.WINDOW_WIDTH/2 - 140);
        generalVBox.setLayoutY(MenuView.WINDOW_HEIGHT/2 - 100);

        generalVBox.getChildren().addAll(hBox, backStack);

        AllDatas.currentRoot.getChildren().add(generalVBox);

        BattleController.handleEventsOfChoosingMultiOrSingleMode(multiStack, singleStack, backStack);
    }


}
