package model;

import controller.BattleController;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.collection.Card;
import model.collection.Force;
import model.collection.HandleFiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Random;

public class Cell {
    //    private int coordinateX;
//    private int coordinateY;
    private CellType cellType;
    //    private ArrayList<Buff> actionBuffs = new ArrayList<>();
//    private ArrayList<Buff> buffs = new ArrayList<>();
//    private ArrayList<CellImpactType> impactTypes = new ArrayList<>();
    private static CellItemType cellItemType;
    private static boolean aForceIsSelected;
    private static int selectedXImage;
    private static int selectedYImage;


    public static CellItemType getCellItemType() {
        return cellItemType;
    }

    public static void setCellItemType(CellItemType cellItemType) {
        Cell.cellItemType = cellItemType;
    }


    private static void setaForceIsSelected(boolean aForceIsSelected) {
        Cell.aForceIsSelected = aForceIsSelected;
    }

    public static void handleForce() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                handleEventForce(Map.getForcesView()[i][j], i, j);
            }
        }
    }

    public static void handleCell() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                handleEventCell(Map.getCellsView()[i][j], i, j);
            }
        }
    }

    private static void handleEventCell(ImageView cell, int xImage, int yImage) {
        cell.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> cell.setEffect(new Glow(0.7)));
        cell.addEventHandler(MouseEvent.MOUSE_EXITED, event -> cell.setEffect(null));
        cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            for(int i=0; i<9; i++){
                for(int j=0; j<5; j++){
                    try {
                        Map.getCellsView()[i][j].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/tiles_board.png")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (aForceIsSelected) {
                setaForceIsSelected(false);
                Map.getForcesView()[selectedXImage][selectedYImage].setDisable(false);
                if (Map.cardCanBeMovedToThisCell(Card.getCardByCoordination(selectedYImage,selectedXImage), yImage, xImage)) {
                    BattleController.move((Force) Objects.requireNonNull(Card.getCardByCoordination(selectedYImage, selectedXImage)), yImage, xImage);
                }
            } else if (Hand.isSelectedInHand()) {
                Hand.setSelectedInHand(false);
                if(Map.checkIfMinionCardCanBeInsertedInThisCoordination(yImage,xImage)){
                    Game.getInstance().getPlayer1().getMainDeck().getHand().insertCardFromHandInMap(Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand().get(Hand.getIndexInHand()).getName(),yImage,xImage);
                }
            }
        });
//        else {
//            cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//                if (Map.checkIfMinionCardCanBeInsertedInThisCoordination(yImage, xImage)) {
//                    try {
//                        Game.getInstance().getPlayer1().getMainDeck().getHand().insertCardFromHandInMap(Game.getInstance().getPlayer1().getMainDeck().getHand().getCardsInHand().get(Hand.getIndexInHand()).getName(), yImage, xImage);
//                    } catch (CloneNotSupportedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Hand.setSelectedInHand(false);
//            });
//        }
    }


    private static void handleEventForce(ImageView force, int xImage, int yImage) {
        force.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    if (Card.thisCardIsYours(yImage, xImage) && !Card.getCardByCoordination(yImage,xImage).isHasMovedInThisTurn()) {
                        force.setEffect(new Glow(0.7));
                    }
                }
        );
        force.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (Card.thisCardIsYours(yImage, xImage)) {
                force.setEffect(null);
            }
        });
        force.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Card.thisCardIsYours(yImage, xImage)) {
                if(!Objects.requireNonNull(Card.getCardByCoordination(yImage, xImage)).isHasMovedInThisTurn()) {
                    force.setDisable(true);
                    BattleController.showAllPossibilities((Force) Card.getCardByCoordination(yImage, xImage));
                    Cell.setaForceIsSelected(true);
                    Cell.selectedXImage = xImage;
                    Cell.selectedYImage = yImage;
                }
            }
        });
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public static Cell getCellByCoordination(int x, int y) {
        return Game.getInstance().getMap().getCells()[x][y];
    }

    public static int returnRandomNumberForCoordinationInThisRange(int i1, int i2) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(i2) + i1;
    }


}
