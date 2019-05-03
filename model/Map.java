package model;

public class Map {
    private static Cell[][] cells = new Cell[5][9];

    public static Cell[][] getCells() {
        return cells;
    }

    public static void setCells(Cell[][] cells) {
        Map.cells = cells;
    }
}
