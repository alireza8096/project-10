package model;

import model.collection.Buff;

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
