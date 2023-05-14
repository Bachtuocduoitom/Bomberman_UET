package entities.StaticObject;

import bombermanGame.BombermanGame;
import entities.Entity;
import entities.Items.BombItem;
import entities.Items.FlameItem;
import entities.Items.SpeedItem;
import graphic.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


import java.util.ArrayList;
import java.util.List;

import static bombermanGame.BombermanGame.stillObjects;

public class Brick extends StaticObject {
    private final int MAX_ANIMATE = 7500;
    private int _animate = 0;
    public boolean _destroyed = false;
    protected int _timeToDisappear = 20;

    public Brick(double x, double y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(_destroyed) {
            if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
            if(_timeToDisappear > 0)
                _timeToDisappear--;

        }

    }

    @Override
    public void render (GraphicsContext gc) {
        if(_destroyed) {
            if (_animate < 30)
                img = movingSprite(Sprite.brick_exploded.getFxImage(), Sprite.brick_exploded1.getFxImage(), Sprite.brick_exploded2.getFxImage());
            else {
                List<Entity> tmp = new ArrayList<>();
                for (int i = 0; i < stillObjects.size(); i++) {
                    Entity e = stillObjects.get(i);
                    if (e.getY() != y || e.getX() != x) {
                        tmp.add(e);
                    }
                }
                stillObjects = tmp;
                stillObjects.add(new Grass(x, y, Sprite.grass.getFxImage()));

                if (BombermanGame.map.get((int)y).charAt((int)x) == 'f') {
                    stillObjects.add(new FlameItem(x, y, Sprite.powerup_flames.getFxImage()));
                }
                if (BombermanGame.map.get((int)y).charAt((int)x) == 'b') {
                    stillObjects.add(new BombItem(x, y, Sprite.powerup_bombs.getFxImage()));
                }
                if (BombermanGame.map.get((int)y).charAt((int)x) == 's') {
                    stillObjects.add(new SpeedItem(x, y, Sprite.powerup_speed.getFxImage()));
                }
                if(BombermanGame.map.get((int)y).charAt((int)x) == 'x') {
                    stillObjects.add(new Portal(x, y, Sprite.portal.getFxImage()));
                }


            }
        }
        super.render(gc);
    }

    protected Image movingSprite(Image img, Image img1, Image img2) {
        int calc = _animate % 30;

        if(calc < 10) {
            return img;
        }

        if(calc < 20) {
            return img1;
        }

        return img2;
    }
}
