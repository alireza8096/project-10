package model;

public class Cell {
    private int coordinateX;
    private int coordinateY;
    private CellType cellType;
    private CellImpactType cellImpactType;
    private CellItemType cellItemType;

    public CellImpactType getCellImpactType() {
        return cellImpactType;
    }

    public void setCellImpactType(CellImpactType cellImpactType) {
        this.cellImpactType = cellImpactType;
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
        return Map.getCells()[x][y];
    }



}
