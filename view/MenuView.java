package view;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        Image background = new Image(new FileInputStream("/Users/hamilamailee/Documents/" +
                "project-10/view/Photos/background@2x.jpg"));
        ImageView backgroundImageView = new ImageView(background);
        backgroundImageView.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        backgroundImageView.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());

        Image midground = new Image(new FileInputStream("/Users/hamilamailee/Documents/project-10/view/" +
                "Photos/pillars_near@2x.png"));
        ImageView midgroundImageView = new ImageView(midground);
        midgroundImageView.fitWidthProperty().bind(AllDatas.currentRoot.widthProperty());
        midgroundImageView.fitHeightProperty().bind(AllDatas.currentRoot.heightProperty());


        Image foreground = new Image(new FileInputStream("/Users/hamilamailee/Documents/" +
                "project-10/view/Photos/foreground@2x.png"));
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
