package view;

import model.Game;

public interface BattleView {
    public static void showGameForFirstMode(){
        int ownHP = Game.getInstance().getHeroOfPlayer1().getHealthPoint();
        int enemyHP = Game.getInstance().getHeroOfPlayer2().getHealthPoint();
        System.out.println("Your health point : " + ownHP);
        System.out.println("Enemy's health point : " + enemyHP);
    }
    public static void showGameForSecondMode(){

    }
    public static void showGameForThirdMode(){

    }
}
