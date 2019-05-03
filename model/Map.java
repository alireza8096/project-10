package model;

import model.collection.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Map {
    private static final String ADDRESS_OF_JSON_FILES = "/Users/shabnamkhodabakhshian/Desktop/project-10-master/src/model/collection/";
    private static Cell[][] cells = new Cell[5][9];

    private ArrayList<Card> minions = new ArrayList<>();
    private ArrayList<Card> heroes = new ArrayList<>();

    static {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell();
            }
        }



    }

    public ArrayList<Card> getMinions() {
        return minions;
    }

    public void setMinions(ArrayList<Card> minions) {
        this.minions = minions;
    }

    public ArrayList<Card> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Card> heroes) {
        this.heroes = heroes;
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


}
