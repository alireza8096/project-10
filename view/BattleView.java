package view;

import controller.BattleController;
import model.Game;
import model.Shop;
import model.collection.Card;
import model.collection.Hero;
import model.collection.Minion;
import model.collection.Spell;

public interface BattleView {
    //not completed
//    public static void showGameInfo(){
//        System.out.println("Game info :");
//        int ownMana = Game.getInstance().getPlayer1().getNumOfMana();
//        int enemyMana = Game.getInstance().getPlayer2().getNumOfMana();
//        System.out.println("Your mana point : " + ownMana);
//        System.out.println("Enemy's mana point : " + enemyMana);
//        switch (Game.getInstance().getGameMode()){
//            case killingHeroOfEnemy:
//                BattleView.showGameForFirstMode();
//                break;
//            case collectingAndKeepingFlags:
//                break;
//            case collectingHalfOfTheFlags:
//                break;
//        }
//    }
    public static void showMyMinions(){
        Hero hero = Game.getInstance().getHeroOfPlayer1();
        System.out.println((300+hero.getId())+" : "+hero.getName()+", health : "+hero.getHealthPoint()+", location : "+"("+hero.getX()+", "+hero.getY()+"), power : "+hero.getAttackPower());
        for (Card card: Game.getInstance().getPlayer1CardsInField() ) {
            if(Minion.thisCardIsMinion(card.getName())){
                Minion minion = (Minion) card;
                System.out.println((300+minion.getId())+" : "+minion.getName()+", health : "+minion.getHealthPoint()+", location : "+"("+minion.getX()+", "+minion.getY()+"), power : "+minion.getAttackPower());
            }
        }
    }
    public static void showOpponentMinions(){
        Hero hero = Game.getInstance().getHeroOfPlayer2();
        System.out.println((300+hero.getId())+" : "+hero.getName()+", health : "+hero.getHealthPoint()+", location : "+"("+hero.getX()+", "+hero.getY()+"), power : "+hero.getAttackPower());
        for (Card card: Game.getInstance().getPlayer2CardsInField() ) {
            if(Minion.thisCardIsMinion(card.getName())){
                Minion minion = (Minion) card;
                System.out.println((300+minion.getId())+" : "+minion.getName()+", health : "+minion.getHealthPoint()+", location : "+"("+minion.getX()+", "+minion.getY()+"), power : "+minion.getAttackPower());
            }
        }
    }
    public static void showCardInfo(int cardId) throws Exception{
        if(Shop.checkValidId(cardId)){
            String name = BattleController.returnNameById(cardId);
            if(BattleController.cardIsInGame(name)){
                showHeroInGame(name);
                showMinionInGame(name);
                showSpellInGame(name);
            }
            else {
                System.out.println("Card with this name is not in game");
            }
        }
        else System.out.println("The ID is not valid");
    }
    public static void showGameForFirstMode(){
        int ownHP = Game.getInstance().getHeroOfPlayer1().getHealthPoint();
        int enemyHP = Game.getInstance().getHeroOfPlayer2().getHealthPoint();
        System.out.println("Your health point : " + ownHP);
        System.out.println("Enemy's health point : " + enemyHP);
    }
    public static void showHeroInGame(String cardName){
        Hero hero;
        if(cardName.equals(Game.getInstance().getHeroOfPlayer1().getName())){
            hero = Game.getInstance().getHeroOfPlayer1();
        }
        else hero = Game.getInstance().getHeroOfPlayer2();
        System.out.println("Hero :");
        System.out.println("Name : " +hero.getName());
        System.out.println("Cost : " +hero.getPrice());
        System.out.println("Desc : "+hero.getSpecialPower());
    }
    public static void showMinionInGame(String cardName) {
        Minion minion = null;
        for (Card card:
                Game.getInstance().getPlayer1CardsInField()) {
            if(card.getName().equals(cardName)){
                minion = (Minion)card;
            }
        }
        for (Card card:
                Game.getInstance().getPlayer2CardsInField()) {
            if(card.getName().equals(cardName)){
                minion = (Minion)card;
            }
        }
        System.out.println("Minion :");
        System.out.println("Name : " + minion.getName());
        System.out.println("HP : " + minion.getHealthPoint() + "    AP : " + minion.getAttackPower() + "    MP : " + minion.getMana());
        System.out.println("Range : " + minion.getAttackRange());
        System.out.println("Combo-ability : ");
        System.out.println("Cost : " + minion.getPrice());
        System.out.println("Desc : " + minion.getSpecialPower());
    }
    public static void showSpellInGame(String cardName){
        Spell spell = null;
        for (Card card:
                Game.getInstance().getPlayer1CardsInField()) {
            if(card.getName().equals(cardName)){
                spell = (Spell) card;
            }
        }
        for (Card card:
                Game.getInstance().getPlayer2CardsInField()) {
            if(card.getName().equals(cardName)){
                spell = (Spell) card;
            }
        }
        System.out.println("Spell: ");
        System.out.println("Name : " + spell.getName());
        System.out.println("MP : " + spell.getMana());
        System.out.println("Cost : " + spell.getPrice());
        System.out.println("Desc : " + spell.getDesc());
    }
}
