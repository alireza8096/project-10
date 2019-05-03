package model;

public class Cell {
    private int coordinateX;
    private int coordinateY;
    private CellType cellSituation;

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

    public CellType getCellSituation() {
        return cellSituation;
    }

    public void setCellSituation(CellType cellSituation) {
        this.cellSituation = cellSituation;
    }

}
