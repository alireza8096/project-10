package model;

import com.google.gson.Gson;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.collection.Card;
import network.Message;
import view.MainView;

public class Timer {

    private Card card;
    private AnimationTimer timer;
    private Label lblTime = new Label("0 .s");
    private int seconds;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public AnimationTimer getTimer() {
        return timer;
    }

    public void setTimer(AnimationTimer timer) {
        this.timer = timer;
    }

    public Label getLblTime() {
        return lblTime;
    }

    public void setLblTime(Label lblTime) {
        this.lblTime = lblTime;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    {
        timer = new AnimationTimer() {

            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    if (now > lastTime + 1_000_000_000) {
                        seconds++;
                        lblTime.setText(Integer.toString(seconds) + " .s");
                        lastTime = now;

                        Gson gson = new Gson();

                    }
                    if (seconds == 50){
                        stop();
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(card, Card.class);
                        Message message = new Message(jsonString, "Card", "sellCardInAuction");
                        MainView.getClient().getDos().println(message.messageToString());
                        MainView.getClient().getDos().flush();
                    }
                } else {
                    lastTime = now;
                }
            }

            @Override
            public void stop() {
                super.stop();
                lastTime = 0;
                seconds = 0;
            }

            public void start(int from){
                super.stop();
                lastTime = 0;
                seconds = 0;
            }
        };
    }

//    @Override
//    public void start(Stage primaryStage) {
//        timer = new AnimationTimer() {
//
//            private long lastTime = 0;
//
//            @Override
//            public void handle(long now) {
//                if (lastTime != 0) {
//                    if (now > lastTime + 1_000_000_000) {
//                        seconds++;
//                        lblTime.setText(Integer.toString(seconds) + " .s");
//                        lastTime = now;
//                    }
//                    if (seconds == 5){
//                        stop();
//                    }
//                } else {
//                    lastTime = now;
//                }
//            }
//
//            @Override
//            public void stop() {
//                super.stop();
//                lastTime = 0;
//                seconds = 0;
//            }
//        };
//
//        Button btnStart = new Button("START");
//        btnStart.setOnAction(e ->
//        {
//            lblTime.setText("0 .s");
//            timer.start();
//        });
//
//        Button btnStop = new Button("STOP");
//        btnStop.setOnAction(e -> timer.stop());
//
//
//        VBox box = new VBox(16, lblTime, btnStart, btnStop);
//        box.setAlignment(Pos.CENTER);
//
//        primaryStage.setScene(new Scene(new StackPane(box)));
//        primaryStage.show();
//    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}