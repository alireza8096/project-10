package model.CustomCardsEnums;

public enum BuffNames {
    healthPointWeaknessBuff,
    healthPointBuff,
    attackPowerWeaknessBuff,
    attackPowerBuff,
    holyBuff,
    antiHolyBuff,
    disarm,
    poisonBuff,
    stunBuff,
    fireCellImpact,
    poisonCellImpact,
    addManaBuff;

    public static BuffNames returnEnum(String string){
        switch (string){
            case "healthPointWeaknessBuff":
                return healthPointWeaknessBuff;
            case "healthPointBuff":
                return healthPointBuff;
            case "attackPowerWeaknessBuff":
                return attackPowerWeaknessBuff;
            case "attackPowerBuff":
                return attackPowerBuff;
            case "holyBuff":
                return holyBuff;
            case "antiHolyBuff":
                return antiHolyBuff;
            case "disarm":
                return disarm;
            case "poisonBuff":
                return poisonBuff;
            case "stunBuff":
                return stunBuff;
            case "fireCellImpact":
                return fireCellImpact;
            case "poisonCellImpact":
                return poisonCellImpact;
            case "addManaBuff":
                return addManaBuff;
        }
        return null;
    }
}
