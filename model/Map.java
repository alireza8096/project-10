package model;

import animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.collection.*;
import view.MainView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Map {
    public static Random random = new Random();
    private Cell[][] cells = new Cell[5][9];
    private static ImageView[][] cellsView = new ImageView[9][5];
    private static ImageView[][] forcesView = new ImageView[9][5];
    private static ImageView[][] itemsView = new ImageView[9][5];
    private static StackPane[][] forcesStack = new StackPane[9][5];
    private static SpriteAnimation[][] animations = new SpriteAnimation[9][5];
    private static ArrayList<Animation> spriteAnimations = new ArrayList<>();

    private ArrayList<Minion> enemyMinions = new ArrayList<>();
    private ArrayList<Minion> friendMinions = new ArrayList<>();
    private Hero enemyHero;
    private Hero friendHero;

    public static ArrayList<Animation> getSpriteAnimations() {
        return spriteAnimations;
    }

    public static ImageView[][] getItemsView() {
        return itemsView;
    }

    public static void setItemsView(ImageView[][] itemsView) {
        Map.itemsView = itemsView;
    }

    public static void insertFlagInThisCell(int x, int y) throws FileNotFoundException {
        Game.getInstance().getMap().getCells()[x][y].setCellItemType(CellItemType.flag);
        itemsView[y][x].setImage(new Image(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Photos/battle/temp_flag.png")));

    }

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

                    Bounds boundsInScene = cellsView[i][j].localToScene(cellsView[i][j].getBoundsInLocal());

                    cellsView[i][j].setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {

                        }
                    });

                    itemsView[i][j] = new ImageView();
                    itemsView[i][j].setScaleX(0.45);
                    itemsView[i][j].setScaleY(0.45);
                    itemsView[i][j].setX(cellsView[i][j].getX() - 50);
                    itemsView[i][j].setY(cellsView[i][j].getY() - 50);
                    AllDatas.currentRoot.getChildren().add(itemsView[i][j]);

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

    public static void setAnimations(int x,int y,SpriteAnimation animation) {
        Map.getAnimations()[x][y] = animation;
    }

    public static SpriteAnimation[][] getAnimations() {
        return animations;
    }

    public void placeFlagsOnMap(){
        for (int i = 0; i < Game.getInstance().getNumOfFlagsInGame(); i++) {
            placeOneFlagOnMap();
        }
    }

    public boolean coordinationOfFlagIsRightAtFirst(int x, int y){
        if (cells[x][y].getCellItemType() != CellItemType.flag) {
            if (x == 2 && y == 0) {
                return false;
            } else if (x == 2 && y == 8) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public void placeOneFlagOnMap() {
        int flagX = random.nextInt(5);
        int flagY = random.nextInt(9);
        while (!coordinationOfFlagIsRightAtFirst(flagX, flagY)){
            flagX = random.nextInt(5);
            flagY = random.nextInt(9);
        }
        try {
            itemsView[flagY][flagX].setImage(new Image(new FileInputStream(
                    HandleFiles.BEFORE_RELATIVE + "view/Photos/shop/price_icon.png")));
            itemsView[flagY][flagX].setFitWidth(cellsView[flagY][flagX].getFitWidth() - 100);
            itemsView[flagY][flagX].setFitHeight(cellsView[flagY][flagX].getFitHeight() - 100);
            cells[flagX][flagY].setCellItemType(CellItemType.flag);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
}
