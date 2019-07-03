package model.collection;

import jdk.nashorn.api.tree.ForInLoopTree;
import model.AttackType;
import model.Cell;
import model.CellType;
import model.CustomCardsEnums.TargetType;
import model.Game;

public class Target {
    private String target;
    private String numOfTargets;
    private String locationOfTarget;
    private String friendOrEnemy;

    public Target(String target, String numOfTargets, String locationOfTarget, String friendOrEnemy) {
        this.target = target;
        this.numOfTargets = numOfTargets;
        this.locationOfTarget = locationOfTarget;
        this.friendOrEnemy = friendOrEnemy;
    }

    public String getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(String numOfTargets) {
        this.numOfTargets = numOfTargets;
    }

    public String getLocationOfTarget() {
        return locationOfTarget;
    }

    public void setLocationOfTarget(String locationOfTarget) {
        this.locationOfTarget = locationOfTarget;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTargetOfSpell(Spell spell, int x, int  y){
        String target = this.target;

        if (target.equals("cell")){
            setCellTargets(spell, x, y);
        }else{
            setForceTargets(spell, x, y);
        }
    }

    public void setCellTargets(Spell spell, int x, int y){
        //Todo : based on location of targets but it differs in json files
        String locationOfTarget = this.locationOfTarget;

        switch (locationOfTarget){
            case "square2":
                setSquareCellTargetsOfSpell(spell, x, y, 2);
                break;
            case "square3":
                setSquareCellTargetsOfSpell(spell, x, y, 3);
                break;
            case "square1":
                setSquareCellTargetsOfSpell(spell, x, y, 1);
        }
    }

    public void setSquareCellTargetsOfSpell(Spell spell, int x, int y, int squareSize){
        //Todo : check if x and y are given right
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                spell.getCellTargets().add(Game.getInstance().getMap().getCells()[x + i][y + i]);
            }
        }
    }

    public boolean checkIfTargetHasBeenChosenCorrectly(Spell spell, int x, int y){
        //Todo : check if coordination has been given correctly
        //Todo : for the 8round spell
        String target = this.target;
        switch (target){
            case "minion":
                if (Game.getInstance().getMap().getCells()[y][x].minionIsOnCell())
                    return true;
                break;
            case "hero":
                if (Game.getInstance().getMap().getCells()[y][x].heroIsOnCell())
                    return true;
                break;
            case "minion/hero":
                if (Game.getInstance().getMap().getCells()[y][x].minionIsOnCell() ||
                        Game.getInstance().getMap().getCells()[y][x].heroIsOnCell())
                    return true;
                break;
            case "cell":
                return true;
        }
        return false;
    }

    public void setForceTargets(Spell spell, int x, int y){
        String numOfTargets = this.numOfTargets;

        if (numOfTargets.equals("1")){
            setOneForceTarget(spell, x, y);
        }else{
            setAllForceTargets(spell, x, y);
        }

    }

    public void setOneForceTarget(Spell spell, int x, int y){
        spell.getForceTargets().add(((Force)Card.getCardByCoordination(x, y)));
    }

    public void setAllForceTargets(Spell spell, int x, int y){
        String friendOrEnemy = this.friendOrEnemy;
        String locationOfTarget = this.locationOfTarget;

        switch (friendOrEnemy){
            case "friend":
                setAllFriendTargets(spell, x, y, locationOfTarget);
                break;
            case "enemy":
                setAllEnemyTargets(spell, x, y, locationOfTarget);
                break;
            case "friend/enemy":
                setAllFriendAndEnemyTargets(spell, x, y, locationOfTarget);
                break;
        }
    }

    public void setAllFriendAndEnemyTargets(Spell spell, int x, int y, String locationOfTarget){
        setAllFriendTargets(spell, x, y, locationOfTarget);
        setAllEnemyTargets(spell, x, y, locationOfTarget);
    }

    public void setAllFriendTargets(Spell spell, int x, int y, String locationOfTarget){

        switch (locationOfTarget){
            case "null":
                setAllForcesAsTarget(spell, TargetType.friend);
                break;
            case "column":
                setColumnForcesAsTarget(spell, TargetType.friend, x);
                break;
            case "row":
                setRowForcesAsTarget(spell, TargetType.friend, y);
                break;
            case "8round":

                break;
            case "distance2":
                break;
                //Todo : "closest" and "random8" and "8round" and "distance2" test case hasn't been handled
        }
    }

    public void setAllEnemyTargets(Spell spell, int x, int y, String locationOfTarget){
        switch (locationOfTarget){
            case "null":
                setAllForcesAsTarget(spell, TargetType.enemy);
                break;
            case "column":
                setColumnForcesAsTarget(spell, TargetType.enemy, x);
                break;
            case "row":
                setRowForcesAsTarget(spell, TargetType.enemy, y);
                break;
            case "8round":

                break;
            case "distance2":
                break;
            //Todo : "closest" and "random8" and "8round" and "distance2" test case hasn't been handled
        }
    }

    public void setAllEightRoundAsTarget(Spell spell, TargetType targetType, int x, int y){

    }

    public void setRowForcesAsTarget(Spell spell, TargetType targetType, int y){
        if (targetType == TargetType.friend){
            for (int i = 0; i < Game.getInstance().getMap().getCells()[y].length; i++) {
                Cell cell;
                cell = Game.getInstance().getMap().getCells()[i][y];
                if (cell.getCellType() == CellType.selfHero || cell.getCellType() == CellType.selfMinion)
                    spell.getForceTargets().add((Force) Card.getCardByCoordination(i, y));
            }
        }else{
            for (int i = 0; i < Game.getInstance().getMap().getCells()[y].length; i++) {
                Cell cell = Game.getInstance().getMap().getCells()[i][y];
                if (cell.getCellType() == CellType.enemyHero || cell.getCellType() == CellType.enemyMinion)
                    spell.getForceTargets().add((Force) Card.getCardByCoordination(i, y));
            }
        }
    }

    public void setColumnForcesAsTarget(Spell spell, TargetType targetType, int x){
        if (targetType == TargetType.friend) {
            for (int i = 0; i < Game.getInstance().getMap().getCells()[x].length; i++) {
                Cell cell = Game.getInstance().getMap().getCells()[x][i];
                if (cell.getCellType() == CellType.selfHero || cell.getCellType() == CellType.selfMinion)
                    spell.getForceTargets().add((Force) Card.getCardByCoordination(x, i));
            }
        }else {
            for (int i = 0; i < Game.getInstance().getMap().getCells()[x].length; i++) {
                Cell cell = Game.getInstance().getMap().getCells()[x][i];
                if (cell.getCellType() == CellType.enemyHero || cell.getCellType() == CellType.enemyMinion)
                    spell.getForceTargets().add((Force) Card.getCardByCoordination(x, i));
            }
        }
    }

    public void setAllForcesAsTarget(Spell spell, TargetType targetType){
        if (targetType == TargetType.friend){
            for (Minion minion : Game.getInstance().getMap().getFriendMinions()) {
                spell.getForceTargets().add(minion);
            }
            spell.getForceTargets().add(Game.getInstance().getMap().getFriendHero());
        }else {
            for (Minion minion : Game.getInstance().getMap().getEnemyMinions()) {
                spell.getForceTargets().add(minion);
            }
            spell.getForceTargets().add(Game.getInstance().getMap().getEnemyHero());
        }
    }
}
