package entities.StaticObject;

import entities.Entity;
import graphic.Sprite;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;


public abstract class StaticObject extends Entity {
    public StaticObject (double x, double y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }


}
