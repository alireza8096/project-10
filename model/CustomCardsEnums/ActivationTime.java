package model.CustomCardsEnums;

public enum ActivationTime {
    onSpawn,
    passive,
    onDeath,
    onAttack,
    onDefend,
    combo;

    public static ActivationTime returnEnum(String string){
        switch (string){
            case "onSpawn":
                return onSpawn;
            case "passive":
                return passive;
            case "onDeath":
                return onDeath;
            case "onAttack":
                return onAttack;
            case "onDefend":
                return onDefend;
            case "combo":
                return combo;
        }
        return null;
    }
}
