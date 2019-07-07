package model.CustomCardsEnums;

public enum ItemUserNumber {
    all,
    random;

    public static ItemUserNumber returnEnum(String string){
        switch (string){
            case "all":
                return all;
            case "random":
                return random;
        }

        return null;
    }
}
