package model.CustomCardsEnums;

public enum NumOfTargets {
    one,
    all,
    squareTwo,
    squareThree,
    oneCell;

    public static NumOfTargets returnEnum(String string){
        switch (string){
            case "1":
                return one;
            case "all":
                return all;
            case "square2":
                return squareTwo;
            case "square3":
                return squareThree;
            case "square1":
                return oneCell;
        }
        return null;
    }
}
