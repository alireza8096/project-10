package animation;

import animation.GetImage;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Game;
import model.collection.Card;

import java.util.ArrayList;

public class SpriteAnimation extends Transition {
    private transient final ImageView imageView;
    private String type;
    private int lastIndex;
    private transient Card card;

    public SpriteAnimation(ImageView imageView){
        this.imageView = imageView;
    }
    public SpriteAnimation(
            Card card,
            String type,
            ImageView imageView,
            Duration duration) {
        this.type = type;
        this.card = card;
        this.imageView = imageView;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    public void setSpriteAnimation(
            Card card,
            String type,
            Duration duration) {
        this.type = type;
        this.card = card;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double k) {
        final int index = card.getGetImage().getIndex(k,type);

        if (index != lastIndex) {
            int x = 0;
            int y = 0;
            int width = 0;
            int height = 0;
            switch (type) {
                case "run":
                    x = GetImage.getX(card.getGetImage().runs[index]);
                    y = GetImage.getY(card.getGetImage().runs[index]);
                    width = GetImage.getWidth(card.getGetImage().runs[index]);
                    height = GetImage.getHeight(card.getGetImage().runs[index]);
                    break;
                case "attack":
                    x = GetImage.getX(card.getGetImage().attacks[index]);
                    y = GetImage.getY(card.getGetImage().attacks[index]);
                    width = GetImage.getWidth(card.getGetImage().attacks[index]);
                    height = GetImage.getHeight(card.getGetImage().attacks[index]);
                    break;
                case "idle":
                    x = GetImage.getX(card.getGetImage().idles[index]);
                    y = GetImage.getY(card.getGetImage().idles[index]);
                    width = GetImage.getWidth(card.getGetImage().idles[index]);
                    height = GetImage.getHeight(card.getGetImage().idles[index]);
                    break;
                case "hit":
                    x = GetImage.getX(card.getGetImage().hits[index]);
                    y = GetImage.getY(card.getGetImage().hits[index]);
                    width = GetImage.getWidth(card.getGetImage().hits[index]);
                    height = GetImage.getHeight(card.getGetImage().hits[index]);
                    break;
                case "breathing":
//                    System.out.println("name : " + card.getName());
//                    System.out.println("index : " +index);
//                    System.out.println("length : " + card.getGetImage().breathings.length);
                    x = GetImage.getX(card.getGetImage().breathings[index]);
                    y = GetImage.getY(card.getGetImage().breathings[index]);
                    width = GetImage.getWidth(card.getGetImage().breathings[index]);
                    height = GetImage.getHeight(card.getGetImage().breathings[index]);
                    break;
                case "death":
                    x = GetImage.getX(card.getGetImage().deaths[index]);
                    y = GetImage.getY(card.getGetImage().deaths[index]);
                    width = GetImage.getWidth(card.getGetImage().deaths[index]);
                    height = GetImage.getHeight(card.getGetImage().deaths[index]);
                    break;
            }
            imageView.setViewport(new Rectangle2D(x, y,width,height));
            lastIndex = index;
        }
    }

//    public void removeThisAnimation(){
//        ArrayList<SpriteAnimation> copy = new ArrayList<>(Game.getInstance().getMap().getAnimations());
//        for(SpriteAnimation remove : copy){
//            if(remove.equals(this)){
//                Game.getInstance().getMap().getAnimations().remove(this);
//            }
//        }
//    }
}