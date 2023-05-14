package entities.Bomb;

import entities.Entity;
import graphic.Sprite;
import javafx.scene.image.Image;

public class FlameSegment extends Entity {
    protected boolean last;
    public FlameSegment(int x, int y, String direction, boolean last) {
        super(x, y);
        this.last = last;

        switch (direction) {
            case "up":
                if(!last) {
                    img = Sprite.explosion_vertical2.getFxImage();
                } else {
                    img = Sprite.explosion_vertical_top_last2.getFxImage();
                }
                break;
            case "right":
                if(!last) {
                    img = Sprite.explosion_horizontal2.getFxImage();
                } else {
                    img = Sprite.explosion_horizontal_right_last2.getFxImage();
                }
                break;
            case "down":
                if(!last) {
                    img = Sprite.explosion_vertical2.getFxImage();
                } else {
                    img = Sprite.explosion_vertical_down_last2.getFxImage();
                }
                break;
            case "left":
                if(!last) {
                    img = Sprite.explosion_horizontal2.getFxImage();
                } else {
                    img = Sprite.explosion_horizontal_left_last2.getFxImage();
                }
                break;
        }
    }


    @Override
    public void update() {

    }


}
