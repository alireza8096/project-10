package model.CustomCardsEnums;

public enum ItemUser{
    minionOrHero,
    minion,
    hero;

    public static ItemUser returnEnum(String string){
        switch (string){
            case "minion/hero":
                return minionOrHero;
            case "minion":
                return minion;
            case "hero":
                return hero;
        }
        return null;
    }
}
