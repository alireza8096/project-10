package model;

import controller.BattleController;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.collection.Buff;
import model.collection.Card;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Cell {
    private int coordinateX;
    private int coordinateY;
    private CellType cellType;
    private ArrayList<Buff> actionBuffs = new ArrayList<>();
    private ArrayList<Buff> buffs = new ArrayList<>();
    private ArrayList<CellImpactType> impactTypes = new ArrayList<>();
    private CellItemType cellItemType;
    private static boolean aCellIsSelected;

    public static boolean isaCellIsSelected() {
        return aCellIsSelected;
    }

    public static void setaCellIsSelected(boolean aCellIsSelected) {
        Cell.aCellIsSelected = aCellIsSelected;
    }

    public static void handleForce() throws CloneNotSupportedException {
        for(int i=0; i<9; i++){
            for(int j=0; j<5; j++){
                handleEventForce(Map.getForcesView()[i][j],j,i);
            }
        }
    }
    public static void handleCell() throws FileNotFoundException {
        for(int i=0; i<9; i++){
            for(int j=0; j<5; j++){
                handleEventCell(Map.getCellsView()[i][j]);
            }
        }
    }
    public static void handleEventCell(ImageView cell){
        cell.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cell.setEffect(new Glow(0.7));
            }
        });
        cell.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cell.setEffect(null);
            }
        });
    }
    public static void handleCardAfterSelection(int x,int y) throws FileNotFoundException {
        BattleController.move(x,y);
    }
    public static void handleEventForce(ImageView force,int x,int y){
        if(Card.thisCardIsYours(x,y)) {
            force.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    force.setEffect(new Glow(0.7));
                }
            });
            force.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    force.setEffect(null);
                }
            });
            force.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    force.setEffect(new Glow(0.7));
                    try {
                        System.out.println("***********");
                        Cell.setaCellIsSelected(true);
                        handleCardAfterSelection(x,y);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    force.setDisable(true);
                }
            });
        }
    }
    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public CellItemType getCellItemType() {
        return cellItemType;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public void setCellItemType(CellItemType cellItemType) {
        this.cellItemType = cellItemType;
    }

    public static Cell getCellByCoordination(int x, int y){
        return Game.getInstance().getMap().getCells()[x][y];
    }

    public static int returnRandomNumberForCoordinationInThisRange(int i1, int i2) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(i2) + i1;
        return randomInt;
    }





}
