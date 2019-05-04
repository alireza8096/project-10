package model;

import java.util.ArrayList;

public class Cell {
    private int coordinateX;
    private int coordinateY;
    private ArrayList<CellType> cellTypes = new ArrayList<>();

    public ArrayList<CellType> getCellTypes() {
        return cellTypes;
    }

    public void setCellTypes(ArrayList<CellType> cellTypes) {
        this.cellTypes = cellTypes;
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

    public static Cell getCellByCoordination(int x, int y){
        return Map.getCells()[x][y];
    }





}
