package model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.collection.Buff;
import view.MainView;

import java.io.FileInputStream;
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

    public static void createCellsAndView() throws FileNotFoundException {
        for(int i=0; i<9; i++){
            for(int j=0; j<5; j++){
                Map.getCellsView()[i][j]= MainView.getPhotoWithThisPath("/Users/hamilamailee/Documents/project-10/view/Photos/battle/tiles_board.png");
                Map.getCellsView()[i][j].setScaleX(0.55);
                Map.getCellsView()[i][j].setScaleY(0.55);
                Map.getCellsView()[i][j].setX(510 + i*69);
                Map.getCellsView()[i][j].setY(320+j*69);
                handleEventCell(Map.getCellsView()[i][j]);
                AllDatas.currentRoot.getChildren().add(Map.getCellsView()[i][j]);
            }
        }
    }
    public static void handleEventCell(ImageView cell){
        cell.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Image selectedCell = new Image(new FileInputStream("/Users/hamilamailee/Documents/project-10/view/Photos/battle/tiles_board_select.png"));
                    cell.setImage(selectedCell);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        cell.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Image normalCell = new Image(new FileInputStream("/Users/hamilamailee/Documents/project-10/view/Photos/battle/tiles_board.png"));
                    cell.setImage(normalCell);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
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
