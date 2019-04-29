package controller;

import com.sun.tools.javac.Main;
import org.json.simple.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CollectionController {
    public static void writeHeroCard(String filename, int id, String name, int price, int healthPoint, int attackPower, String attackType, int attackRange, int mana, int coolDown) throws Exception {
        JSONObject hero = new JSONObject();
        hero.put("id", id);
        hero.put("name", name);
        hero.put("price", price);
        hero.put("healthPoint", healthPoint);
        hero.put("attackPower", attackPower);
        hero.put("attackType", attackType);
        hero.put("attackRange", attackRange);
        hero.put("mana", mana);
        hero.put("coolDown", coolDown);
        Files.write(Paths.get(filename), hero.toJSONString().getBytes());
    }


    public static void writeItem(String filename, int id, String name, String itemType, int price) throws Exception {
        JSONObject item = new JSONObject();
        item.put("id", id);
        item.put("name", name);
        item.put("itemType", itemType);
        item.put("price", price);
        Files.write(Paths.get(filename), item.toJSONString().getBytes());
    }

    public static void writeMinionCard(String filename, int id, String name, int price, int mana, int healthPoint, int attackPower, String attackType, int attackRange, String activationTime) throws Exception {
        JSONObject minion = new JSONObject();
        minion.put("id", id);
        minion.put("name", name);
        minion.put("price", price);
        minion.put("mana", mana);
        minion.put("healthPoint", healthPoint);
        minion.put("attackPower", attackPower);
        minion.put("attackType", attackType);
        minion.put("attackRange", attackRange);
        minion.put("activationTime", activationTime);
        Files.write(Paths.get(filename), minion.toJSONString().getBytes());
    }

    public static void writeSpellCard(String filename, int id, String name, int price, int mana) throws Exception {
        JSONObject spell = new JSONObject();
        spell.put("id", id);
        spell.put("name", name);
        spell.put("price", price);
        spell.put("mana", mana);
        Files.write(Paths.get(filename), spell.toJSONString().getBytes());
    }
}
