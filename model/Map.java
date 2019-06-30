package model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.collection.*;
import view.MainView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

public class Map {
    private Cell[][] cells = new Cell[5][9];
    private static ImageView[][] cellsView = new ImageView[9][5];
    private static ImageView[][] forcesView = new ImageView[9][5];

    private static StackPane[][] forcesStack = new StackPane[9][5];

    private ArrayList<Minion> enemyMinions = new ArrayList<>();
    private ArrayList<Minion> friendMinions = new ArrayList<>();
    private Hero enemyHero;
    private Hero friendHero;


    {
        for(int i=0; i<9; i++){
            for(int j=0; j<5; j++){
                try {
                    cellsView[i][j]= MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/tiles_board.png");
                    cellsView[i][j].setScaleX(0.55);
                    cellsView[i][j].setScaleY(0.55);
                    cellsView[i][j].setX(510 + i*69);
                    cellsView[i][j].setY(320+j*69);
                    AllDatas.currentRoot.getChildren().add(cellsView[i][j]);

                    forcesStack[i][j] = new StackPane();
                    forcesStack[i][j].setPrefWidth(cellsView[i][j].getFitWidth());
                    forcesStack[i][j].setPrefHeight(cellsView[i][j].getFitHeight());
                    forcesStack[i][j].setLayoutX(cellsView[i][j].getX());
                    forcesStack[i][j].setLayoutY(cellsView[i][j].getY());
                    AllDatas.currentRoot.getChildren().add(forcesStack[i][j]);

                    forcesView[i][j] = new ImageView();
                    forcesView[i][j].setX(cellsView[i][j].getX());
                    forcesView[i][j].setY(cellsView[i][j].getY());
                    AllDatas.currentRoot.getChildren().add(forcesView[i][j]);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static StackPane[][] getForcesStack() {
        return forcesStack;
    }

    public static void setForcesStack(StackPane[][] forcesStack) {
        Map.forcesStack = forcesStack;
    }

    public static ImageView[][] getForcesView() {
        return forcesView;
    }

    public static ImageView[][] getCellsView() {
        return cellsView;
    }

    public void setForcesView(ImageView view,int i,int j) {
        forcesView[i][j] = view;
    }

    public static void show() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(Game.getInstance().getMap().cells[i][j].getCellType() + " ");
            }
            System.out.println();
        }
        Hero hero1 = Game.getInstance().getMap().friendHero;
        Hero hero2 = Game.getInstance().getMap().enemyHero;
        System.out.println("player1 Mana : " + Game.getInstance().getPlayer1().getNumOfMana());
        System.out.println("player2 Mana : " + Game.getInstance().getPlayer2().getNumOfMana());
        System.out.println(hero1.getName() + " " + hero1.getX() + " " + hero1.getY() + "/ Hp : " + hero1.getHealthPoint() + " AP : " + hero1.getAttackPower() +" id : " + hero1.getId());
        System.out.println(hero2.getName() + " " + hero2.getX() + " " + hero2.getY() + "/ Hp : " + hero2.getHealthPoint() + " AP : " + hero2.getAttackPower() + " id : " + hero2.getId());

        for (Force force : Game.getInstance().getMap().getFriendMinions()) {
            System.out.println(force.getName() + " " + force.getX() + " " + force.getY() + "/ HP : " + force.getHealthPoint() + " AP : " + force.getAttackPower() + " id  : " + force.getId());
        }

        for (Force force : Game.getInstance().getMap().getEnemyMinions()) {
            System.out.println(force.getName() + " " + force.getX() + " " + force.getY() + "/ Hp : " + force.getHealthPoint() + " AP : " + force.getAttackPower() + " id : " + force.getId());
        }
    }

    public Map() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell();
                cells[i][j].setCellType(CellType.empty);
            }
        }
    }


    public static void setFlagForGame() {
        int x = (int) Math.random() % 5;
        int y = (int) Math.random() % 9;
        Cell.getCellByCoordination(x, y).setCellItemType(CellItemType.flag);
    }

    public static void setMultiFlag() {
        for (int i = 0; i < 5; i++) {
            int x = (int) Math.random() % 5;
            int y = (int) Math.random() % 9;
            Cell.getCellByCoordination(x, y).setCellItemType(CellItemType.collectibleItem);
        }
    }

    public ArrayList<Minion> getEnemyMinions() {
        return enemyMinions;
    }

    public void setEnemyMinions(ArrayList<Minion> enemyMinions) {
        this.enemyMinions = enemyMinions;
    }

    public ArrayList<Minion> getFriendMinions() {
        return friendMinions;
    }

    public void setFriendMinions(ArrayList<Minion> friendMinions) {
        this.friendMinions = friendMinions;
    }

    public Hero getEnemyHero() {
        return enemyHero;
    }

    public void setEnemyHero(Hero enemyHero) {
        this.enemyHero = enemyHero;
    }

    public Hero getFriendHero() {
        return friendHero;
    }

    public void setFriendHero(Hero friendHero) {
        this.friendHero = friendHero;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public static boolean cellIsEnemy(int x, int y) {
        if (Game.getInstance().getMap().getCells()[x][y].getCellType() == CellType.enemyHero) {
            return true;
        } else if (Game.getInstance().getMap().getCells()[x][y].getCellType() == CellType.enemyMinion) {
            return true;
        }
        return false;
    }

    public static boolean cellIsFriend(int x, int y) {
        if (Game.getInstance().getMap().getCells()[x][y].getCellType() == CellType.selfHero) {
            return true;
        } else if (Game.getInstance().getMap().getCells()[x][y].getCellType() == CellType.selfMinion) {
            return true;
        }
        return false;
    }


    public static boolean checkIfMinionCardCanBeInsertedInThisCoordinationAI(int x, int y) {
        if (thisCellIsEmpty(x, y)) {
            if ((x - 1) >= 0 && cellIsEnemy(x - 1, y))
                return true;
            else if ((x + 1) <= 4 && cellIsEnemy(x + 1, y))
                return true;
            else if ((y - 1) >= 0 && cellIsEnemy(x, y - 1))
                return true;
            else return (y + 1) <= 8 && cellIsEnemy(x, y + 1);
        }
        return false;
    }

    public static boolean checkIfMinionCardCanBeInsertedInThisCoordination(int x, int y) {
        if (thisCellIsEmpty(x, y)) {
            if ((x - 1) >= 0 && cellIsFriend(x - 1, y))
                return true;
            else if ((x + 1) <= 4 && cellIsFriend(x + 1, y))
                return true;
            else if ((y - 1) >= 0 && cellIsFriend(x, y - 1))
                return true;
            else return (y + 1) <= 8 && cellIsFriend(x, y + 1);
        }
        return false;
    }

    public static boolean cardCanBeMovedToThisCell(Card card, int x, int y) {
        if(card!=null) {
            if (!card.isHasMovedInThisTurn()) {
                if (distance(card.getX(), card.getY(), x, y) > 2) {
                    System.out.println("1");
                    return false;
                }
                if (!thisCellIsEmpty(x, y)) {
                    System.out.println("2");
                    return false;
                }
                if (enemyInWay(card.getX(), card.getY(), x, y)) {
                    System.out.println("3");
                    return false;
                }
                System.out.println("4");
                return true;
            }
            return false;
        }
        return false;
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static boolean thisCellIsEmpty(int x, int y) {
        Cell cell = Cell.getCellByCoordination(x, y);
        if (cell.getCellType() == CellType.empty)
            return true;
        return false;
    }

    public static boolean enemyInWay(int x1, int y1, int x2, int y2) {
        if (distance(x1, y1, x2, y2) == 1)
            return false;
        else {
            if (x1 == x2 || y1 == y2) {
                if (enemyIsAvailableBetweenThis2Cells(x1, y1, x2, y2)) {
                    return true;
                }
                return false;
            } else {
                if (checkAllWaysToReach(x1, y1, x2, y2)) {
                    return false;
                }
                return true;
            }
        }
    }

    public static boolean enemyIsAvailableBetweenThis2Cells(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            int maxY = Math.max(y1, y2);
            int minY = Math.min(y1, y2);
            for (int i = minY; i <= maxY; i++) {
                if (cellIsEnemy(x1, i)) {
                    return true;
                }
            }
        } else if (y1 == y2) {
            int maxX = Math.max(x1, x2);
            int minX = Math.max(x2, x2);
            for (int i = minX; i <= maxX; i++) {
                if (cellIsEnemy(i, y1))
                    return true;
            }
        }
        return false;
    }

    public static boolean checkAllWaysToReach(int x1, int y1, int x2, int y2) {
        if (x1 > x2 && y1 > y2) {
            if (!cellIsEnemy(x1 - 1, y1) || !cellIsEnemy(x1, y1 - 1))
                return true;
            return false;
        } else if (x1 < x2 && y1 > y2) {
            if (!cellIsEnemy(x1, y1 - 1) || !cellIsEnemy(x1 + 1, y1))
                return true;
            return false;
        } else if (x1 > x2 && y1 < y2) {
            if (!cellIsEnemy(x1 - 1, y1) || !cellIsEnemy(x1, y1 + 1))
                return true;
            return false;
        } else {
            if (!cellIsEnemy(x1, y1 + 1) || !cellIsEnemy(x1 + 1, y1))
                return true;
            return false;
        }
    }

    public static boolean thisCellsAreAdjusting(int x1, int y1, int x2, int y2) {
        if (((x1 == x2 - 1) && (y1 == y2 - 1)) ||
                ((x1 == x2) && (y1 == y2 - 1)) ||
                ((x1 == x2 + 1) && (y1 == y2 - 1)) ||
                ((x1 == x2 - 1) && (y1 == y2)) ||
                ((x1 == x2 + 1) && (y1 == y2)) ||
                ((x1 == x2 - 1) && (y1 == y2 + 1)) ||
                ((x1 == x2) && (y1 == y2 + 1)) ||
                ((x1 == x2 + 1) && (y1 == y2 + 1))) {
            return true;
        }
        return false;
    }

    public void changeCellTypesWhenTurnChanges() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.getCells()[i][j].getCellType().equals(CellType.selfHero)) {
                    this.getCells()[i][j].setCellType(CellType.enemyHero);
                } else if (this.getCells()[i][j].getCellType().equals(CellType.selfMinion)) {
                    this.getCells()[i][j].setCellType(CellType.enemyMinion);
                } else if (this.getCells()[i][j].getCellType().equals(CellType.enemyHero)) {
                    this.getCells()[i][j].setCellType(CellType.selfHero);
                } else if (this.getCells()[i][j].getCellType().equals(CellType.enemyMinion)) {
                    this.getCells()[i][j].setCellType(CellType.selfMinion);
                }
            }
        }
    }
}
