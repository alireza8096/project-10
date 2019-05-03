import model.Deck;
import model.Hand;
import model.Player;
import model.collection.Account;
import model.collection.Buff;
import model.Game;
import model.collection.HandleFiles;
import model.collection.Hero;
import model.collection.Minion;
import view.GameView;

import java.util.Scanner;

import static model.collection.HandleFiles.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
//        HandleFiles.createStringOfHeroes();
//        HandleFiles.createStringOfItems();
//        HandleFiles.createStringOfMinions();
//        HandleFiles.createStringOfSpells();
//        HandleFiles.createStringOfPlayers();
//
//        Buff buff = new Buff();
//        buff.setForHowManyTurns(2);
//        buff.setHowMuchImpact(4);
//        buff.setName("attackPowerWeaknessBuff");
//        buff.setType("negative");
//
//        Buff buff1 = new Buff();
//        buff1.setForHowManyTurns(1);
//        buff1.setHowMuchImpact(3);
//        buff1.setName("poisonBuff");
//        buff1.setType("negative");
//
//        Buff buff2 = new Buff();
//        buff2.setForHowManyTurns(3);
//        buff2.setHowMuchImpact(2);
//        buff2.setName("holyBuff");
//        buff2.setType("positive");
//
//        Hero hero = new Hero();
//        hero.getNegativeBuffs().add(buff);
//        hero.getNegativeBuffs().add(buff1);
//        hero.getPositiveBuffs().add(buff2);
//        hero.setHealthPoint(20);
//        hero.setAttackPower(30);
//
//        showInfo(hero);
//        hero.applyBuffsOnHero();
//        showInfo(hero);
//        hero.applyBuffsOnHero();
//        showInfo(hero);
//        hero.applyBuffsOnHero();
//        showInfo(hero);
//        hero.applyBuffsOnHero();
//        showInfo(hero);





    }

//    public static void showInfo(Hero hero){
//        System.out.println("attack power : " + hero.getAttackPower());
//        System.out.println("health point : " + hero.getHealthPoint());
//        System.out.println("holy buff is used : " + hero.isHolyBuffIsActive());
//        for (Buff buff : hero.getPositiveBuffs())
//            System.out.println("+ name : " + buff.getName());
//        for (Buff buff : hero.getNegativeBuffs())
//            System.out.println("- name : " + buff.getName());
//        System.out.println("***");
//    }
}
