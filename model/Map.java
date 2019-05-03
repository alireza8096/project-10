package model;

import static model.CellType.*;

public class Map {
    private static Cell[][] cells = new Cell[5][9];

    static {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public static Cell[][] getCells() {
        return cells;
    }

    public static void setCells(Cell[][] cells) {
        Map.cells = cells;
    }

    public static boolean checkIfMinionCanBeInseretdInThisCoordination(int x, int y){
        if (thisCoordinationIsEmpty(x, y)){
            if ((x - 1) >= 0 && !thisCoordinationIsEmpty(x - 1, y)){
                return true;
            }else if ((x + 1) <= 4 && !thisCoordinationIsEmpty(x + 1, y)){
                return true;
            }else if ((y - 1) >= 0 && !thisCoordinationIsEmpty(x, y - 1)){
                return true;
            }else if ((y + 1) <= 4 && !thisCoordinationIsEmpty(x, y + 1)){
                return true;
            }
            return false;
        }else
            return false;
    }

    public static boolean thisCoordinationIsEmpty(int x, int y){
        Cell cell = Cell.getCellByCoordination(x, y);
        CellType cellType = cell.getCellSituation();
        if (cellType != selfHero && cellType != selfMinion && cellType != enemyHero && cellType != enemyMinion){
            return true;
        }else{
            return false;
        }
    }


}
