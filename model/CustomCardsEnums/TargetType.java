package model.CustomCardsEnums;

public enum TargetType {
    friend,
    enemy,
    friendOrEnemy;

    public static TargetType returnEnum(String string){
        switch (string){
            case "friend":
                return friend;
            case "enemy":
                return enemy;
            case "friend/enemy":
                return friendOrEnemy;
        }
        return null;
    }
}
