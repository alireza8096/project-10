package model;

public class Cell {
    private int coordinateX;
    private int coordinateY;
    private CellType cellSituation;
    private CellImpactType cellImpactType;

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

    public CellType getCellSituation() {
        return cellSituation;
    }

    public void setCellSituation(CellType cellSituation) {
        this.cellSituation = cellSituation;
    }

    public static Cell getCellByCoordination(int x, int y){
        return Map.getCells()[x][y];
    }



}
