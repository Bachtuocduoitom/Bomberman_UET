package entities.DynamicObject;

import graphic.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Balloom extends Enemy{
    public Balloom(double x, double y, Image img) {
        super(x, y, img, 0.03);
        _direction = calculateDirection();
    }



    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case "up":
            case "right":
                img = Sprite.movingSprite(Sprite.balloom_right1,
                        Sprite.balloom_right2,
                        Sprite.balloom_right3, _animate, 60).getFxImage();
                break;
            case "down":
            case "left":
                img = Sprite.movingSprite(Sprite.balloom_left1,
                        Sprite.balloom_left2,
                        Sprite.balloom_left3, _animate, 60).getFxImage();
                break;
        }
    }

    @Override
    public String calculateDirection() {
        Random random = new Random();
        int value = random.nextInt(4);
        if (value == 0) {
            return "up";
        } else if (value == 1) {
            return "right";
        } else if (value == 2) {
            return "down";
        } else {
            return "left";
        }
    }
}
