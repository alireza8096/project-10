package view;

import controller.Controller;
import javafx.application.Application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.AllDatas;
import model.Shop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainView extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        Shop.setRightVBox(new VBox());
        Shop.setLeftVBox(new VBox());
        Shop.getLeftVBox().setLayoutX(300);


        MenuView.setVBoxForShowingMinionCards();
        primaryStage = stage;
        primaryStage.setMaximized(true);

        Controller.enterLoginMenu();
        MenuView.showLoginMenu();
        stage.show();
    }
    public static void main(String[] args) throws Exception {
        Controller.createAll();
        launch(args);
        Scanner scanner = new Scanner(System.in);
        while (AllDatas.gameBoolean){
            Controller.handleCommands(scanner);
        }
    }

    public static ImageView getPhotoWithThisPath(String path) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(path));
        return new ImageView(image);

    }
}
