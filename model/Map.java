package model;

import model.collection.Card;
import model.collection.HandleFiles;
import model.collection.Spell;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Map {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/hamilamailee/Documents/Duelyst Project/model/collection/";
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

    public static boolean checkIfMinionInsertCardInThisCoordination(int x, int y){
        if (thisCellIsEmpty(x, y)){
            if ((x - 1) >= 0 && !thisCellIsEmpty(x - 1, y))
                return true;
            else if ((x + 1) <= 4 && !thisCellIsEmpty(x + 1, y))
                return true;
            else if ((y - 1) >= 0 && !thisCellIsEmpty(x, y - 1))
                return true;
            else if ((y + 1) <= 8 && !thisCellIsEmpty(x, y + 1))
                return true;
            return false;
        }else
            return false;
    }

    public static boolean thisCellIsEmpty(int x, int y){
        Cell cell = Cell.getCellByCoordination(x, y);
        CellType cellType = cell.getCellSituation();
        if (cellType != CellType.selfHero && cellType != CellType.enemyHero &&
                cellType != CellType.selfMinion && cellType != CellType.enemyMinion ){
            return true;
        }else
            return false;
    }

//    ˚˚
}
