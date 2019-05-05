package model;

import model.collection.Buff;
import model.collection.Card;
import model.collection.Hero;
import model.collection.Minion;

public enum CellImpactType {
    empty,
    poison,
    fire,
    holy;

    public static void applyFireImpactOnCard(Card card){
        if (card.getCardType().equals("minion")){
            int currentHP = ((Minion) card).getHealthPoint();
            ((Minion) card).setHealthPoint(currentHP - 2);
        }else if (card.getCardType().equals("hero")){
            int currentHP = ((Hero) card).getHealthPoint();
            ((Hero) card).setHealthPoint(currentHP - 2);
        }
    }

    public static void applyHolyImpactOnCard(Card card){
        if (card.getCardType().equals("minion")){
            ((Minion) card).setHasHolyBuff(true);
        }else if (card.getCardType().equals("hero")){
            ((Hero) card).setHolyBuffIsActive(true);
        }
    }

    public static void applyPoisonImpactOnCard(Card card){
        Buff buff = new Buff(2, 1, "poisonBuff", "negative");
        if (card.getCardType().equals("minion")){
            ((Minion) card).getMinionNegativeBuffs().add(buff);
        }else if (card.getCardType().equals("hero")){
            ((Hero) card).getNegativeBuffs().add(buff);
        }
    }
}
