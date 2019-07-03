package model.CustomCardsEnums;

public enum LocationOfTargets {
    row,
    eightRound,
    random,
    distanceTwo,
    closest;

    public static LocationOfTargets returnEnum(String string){
        switch (string){
            case "row":
                return row;
            case "8round":
                return eightRound;
            case "distance2":
                return distanceTwo;
            case "closest":
                return closest;
        }
        return null;
    }
}
