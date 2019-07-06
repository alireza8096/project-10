package model.CustomCardsEnums;

public enum ActionType {
    addBuff,
    addAction,
    dispel,
    kill;

    public static ActionType returnEnum(String string){
        switch (string){
            case "addBuff":
                return addBuff;
            case "addAction":
                return addAction;
            case "dispel":
                return dispel;
            case "kill":
                return kill;
        }
        return null;
    }
}
