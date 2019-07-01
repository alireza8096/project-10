package model.CustomCardsEnums;

public enum Targets {
    minion,
    hero,
    minionOrHero,
    cell;

    public static Targets returnEnum(String string){
        switch (string){
            case "minion":
                return minion;
            case "hero":
                return hero;
            case "minion/hero":
                return minionOrHero;
            case "cell":
                return cell;
        }
        return null;
    }


}
