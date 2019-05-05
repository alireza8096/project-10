package model;

import model.collection.*;
import java.math.*;
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

    public static boolean checkIfMinionCardCanBeInsertedInThisCoordination(int x, int y){
        if (thisCellIsEmpty(x, y)){
            if ((x - 1) >= 0 && !thisCellIsEmpty(x - 1, y))
                return true;
            else if ((x + 1) <= 4 && !thisCellIsEmpty(x + 1, y))
                return true;
            else if ((y - 1) >= 0 && !thisCellIsEmpty(x, y - 1))
                return true;
            else return (y + 1) <= 8 && !thisCellIsEmpty(x, y + 1);
        }else
            return false;
    }

    public static boolean cardCanBeMovedToThisCell(Card card,int x,int y){
        if(distance(card.getX(),card.getY(),x,y)>2) {
            return false;
        }
        if(!thisCellIsEmpty(x,y)) {
            return false;
        }
        if(enemyInWay(card.getX(),card.getY(),x,y)){
            return false;
        }
        return true;
    }
    public static int distance(int x1,int y1,int x2,int y2){
        return Math.abs(x1-x2)+Math.abs(y1-y2);
    }
    public static boolean thisCellIsEmpty(int x, int y){
        Cell cell = Cell.getCellByCoordination(x, y);
        if(cell.getCellType() == CellType.empty)
            return true;
        return false;
    }
    public static boolean thisCellIsEnemy(int x,int y) {
        Cell cell = Cell.getCellByCoordination(x, y);
        if (cell.getCellType() == CellType.enemyMinion || cell.getCellType() == CellType.enemyHero)
            return true;
        return false;
    }
    public static boolean enemyInWay(int x1,int y1,int x2,int y2){
        if(distance(x1,y1,x2,y2) == 1)
            return true;
        else{
            if(x1 == x2 || y1==y2){
                if(thisCellIsEnemy((x1+x2)/2,(y1+y2)/2)){
                    return false;
                }
                return true;
            }
            else{
                if(checkAllWaysToReach(x1,y1,x2,y2)){
                    return true;
                }
                return false;
            }
        }
    }
    public static boolean checkAllWaysToReach(int x1,int y1,int x2,int y2){
        if(x1 > x2 && y1 > y2){
            if(!thisCellIsEnemy(x1-1,y1) || !thisCellIsEnemy(x1,y1-1))
                return true;
            return false;
        }
        else if(x1 < x2 && y1>y2){
            if(!thisCellIsEnemy(x1,y1-1) || !thisCellIsEnemy(x1+1,y1))
                return true;
            return false;
        }
        else if(x1 > x2 && y1 < y2){
            if(!thisCellIsEnemy(x1-1,y1) || !thisCellIsEnemy(x1,y1+1))
                return true;
            return false;
        }
        else{
            if(!thisCellIsEnemy(x1,y1+1) || !thisCellIsEnemy(x1+1,y1))
                return true;
            return false;
        }
    }

    public static boolean thisCellsAreAdjusting(int x1, int y1, int x2, int y2){
        if ( ((x1 == x2 - 1) && (y1 == y2 - 1)) ||
                ((x1 == x2) && (y1 == y2 - 1)) ||
                ((x1 == x2 + 1) && (y1 == y2 - 1)) ||
                ((x1 == x2 - 1) && (y1 == y2)) ||
                ((x1 == x2 + 1) && (y1 == y2)) ||
                ((x1 == x2 - 1) && (y1 == y2 + 1)) ||
                ((x1 == x2) && (y1 == y2 + 1)) ||
                ((x1 == x2 + 1) && (y1 == y2 + 1))){
            return true;
        }
        return false;
    }
}
