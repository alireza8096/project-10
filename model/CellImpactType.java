package model;

import model.collection.*;

public enum CellImpactType {
    empty,
    poison,
    fire,
    holy;

    public static void applyFireImpactOnCard(Card card, Buff buff) {
        if (card.getCardType().equals("minion")) {
            int currentHP = ((Minion) card).getHealthPoint();
//            ((Minion) card).setHealthPoint(currentHP - buff.getForHowManyTurns());
        } else if (card.getCardType().equals("hero")) {
            int currentHP = ((Hero) card).getHealthPoint();
//            ((Hero) card).setHealthPoint(currentHP - buff.getForHowManyTurns());
        }
    }
    public static void applyFireImpactOnForce(Force force, Buff buff){

    }

    public static void applyHolyImpactOnForce(Force force){

    }

    public static void applyPoisonImpactOnForce(Force force, Buff buff){

    }

//    public static void applyPoisonImpactOnCard(Card card, Buff buff){
//        if (card.getCardType().equals("minion")){
////            ((Minion) card).getMinionNegativeBuffs().add(buff);
//        }else if (card.getCardType().equals("hero")){
//            ((Hero) card).getNegativeBuffs().add(buff);
//        }
//    }
//    public static void applyFireImpactOnCard(Card card, Buff buff){
//        if (card.getCardType().equals("minion")){
//            int currentHP = ((Minion) card).getHealthPoint();
//            ((Minion) card).setHealthPoint(currentHP - buff.getForHowManyTurns());
//        }else if (card.getCardType().equals("hero")){
//            int currentHP = ((Hero) card).getHealthPoint();
//            ((Hero) card).setHealthPoint(currentHP - buff.getForHowManyTurns());
//        }
//    }
//
//    public static void applyHolyImpactOnCard(Card card){
//        if (card.getCardType().equals("minion")){
//            ((Minion) card).setHasHolyBuff(true);
//        }else if (card.getCardType().equals("hero")){
//            ((Hero) card).setHasHolyBuff(true);
//        }
//    }
//
//    public static void applyPoisonImpactOnCard(Card card, Buff buff){
//        if (card.getCardType().equals("minion")){
//            ((Minion) card).getMinionNegativeBuffs().add(buff);
//        }else if (card.getCardType().equals("hero")){
//            ((Hero) card).getNegativeBuffs().add(buff);
//        }
//    }
    public static void applyPoisonImpactOnCard(Card card, Buff buff){
        if (card.getCardType().equals("minion")){
//            ((Minion) card).getMinionNegativeBuffs().add(buff);
        }else if (card.getCardType().equals("hero")){
            ((Hero) card).getNegativeBuffs().add(buff);
        }
    }
}
