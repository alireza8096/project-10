package model.collection;

import org.json.simple.JSONObject;

public class Buff {
    private int forHowManyTurns;
    private String name;
    private String type;
    private int howMuchImpact;
    private boolean isUsed;

    public Buff(int howMuchImpact, int forHowManyTurns, String name, String type){
        this.howMuchImpact = howMuchImpact;
        this.forHowManyTurns = forHowManyTurns;
        this.name = name;
        this.type = type;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getForHowManyTurns() {
        return forHowManyTurns;
    }

    public void setForHowManyTurns(int forHowManyTurns) {
        this.forHowManyTurns = forHowManyTurns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHowMuchImpact() {
        return howMuchImpact;
    }

    public void setHowMuchImpact(int howMuchImpact) {
        this.howMuchImpact = howMuchImpact;
    }

}
