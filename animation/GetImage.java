package animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.collection.Card;
import model.collection.HandleFiles;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetImage {
     ArrayList<String> run = new ArrayList<>();
     String[] runs;

     ArrayList<String> attack = new ArrayList<>();
     String[] attacks;

     ArrayList<String> idle = new ArrayList<>();
     String[] idles;

     ArrayList<String> hit = new ArrayList<>();
     String[] hits;

     ArrayList<String> breathing = new ArrayList<>();
     String[] breathings;

     ArrayList<String> death = new ArrayList<>();
     String[] deaths;

     ArrayList<String> cast = new ArrayList<>();
     String[] casts;

     ArrayList<String> castend = new ArrayList<>();
     String[] castends;

     ArrayList<String> castloop = new ArrayList<>();
     String[] castloops;

     ArrayList<String> caststart = new ArrayList<>();
     String[] caststarts;

    public GetImage(Card card) throws IOException, ParseException {
        JSONObject jsonObject =(JSONObject) readJsonFilesCaseCard(card);
        String frames = jsonObject.get("frames").toString();
        JSONObject metadata =(JSONObject) jsonObject.get("metadata");
        String name = metadata.get("textureFileName").toString();
        String[] parts = frames.split(name.substring(0,name.indexOf(".")) + "_");
        runs = createArray(run,"run",parts);
        attacks = createArray(attack,"attack",parts);
        idles = createArray(idle,"idle",parts);
        hits = createArray(hit,"hit",parts);
        breathings = createArray(breathing,"breathing",parts);
        deaths = createArray(death,"death",parts);
        casts = createArray(cast,"cast",parts);
        castends = createArray(castend,"castend",parts);
        castloops = createArray(castloop,"castloop",parts);
        caststarts = createArray(caststart,"caststart",parts);
    }

    public static String[] createArray(ArrayList<String> str,String type,String[] parts){
        for(int i=1; i<parts.length; i++){
            if(parts[i].contains(type)){
                str.add(parts[i]);
            }
        }
        String[] strs = new String[str.size()];
        for(int i=0; i<str.size(); i++){
            int index = str.get(i).indexOf(".png");
            strs[Integer.parseInt(str.get(i).substring(index-3,index))] = str.get(i);
        }
        return strs;
    }

    public static int getX(String str){
        String getX = str.substring(str.indexOf("\"frame\":\"{{")+11);
        return Integer.parseInt(getX.substring(0,getX.indexOf(",")));
    }

    public static int getY(String str){
        String getY = str.substring(str.indexOf("\"frame\":\"{{")+11);
        return Integer.parseInt(getY.substring(getY.indexOf(",")+1,getY.indexOf("}")));
    }

    public static int getWidth(String str){
        String getWidth = str.substring(str.indexOf("\"frame\":\"{{")+11);
        String coordination = getWidth.substring(getWidth.indexOf("},{") + 3);
        return Integer.parseInt(coordination.substring(0,coordination.indexOf(",")));
    }

    public static int getHeight(String str){
        String getHeight = str.substring(str.indexOf("\"frame\":\"{{")+11);
        String coordination = getHeight.substring(getHeight.indexOf("},{") + 3);
        return Integer.parseInt(coordination.substring(coordination.indexOf(",")+1,coordination.indexOf("}}")));
    }
    public int getIndex(double k,String type) {
        switch (type) {
            case "run":
                return Math.min((int) Math.floor(k * runs.length), runs.length - 1);
            case "attack":
                return Math.min((int) Math.floor(k * attacks.length), attacks.length - 1);
            case "idle":
                return Math.min((int) Math.floor(k * idles.length), idles.length - 1);
            case "hit":
                return Math.min((int) Math.floor(k * hits.length), hits.length - 1);
            case "breathing":
                return Math.min((int) Math.floor(k * breathings.length), breathings.length - 1);
            case "death":
                return Math.min((int) Math.floor(k * deaths.length), deaths.length - 1);
            default:
                return 0;
        }
    }

    public static Object readJsonFilesCaseCard(Card card) throws IOException, ParseException {
        FileReader fileReader = null;
        switch (card.getCardType()){
            case "hero" :
                fileReader = new FileReader(HandleFiles.BEFORE_RELATIVE + "view/Photos/Heros/" + card.getName() + "/" +card.getName() + ".json");
                break;
            case "minion" :
                fileReader = new FileReader(HandleFiles.BEFORE_RELATIVE + "view/Photos/Minions/" + card.getName() + "/" +card.getName() + ".json");
                break;
            case "spell" :
                fileReader = new FileReader(HandleFiles.BEFORE_RELATIVE + "view/Photos/Spells/" + card.getName() + "/" +card.getName() + ".json");
                break;
            case "item" :
                fileReader = new FileReader(HandleFiles.BEFORE_RELATIVE + "view/Photos/Items/" + card.getName() + "/" +card.getName() + ".json");
                break;
        }
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(fileReader);
    }

}
