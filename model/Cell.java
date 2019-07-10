package model;

import controller.BattleController;
import javafx.scene.control.Alert;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.collection.Buff;
import model.collection.Card;
import model.collection.Force;
import model.collection.HandleFiles;
import view.MenuView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Cell {
    //    private int coordinateX;
//    private int coordinateY;
    private CellType cellType;
//
//        private ArrayList<Buff> actionBuffs = new ArrayList<>();
//    private ArrayList<Buff> buffs = new ArrayList<>();
//    private ArrayList<CellImpactType> impactTypes = new ArrayList<>();

    private static CellItemType cellItemType;
    private static boolean aForceIsSelected;
    private static int selectedXImage;
    private static int selectedYImage;

    private ArrayList<Buff> cellBuffs = new ArrayList<>();

    public ArrayList<Buff> getCellBuffs() {
        return cellBuffs;
    }

    public void setCellBuffs(ArrayList<Buff> cellBuffs) {
        this.cellBuffs = cellBuffs;
    }

//    public ArrayList<Buff> getActionBuffs() {
//        return actionBuffs;
//    }
//
//    public void setActionBuffs(ArrayList<Buff> actionBuffs) {
//        this.actionBuffs = actionBuffs;
//    }
//
//    public ArrayList<Buff> getBuffs() {
//        return buffs;
//    }
//
//    public void setBuffs(ArrayList<Buff> buffs) {
//        this.buffs = buffs;
//    }
//
//    public ArrayList<CellImpactType> getImpactTypes() {
//        return impactTypes;
//    }
//
//    public void setImpactTypes(ArrayList<CellImpactType> impactTypes) {
//        this.impactTypes = impactTypes;
//    }

    public static boolean isaForceIsSelected() {
        return aForceIsSelected;
    }

    public static CellItemType getCellItemType() {
        return cellItemType;
    }

    public static void setCellItemType(CellItemType cellItemType) {
        Cell.cellItemType = cellItemType;
    }

    public static void setAForceIsSelected(boolean aForceIsSelected) {
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
        cell.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            if(Game.getInstance().getMap().getCells()[yImage][xImage].getCellType() == CellType.empty) {
                cell.setEffect(new Glow(0.7));
            }
        });
        cell.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if(Game.getInstance().getMap().getCells()[yImage][xImage].getCellType() == CellType.empty) {
                cell.setEffect(null);
            }
        });
        cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            MenuView.resetMap();
            if (aForceIsSelected) {
                setAForceIsSelected(false);
                if(Card.getCardByCoordination(yImage,xImage)!=null) {
                    BattleController.checkAllConditionsToAttack(Card.getCardByCoordination(selectedYImage, selectedXImage), Card.getCardByCoordination(yImage, xImage));
                }
                if (Map.cardCanBeMovedToThisCell(Card.getCardByCoordination(selectedYImage, selectedXImage), yImage, xImage)) {
                    BattleController.move((Force) Objects.requireNonNull(Card.getCardByCoordination(selectedYImage, selectedXImage)), yImage, xImage);
                }
            } else if (Hand.isSelectedInHand()) {
                Hand.setSelectedInHand(false);
                if (Map.checkIfMinionCardCanBeInsertedInThisCoordination(yImage, xImage)) {
                    try {
                        Game.getInstance().getPlayer1().getMainDeck().getHand().insertCardFromHandInMap(Hand.getIndexInHand(), yImage, xImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private static void handleEventForce(ImageView force, int xImage, int yImage) {
        force.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    Card card = Card.getCardByCoordination(yImage, xImage);
                    System.out.println("name : " + card.getName());
                    System.out.println("attacked : " + card.isHasAttackedInThisTurn());
                    System.out.println("moved : " + card.isHasMovedInThisTurn());
                    if (Card.thisCardIsYours(yImage, xImage) && (!Card.getCardByCoordination(yImage, xImage).isHasMovedInThisTurn()
                            || !Card.getCardByCoordination(yImage, xImage).isHasAttackedInThisTurn())) {
                        force.setEffect(new Glow(0.7));
                    }
                }
        );
        force.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (Card.thisCardIsYours(yImage, xImage) && (!Card.getCardByCoordination(yImage, xImage).isHasMovedInThisTurn()
                    || !Card.getCardByCoordination(yImage, xImage).isHasAttackedInThisTurn())) {
                force.setEffect(null);
            }
        });
        force.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            MenuView.resetMap();
            if (Card.thisCardIsYours(yImage, xImage)) {
                if (!Objects.requireNonNull(Card.getCardByCoordination(yImage, xImage)).isHasMovedInThisTurn()) {
//                    force.setDisable(true);
                    BattleController.showAllPossibilities((Force) Card.getCardByCoordination(yImage, xImage));
                    Cell.setAForceIsSelected(true);
                    Cell.selectedXImage = xImage;
                    Cell.selectedYImage = yImage;
                }
                if (!Card.getCardByCoordination(yImage, xImage).isHasAttackedInThisTurn()) {
//                    force.setDisable(true);
                    showAllPossibleAttacks((Force) Card.getCardByCoordination(yImage, xImage));
                    Cell.setAForceIsSelected(true);
                    Cell.selectedXImage = xImage;
                    Cell.selectedYImage = yImage;
                }
            }
        });
    }

    public static void showAllPossibleAttacks(Force force) {
        int counter = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                if (Card.getCardByCoordination(j, i) != null) {
                    if (Card.getCardByCoordination(j, i).isEnemy(j, i)) {
                        if (BattleController.checkRangeForAttack(force, (Force) Card.getCardByCoordination(j, i))) {
                            try {
                                counter++;
                                Map.getCellsView()[i][j].setImage(new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/tiles_board_target.png")));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        if (counter == 0 && force.isHasMovedInThisTurn()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No target available to attack");
            alert.show();
        }
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

    public boolean heroIsOnCell(){
        if (this.cellType == CellType.selfHero || this.cellType == CellType.enemyHero){
            return true;
        }
        return false;
    }

    public boolean minionIsOnCell(){
        if (this.cellType == CellType.selfMinion || this.cellType == CellType.enemyMinion){
            return true;
        }
        return false;
    }

    public static Coordination returnCellHasThisCoordinationInside(double x, double y){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView cellView = Map.getCellsView()[i][j];
                double cellX = cellView.getLayoutX();
                double cellY = cellView.getLayoutY();
                if ((x >= cellX && x <= cellX + 65) && (y >= cellY && y <= cellY + 65)){
                    return new Coordination(j, i);
                }
            }
        }
        return null;
    }


}
