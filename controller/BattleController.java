package controller;

import model.Game;
import model.GameMode;
import view.BattleView;

public interface BattleController {
    public static void showGameInfo(){
        System.out.println("Game info :");
        int ownMana = Game.getInstance().getPlayer1().getNumOfMana();
        int enemyMana = Game.getInstance().getPlayer2().getNumOfMana();
        System.out.println("Your mana point : " + ownMana);
        System.out.println("Enemy's mana point : " + enemyMana);
        switch (Game.getInstance().getGameMode()){
            case killingHeroOfEnemy:
                BattleView.showGameForFirstMode();
                break;
            case collectingAndKeepingFlags:
                break;
            case collectingHalfOfTheFlags:
            break;
        }
    }

}
